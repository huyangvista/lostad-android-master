package com.lostad.app.demo.view;

import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;

import h264.com.VView;
import com.lostad.app.base.view.BaseActivity;
import com.lostad.app.demo.R;

import java.io.File;

public class TestActivity extends BaseActivity {

    VView vv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_test);
        vv = new VView(this);
        vv.setScalcScene(1,1);
        setContentView(vv);
    }

    // Menu item Ids
    public static final int PLAY_ID = Menu.FIRST;
    public static final int EXIT_ID = Menu.FIRST + 1;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        menu.add(0, PLAY_ID, 0, R.string.play);
        menu.add(0, EXIT_ID, 1, R.string.exit);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case PLAY_ID:
                // 此处设定不同分辨率的码流文件
                File skRoot = Environment.getExternalStorageDirectory();
                String file =   "/mnt/sdcard/352x288.264"; //352x288.264"; //240x320.264";
               // String file =   "/mnt/shared/Other/test.h264"; //352x288.264"; //240x320.264";
                //String file = skRoot.getParent() +   "/352x288.264"; //352x288.264"; //240x320.264";
                vv.PlayVideo(file);
                return true;
            case EXIT_ID:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
