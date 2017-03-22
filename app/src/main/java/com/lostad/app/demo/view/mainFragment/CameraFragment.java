package com.lostad.app.demo.view.mainFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.lostad.app.base.view.fragment.BaseFragment;
import com.lostad.app.demo.MyApplication;
import com.lostad.app.demo.R;
import com.lostad.app.demo.entity.TouchListViewDataMsg;
import com.lostad.app.demo.entity.Video;
import com.lostad.app.demo.util.view.TouchListView;
import com.lostad.app.demo.view.AddCameraActivity;
import com.lostad.applib.core.MyCallback;
import com.lostad.applib.util.ui.ContextUtil;
import com.lostad.applib.util.ui.DialogUtil;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import h264.com.VView;

/**
 * @author sszvip@qq.com
 */
public class CameraFragment extends BaseFragment {

    private LinearLayout linearLayout;
    VView vv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_camera, null);
        linearLayout = (LinearLayout) rootView.findViewById(R.id.line_layout);
        x.view().inject(getActivity());
        vv = new VView(getContext());
        vv.setScalcScene(1, 1);

        TouchListView tlv = new TouchListView(getContext()) {
            @Override
            public View loadItemLayout(LayoutInflater inf) {
                return inf.inflate(R.layout.list_item_touchlistview, null);
            }

            @Override
            public Object loadItemView(View con) {
                return new ViewHolder(con);
            }

            @Override
            public void loadSetItemView(Object holders, Object demo) {
                final Video video = (Video) demo;
                ViewHolder holder = (ViewHolder) holders;
                holder.tv_title.setText(video.uid);
                holder.tv_desc.setText(video.macname);
                holder.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        com.lostad.applib.util.DialogUtil.showAlertMenu(getActivity(), "设置",
                                new String[]{"重新连接", "修改摄像机", "查看事件", "查看快照", "删除相机"}, new MyCallback<Integer>() {
                                    @Override
                                    public void onCallback(Integer data) {
                                        DialogUtil.showToastCust("heh " + data);
                                        //设置后 回调
                                        switch (data) {
                                            case 0: //重新连接
                                                break;
                                            case 1: //修改摄像机
                                                Map<String, Video> map = new HashMap<>();
                                                map.put("data", video);
                                                ContextUtil.toActivtyResult(getActivity(), map, AddCameraActivity.class);
                                                break;
                                            case 2: //查看事件
                                                break;
                                            case 3: //查看快照
                                                break;
                                            case 4: //删除相机
                                                DbManager db = MyApplication.getInstance().getDb();
                                                try {
                                                    db.delete(video);
                                                } catch (DbException e) {
                                                    e.printStackTrace();
                                                }
                                                break;
                                        }
                                    }
                                });
                    }
                });
            }

            @Override
            public TouchListViewDataMsg loadDataAny(int start, TouchListViewDataMsg dataMsg) {

                DbManager db = MyApplication.getInstance().getDb();
                List<Object> list = new ArrayList<>();
                try {
                    List<Video> videos = db.findAll(Video.class);
                    if (videos != null && start < videos.size()) {
                        for (int i = start; i < videos.size(); i++) {
                            Video v = videos.get(i);
                            list.add(v);
                        }
                    }
                } catch (DbException e) {
                    e.printStackTrace();
                }
                //g4j = new TouchListViewDataMsg(true,"success");
                dataMsg.list = list;
                return dataMsg;
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        menu.add(2, 0, 0, "增加");
        menu.add(2, 1, 1, "关于");
        menu.add(2, 2, 2, "退出");
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getGroupId() == 2) {
            switch (item.getItemId()) {
                case 0:
                    ContextUtil.toActivtyResult(getActivity(), AddCameraActivity.class);
                    break;
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                default:
                    break;
            }
        }
        return super.onOptionsItemSelected(item);
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
