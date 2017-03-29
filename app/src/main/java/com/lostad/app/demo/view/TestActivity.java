package com.lostad.app.demo.view;

import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;

import h264.com.VView;
import h264.com.VideoView;

import com.lostad.app.base.view.BaseActivity;
import com.lostad.app.demo.R;
import com.lostad.app.demo.entity.Tour;
import com.lostad.app.demo.util.view.TouchListView;
import com.lostad.app.demo.util.view.folder.CallbackBundle;
import com.lostad.app.demo.util.view.folder.OpenFileDialog;
import com.lostad.applib.core.MyCallback;
import com.lostad.applib.util.DialogUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Hocean on 2017/3/21.
 */
@ContentView(R.layout.activity_test)
public class TestActivity extends BaseActivity {

    @ViewInject(R.id.line_layout)
    private LinearLayout linearLayout;
//    VView vv;

    private VideoView videoView = null;

    @ViewInject(R.id.editText)
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);


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
                if (videoView != null) videoView.stop();
                videoView = new VideoView(this);
                videoView.setScalcScene(1, 1);
                linearLayout.removeAllViews();
                linearLayout.addView(videoView);
                String file = "/mnt/shared/Other/352x288s.264"; //352x288.264"; //240x320.264";
                videoView.load();
                videoView.ready(file);
                videoView.start();
                return true;
            case EXIT_ID:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Event(R.id.button)
    private void onClickLoginByPhone(View v) {
        videoView.paue();
    }

    @Event(R.id.button2)
    private void onClickButton2(View v) {
        videoView.stop();
    }

    @Event(R.id.button4)
    private void onClickButton23sdfgsdfg(View v) {
        String file = "/mnt/shared/Other/352x288.264"; //352x288.264"; //240x320.264";
        String fileO = "/mnt/shared/Other/352x288s.264"; //352x288.264"; //240x320.264";
        try {
            FileInputStream is = new FileInputStream(file);
            FileOutputStream os = new FileOutputStream(fileO);
            int temp = 0;
            byte[] bs = new byte[1024 * 4];

            for (int i = 0; i < 4; i++) {
                is = new FileInputStream(file);
                while ((temp = is.read(bs, 0, 1024 * 4)) > 0) {
                    os.write(bs, 0, temp);
                }
            }

            os.close();
            is.close();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Event(R.id.button3)
    private void onClickButton23(View v) {

        String file = "/mnt/shared/Other/352x288.264"; //352x288.264"; //240x320.264";
        String fileO = "/mnt/shared/Other/352x288s.264"; //352x288.264"; //240x320.264";
        try {
            FileInputStream isb = new FileInputStream(file);
            BufferedInputStream is = new BufferedInputStream(new FileInputStream(fileO));
            //int read = is.read(b,0,1024 * 5);
            //is.read()

            //int temp = 0;
//            int len = 0;
//            int mTrans = 0x0F0F0F0F;
//            byte[] bs = new byte[1024 * 4];
//
            for (int i = 0; i < 200; i++) {
                 is.read();
            }
//
//            int fileLength = Integer.MAX_VALUE;
//            int count = 0;
//            while (true) {
//                if(count > 4){
//                    is.mark(fileLength);
//                }
//                int temp = is.read();
//                if (temp == -1) {
//                    break;
//                }
//                count++;
//                byte ib = (byte) temp;
//                mTrans <<= 8;
//                mTrans |= ib;
//                if (mTrans == 1) // 找到一个开始字
//                {
//                    if (is.read() == 0x67) {
//                        is.reset();
//                        break;
//                    }
//                }
//            }
            //is.available()
//            is.close();
//            is = new BufferedInputStream(new FileInputStream(fileO));
//            for (int i = 0; i < count - 4; i++) {
//                is.read();
//            }

            if (videoView != null) videoView.stop();
            videoView = new VideoView(this);
            videoView.setScalcScene(1, 1);
            linearLayout.removeAllViews();
            linearLayout.addView(videoView);
            videoView.load();
            videoView.ready(is);
            //videoView.setmTrans(0x0F0F0F0F);
            videoView.start();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
