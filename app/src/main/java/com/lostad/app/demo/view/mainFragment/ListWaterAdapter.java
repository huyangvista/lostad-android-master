package com.lostad.app.demo.view.mainFragment;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lostad.app.base.util.DownloadUtil;
import com.lostad.app.demo.R;
import com.lostad.app.demo.entity.Tour;
import com.lostad.applib.util.Validator;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

public class ListWaterAdapter extends BaseAdapter {

	private Activity mContext;
    private String mType;
	List<Tour> mListData = null;

	private LayoutInflater mInflater;
	private String myId;
	public ListWaterAdapter(String type, Activity context, List<Tour> list) {
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
		ViewHolder holder ;
		if (convertView == null) {
			convertView     = mInflater.inflate(R.layout.list_item_tour, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);//缓存view的id控件
		} else {
			holder = (ViewHolder) convertView.getTag();//获取缓存view的id控件
		} 

		final Tour f = mListData.get(position);
		holder.tv_title.setText(f.title );
		holder.tv_desc.setText(f.desc);
		if(Validator.isNotEmpty(f.picUrl)){
			//DownloadUtil.loadImage(holder.iv_pic, IConst.URL_BASE+f.XMTP,R.drawable.loading_frame1,R.mipmap.img_default,R.mipmap.load_fail);
			DownloadUtil.loadImage(mContext,holder.iv_pic, f.picUrl);
		}


		return convertView;
	}

	
	
	public void checkAll(){
		notifyDataSetChanged();
	}
	
	
	public class ViewHolder {    
		public ViewHolder(View convertView) {

			x.view().inject(this, convertView);
		}
		
		@ViewInject(R.id.iv_pic)
		public ImageView  iv_pic;

		@ViewInject(R.id.tv_title)
		public TextView tv_title;

		@ViewInject(R.id.tv_desc)
		public TextView tv_desc;

	}
	
	
}
