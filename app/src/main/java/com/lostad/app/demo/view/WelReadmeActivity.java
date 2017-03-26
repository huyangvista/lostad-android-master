package com.lostad.app.demo.view;

import android.app.Activity;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lostad.app.demo.IConst;
import com.lostad.app.demo.R;
import com.lostad.app.demo.util.view.ViewPageBuild;
import com.lostad.applib.util.sys.PrefManager;
import com.lostad.applib.util.ui.ContextUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_wel_readme)
public class WelReadmeActivity extends Activity {

    @ViewInject(R.id.ll_vp)
    private LinearLayout ll_vp;
    @ViewInject(R.id.ll_group)
    private LinearLayout ll_group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /// PrefManager.saveString(this, IConst.APP_FIRST, "0");
        x.view().inject(this);

        ViewPager vg = new ViewPager(this);
		ViewPageBuild vpg = new ViewPageBuild();
        vpg.addPage(this, R.mipmap.camera);
        vpg.addPage(this, R.mipmap.camera_load);

//        RelativeLayout ll = new RelativeLayout(this);
//        //ll.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT));
//        ImageView iv = new ImageView(this);
//        iv.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT));
//        iv.setImageResource(R.mipmap.camera);
//        iv.setScaleType(ImageView.ScaleType.FIT_XY);
//        ll.addView(iv);
//        Button go = new Button(this);
//        go.setText("开始试用");
//        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams( RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
//        lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
//        lp.addRule(RelativeLayout.ALIGN_BOTTOM,100);
//        go.setLayoutParams(lp);
//        ll.addView(go);
//        vpg.addPage(ll);

        View v  = ContextUtil.getLayoutInflater(this).inflate(R.layout.activity_welreadme_startgoto,null);
        vpg.addPage(v);
        vpg.setNavigation(ll_group);
        vpg.show(vg);
        ll_vp.addView(vg);

        Button go = (Button) v.findViewById(R.id.wel_start);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContextUtil.toActivtyClear(MainActivity.class);
            }
        });
    }


}
