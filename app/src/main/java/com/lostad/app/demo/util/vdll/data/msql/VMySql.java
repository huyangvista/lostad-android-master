package com.lostad.app.demo.util.vdll.data.msql;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 数据库 过程类
 * @author Hocean
 *
 */
public class VMySql
{
	private Connection conn = null; //连接
	private PreparedStatement pst = null; //预处理语句
	private ResultSet rs = null; //游标
	
	//静态块
	public static String databaseName = "vdata";
	public static String username = "root";
	public static String password = "hoceanvista";
	static //加载驱动
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		InputStream is = VMySql.class.getClassLoader().getResourceAsStream("db.properties");
		Properties prop = new Properties();
		try
		{
			prop.load(is);

			databaseName = prop.getProperty("DATABASENAME");
			username = prop.getProperty("USERNAME");
			password = prop.getProperty("PASSWORD");
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//0
	public VMySql()
	{
		// TODO Auto-generated constructor stub

	}
	//0 -> 1
	public Connection vopen()
	{		
		return vopen(databaseName,username,password);
	}
	//0 -> 1
	public Connection vopen(String databaseName,  String username, String password)
	{
		// TODO Auto-generated method stub
		String url = "jdbc:mysql://localhost:3306/" + databaseName + "?useUnicode=true&characterEncoding=utf8";
		try
		{
			conn = DriverManager.getConnection(url, username, password);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return conn;
	}
	//1 -> 2  主动设置参数语句用 ? 设置好参数在 vexe()
	public PreparedStatement vsql(String sql)
	{
		try
		{
			if (pst != null)
			{
				pst.close();
				pst = null;
			}
			pst = conn.prepareStatement(sql);
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pst;
	}
	//2 -> 3.0
	public ResultSet vexeQ()
	{
		try
		{
			rs = pst.executeQuery();
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			//String vse = pst.toString();
			//if(vse.indexOf("SHOW CREATE5") < 0)
			{
				System.err.println("vexeQ => " + pst.toString() + "   System => " + e.toString());
			}
		}
		return rs;
	}
	//2 -> 3.1
	public int vexeU()
	{
		int viu = -1;
		try
		{
			viu = pst.executeUpdate();
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.err.println("vexeU => " + pst.toString() + "   System => " + e.toString());
		}
		return viu;
	}
	//2 -> 3.2
	public boolean vexe()
	{
		boolean vb = false;
		try
		{
			vb = pst.execute();
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();		
			//System.err.println("vexe => " + pst.toString() + "   System => " + e.toString());
		}
		return vb;
	}
	//4
	public void vclose()
	{
		try
		{
			if (rs != null)
			{
				rs.close();
			}
			if (pst != null)
			{
				pst.close();
			}
			if (conn != null)
			{
				conn.close();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	//3.0 -> 3.0.0     查询字段
	public List<Map<String, Object>> vgetParms(String... parms)
	{
		List<Map<String, Object>> vlist = new ArrayList<>();
		try
		{
			while (rs.next())
			{
				Map<String, Object> map = new HashMap<>();
				for (int i = 0; i < parms.length; i++)
				{	
					String vsp = parms[i];
					Object vo = rs.getObject(vsp);
					map.put(vsp, vo);
				}
				vlist.add(map);
			}
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return vlist;
	}
	
	//0, 1, 3.0, 4 注意关闭
	public ResultSet vexeQBuild(String sql)
	{
		vopen();
		vsql(sql);
		vexeQ();
		return rs;
	}
	//0, 1, 3.0, 4
	public List<Map<String, Object>> vexeQBuild(String sql, String... parms)
	{
		vopen();
		vsql(sql);
		vexeQ();		
		List<Map<String, Object>> vlist = vgetParms(parms);
		vclose();
		return vlist;
	}
	//0, 1, 3.0, 4
	public int vexeUBuild(String sql)
	{
		vopen();
		vsql(sql);
		int vi = vexeU();
		vclose();
		return vi;
	}

	//设置 是否自动提交  默认为 true
	public void vsetAutoCommit(boolean vautoCommit)
	{
		try
		{
			conn.setAutoCommit(vautoCommit);
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}
	//提交数据库修改
	public void vcommit()
	{
		try
		{
			conn.commit();
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//属性
	public Connection getConn()
	{
		return conn;
	}
	public void setConn(Connection conn)
	{
		this.conn = conn;
	}
	public PreparedStatement getPst()
	{
		return pst;
	}
	public void setPst(PreparedStatement pst)
	{
		this.pst = pst;
	}
	public ResultSet getRs()
	{
		return rs;
	}
	public void setRs(ResultSet rs)
	{
		this.rs = rs;
	}
	public static String getDatabaseName()
	{
		return databaseName;
	}
	public static void setDatabaseName(String databaseName)
	{
		VMySql.databaseName = databaseName;
	}
	public static String getUsername()
	{
		return username;
	}
	public static void setUsername(String username)
	{
		VMySql.username = username;
	}
	public static String getPassword()
	{
		return password;
	}
	public static void setPassword(String password)
	{
		VMySql.password = password;
	}

}

/*   //使用方法
	public void mysql()
	{
		VMySqlBuild<User> vmysqlbuild = new VMySqlBuild<>(User.class); //实例 SQL 语句
		VMySql vmsql = new VMySql(); //实例 SQL 连接
		vmsql.vopen(); //开启数据库
		
		vmsql.vsql(vmysqlbuild.vcreateSql());
		vmsql.vexeU(); //新建表
		
		User user = new User();user.u_name = "123";user.u_id = "456";user.u_address = "789";user.id = "1"; //测试 用户
		vmsql.vsql(vmysqlbuild.vaddSql(user)); //设置SQL语句   添加用户
		vmsql.vexeU(); //更新

		vmsql.vsql(vmysqlbuild.vgetSql()); //设置SQL语句   获取所有信息
		vmsql.vexeQ(); //查询
		
		List<Map<String, Object>> vlist = vmsql.vgetParms("u_name", "u_id"); //获取游标信息
		
		vmsql.vsql(vmysqlbuild.vdropSql()); //设置SQL语句   删除表
		vmsql.vexeU(); //删除
		vmsql.vclose(); //关闭

		System.out.println(vlist.get(0).get("u_name"));
		System.out.println(vlist.get(0).get("u_id"));
	}

*/


/*  //测试数据库 源代码
   private static void MySql()
	{		
		try
		{
			java.sql.Connection con = null; //数据库实例
			Class.forName("com.mysql.jdbc.Driver").newInstance(); // 反射驱动
			con = java.sql.DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/vdata", "root", "hoceanvista"); //返回实例MYSQL

			java.sql.Statement stmt; //操作对象
			stmt = con.createStatement();

			//插入
			stmt.executeUpdate("INSERT INTO vtab00 (vid) VALUES ( '123456')");
			java.sql.ResultSet res = stmt.executeQuery("select * from vtab00");  
			int ret_id;
			while (res.next())
			{
				ret_id = res.getInt(1);
				System.out.print(ret_id);
			}

		}
		catch (Exception e)
		{
			System.out.print("MYSQL ERROR:" + e.getMessage());
		}
	}
 */


