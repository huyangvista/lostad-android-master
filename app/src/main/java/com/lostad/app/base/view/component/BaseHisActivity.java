package com.lostad.app.base.view.component;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lostad.app.demo.R;
import com.lostad.applib.util.ui.ContextUtil;
import com.lostad.applib.view.BaseAppActivity;

import org.xutils.view.annotation.Event;
import org.xutils.x;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


/**
 * 返回上一页的基类
 * Created by huangzhe on 2016/12/20.
 */

//@ContentView(R.layout.activity_base_his_head_default)
public abstract class BaseHisActivity extends BaseAppActivity {
    //protected HeaderLayout headerLayout;
    private Intent intentData;
    public final static String TITLE = "title";

    public abstract Bundle setResult(Bundle bundle);
    //@ViewInject(R.id.body)
    //protected LinearLayout body;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //x.view().inject(this);
        intentData = this.getIntent();

        //this.requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        //this.setContentView(R.layout.activity_base_his_head_default);
        //this.getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,   R.layout.activity_base_his_head_default);

//        TextView textView = (TextView) this.findViewById(R.id.head_center_text);
//        textView.setText("title");
//        Button titleBackBtn = (Button) this.findViewById(R.id.TitleBackBtn);
//        titleBackBtn.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                BaseHisActivity.finish();
//            }
//        });
    }

    public void loadLayout(ViewGroup vg){
        ContextUtil.getLayoutInflater(vg.getContext()).inflate(R.layout.activity_base_his_head,vg);
        Bundle bundle = intentData.getExtras(); // ContextUtil.getBundle(this);
        if(bundle != null ){ //确定窗口传值了
            TextView tv = (TextView) vg.findViewById(R.id.textViewTitle);
            if(tv != null){
                String title = bundle.getString(TITLE, "");
                tv.setText(title);
            }
        }

        View buttonBack = vg.findViewById(R.id.buttonBack);
        if(buttonBack != null){
            buttonBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBack();
                }
            });
        }
        View buttonSubmit =  vg.findViewById(R.id.buttonSubmit);
        if(buttonSubmit != null){
            buttonSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onSubmit();
                }
            });
        }
//        vg.removeAllViews();
//        vg.addView(view);
    }

    @Deprecated
    public void setBodyContentView(int rid){
        setBodyContentView(ContextUtil.loadLayout(rid));
    }
    @Deprecated
    public void setBodyContentView(View view){
        this.setContentView(R.layout.activity_base_his_head_default);
        LinearLayout body = (LinearLayout) this.findViewById(R.id.body);
        body.removeAllViews();
        body.addView(view);
        loadLayout(body);
    }

    @Event(R.id.buttonBack)
    private void onClickButtonBack(View v){
        onBack();
    }
    @Event(R.id.buttonSubmit)
    private void onClickButtonSubmit(View v){
        onSubmit();
    }


    /**
     * 带覆盖
     */
    public void onSubmit(){
        Bundle bundle=setResult(getExtras());
        if(bundle != null){
            intentData.putExtras(bundle);
        }
        setResult(RESULT_OK, intentData); //intent为A传来的带有Bundle的intent，当然也可以自己定义新的Bundle
        quitApp();
    }

    /**
     * 返回上一页
     */
    public void onBack(){
        quitApp();
    }

    /**
     * 得到上一页传递的值
     * @return
     */
    public Bundle getExtras(){
        Bundle b = intentData.getExtras();
        if(b == null){
            b = new Bundle();
        }
        return b;
    }

    @Override
    public void quitApp() {
        finish();
    }

    protected void x(Activity act){
        x.view().inject(act);
    }

    public static Map<String, Serializable> markParms(Serializable... parms){
        Map<String, Serializable> map = new HashMap<>();
        if(parms != null){
            for (int i = 0; i+1 < parms.length; i+=2) {
                map.put((String) parms[i],parms[i+1]);
            }
        }
        return map;
    }
}
