package com.lostad.app.base.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 窗口工具类,提供可重用的窗口
 * @author sszvip
 *
 */
public class ContextUtil {

	public static String getRunningActivityName(Context ctx) {
		String contextString = ctx.toString();
		return contextString.substring(contextString.lastIndexOf(".") + 1,contextString.indexOf("@"));
	}


	public static View initView(Context context, int rid, ViewGroup root, boolean att){
		LayoutInflater in = LayoutInflater.from(context);
		return in.inflate(rid,root,att);
	}
	public static View initView(Context context, int rid, ViewGroup root){
		return initView(context,rid,root,false);
	}
	public static View initView(Context context, int rid){
		return initView(context,rid,null);
	}

	
}
