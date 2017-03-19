package com.lostad.app.demo.util.vdll.data.msql;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.lostad.app.demo.util.vdll.data.msql.VTag.VTagRow;
import com.lostad.app.demo.util.vdll.tools.VReflect;

/**
 * 数据库  对象类
 * @author Hocean
 * 标记--   后期完善
 * @param <T>
 */
public class VMySqlBuild<T> extends VMySql
{
	public List<T> vlist; //输出列表
	//private List<Integer> vlistIndex;

	private Class<T> mcls;  //数据表类型
	//private String[] tableRow;

	VTag vtag; //标记集合
	//VTag vtagNoKey;
	//VTag vtagKey;

	//构造
	public VMySqlBuild(Class<T> cls)
	{
		super();
		this.mcls = cls;
		vtag = new VTag(mcls);
		//vtagNoKey = new VTag(mcls, ETag.tagNoKey);
		//vtagKey = new VTag(mcls, ETag.tagKey);
		//反射
		/*Field[] fields = VReflect.getFields(mcls);
		int count = fields.length;
		tableRow = new String[count];
		
		for (int i = 0; i < fields.length; i++)
		{
			tableRow[i] = fields[i].getName();
		}*/
		
		
	}

	//构造
	public VMySqlBuild(Class<T> cls, VMySql vmsql)
	{
		this(cls);
		super.setConn(vmsql.getConn());
		super.setPst(vmsql.getPst());
		super.setRs(vmsql.getRs());
		
	}
		
	//-1 失败  0成功 -2已存在
	public int vcreate()
	{
		if (vexist()) return -2;

		int res = -1;
		String sql = vcreateSql(); //创建语句
		vsql(sql);
		res = vexeU();

		return res;
	}
	
	//-1 失败  1成功 -2已存在
	public int vcreateDB()
	{
		if (vexistDB()) return -2;

		int res = -1;
		String sql = "CREATE DATABASE " + databaseName + " DEFAULT CHARSET=utf8;"; //创建语句
		vsql(sql);
		res = vexeU();

		return res;
	}

	//添加一个用户
	public int vadd(T t)
	{
		int res = -1;
		String sql = vaddSql(t);
		vsql(sql);
		res = vexeU();

		return res;
	}

