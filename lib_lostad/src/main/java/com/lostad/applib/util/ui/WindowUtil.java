package com.lostad.applib.util.ui;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;


/**
 * Created by Hocean on 2016/12/12.
 */

public class WindowUtil {
    /**
     * 隐藏标题栏
     * @param appActivity
     */
    public static void hidTitle(AppCompatActivity appActivity) {  //主视图单独使用的 标题
        //当系统版本为4.4或者4.4以上时可以使用沉浸式状态栏
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
//        {
//            requestWindowFeature(Window.FEATURE_NO_TITLE); //去标题
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS); //透明状态栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);  //透明导航栏
//            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
//        }

        //自定义标题 Activity
        //requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        //getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.activity_main_title);

        //自定义标题 AppCompatActivity
        ActionBar ab = appActivity.getSupportActionBar();
        ab.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        // ab.setCustomView(R.layout.activity_main_title);
        ab.hide(); //隐藏标题
        //appActivity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);  //全屏

    }

    /**
     * 全屏
     * @param appActivity
     */
    public static void Full(AppCompatActivity appActivity){
        appActivity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);  //全屏
    }

    /**
     * 反射状态栏高度
     * @return
     */
    public static int getStatusBarHeight(AppCompatActivity appActivity) {
        int resourceId = appActivity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        int statusBarHeight = appActivity.getResources().getDimensionPixelSize(resourceId);
        return statusBarHeight;
    }
}
