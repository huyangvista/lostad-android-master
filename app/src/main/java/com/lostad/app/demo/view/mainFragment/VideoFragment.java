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
import com.lostad.app.demo.util.view.TouchListView;
import com.lostad.app.demo.view.MainActivity;
import com.lostad.app.demo.view.SelectVideoActivity;
import com.lostad.app.demo.view.TestActivity;
import com.lostad.applib.core.MyCallback;
import com.lostad.applib.util.ui.DialogUtil;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import h264.com.VView;

/**
 * @author sszvip@qq.com
 * 
 */
public class VideoFragment extends BaseFragment {

	private LinearLayout linearLayout;
	VView vv;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater,container,savedInstanceState);
		View rootView = inflater.inflate(R.layout.fragment_video,null);
		linearLayout = (LinearLayout) rootView.findViewById(R.id.line_layout);
		x.view().inject(getActivity());
		vv = new VView(getContext());
		vv.setScalcScene(1,1);

		TouchListView tlv = new TouchListView(getContext()){
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

						com.lostad.applib.util.DialogUtil.showAlertMenu(getActivity(), "设置", new String[]{"重新连接", "修改摄像机", "查看事件", "查看快照", "删除相机"}, new MyCallback<Integer>() {
							@Override
							public void onCallback(Integer data) {
								DialogUtil.showToastCust("heh " + data);
								//设置后 回调

							}
						});
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

		return rootView;
	}

//	@Override
//	public TourList4j loadDataAny(int start, TourList4j tourList4j) {
//		//return  TourManager.getInstance().listTourAll(start);
//
//		TourList4j g4j = null;
//		String index = "Title :"+ start++;
//		List<Tour> list = new ArrayList<Tour>();
//		list.add(new Tour(index,index, null,"Lostad-android framework is ready for u !"));
//		index = "Title :"+ start++;
//		list.add(new Tour(index,index,null,"Lostad-android framework"));
//		index = "Title :"+ start++;
//		list.add(new Tour(index,index,null,"Lostad-android framework"));
//		index = "Title :"+ start++;
//
//		g4j = new TourList4j(true,"success");
//		g4j.list = list;
//		return g4j;
//	}
//
//	@Override
//	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//		super.onItemClick(parent, view, position, id);
//		Intent intent=new Intent();
//		intent.putExtra("msg","mmm");
//		intent.setClass(getActivity(), SelectVideoActivity.class);
////                startActivity(intent);
//		startActivityForResult(intent,0);//跳转并发送请求码
//	}


}
