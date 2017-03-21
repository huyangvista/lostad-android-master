package com.lostad.applib.util.ui;

import android.content.Context;
import android.content.Intent;

import com.lostad.applib.util.ReflectUtil;

import java.util.Map;


/**
 * 窗体工具
 * @author sszvip
 *
 */
public class ContextUtil {
	private static Context appContext; //全局上下文

	/**
	 * 得到当前窗体的名称
	 * @param ctx
	 * @return
     */
	public static String getRunningActivityName(Context ctx) {
		String contextString = ctx.toString();
		return contextString.substring(contextString.lastIndexOf(".") + 1,contextString.indexOf("@"));
	}

	/**
	 * 得到全局上下文
	 * @return
     */
	public static Context getAppContext() {
		if (appContext == null) {
			Class c = ReflectUtil.getClassFromName("android.app.ActivityThread");
			Object staMet = ReflectUtil.invokeStaticMethodAll(c,"currentApplication",new Class[]{},new Object[]{});
			Object oCont = ReflectUtil.invokeMethodAll(staMet,"getApplicationContext",new Class[]{},new Object[]{});
			appContext = (Context)oCont;
		}
		return appContext;
	}

	/**
	 * 设置全局上下文
	 * @param con
     */
	public static void setAppContext(Context con) {
		appContext = con;
	}

	/**
	 * 跳转
	 * @param con
	 * @param cls
     */
	public static void startActivity(Context con, Class<?> cls){
		con.startActivity(new Intent(con,cls));
	}

	/*
* 跳转指定页面
*/
	public static void  toActivty(Class activityClass){
		Intent intent = new Intent(getAppContext(),activityClass);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
		getAppContext().startActivity(intent);
	}

	/**
	 * vive
	 * @param activityClass
	 */
	public void  toActivtyNoClear(Class activityClass){
		Intent intent = new Intent(getAppContext(),activityClass);
		getAppContext().startActivity(intent);
	}
	/**
	 * 跳转传参
	 * @param params
	 * @param activityClass
	 */
	public void toActivty(Map<String,String> params, Class activityClass){
		Intent intent = new Intent(getAppContext(),activityClass);
		for(String key:params.keySet()){
			intent.putExtra(key,params.get(key));
		}
		getAppContext().startActivity(intent);
	}

}
