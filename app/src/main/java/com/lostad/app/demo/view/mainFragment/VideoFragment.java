package com.lostad.app.demo.view.mainFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.lostad.app.base.view.fragment.BaseFragment;
import com.lostad.app.demo.R;
import com.lostad.app.demo.entity.Tour;
import com.lostad.app.demo.entity.TourList4j;
import com.lostad.app.demo.manager.TourManager;
import com.lostad.app.demo.util.view.ListViewBuild;
import com.lostad.app.demo.view.MainActivity;
import com.lostad.app.demo.view.SelectVideoActivity;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sszvip@qq.com
 * 
 */
public class VideoFragment extends BaseListFragment {

	@Override
	public TourList4j loadDataAny(int start, TourList4j tourList4j) {
		//return  TourManager.getInstance().listTourAll(start);

		TourList4j g4j = null;
		String index = "Title :"+ start++;
		List<Tour> list = new ArrayList<Tour>();
		list.add(new Tour(index,index, null,"Lostad-android framework is ready for u !"));
		index = "Title :"+ start++;
		list.add(new Tour(index,index,null,"Lostad-android framework"));
		index = "Title :"+ start++;
		list.add(new Tour(index,index,null,"Lostad-android framework"));
		index = "Title :"+ start++;

		g4j = new TourList4j(true,"success");
		g4j.list = list;
		return g4j;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		super.onItemClick(parent, view, position, id);
		Intent intent=new Intent();
		intent.putExtra("msg","mmm");
		intent.setClass(getActivity(), SelectVideoActivity.class);
//                startActivity(intent);
		startActivityForResult(intent,0);//跳转并发送请求码
	}


}
