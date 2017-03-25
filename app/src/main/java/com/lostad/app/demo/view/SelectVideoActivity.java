package com.lostad.app.demo.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.lostad.app.base.view.component.BaseHisActivity;
import com.lostad.app.demo.R;

import org.xutils.view.annotation.ContentView;

/**
 * Created by Hocean on 2017/3/20.
 *
 */
@ContentView(R.layout.activity_wel)
public class SelectVideoActivity extends BaseHisActivity {
	@Override
	public Bundle setResult(Bundle bundle) {
		bundle.putString("mmm","xiaoxi");
		return bundle;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		x(this);
//		Intent intentData = this.getIntent();
//         setResult(RESULT_OK, intentData); //intent为A传来的带有Bundle的intent，当然也可以自己定义新的Bundle
//         finish();

		loadLayout(new LinearLayout(this));
	}

}
