package com.lostad.app.demo.view;

import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import h264.com.VView;
import com.lostad.app.base.view.BaseActivity;
import com.lostad.app.demo.R;
import com.lostad.app.demo.entity.Tour;
import com.lostad.app.demo.util.view.TouchListView;
import com.lostad.applib.util.ui.DialogUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;

@ContentView(R.layout.activity_test)
public class TestActivity extends BaseActivity {

    @ViewInject(R.id.line_layout)
    private LinearLayout linearLayout;
    VView vv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(vv);
        x.view().inject(this);
        vv = new VView(this);
        vv.setScalcScene(1,1);

        TouchListView tlv = new TouchListView(this){
            @Override
            public View loadItemLayout(LayoutInflater inf){
                return inf.inflate(R.layout.list_item_touchlistview, null);
            }
            @Override
            public Object loadItemView(View con){
                return new ViewHolder(con);
            }
            @Override
            public void loadSetItemView(Object holders, Object demo){
                final Tour f = (Tour) demo;
                ViewHolder holder = (ViewHolder) holders;
                holder.tv_title.setText(f.title );
                holder.tv_desc.setText(f.desc);
                holder.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DialogUtil.showToastCust("heh ");
                    }
                });
//                if(Validator.isNotEmpty(f.picUrl)){
//                    //DownloadUtil.loadImage(holder.iv_pic, IConst.URL_BASE+f.XMTP,R.drawable.loading_frame1,R.mipmap.img_default,R.mipmap.load_fail);
//                    DownloadUtil.loadImage(mContext,holder.iv_pic, f.picUrl);
//                }
            }
            //点击列表
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DialogUtil.showToastCust("heh 00000");
            }
        };


        linearLayout.addView(tlv.getRootView());
        tlv.onLoadMore();
        //linearLayout.addView(vv);
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
                String file =   "/mnt/shared/Other/352x288.264"; //352x288.264"; //240x320.264";
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

    @Event(R.id.button)
    private void onClickLoginByPhone(View v) {
        toActivty(LoginActivity.class);
    }
    @Event(R.id.button2)
    private void onClickButton2(View v){
        toActivty(LoginActivity.class);
    }

}