	//获得所有用户
	public List<T> vget()
	{
		vlist = new ArrayList<>();
		//vlistIndex = new ArrayList<>();

		String sql = vgetSql();
		vsql(sql);
		ResultSet rs = vexeQ();

		try
		{
			while (rs.next())
			{
				@SuppressWarnings("unchecked")
				T vu = (T) VReflect.newInstance(mcls, new Object[] {});
				for (int i = 0; i < vtag.valRow.size(); i++)
				{
					String vs = rs.getString(vtag.valRow.get(i).vsname);
					Field field = VReflect.getFields(mcls, vtag.valRow.get(i).vsname);
					field.set(vu, vs);
				}
				vlist.add(vu);
				//vlistIndex.add(rs.getInt("id")); //需要修改成主键
			}
		}
		catch (IllegalArgumentException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IllegalAccessException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return vlist;
	}

	//按主键 获取并填充用户
	public T vget(T t)
	{
		String sql = vgetSql(t);
		vsql(sql);
		ResultSet rs = vexeQ();
		
		try
		{
			if (rs.next())
			{
				@SuppressWarnings("unchecked")
				T vu = (T) VReflect.newInstance(mcls, new Object[] {});
				for (int i = 0; i < vtag.valRow.size(); i++)
				{
					String vs = rs.getString(vtag.valRow.get(i).vsname);
					Field field = VReflect.getFields(mcls, vtag.valRow.get(i).vsname);
					field.set(vu, vs);
				}
				//vlist.add(vu);
				//vlistIndex.add(rs.getInt("id")); //需要修改成主键				
				return vu;
			}
		}		
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		return null;
	}
	
	//按主键 删除
	public int vdelete(T t)
	{
		int res = -1;
		String sql = vdeleteSql(t);
		vsql(sql);
		res = vexeU();
		return res;
	}

	//删除数据表
	public int vdrop()
	{
		if (!vexist()) return -2;
		int res = -1;

		String sql = vdropSql();
		vsql(sql);
		res = vexeU();
		return res;
	}

	//按主键 更新数据
	public int vupdate(T t)
	{
		int res = -1;
		//if (vlist == null) vget();

		//index = VReflect.getFields(t, fieldName)
		String sql = vupdateSql(t);
		vsql(sql);
		res = vexeU();
		return res;
	}

	//是否存在数据表
	public boolean vexist()
	{
		//boolean exist = false;

		String sql = vexistSql();
		vsql(sql);
		/*try
		{
			ResultSet rs = vexeQ();
			if (rs.next())
			{

			}
			exist = true;
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		return exist;*/
		return vexe();
	}

	//是否存在数据库
	public boolean vexistDB()
	{
		//boolean exist = false;

		String sql = vexistSqlDB();
		vsql(sql);
		/*try
		{
			ResultSet rs = vexeQ();
			if (rs.next())
			{

			}
			exist = true;
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		return exist;*/		
		return vexe();

	}
	
	//-------------------------------------------------      合成语句          --------------
	//创建数据表语句   //ENGINE=InnoDB DEFAULT CHARSET=utf8;
	public String vcreateSql()
	{

		StringBuilder sb = new StringBuilder();
		sb.append("CREATE TABLE ");
		sb.append("`").append(mcls.getName()).append("`");
		sb.append(" ( ");
		//String vspkai = null;

		for (int i = 0; i < vtag.valRowKey.size(); i++)
		{ //                      sb.append();    sb.append("`").append(vspkai).append("`");
				//if (vtag.get(i).itag == null) continue;	
			sb.append("`").append(vtag.valRowKey.get(i).vsname).append("` ");
			sb.append(vtag.valRowKey.get(i).aitag.value());			
			if (vtag.valRowKey.get(i).aitag.vsqlValue().equals(VSTag._PK + VSTag._AI))
			{				
				sb.append(" NOT NULL AUTO_INCREMENT");
			}
			else if (vtag.valRowKey.get(i).aitag.vsqlValue().equals(VSTag._PK ))
			{			
				sb.append(" NOT NULL");
			}
			sb.append(", ");
		}

		for (int i = 0; i < vtag.valRowNoKey.size(); i++)
		{ //                      sb.append();    sb.append("`").append(vspkai).append("`");
				//if (vtag.get(i).itag == null) continue;			
			sb.append("`").append( vtag.valRowNoKey.get(i).vsname).append("` ");
			sb.append( vtag.valRowNoKey.get(i).aitag.value());
			sb.append(", ");
		}
		sb.delete(sb.length() - 1, sb.length());
		if (vtag.valRowKey.size() > 0)
		{
			sb.append(" PRIMARY KEY (");
			for (int i = 0; i < vtag.valRowKey.size(); i++)
			{
				sb.append("`").append(vtag.valRowKey.get(i).vsname).append("`,");
			}
			sb.delete(sb.length() - 1, sb.length());
			sb.append(")");			
		}		
		sb.append(")");
		return sb.toString();
	}
	//添加用户语句
	public String vaddSql(T t)
	{
		//反射
		//Field[] fields = VReflect.getFields(t);
		//int count = fields.length;
		int count = vtag.valRowNoAi.size();
		String[] tableRow = new String[count];
		String[] tableValue = new String[count];

		for (int i = 0; i < count; i++)
		{
			try
			{
				tableRow[i] = vtag.valRowNoAi.get(i).vsname;
				Field field = VReflect.getFields(t, tableRow[i]);
				tableValue[i] = (String) field.get(t);
			}
			catch (IllegalArgumentException | IllegalAccessException e)
			{
				// TODO Auto-generated catch block
				//e.printStackTrace();
				tableValue[i] = "";
			}
		}

		StringBuilder sbk = new StringBuilder();
		StringBuilder sbv = new StringBuilder();
		for (int i = 0; i < tableRow.length; i++)
		{
			sbk.append(tableRow[i]);
			sbk.append(",");

			sbv.append("'");
			sbv.append(tableValue[i]);
			sbv.append("',");
		}
		sbv.delete(sbv.length() - 1, sbv.length());
		sbk.delete(sbk.length() - 1, sbk.length());

		String sql = String.format("INSERT INTO `%s` (%s) VALUES (%s);", mcls.getName(), sbk, sbv);

		return sql;
	}
	//获取全部用户语句
	public String vgetSql()
	{
		String sql = String.format("SELECT * FROM `%s`;", mcls.getName());
		return sql;
	}
	//--获取用户语句
	public String vgetSql(T t)
	{
		VTagRow vtr = vtag.valRowkeyBase.get(0);
		Object vo = VReflect.getFieldsValue(t, vtr.vsname);
		String sql = String.format("SELECT * FROM `%s` WHERE `%s`='%s';", mcls.getName(), vtr.vsname, vo);
		return sql;
	}
	//--删除用户语句
	public String vdeleteSql(T t)
	{
		VTagRow vtr = vtag.valRowkeyBase.get(0);
		Object vo = VReflect.getFieldsValue(t, vtr.vsname);
		String sql = String.format("DELETE FROM `%s` WHERE (`%s`='%s');", mcls.getName(), vtr.vsname, vo);
		return sql;
	}
	//删除数据表语句
	public String vdropSql()
	{
		String sql = String.format("drop table if exists `%s`", mcls.getName());
		return sql;
	}
	//--更新数据表语句
	public String vupdateSql(T t)
	{
		VTagRow vtr = vtag.valRowkeyBase.get(0);
		Object vo = VReflect.getFieldsValue(t, vtr.vsname);

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < vtag.valRowNoAi.size(); i++)
		{			
			try
			{
				VTagRow vtrs = vtag.valRowNoAi.get(i);
				Field field = VReflect.getFields(mcls, vtrs.vsname);
				sb.append("`");
				sb.append(vtrs.vsname);
				sb.append("`='");
				sb.append(field.get(t));
				sb.append("',");
			}
			catch (IllegalArgumentException | IllegalAccessException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		sb.delete(sb.length() - 1, sb.length());

		String sql = String.format("UPDATE `%s` SET %s WHERE `%s`='%s';", mcls.getName(), sb.toString(), vtr.vsname, vo);

		return sql;
	}
	//是否存在数据表
	public String vexistSql()
	{
		String sql = String.format("SHOW CREATE TABLE `%s`", mcls.getName());
		return sql;
	}
	//是否存在数据库
	public String vexistSqlDB()
	{
		String sql = String.format("SHOW CREATE DATABASE  `%s`", databaseName);
		return sql;
	}
	//-------------------------------------------------      合成语句          --------------

	
}


/*  //使用
	public void mysqlBuild()
	{
		VMySqlBuild<User> vmysql = new VMySqlBuild<>(User.class);
		vmysql.vopen();		
		User user = new User();user.u_name = "123";user.u_id = "456";user.u_address = "789";user.id = "1";
		System.out.println(    vmysql.vcreate()     		);
		System.out.println(    vmysql.vadd(user)     		);
		System.out.println(    vmysql.vget(user).u_id       );  user.u_id = "666";
		System.out.println(    vmysql.vupdate(user)         );  //List<User>  vList = vmysql.vget();			
		System.out.println(    vmysql.vget().get(0).u_id    );
		System.out.println(    vmysql.vdelete(user)         );
		System.out.println(    vmysql.vdrop()               );
		System.out.println(    vmysql.vexist()              );		
		vmysql.vclose();
	}
*/








/*// 数据库测试
	private static void mysql()
	{
		java.sql.Connection conn = null;
		java.sql.PreparedStatement pst = null;
		java.sql.ResultSet rs = null;

		try
		{
			conn = DBUtil.getConnection();

			/*pst = DBUtil.getPstmt(conn, "INSERT INTO tabs(vname) VALUES (?)");
			pst.setString(1, "000000000");
			int count = pst.executeUpdate();
			System.out.println(count);
			
			DBUtil.close(pst);
			pst = DBUtil.getPstmt(conn, "SELECT * FROM tabs t");
			//pst.setString(1, "*");
			rs = pst.executeQuery();
			while (rs.next())
			{
				String name = rs.getString("t.id");
				System.out.println(name);
			}
			这有个结束 注释 

			//VCarUser.class.getName();
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < tableRow.length; i++)
			{
				sb.append(",");
				sb.append(tableRow[i]);
				sb.append(" VARCHAR(45)");
			}
			sb.append(" ,PRIMARY KEY (`id`)");
			System.out.println(sb);

			conn = DBUtil.getConnection();
			//pst = DBUtil.getPstmt(conn, "CREATE TABLE `tabbbb` (  `idnew_table` INT NOT NULL AUTO_INCREMENT,`new_tablecol` VARCHAR(45) NULL,PRIMARY KEY (`idnew_table`))");
			String sqls = "CREATE TABLE `tabbbb` (  `id` INT NOT NULL AUTO_INCREMENT " + sb.toString() + ")";
			System.out.println(sqls);
			pst = DBUtil.getPstmt(conn, sqls);

			pst.executeUpdate();
			DBUtil.close(pst);

		}
		catch (java.sql.SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			DBUtil.close(rs, pst, conn);
		}
	}
	*/

