package com.lostad.app.demo.util.view;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.lostad.app.demo.R;
import com.lostad.app.demo.entity.TouchListViewDataMsg;
import com.lostad.app.demo.entity.Tour;
import com.lostad.applib.view.widget.WaterDropListView;

import net.frakbot.jumpingbeans.JumpingBeans;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hocean on 2017/3/21.
 */

public class TouchListView   implements WaterDropListView.IWaterDropListViewListener,AdapterView.OnItemClickListener {

    @ViewInject(R.id.lv_data)
    private WaterDropListView lv_data;
    //正在加载
    @ViewInject( R.id.tv_loading)
    private TextView tv_loading;
    @ViewInject(R.id.iv_loading)
    private ImageView iv_loading;

    private TouchListViewAdapter mAdapter;

    private List<Object> mListData = null;
    private String mType = null;

    View rootView;
    public View getRootView(){
        return rootView;
    }

    public TouchListView(Context context) {
        rootView = LayoutInflater.from(context).inflate(R.layout.fragment_list_water, null, false);
        x.view().inject(this, rootView);

        mListData= new ArrayList<>();
        mAdapter = new TouchListViewAdapter(mType, context, mListData) {
            @Override
            public View loadItemLayout(LayoutInflater inf) {
                return TouchListView.this.loadItemLayout(inf);
            }

            @Override
            public Object loadItemView(View con) {
                return TouchListView.this.loadItemView(con);
            }

            @Override
            public void loadSetItemView(Object holders, Object demo) {
                TouchListView.this.loadSetItemView(holders,demo);
            }
        };
        lv_data.setAdapter(mAdapter);
        lv_data.setWaterDropListViewListener(this);
        lv_data.setOnItemClickListener(this);
        lv_data.setPullLoadEnable(true);
        //mListData.add(new Tour("测试", "测试", null, "测试哇测试!"));
    }

    /////////////////////////// 需要重写的
    public View loadItemLayout(LayoutInflater inf){
        return inf.inflate(R.layout.list_item_tour, null);
    }
    public Object loadItemView(View con){
        return new ViewHolder(con);
    }
    public void loadSetItemView(Object holders, Object demo){
        final Tour f = (Tour) demo;
        ViewHolder holder = (ViewHolder) holders;
        holder.tv_title.setText(f.title );
        holder.tv_desc.setText(f.desc);
//                if(Validator.isNotEmpty(f.picUrl)){
//                    //DownloadUtil.loadImage(holder.iv_pic, IConst.URL_BASE+f.XMTP,R.drawable.loading_frame1,R.mipmap.img_default,R.mipmap.load_fail);
//                    DownloadUtil.loadImage(mContext,holder.iv_pic, f.picUrl);
//                }
    }

    //点击列表
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
    public TouchListViewDataMsg loadDataAny(int start, TouchListViewDataMsg dataMsg) {

        TouchListViewDataMsg g4j = null;
        String index = "Title :"+ start++;
        List<Object> list = new ArrayList<Object>();
        list.add(new Tour(index,index, null,"Lostad-android framework is ready for u !"));
        index = "Title :"+ start++;
        list.add(new Tour(index,index,null,"Lostad-android framework"));
        index = "Title :"+ start++;
        list.add(new Tour(index,index,null,"Lostad-android framework"));
        index = "Title :"+ start++;

        g4j = new TouchListViewDataMsg(true,"success");
        g4j.list = list;
        return g4j;
    }
    /////////////////////////// 需要重写的

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        loadData(false);
    }

    /**
     * 初始化加载
     */
    @Override
    public void onLoadMore() {
        loadData(true);
    }

    /**
     * 异步加载数据
     * @param isLoadMore
     */
    private void loadData(final boolean isLoadMore) {
        showLoading();
        String url = "";
        new AsyncTask<String,String,TouchListViewDataMsg>(){//启动任务输入参数类型,后台任务执行中返回进度值类型，后台执行任务完成后放回结果类型
            @Override
            protected TouchListViewDataMsg doInBackground(String... params) {//必须重写，异步执行后后台线程将要完成任务
                int start = 0;
                if(isLoadMore){
                    start = mListData.size();
                }
                try{
                    Thread.sleep(100);
                }catch (Exception e){
                    e.printStackTrace();
                }
                //TourList4j g4j = TourManager.getInstance().listTourAll(start);
                TouchListViewDataMsg g4j = loadDataAny(start,new TouchListViewDataMsg(true,"success"));
                return g4j;
            }
            @Override
            protected void onPostExecute(TouchListViewDataMsg g4j) {//当 doInBackground被调用后 系统自动调用 并将doInBackground返回参数传入
                boolean isTheEnd = false ;
                if(g4j.isSuccess()){
                    if(g4j.list.size()==0){
                        isTheEnd = true;
                    }else{
                        if(!isLoadMore){//刷新
                            mListData.clear();
                        }
                        mListData.addAll(g4j.list);
                        mAdapter.notifyDataSetChanged();
                    }
                }
                dismissLoding(isTheEnd);
            }
            @Override
            protected void onCancelled() {
                dismissLoding(true);
            }
        }.execute();
    }



    //demo
    public class ViewHolder extends ItemView{

        @ViewInject(R.id.iv_pic)
        public ImageView  iv_pic;

        @ViewInject(R.id.tv_title)
        public TextView tv_title;

        @ViewInject(R.id.tv_desc)
        public TextView tv_desc;

        @ViewInject(R.id.imageView)
        public ImageView  imageView;


        public ViewHolder(View convertView) {
            super(convertView);
        }
    }

    /**
     * 你的布局文件  对应的  控件
     */
    public class ItemView {
        public ItemView(View convertView) {
            x.view().inject(this, convertView);
        }
    }
    public class ItemText implements Serializable {
        //列表内容
        public String id;
        public String desc;//简介
        public String picUrl;//名称
        public String title;//图片

        public ItemText() {
        }
        public ItemText(String id,String title,String picUrl,String desc) {
            this.id = id;
            this.title = title;
            this.picUrl = picUrl;
            this.desc = desc;
        }

    }

    public List<Object> getmListData() {
        return mListData;
    }

    public void setmListData(List<Object> mListData) {
        this.mListData = mListData;
    }


    //region 效果
    //////////////////加载效果,以下代码可以直接复制粘贴////////////////////////////////////////////////////////////////////////////////
    private void showLoading() {
        if (mListData == null || mListData.size() == 0) {
            iv_loading.setVisibility(View.GONE);

            tv_loading.setVisibility(View.VISIBLE);
            tv_loading.setText("正在加载...");
            mJumpingBeans = JumpingBeans.with(tv_loading)
                    .appendJumpingDots()
                    .build();
        }
    }


    private void dismissLoding(boolean isNoData) {
        try{
            lv_data. stopLoading();
            if(isNoData){
                lv_data.end();
            }
            if(mJumpingBeans!=null){
                mJumpingBeans.stopJumping();
            }
            /// ((AnimationDrawable) iv_loading.getDrawable()).stop();
            if (mListData == null || mListData.size() == 0) {
                iv_loading.setVisibility(View.VISIBLE);
                tv_loading.setVisibility(View.VISIBLE);
                tv_loading.setText("没有数据，点击刷新。");
                iv_loading.setImageResource(R.mipmap.img_no_data);
                iv_loading.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onLoadMore();
                    }
                });
            } else {
                iv_loading.setVisibility(View.GONE);
                tv_loading.setVisibility(View.GONE);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private JumpingBeans mJumpingBeans;
    //endregion
}
