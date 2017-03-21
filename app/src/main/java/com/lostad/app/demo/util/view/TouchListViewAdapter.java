package com.lostad.app.demo.util.view;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lostad.app.base.util.DownloadUtil;
import com.lostad.app.demo.R;
import com.lostad.applib.util.Validator;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

public abstract class TouchListViewAdapter extends BaseAdapter {

	private Context mContext;
    private String mType;
	List<Object> mListData = null;

	private LayoutInflater mInflater;
	private String myId;
	public TouchListViewAdapter(String type, Context context, List<Object> list) {
		this.mType = type;
		mContext = context;
		this.mListData = list;

		mInflater =  LayoutInflater.from(mContext);

	}

	public int getCount() {//获取数据集中总个数
		return mListData.size();
	}

	public Object getItem(int position) {//获取数据集中指定索引对应数据项
		return position;
	}

	public long getItemId(int position) {//获取指定对应id
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {//获取每一个Item的显示内容
		Object holder ;
		if (convertView == null) {
			convertView  = loadItemLayout(mInflater); // mInflater.inflate(R.layout.list_item_tour, null);
			holder = loadItemView(convertView); //  = new ViewHolder(convertView);
			convertView.setTag(holder);//缓存view的id控件
		} else {
			holder =  convertView.getTag();//获取缓存view的id控件
		}

		final Object demo = mListData.get(position);
		loadSetItemView(holder,demo);
//		holder.tv_title.setText(f.title );
//		holder.tv_desc.setText(f.desc);
//		if(Validator.isNotEmpty(f.picUrl)){
//			//DownloadUtil.loadImage(holder.iv_pic, IConst.URL_BASE+f.XMTP,R.drawable.loading_frame1,R.mipmap.img_default,R.mipmap.load_fail);
//			DownloadUtil.loadImage(mContext,holder.iv_pic, f.picUrl);
//		}


		return convertView;
	}

	public abstract View loadItemLayout(LayoutInflater inf);
	public abstract Object loadItemView(View con);
	public abstract void loadSetItemView(Object holder, Object demo);


	public void checkAll(){
		notifyDataSetChanged();
	}
	
	

	
	
}
