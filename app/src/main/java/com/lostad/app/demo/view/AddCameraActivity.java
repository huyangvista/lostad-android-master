package com.lostad.app.demo.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.lostad.app.base.view.BaseActivity;
import com.lostad.app.demo.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_add_camera)
public class AddCameraActivity extends BaseActivity {

    @ViewInject(R.id.editTextUid)
    private EditText editTextUid;
    @ViewInject(R.id.editTextUsername)
    private EditText editTextUsername;
    @ViewInject(R.id.editTextPassword)
    private EditText editTextPassword;

    @ViewInject(R.id.buttonSubmit)
    private Button buttonSubmit;
    @ViewInject(R.id.buttonCancel)
    private Button buttonCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
    }

    @Event(R.id.buttonSubmit)
    private void onClickSubmit(View v){

    }

    @Event(R.id.buttonCancel)
    private void onClickCancel(View v){

    }
}
