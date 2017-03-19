package com.lostad.app.demo.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.lostad.app.base.view.widget.BaseHisActivity;
import com.lostad.app.demo.IConst;
import com.lostad.app.demo.R;

/**
 *
 */
public class SelectVideoActivity extends BaseHisActivity {
	@Override
	public Bundle setResult() {
		Bundle bundle = getExtras();
		bundle.putString("mmm","xiaoxi");
		return bundle;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wel);

		Intent intentData = this.getIntent();
		intentData.putExtras(setResult());
         setResult(RESULT_OK, intentData); //intent为A传来的带有Bundle的intent，当然也可以自己定义新的Bundle
         finish();
	}

}
