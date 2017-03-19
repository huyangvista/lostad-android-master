package com.lostad.applib.util.ui;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;


/**
 * 窗口工具类,提供可重用的窗口
 * 
 * @author sszvip@qq.com
 * @copyright lostad.com
 */
public class EffectUtil {

	public static void showShake(Activity ctx, View v,int effectName) {
		Animation shake = AnimationUtils
				.loadAnimation(ctx, effectName);
		v.startAnimation(shake);
	}

	public void showOrExpandTextView(TextView tv_text, boolean show) {
		if (show) {
			tv_text.setEllipsize(null); // 展开
			tv_text.setSingleLine(false);
		} else {
			tv_text.setEllipsize(TextUtils.TruncateAt.END); // 收缩
			tv_text.setSingleLine(true);
		}
	}

}
