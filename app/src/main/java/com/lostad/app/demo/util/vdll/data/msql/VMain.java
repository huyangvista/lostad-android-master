package com.lostad.app.demo.util.vdll.data.msql;

import java.util.List;
import java.util.Map;

//演示类
public class VMain
{

	public static void main(String[] args)
	{
		new VMain();
	}

	public VMain()
	{
		mysqlBuild();
		mysql();		
		
	}
	
	
	public void mysqlBuild()
	{
		VMySqlBuild<User> vmysql = new VMySqlBuild<>(User.class);
		vmysql.vopen();		
		User user = new User();user.u_name = "123";user.u_id = "456";user.u_address = "789";user.id = "1";
		System.out.println(    vmysql.vcreate()     		);		
		System.out.println(    vmysql.vadd(user)     		);	//user.u_name = "888";  //找不到用户返回NULL		
		System.out.println(    vmysql.vget(user).u_id       );  user.u_id = "666";
		System.out.println(    vmysql.vupdate(user)         );  //List<User>  vList = vmysql.vget();			
		System.out.println(    vmysql.vget().get(0).u_id    );
		System.out.println(    vmysql.vdelete(user)         );
		System.out.println(    vmysql.vdrop()               );
		System.out.println(    vmysql.vexist()              );
		vmysql.vclose();
	}
	public void mysql()
	{
		VMySqlBuild<User> vmysqlbuild = new VMySqlBuild<>(User.class); //实例 SQL 语句
		VMySql vmsql = new VMySql(); //实例 SQL 连接
		vmsql.vopen(); //开启数据库
		
		vmsql.vsql(vmysqlbuild.vcreateSql());
		vmsql.vexeU(); //新建表
		
		User user = new User();user.u_name = "123";user.u_id = "456";user.u_address = "789";
		vmsql.vsql(vmysqlbuild.vaddSql(user)); //设置SQL语句   添加用户
		vmsql.vexeU(); //添加
		
		user.id = "1"; user.u_id = "456456";user.u_address = "update u_address"; //测试 用户
		vmsql.vsql(vmysqlbuild.vupdateSql(user)); //设置SQL语句   添加用户
		vmsql.vexeU(); //更新

		vmsql.vsql(vmysqlbuild.vgetSql()); //设置SQL语句   获取所有信息
		vmsql.vexeQ(); //查询
		
		List<Map<String, Object>> vlist = vmsql.vgetParms("u_name", "u_id"); //获取游标信息
		
		vmsql.vsql(vmysqlbuild.vdropSql()); //设置SQL语句   删除表
		vmsql.vexeU(); //删除
		vmsql.vclose(); //关闭

		System.out.println(vlist.get(0).get("u_id"));
		System.out.println(vlist.get(0).get("u_name"));
	}
	
	
}

/*
public void mysqlBuild()
{
	VMySqlBuild<User> vmysql = new VMySqlBuild<>(User.class);
	vmysql.vopen();		
	User user = new User();user.u_name = "123";user.u_id = "456";user.u_address = "789";user.id = "1";
	System.out.println(    vmysql.vcreate()     		);
	System.out.println(    vmysql.vadd(user)     		);	//user.u_name = "888";  //找不到用户返回NULL
	System.out.println(    vmysql.vget(user).u_id       );  user.u_id = "666";
	System.out.println(    vmysql.vupdate(user)         );  //List<User>  vList = vmysql.vget();			
	System.out.println(    vmysql.vget().get(0).u_id    );
	System.out.println(    vmysql.vdelete(user)         );
	System.out.println(    vmysql.vdrop()               );
	System.out.println(    vmysql.vexist()              );
	vmysql.vclose();
}
public void mysql()
{
	VMySqlBuild<User> vmysqlbuild = new VMySqlBuild<>(User.class); //实例 SQL 语句
	VMySql vmsql = new VMySql(); //实例 SQL 连接
	vmsql.vopen(); //开启数据库
	
	vmsql.vsql(vmysqlbuild.vcreateSql());
	vmsql.vexeU(); //新建表
	
	User user = new User();user.u_name = "123";user.u_id = "456";user.u_address = "789";
	vmsql.vsql(vmysqlbuild.vaddSql(user)); //设置SQL语句   添加用户
	vmsql.vexeU(); //添加
	
	user.id = "1"; user.u_id = "456456";user.u_address = "update u_address"; //测试 用户
	vmsql.vsql(vmysqlbuild.vupdateSql(user)); //设置SQL语句   添加用户
	vmsql.vexeU(); //更新

	vmsql.vsql(vmysqlbuild.vgetSql()); //设置SQL语句   获取所有信息
	vmsql.vexeQ(); //查询
	
	List<Map<String, Object>> vlist = vmsql.vgetParms("u_name", "u_id"); //获取游标信息
	
	vmsql.vsql(vmysqlbuild.vdropSql()); //设置SQL语句   删除表
	vmsql.vexeU(); //删除
	vmsql.vclose(); //关闭

	System.out.println(vlist.get(0).get("u_id"));
	System.out.println(vlist.get(0).get("u_name"));
}
*/

/*
SHOW TABLE STATUS FROM	vdata ;
SHOW TABLE STATUS FROM	vdata WHERE `Name`= 'vive.unity.vuser';
SHOW DATABASES WHERE `Database`='vdata';
*/