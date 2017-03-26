package com.lostad.app.demo.util.view;

import java.util.ArrayList;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * 左右滑动页 构建  页侦听 ViewPage.addOnPageChangeListener
 * Created by Hocean on 2016/11/23.
 */
public class ViewPageBuild extends PagerAdapter{

	private ArrayList<View> pages = new ArrayList<>();
	private ArrayList<String> titles = new ArrayList<>();
	private ViewGroup navigation;
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		//super.destroyItem(container, position, object);
		container.removeView(pages.get(position));
	}
	@Override
	public int getCount() {
		return pages.size();
	}
	@Override
	public CharSequence getPageTitle(int position) {
		return titles.get(position);
	}
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		//return super.instantiateItem(container, position);
		((ViewGroup)container).addView(pages.get(position));
		return pages.get(position);
	}
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0==arg1;
	}
	public void show(ViewPager vp){
		//vp.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
		vp.setAdapter(this);
		setPoint(0);
		vp.setCurrentItem(0);
		vp.addOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				setPoint(arg0);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
	}
	public void setPoint(int point){
		setPoint(point,android.R.drawable.presence_online, android.R.drawable.presence_invisible);
	}
	public void setPoint(int point,int resOn, int resOff){
		if (navigation == null) return ;
		navigation.removeAllViews();
		for (int i = 0; i < pages.size(); i++) {
			ImageView img = new ImageView(navigation.getContext());
			if(i==point){
				img.setImageResource(resOn);
			}else{
				img.setImageResource(resOff);
			}
			navigation.addView(img);
		}
	}
	private void addTitle(String title){
		titles.add(title);
	}
	public void addPage(View v){
		pages.add(v);
	}
	public void addPage(View v, String title){
		pages.add(v);
		titles.add(title);
	}
	public void addPage(Context context, @DrawableRes int rid){
		ImageView iv = new ImageView(context);
		iv.setImageResource(rid);
		//iv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
		iv.setScaleType(ImageView.ScaleType.FIT_XY);
		addPage(iv);
	}
	public void addPage(Context context, @DrawableRes int rid, String title){
		addPage(context,rid);
		addTitle(title);
	}

	public ArrayList<View> getPages() {
		return pages;
	}

	public void setPages(ArrayList<View> pages) {
		this.pages = pages;
	}

	public ArrayList<String> getTitles() {
		return titles;
	}

	public void setTitles(ArrayList<String> titles) {
		this.titles = titles;
	}

	public ViewGroup getNavigation() {
		return navigation;
	}

	public void setNavigation(ViewGroup navigation) {
		this.navigation = navigation;
	}
}


//demo
//ViewPager vg = new ViewPager(this);
//	ViewPageBuild vpg = new ViewPageBuild();
//        vpg.addPage(this, R.mipmap.camera,"0000");
//				vpg.addPage(this, R.mipmap.camera_load,"222");
//				vpg.setNavigation(ll_group);
//				vpg.show(vg);
//				ll_vp.addView(vg);
