package com.lostad.applib.util.ui;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Point;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.lostad.applib.R;
import com.lostad.applib.core.MyCallback;
import com.lostad.applib.util.Validator;
import com.lostad.applib.view.widget.CustomProgressDialog;

import org.w3c.dom.Text;

import java.util.List;

import static com.lostad.applib.util.DialogUtil.showAlert;


/**
 * 窗口工具类,提供可重用的窗口
 * @author     sszvip@qq.com
 * @copyright  weibo.com/lostbottle
 */
public class DialogUtil {

    private static CustomProgressDialog progressDialogMy;
    private static ProgressDialog progDialog;
    private static Toast mToast;//为了实现疯狂模式下toast延时消失的问题
    private static Toast mToastCust ;

    /**
     * 显示等待条
     * @param ctx
     * @param msg
     */
    public static void showProgress(Activity ctx,String msg) {
        if (progDialog == null){
            progDialog = new ProgressDialog(ctx);
        }else{
            if(progDialog.isShowing()){
                return;
            }
        }
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setIndeterminate(false);
        //progDialog.setCancelable(false);//按返回取消
        progDialog.setCanceledOnTouchOutside(false);//点区域外不取消quxiao
        if(!Validator.isBlank(msg)){
            progDialog.setMessage(msg);
        }
        progDialog.show();
    }

    /**
     * 显示等待条
     * Context == Activity
     */
    public static void showProgress(Context ctx,final boolean isKeyBack) {
        progressDialogMy = CustomProgressDialog.createDialog(ctx);
        progressDialogMy.setCanceledOnTouchOutside(false);//点区域外quxiao
        // 添加按键监听
        progressDialogMy.setOnKeyListener(new DialogInterface.OnKeyListener() {
            public boolean onKey(DialogInterface arg0, int arg1, KeyEvent arg2) {
                if(isKeyBack) {
                    if (arg1 == KeyEvent.KEYCODE_BACK) {
                        if ((progressDialogMy != null) && progressDialogMy.isShowing()) {
                            progressDialogMy.cancel();
                        }
                    }
                }
                return false;
            }
        });
        progressDialogMy.show();
    }

    /**
     * 隐藏progress
     */
    public static void dismissProgress() {
        if ((progressDialogMy != null) && progressDialogMy.isShowing()) {
            progressDialogMy.dismiss();
        }
        if(progDialog!=null){
            progDialog.dismiss();
        }
    }

    /**
     * 显示并自动关闭
     * @param act
     * @param msg
     */
    public static void showToastOnUIThread(final Activity act,final String msg) {
        act.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showToastCust(act,msg);
            }
        });

    }

    /**
     * 此方法可以避免疯狂模式下点击按钮造成的长时间显示toast的问题
     * @param ctx
     * @param msg
     */
    public static void showToastCust(Context ctx, String msg) {
        if(mToast==null){
            mToast = new Toast(ctx);
            mToast.setGravity(Gravity.CENTER, 0, 0);
            mToast.setDuration(Toast.LENGTH_LONG);
            View toastRoot = LayoutInflater.from(ctx).inflate(R.layout.toast_my, null);
            mToast.setView(toastRoot);
        }
        TextView message = (TextView) mToast.getView().findViewById(R.id.tv_toast);
        message.setText(msg);
        mToast.show();
    }
    public static void showToastCust(Context ctx, int resId) {
        if(mToast==null){
            mToast = new Toast(ctx);
            mToast.setGravity(Gravity.CENTER, 0, 0);
            mToast.setDuration(Toast.LENGTH_LONG);
            View toastRoot = LayoutInflater.from(ctx).inflate(R.layout.toast_my, null);
            mToast.setView(toastRoot);
        }
        TextView message = (TextView) mToast.getView().findViewById(R.id.tv_toast);
        message.setText(resId);
        mToast.show();
    }
    public static void showNoNet(Context ctx) {
        showToastCust(ctx, "网络不可用，请检查网络！");
    }



    public static void showToastNoNet(Context ctx) {
        View toastRoot =  LayoutInflater.from(ctx).inflate(R.layout.toast_my, null);
        TextView message = (TextView) toastRoot.findViewById(R.id.tv_toast);
        message.setText("网络不可用！");

        Toast toastStart = new Toast(ctx);
        toastStart.setGravity(Gravity.CENTER, 0, 0);
        toastStart.setDuration(Toast.LENGTH_SHORT);
        toastStart.setView(toastRoot);
        toastStart.show();
    }

    public static void showAlertOnUIThread(final Activity ctx,final String msg,final MyCallback callback) {
        ctx.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showAlert(ctx, msg, callback);
            }
        });
    }



    public static void showAlertMenuCust(final Activity ctx,String title,final List<String> itemList,final MyCallback<Integer> callback) {
        try{
            final AlertDialog mAlertDialog = new AlertDialog.Builder(ctx).create();
            //内容
            ListAdapter mAdapter = new ArrayAdapter(ctx, R.layout.alertdialog_item, itemList);
            LayoutInflater inflater = LayoutInflater.from(ctx);
            View view = inflater.inflate(R.layout.alertdialog, null);

            TextView titleView = (TextView)view.findViewById(R.id.tv_title);
            if(Validator.isNotEmpty(title)){
                titleView.setText(title);
                titleView.setVisibility(View.VISIBLE);
            }else{
                titleView.setVisibility(View.GONE);
            }

            ListView listview = (ListView)view.findViewById(android.R.id.list);
            listview.setAdapter(mAdapter);
            listview.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int pos,long arg3) {
                    callback.onCallback(pos);
                    mAlertDialog.cancel();
                }
            });

            mAlertDialog.show();
            mAlertDialog.getWindow().setContentView(view);
            //mAlertDialog.getWindow().setLayout(150, 320);

        }catch(Exception e){
            e.printStackTrace();
        }
    }


    //va 引用了 ContextUtil
    /**
     *
     * @param msg
     */
    public static void showToastCust(String msg){
        showToastCust(ContextUtil.getAppContext(),msg);
    }
}