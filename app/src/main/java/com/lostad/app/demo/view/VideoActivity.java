package com.lostad.app.demo.view;

import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import com.lostad.app.base.view.component.BaseHisActivity;
import com.lostad.app.demo.IConst;
import com.lostad.app.demo.R;
import com.lostad.applib.core.Action;
import com.lostad.applib.core.Action2;
import com.lostad.applib.util.DateTime;
import com.lostad.applib.util.DialogUtil;
import com.lostad.applib.util.FileDataUtil;
import com.lostad.applib.util.ImageUtil;
import com.lostad.applib.util.sys.PrefManager;
import com.lostad.applib.util.ui.ContextUtil;
import com.lostad.applib.util.ui.WindowUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import h264.com.VideoView;
import h264.com.VideoViewLocal;

/**
 * Created by Hocean on 2017/3/20.
 */
@ContentView(R.layout.activity_video)
public class VideoActivity extends BaseHisActivity {
    public static final String VIDEO_NAME = "VIDEO_NAME";
    public static final String AWAKE_START = "AWAKE_START";
    public static final String AWAKE_START_PLAY = "0";
    public static final String AWAKE_START_STOP = "1";

    private VideoViewLocal videoView;

    @ViewInject(R.id.his)
    private LinearLayout his;
    @ViewInject(R.id.videos)
    private LinearLayout videos;

    @ViewInject(R.id.pre)
    private SeekBar seekBar;
    @ViewInject(R.id.play)
    private Button btnPlay;
    @ViewInject(R.id.fullWin)
    private Button btnFullWin;

    private int pro = 0;
    private boolean onPro = false;

    private int shoHidTime = 5000;
    private Handler handler = new Handler();

    public void showBtn() {
        his.setVisibility(View.VISIBLE);
        his.setAlpha(1);
        btnPlay.setVisibility(View.VISIBLE);
        btnPlay.setVisibility(View.VISIBLE);
        btnFullWin.setVisibility(View.VISIBLE);
    }

    public void hidBtn() {
        his.setVisibility(View.GONE);
        his.setAlpha(0);
        btnPlay.setVisibility(View.GONE);
        btnPlay.setVisibility(View.GONE);
        btnFullWin.setVisibility(View.GONE);
    }


    private Runnable runHid = new Runnable() {
        @Override
        public void run() {
            hidBtn();
        }
    };

    public void showAndHidTime() {
        handler.removeCallbacks(runHid);
        showBtn();
        handler.postDelayed(runHid, shoHidTime);
    }

