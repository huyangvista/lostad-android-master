package com.lostad.app.demo.view;

import android.app.Activity;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lostad.app.demo.IConst;
import com.lostad.app.demo.R;
import com.lostad.app.demo.util.view.ViewPageBuild;
import com.lostad.applib.util.sys.PrefManager;

import org.xutils.view.annotation.ContentView;
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
        vpg.addPage(this, R.mipmap.camera,"0000");
        vpg.addPage(this, R.mipmap.camera_load,"222");
        vpg.setNavigation(ll_group);
        vpg.show(vg);
        ll_vp.addView(vg);
    }
}
