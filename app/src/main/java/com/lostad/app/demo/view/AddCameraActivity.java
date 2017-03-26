package com.lostad.app.demo.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.lostad.app.base.view.BaseActivity;
import com.lostad.app.base.view.component.BaseHisActivity;
import com.lostad.app.demo.MyApplication;
import com.lostad.app.demo.R;
import com.lostad.app.demo.entity.Video;
import com.lostad.applib.util.ui.ContextUtil;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by Hocean on 2017/3/21.
 */

@ContentView(R.layout.activity_add_camera)
public class AddCameraActivity extends BaseHisActivity {

    @ViewInject(R.id.his)
    private LinearLayout his;

    @ViewInject(R.id.editTextUid)
    private EditText editTextUid;
    @ViewInject(R.id.editTextUsername)
    private EditText editTextUsername;
    @ViewInject(R.id.editTextPassword)
    private EditText editTextPassword;

    Video video;

    @Override
    public Bundle setResult(Bundle bundle) {
        return bundle;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setBodyContentView(R.layout.activity_add_camera);
//        editTextUid = (EditText) this.findViewById(R.id.editTextUid);
//        editTextUsername = (EditText) this.findViewById(R.id.editTextUsername);
//        editTextPassword = (EditText) this.findViewById(R.id.editTextPassword);
        x.view().inject(this);

        loadLayout(his);

       Bundle bun =  ContextUtil.getActivtyExtra(this);
        if(bun != null){
            video = (Video) bun.get("data");
            if(video != null){
                editTextUid.setText(video.uid);
                editTextUsername.setText(video.username);
                editTextPassword.setText(video.password);
            }
        }
    }

    @Override
    public void onSubmit() {
        DbManager db =  MyApplication.getInstance().getDb();
        try {
            Video v = new Video();
            if(video != null){
                v.id = video.id;
            }
            v.uid = editTextUid.getText().toString();
            v.username = editTextUsername.getText().toString();
            v.password = editTextPassword.getText().toString();
            db.saveOrUpdate(v);
        } catch (DbException e) {
            e.printStackTrace(); ///storage/emulated/0/1_tour/tour_0107.db
        }
        super.onSubmit();
    }
}
