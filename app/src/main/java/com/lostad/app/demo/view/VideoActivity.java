package com.lostad.app.demo.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.lostad.app.base.view.component.BaseHisActivity;
import com.lostad.app.demo.R;
import com.lostad.app.demo.entity.Video;
import com.lostad.applib.util.ui.ContextUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import h264.com.VideoView;

/**
 * Created by Hocean on 2017/3/20.
 *
 */
@ContentView(R.layout.activity_video)
public class VideoActivity extends BaseHisActivity {

    private VideoView videoView;

    @ViewInject(R.id.his)
    private LinearLayout his;
    @ViewInject(R.id.videos)
    private LinearLayout videos;

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
        Video video = (Video) bundle.get("data");

        videoView = new VideoView(this);
        videoView.setScalcScene(1,1);
        String file =   "/mnt/shared/Other/352x288s.264"; //352x288.264"; //240x320.264";
        videoView.load();
        videoView.ready(file);
        videoView.start();

        videoView.loadLayout(videos);
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
                if(videoView!= null )videoView.stop();
                videoView = new VideoView(this);
                videoView.setScalcScene(1,1);
                String file =   "/mnt/shared/Other/352x288s.264"; //352x288.264"; //240x320.264";
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

    @Override
    public void onBack() {
        super.onBack();
        videoView.close();
    }

    @Override
    public void onSubmit() {
        super.onSubmit();
        videoView.close();
    }
}
