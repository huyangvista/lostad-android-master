package com.lostad.app.demo.util.view;

import java.util.ArrayList;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * 左右滑动页 构建  页侦听 ViewPage.addOnPageChangeListener
 * Created by Hocean on 2016/11/23.
 */
public class ViewPageBuild extends PagerAdapter{

	public ArrayList<View> pages = new ArrayList<>();
	public ArrayList<String> titles = new ArrayList<>();
	public ViewGroup navigation;
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

	public void addPage(View v){
		pages.add(v);
	}
	public void addPage(View v, String title){
		pages.add(v);
		titles.add(title);
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
//		ViewPager rg = new ViewPager(this);
//		ViewPageBuild vpg = new ViewPageBuild();
//      vpg.pages.add(new TextView(this));
//		vpg.titles.add("0");
//		vpg.pages.add(new TextView(this));
//		vpg.titles.add("1");
//		vpg.show(rg, 0);
