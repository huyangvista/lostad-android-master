package com.lostad.app.base.view.component;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.lostad.app.demo.R;
import com.lostad.applib.util.ui.ContextUtil;
import com.lostad.applib.view.BaseAppActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;


/**
 * 返回上一页的基类
 * Created by huangzhe on 2016/12/20.
 */

@ContentView(R.layout.activity_header)
public abstract class BaseHisActivity extends BaseAppActivity {
    //protected HeaderLayout headerLayout;
    private Intent intentData; //传递过来的数据

    public abstract Bundle setResult(Bundle bundle);


    @ViewInject(R.id.body)
    protected LinearLayout body;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        intentData = this.getIntent();

        //this.requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        //this.setContentView(R.layout.activity_header);
        //this.getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,   R.layout.activity_header);

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

    public void setBodyContentView(int rid){
        body.removeAllViews();
        body.addView(ContextUtil.loadLayout(rid));
    }

    @Event(R.id.buttonBack)
    private void onClickButtonBack(View v){
        toBack();
    }
    @Event(R.id.buttonSubmit)
    private void onClickButtonSubmit(View v){
        toSubmit();
    }


    /**
     * 带覆盖
     */
    public void toSubmit(){
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
    public void toBack(){
        quitApp();
    }

    /**
     * 得到上一页传递的值
     * @return
     */
    public Bundle getExtras(){
        return intentData.getExtras();
    }

    @Override
    public void quitApp() {
        finish();
    }

}
