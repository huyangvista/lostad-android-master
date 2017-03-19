package com.lostad.app.demo.view.mainFragment;

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
import com.lostad.app.demo.util.view.ListViewBuild;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sszvip@qq.com
 * 
 */
public class VideoFragment extends BaseFragment {
	@ViewInject(R.id.ll_tabItems)
	private LinearLayout ll_tabItems;
	@ViewInject(R.id.viewpager)
	private ViewPager viewPager;

	@ViewInject(R.id.tab_under_line)
	private ImageView tab_under_line;

	private PagerAdapter pagerAdapter;
	// 当前页面
	private int currentIndex;
	// 屏幕宽度
	private int screenWidth;
	// 三个tab页面的列表
	private List<ListWaterFragment> fragments = new ArrayList<ListWaterFragment>();

	@ViewInject(R.id.ll_video)
	private LinearLayout llVideo;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
	    super.onCreateView(inflater, container,savedInstanceState);
		View rootView = inflater.inflate(R.layout.fragment_video, container, false);//获取view
		x.view().inject(this, rootView);
	    DisplayMetrics getDisplayMetrics = getActivity().getApplicationContext().getResources().getDisplayMetrics();//获取屏幕宽度
		screenWidth = getDisplayMetrics.widthPixels;
		initTabs(inflater);
		//initVideos(inflater);011

		return rootView;
	}
	private void initVideos(LayoutInflater inflater) {
		ListView lv = new ListView(inflater.getContext());  //列表
		ListViewBuild lvb = new ListViewBuild(inflater.getContext());  //列表工具
		ListViewBuild.Parms parms = new ListViewBuild.Parms();  //参数
		parms.setLayoutId(R.layout.fragment_main_listview_item);  //设置布局文件
		parms.adds("id","" + R.id.tv, "value", "请选择出发时间"); //每行参数
		lvb.getParms().add(parms); //设置到列表工具
		parms = new ListViewBuild.Parms();
		parms.setLayoutId(R.layout.fragment_main_listview_item);
		parms.adds("id","" + R.id.tv, "value", "天津滨海机场");
		lvb.getParms().add(parms);

		parms = new ListViewBuild.Parms();
		parms.setLayoutId(R.layout.fragment_main_listview_item);
		parms.adds("id","" + R.id.tv, "value", "您要去哪","color",0xffff8800);
		parms.adds("id",R.id.tv, "value", android.R.drawable.ic_notification_overlay);
		lvb.getParms().add(parms);

		lvb.show(lv);  //显示列表
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() { //列表回调
			@Override
			public void onItemClick(AdapterView<?> parent, View pages, int position, long id) { //滑动页每项侦听
				//DialogBuild.toast(position);
				if (position == 0 ){
				}else if(position == 1){
				}
			}
		});

		llVideo.addView(lv);
	}
	private void initTabs(LayoutInflater inflater) {
        for(int i=0;i<1;i++){
			ListWaterFragment f = new ListWaterFragment();
			Bundle b = new Bundle();
			b.putString("type",i+"");
			f.setArguments(b);

			fragments.add(f);
		}

		int width = screenWidth / fragments.size();//屏幕宽度除以 title长度
		View v1 = createTabItem(inflater,width, "全部项目");
		View v2 = createTabItem(inflater,width, "附近项目");
		View v3 = createTabItem(inflater,width, "我关注的");
		ll_tabItems.addView(v1);
		ll_tabItems.addView(v2);
		ll_tabItems.addView(v3);
		// 初始化tab选中后的下划线
		initTabUnderLine();
		//FragmentManager mgr = getActivity().getSupportFragmentManager();
		FragmentManager mgr = getChildFragmentManager();//由于是嵌套fragment
		
		pagerAdapter = new MyPageAdapter(mgr);
		viewPager.setAdapter(pagerAdapter);
		viewPager.setOnPageChangeListener(pageChangeListener);
		viewPager.setOffscreenPageLimit(2);// 缓存两个fragment
		// example 给第一个tab加上badge
		// BadgeView badge;
		// badge = new BadgeView(ListGoodsActivity.this, firstTab);
		// badge.setText("1");
		// badge.show();
		//viewPager.setCurrentItem(0);
	}


	private TextView createTabItem(LayoutInflater inflater,int width, String text) {
		TextView v = (TextView) inflater.inflate(R.layout.tab_textview, null);
		v.setWidth(width);
		v.setText(text);
		return v;
	}

	private OnPageChangeListener pageChangeListener = new OnPageChangeListener() {

		@Override
		public void onPageSelected(int currIndex) {
			currentIndex = currIndex;
			resetTabTextView();
			TextView tv = (TextView) ll_tabItems.getChildAt(currentIndex);
			tv.setTextColor(getResources().getColor(R.color.red));
		}

		@Override
		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {
			// 从左到右
			if (currentIndex == position) {
				LinearLayout.LayoutParams layoutParam = (LinearLayout.LayoutParams) tab_under_line
						.getLayoutParams();
				layoutParam.leftMargin = (int) (positionOffset
						* (screenWidth * 1.0 / fragments.size()) + currentIndex
						* (screenWidth / fragments.size()));
				tab_under_line.setLayoutParams(layoutParam);
			}
			// 从右到左
			else if (currentIndex > position) {
				LinearLayout.LayoutParams layoutParam = (LinearLayout.LayoutParams) tab_under_line
						.getLayoutParams();
				layoutParam.leftMargin = (int) (-(1 - positionOffset)
						* (screenWidth * 1.0 / fragments.size()) + currentIndex
						* (screenWidth / fragments.size()));
				tab_under_line.setLayoutParams(layoutParam);
			}
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {

		}
	};

	private class MyPageAdapter extends FragmentPagerAdapter {

		public MyPageAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int index) {
			Fragment f = fragments.get(index);
			return f;
		}

		@Override
		public int getCount() {
			return fragments.size();
		}

	}

	// 初始化tab下划线
	private void initTabUnderLine() {
		LinearLayout.LayoutParams layoutParam = (LinearLayout.LayoutParams) tab_under_line
				.getLayoutParams();
		layoutParam.width = screenWidth / fragments.size();
		tab_under_line.setLayoutParams(layoutParam);

		for (int i = 0; i < fragments.size(); i++) {
			final int index = i;
			View v = ll_tabItems.getChildAt(i);
			v.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					viewPager.setCurrentItem(index);
				}
			});
		}

	}

	// 重置tab标题颜色
	private void resetTabTextView() {
		int count = ll_tabItems.getChildCount();
		for (int i = 0; i < count; i++) {
			TextView tv = (TextView) ll_tabItems.getChildAt(i);
			tv.setTextColor(getResources().getColor(R.color.txt_gray));
		}

	}


   
}
