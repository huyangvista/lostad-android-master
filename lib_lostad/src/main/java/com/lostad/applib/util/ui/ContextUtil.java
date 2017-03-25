package com.lostad.applib.util.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lostad.applib.util.ReflectUtil;

import java.io.Serializable;
import java.util.Map;


/**
 * 窗体工具
 * @author sszvip
 *
 */
public class ContextUtil {
	private static Context appContext; //全局上下文

	public void setLayoutInflater(LayoutInflater layoutInflater) {
		this.layoutInflater = layoutInflater;
	}

	private static LayoutInflater layoutInflater;

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
	public static void toActivtyClear(Class activityClass){
		toActivtyClear(getAppContext(),activityClass);
	}
	public static void toActivtyClear(Context context, Class activityClass){
		Intent intent = new Intent(context,activityClass);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}
	public static void toActivtyClear(Context context, Map<String,? extends Serializable> params, Class activityClass){
		Intent intent = new Intent(context,activityClass);
		for(String key:params.keySet()){
			intent.putExtra(key,params.get(key));
		}
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}
	public static void toActivtyClear(Map<String,? extends Serializable> params, Class activityClass){
		toActivtyClear(getAppContext(),params,activityClass);
	}

	/**
	 * vive
	 * @param activityClass
	 */
	public static void toActivty(Class activityClass){
		toActivty(getAppContext(),activityClass);
	}
	public static void toActivty(Context context, Class activityClass){
		Intent intent = new Intent(context,activityClass);
		context.startActivity(intent);
	}
	public static void toActivty(Context context, Map<String,? extends Serializable> params, Class activityClass){
		Intent intent = new Intent(context,activityClass);
		for(String key:params.keySet()){
			intent.putExtra(key,params.get(key));
		}
		context.startActivity(intent);
	}
	public static void toActivty(Map<String,? extends Serializable> params, Class activityClass){
		toActivtyClear(getAppContext(),params,activityClass);
	}

	public static void toActivtyResult(Activity act, Class activityClass) {
		Intent intent = new Intent(act, activityClass);
		act.startActivityForResult(intent, 0);//跳转并发送请求码
	}
	public static void toActivtyResult(Fragment fra, Class activityClass) {
		Intent intent = new Intent(fra.getContext(), activityClass);
		fra.startActivityForResult(intent, 0);//跳转并发送请求码
	}

	public static void toActivtyResult(Activity act, Map<String, ? extends Serializable> params, Class activityClass) {
		Intent intent = new Intent(act, activityClass);
		for (String key : params.keySet()) {
			intent.putExtra(key,  params.get(key));
		}
		act.startActivityForResult(intent, 0);//跳转并发送请求码
	}
	public static void toActivtyResult(Fragment fra, Map<String, ? extends Serializable> params, Class activityClass) {
		Intent intent = new Intent(fra.getContext(), activityClass);
		for (String key : params.keySet()) {
			intent.putExtra(key,  params.get(key));
		}
		fra.startActivityForResult(intent, 0);//跳转并发送请求码
	}

	public static LayoutInflater getLayoutInflater(){
		if(layoutInflater == null){
			layoutInflater = LayoutInflater.from(getAppContext());
		}
		return layoutInflater;
	}
	public static LayoutInflater getLayoutInflater(Context context){
		return LayoutInflater.from(context);
	}

	public static View loadLayout(@LayoutRes int resource, @Nullable ViewGroup root, boolean attachToRoot){
		return getLayoutInflater().inflate(resource,root,attachToRoot);
	}
	public static View loadLayout(@LayoutRes int resource, @Nullable ViewGroup root){
		return getLayoutInflater().inflate(resource,root);
	}
	public static View loadLayout(@LayoutRes int resource){
		return getLayoutInflater().inflate(resource,null);
	}

	public static Bundle getActivtyExtra(Activity act){
		return act.getIntent().getExtras();
	}
	public static Bundle getBundle(Activity act){
		return getActivtyExtra(act);
	}


}