    public void play() {
        try {
            if (videoView != null) videoView.close();
            String file = "/mnt/shared/Other/352x288s.264"; //352x288.264"; //240x320.264";
            file = ContextUtil.getBundle(this).getString(VIDEO_NAME,file);
            videoView = new VideoViewLocal(VideoActivity.this);
            videoView.setScalcScene(1, 1);
            videoView.load();
            videoView.ready(file);
            videoView.setPro(pro / 100.0f);
            //videoView.jump(pro / 100.0f);
            videoView.start();
            videoView.loadLayout(videos);
            videoView.setActPro(new Action2<Integer, Integer>() {
                @Override
                public void invoke(final Integer integer, final Integer integer2) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (!onPro) {
                                seekBar.setProgress(integer * 100 / integer2);
                            }
                        }
                    });
                }
            });
            btnPlay.setBackgroundResource(R.mipmap.pause);
            showAndHidTime();
            videoView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showAndHidTime();
                }
            });
            videoView.setActStop(new Action() {
                @Override
                public void invoke() {
                    //DialogUtil.showToastCust("播放完");
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            btnPlay.setBackgroundResource(R.mipmap.play);
                        }
                    });
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public Bundle setResult(Bundle bundle) {
        return bundle;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x(this);
        loadLayout(his);
        Bundle bundle = ContextUtil.getBundle(this);
        btnPlay.setBackgroundResource(R.mipmap.pause);

        String awakeStart = bundle.getString(AWAKE_START, AWAKE_START_PLAY);
        if (AWAKE_START_PLAY.equals(awakeStart)) {
            play();
        } else if (AWAKE_START_STOP.equals(awakeStart)) {
        }

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                pro = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                onPro = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar sb) {
                play();
                onPro = false;
            }
        });


        int scene = WindowUtil.getScreen(this);
        switch (scene) {
            case WindowUtil.SceneStatus.defult:
            case WindowUtil.SceneStatus.horizontal:  //min
                btnFullWin.setBackgroundResource(R.mipmap.expand);
                WindowUtil.noFull(this);
                break;
            case WindowUtil.SceneStatus.vertical: //full
                btnFullWin.setBackgroundResource(R.mipmap.contract);
                WindowUtil.Full(this);
                break;

        }
    }

    // Menu item Ids
    public static final int PLAY_ID = Menu.FIRST;
    public static final int EXIT_ID = Menu.FIRST + 1;
    public static final int IMAGE_ID = Menu.FIRST + 2;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, PLAY_ID, 0, R.string.play);
        menu.add(0, EXIT_ID, 1, R.string.exit);
        menu.add(0, IMAGE_ID, 2, "截图");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case PLAY_ID:
                if (videoView != null) videoView.stop();
                videoView = new VideoViewLocal(this);
                videoView.setScalcScene(1, 1);
                String file = "/mnt/shared/Other/352x288s.264"; //352x288.264"; //240x320.264";
                videoView.load();
                videoView.ready(file);
                videoView.start();
                return true;
            case EXIT_ID:
                finish();
                return true;
            case IMAGE_ID:
                String vs =  PrefManager.getString(this, IConst.KEY_PATH_IMAGES, "");
                ImageUtil.saveToSDCard(videoView.getVideoBit(),vs , FileDataUtil.createPngFileName("camera_"));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBack() {
        super.onBack();
        if(videoView != null){
            videoView.close();
        }
    }

    @Override
    public void onSubmit() {
//        super.onSubmit();
//        if(videoView != null){
//            videoView.close();
//        }
        String vs =  PrefManager.getString(this, IConst.KEY_PATH_IMAGES, IConst.PATH_ROOT + IConst.KEY_PATH_IMAGES);
        String name = FileDataUtil.createPngFileName("camera_");
        ImageUtil.saveToSDCard(videoView.getVideoBit(),vs , name);
        DialogUtil.showToastCust("已保存到:" + vs + name);
    }

    @Event(R.id.play)
    private void onClickPlay(View v) {
        if (videoView != null) {
            videoView.paue();

            if (videoView.isExit()) {
                videoView.stop();
                pro = 0;
                play();
                btnPlay.setBackgroundResource(R.mipmap.pause);
            } else if (videoView.isPaue()) {
                btnPlay.setBackgroundResource(R.mipmap.play);
            } else {
                btnPlay.setBackgroundResource(R.mipmap.pause);
            }
        }
    }

    @Event(R.id.fullWin)
    private void onClickFullWin(View v) {
        //String vs =  PrefManager.getString(this, IConst.KEY_PATH_VIDEOS, "");
        //ImageUtil.saveToSDCard(videoView.getVideoBit(),vs , FileDataUtil.createPngFileName("camera_"));
        int scene = WindowUtil.getScreen(this);
        switch (scene) {
            case WindowUtil.SceneStatus.defult:
            case WindowUtil.SceneStatus.horizontal:  //min
                if (videoView != null) {
                    videoView.paue(true);
                }
                //btnFullWin.setBackgroundResource(R.mipmap.expand);
                WindowUtil.setScreen(this, WindowUtil.SceneStatus.vertical);
                break;
            case WindowUtil.SceneStatus.vertical: //full

                if (videoView != null) {
                    videoView.paue(true);
                }
                //btnFullWin.setBackgroundResource(R.mipmap.contract);
                WindowUtil.setScreen(this, WindowUtil.SceneStatus.horizontal);
                break;

        }

    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
				if(videoView != null){
                    videoView.close();
                }
        }
        return super.onKeyDown(keyCode, event);
    }



}
