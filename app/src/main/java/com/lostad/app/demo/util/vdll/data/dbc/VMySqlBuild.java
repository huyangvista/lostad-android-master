package com.lostad.app.demo.util.vdll.data.dbc;//package com.lostad.app.demo.util.vdll.data.dbc;
//
//import java.lang.reflect.Field;
//import java.util.ArrayList;
//import java.util.List;
//
//import com.lostad.app.demo.util.vdll.data.msql.ETag;
//import com.lostad.app.demo.util.vdll.data.msql.VTag;
//import com.lostad.app.demo.util.vdll.tools.VReflect;
//
//public class VMySqlBuild<T>
//{
//	public List<T> vlist;
//	private List<Integer> vlistIndex;
//
//	private Class<?> mcls;
//	//private String[] tableRow;
//
//	VTag vtag;
//	VTag vtagNoKey;
//
//	public VMySqlBuild(Class<?> cls)
//	{
//		this.mcls = cls;
//		vtag = new VTag(mcls);
//		vtagNoKey = new VTag(mcls, ETag.tagNoKey);
//		//反射
//		/*Field[] fields = VReflect.getFields(mcls);
//		int count = fields.length;
//		tableRow = new String[count];
//		
//		for (int i = 0; i < fields.length; i++)
//		{
//			tableRow[i] = fields[i].getName();
//		}*/
//	}
//
//	//-1 失败  0成功 -2已存在
//	public int vcreate()
//	{
//		if (vexist()) return -2;
//		int res = -1;
//
//		String sql = vcreateSql(); //创建语句
//
//		//建表
//		java.sql.Connection conn = null;
//		java.sql.PreparedStatement pst = null;
//		java.sql.ResultSet rs = null;
//		try
//		{
//			conn = DBUtil.getConnection();
//			//System.out.println(sql);
//			pst = DBUtil.getPstmt(conn, sql);
//			res = pst.executeUpdate();
//		}
//		catch (java.sql.SQLException e)
//		{
//			//e.printStackTrace();
//			System.err.println("Vive:创建失败.");
//		}
//		finally
//		{
//			DBUtil.close(rs, pst, conn);
//		}
//		return res;
//	}
//
//	public int vadd(T t)
//	{
//		int res = -1;
//
//		String sql = vaddSql(t);
//
//		//插入
//		java.sql.Connection conn = null;
//		java.sql.PreparedStatement pst = null;
//		java.sql.ResultSet rs = null;
//		try
//		{
//			conn = DBUtil.getConnection();
//			//System.out.println(sql);
//			pst = DBUtil.getPstmt(conn, sql);
//			res = pst.executeUpdate();
//		}
//		catch (java.sql.SQLException e)
//		{
//			e.printStackTrace();
//		}
//		finally
//		{
//			DBUtil.close(rs, pst, conn);
//		}
//		return res;
//	}
//
//	public List<T> vget()
//	{
//		vlist = new ArrayList<>();
//		vlistIndex = new ArrayList<>();
//
//		String sql = vgetSql();
//
//		//建表
//		java.sql.Connection conn = null;
//		java.sql.PreparedStatement pst = null;
//		java.sql.ResultSet rs = null;
//		try
//		{
//			conn = DBUtil.getConnection();
//			//System.out.println(sql);
//			pst = DBUtil.getPstmt(conn, sql);
//			rs = pst.executeQuery();
//			while (rs.next())
//			{
//				@SuppressWarnings("unchecked")
//				T vu = (T) VReflect.newInstance(mcls, new Object[] {});
//				for (int i = 0; i < vtag.count(); i++)
//				{
//					String vs = rs.getString(vtag.get(i).vsname);
//					Field field = VReflect.getFields(mcls, vtag.get(i).vsname);
//					field.set(vu, vs);
//				}
//				vlist.add(vu);
//				vlistIndex.add(rs.getInt("id")); //需要修改成主键
//			}
//		}
//		catch (java.sql.SQLException | IllegalArgumentException | IllegalAccessException e)
//		{
//			e.printStackTrace();
//		}
//		finally
//		{
//			DBUtil.close(rs, pst, conn);
//		}
//		return vlist;
//	}
//
//	public int vdelete()
//	{
//		if (!vexist()) return -2;
//		int res = -1;
//		
//		return res;
//	}
//	//drop
//	public int vdrop()
//	{
//		if (!vexist()) return -2;
//		int res = -1;
//
//		String sql = vdropSql();
//
//		java.sql.Connection conn = null;
//		java.sql.PreparedStatement pst = null;
//		java.sql.ResultSet rs = null;
//		try
//		{
//			conn = DBUtil.getConnection();
//			pst = DBUtil.getPstmt(conn, sql);
//			res = pst.executeUpdate();
//		}
//		catch (java.sql.SQLException e)
//		{
//			e.printStackTrace();
//			System.err.println(e);
//		}
//		finally
//		{
//			DBUtil.close(rs, pst, conn);
//		}
//		return res;
//	}
//
//	public int vupdate(T t)
//	{
//		int res = -1;
//		if (vlist == null) vget();
//
//		//index = VReflect.getFields(t, fieldName)
//		String sql = vupdateSql(t);
//
//		java.sql.Connection conn = null;
//		java.sql.PreparedStatement pst = null;
//		java.sql.ResultSet rs = null;
//		try
//		{
//			conn = DBUtil.getConnection();
//			pst = DBUtil.getPstmt(conn, sql);
//			res = pst.executeUpdate();
//		}
//		catch (java.sql.SQLException e)
//		{
//			e.printStackTrace();
//		}
//		finally
//		{
//			DBUtil.close(rs, pst, conn);
//		}
//		return res;
//	}
//
//	public boolean vexist()
//	{
//		boolean exist = false;
//		//建表
//		java.sql.Connection conn = null;
//		java.sql.PreparedStatement pst = null;
//		java.sql.ResultSet rs = null;
//		try
//		{
//			conn = DBUtil.getConnection();
//			String sql = String.format("SHOW CREATE TABLE `%s`", mcls.getName());
//			pst = DBUtil.getPstmt(conn, sql);
//			rs = pst.executeQuery();
//			if (rs.next())
//			{
//				//System.err.println(rs.getString("TABLE"));
//				//System.err.println(rs.getString("CREATE TABLE"));	
//			}
//			exist = true;
//		}
//		catch (java.sql.SQLException | IllegalArgumentException e)
//		{
//			//e.printStackTrace();
//			exist = false;
//		}
//		finally
//		{
//			DBUtil.close(rs, pst, conn);
//		}
//		return exist;
//	}
//
//	
//	public String vcreateSql()
//	{
//
//		StringBuilder sb = new StringBuilder();
//		sb.append("CREATE TABLE ");
//		sb.append("`").append(mcls.getName()).append("`");
//		sb.append(" ( ");
//		String vspkai = null;
//		for (int i = 0; i < vtag.count(); i++)
//		{ //                      sb.append();    sb.append("`").append(vspkai).append("`");
//				//if (vtag.get(i).itag == null) continue;				
//			if (vtag.get(i).itag.vsql().equals("PRIMARY KEY"))
//			{
//				vspkai = vtag.get(i).vsname;
//				sb.append("`").append(vspkai).append("`");
//				sb.append(" INT NOT NULL AUTO_INCREMENT");
//				continue;
//			}
//			sb.append(", ");
//			sb.append("`").append(vtag.get(i).vsname).append("`");
//			sb.append(" ");
//			sb.append(vtag.get(i).itag.value());
//		}
//		if (vspkai != null) sb.append(", PRIMARY KEY (`").append((vspkai)).append("`)");
//		sb.append(")");
//		return sb.toString();
//	}
//
//	public String vaddSql(T t)
//	{
//		//反射
//		//Field[] fields = VReflect.getFields(t);
//		//int count = fields.length;
//		int count = vtagNoKey.count();
//		String[] tableRow = new String[count];
//		String[] tableValue = new String[count];
//
//		for (int i = 0; i < count; i++)
//		{
//			try
//			{
//				tableRow[i] = vtagNoKey.get(i).vsname;
//				Field field = VReflect.getFields(t, tableRow[i]);
//				tableValue[i] = (String) field.get(t);
//			}
//			catch (IllegalArgumentException | IllegalAccessException e)
//			{
//				// TODO Auto-generated catch block
//				//e.printStackTrace();
//				tableValue[i] = "";
//			}
//		}
//
//		StringBuilder sbk = new StringBuilder();
//		StringBuilder sbv = new StringBuilder();
//		for (int i = 0; i < tableRow.length; i++)
//		{
//			sbk.append(tableRow[i]);
//			sbk.append(",");
//
//			sbv.append("'");
//			sbv.append(tableValue[i]);
//			sbv.append("',");
//		}
//		sbv.delete(sbv.length() - 1, sbv.length());
//		sbk.delete(sbk.length() - 1, sbk.length());
//
//		String sql = String.format("INSERT INTO `%s` (%s) VALUES (%s);", t.getClass().getName(), sbk, sbv);
//
//		return sql;
//	}
//
//	public String vgetSql()
//	{
//		String sql = String.format("SELECT * FROM `%s`;", mcls.getName());
//		return sql;
//	}
//
//	public String vdropSql()
//	{
//		String sql = String.format("drop table if exists `%s`", mcls.getName());
//		return sql;
//	}
//
//	public String vupdateSql(T t)
//	{
//		int index = -1;
//		for (int i = 0; i < vlist.size(); i++)
//		{
//			if (vlist.get(i).hashCode() == t.hashCode())
//			{
//				index = i;
//				continue;
//			}
//		}
//		index = vlistIndex.get(index); //修改对象的  id
//
//		StringBuilder sb = new StringBuilder();
//		for (int i = 0; i < vtag.count(); i++)
//		{
//			try
//			{
//				Field field = VReflect.getFields(mcls, vtag.get(i).vsname);
//				sb.append("`");
//				sb.append(vtag.get(i).vsname);
//				sb.append("`='");
//				sb.append(field.get(t));
//				sb.append("',");
//			}
//			catch (IllegalArgumentException | IllegalAccessException e)
//			{
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//		}
//		sb.delete(sb.length() - 1, sb.length());
//
//		String sql = String.format("UPDATE `%s` SET %s WHERE `id`='%s';", mcls.getName(), sb.toString(), index);
//
//		return sql;
//	}
//}
//
//
///*// 数据库测试
//	private static void mysql()
//	{
//		java.sql.Connection conn = null;
//		java.sql.PreparedStatement pst = null;
//		java.sql.ResultSet rs = null;
//
//		try
//		{
//			conn = DBUtil.getConnection();
//
//			/*pst = DBUtil.getPstmt(conn, "INSERT INTO tabs(vname) VALUES (?)");
//			pst.setString(1, "000000000");
//			int count = pst.executeUpdate();
//			System.out.println(count);
//			
//			DBUtil.close(pst);
//			pst = DBUtil.getPstmt(conn, "SELECT * FROM tabs t");
//			//pst.setString(1, "*");
//			rs = pst.executeQuery();
//			while (rs.next())
//			{
//				String name = rs.getString("t.id");
//				System.out.println(name);
//			}
//			这有个结束 注释 
//
//			//VCarUser.class.getName();
//			StringBuilder sb = new StringBuilder();
//			for (int i = 0; i < tableRow.length; i++)
//			{
//				sb.append(",");
//				sb.append(tableRow[i]);
//				sb.append(" VARCHAR(45)");
//			}
//			sb.append(" ,PRIMARY KEY (`id`)");
//			System.out.println(sb);
//
//			conn = DBUtil.getConnection();
//			//pst = DBUtil.getPstmt(conn, "CREATE TABLE `tabbbb` (  `idnew_table` INT NOT NULL AUTO_INCREMENT,`new_tablecol` VARCHAR(45) NULL,PRIMARY KEY (`idnew_table`))");
//			String sqls = "CREATE TABLE `tabbbb` (  `id` INT NOT NULL AUTO_INCREMENT " + sb.toString() + ")";
//			System.out.println(sqls);
//			pst = DBUtil.getPstmt(conn, sqls);
//
//			pst.executeUpdate();
//			DBUtil.close(pst);
//
//		}
//		catch (java.sql.SQLException e)
//		{
//			e.printStackTrace();
//		}
//		finally
//		{
//			DBUtil.close(rs, pst, conn);
//		}
//	}
//	*/
//
///*  //使用
//VMySql<User> vmysql = new VMySql<>(User.class);
//System.out.println(vmysql.vcreate());
//User user = new User();  user.setU_id("0123");user.setU_name("456");user.setU_address("789");
//System.out.println(vmysql.vadd(user));		
//System.out.println(vmysql.vadd(new User()));		
//List<User>  vList = vmysql.vget();		
//User u = (User) vList.get(0); u.setU_name("96969696"); u.setPsword("96969696"); u.setEmail("96969696");	
//System.out.println(vmysql.vupdate(u));		
////System.out.println(vmysql.vdelete());
//System.out.println(vmysql.vexist());
//*/