package com.lostad.app.base.view.widget;

import android.content.Intent;
import android.os.Bundle;

import com.lostad.applib.view.BaseAppActivity;


/**
 * 返回上一页的基类
 * Created by huangzhe on 2016/12/20.
 */

public abstract class BaseHisActivity extends BaseAppActivity {
    //protected HeaderLayout headerLayout;
    private Intent intentData; //传递过来的数据
    public abstract Bundle setResult();//设置返回值
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intentData = this.getIntent();
//        headerLayout=(HeaderLayout)findViewById(R.id.activity_header);
//        if(headerLayout!=null){
//            headerLayout.setBtnOnClickListener(new HeaderBtnOnClickListener() {
//                @Override
//                public void SubmitClick() {
//                    if(submitClk()) {
//                        intentData.putExtras(setResult());
//                        setResult(RESULT_OK, intentData); //intent为A传来的带有Bundle的intent，当然也可以自己定义新的Bundle
//                        finish();
//                    }
//                }
//
//                @Override
//                public void goHisClick() {
//                    getHisClk();
//                }
//            });
//        }
    }

    /**
     * 带覆盖
     */
    public boolean submitClk(){
        return true;
    }

    /**
     * 返回上一页
     */
    public void getHisClk(){

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
