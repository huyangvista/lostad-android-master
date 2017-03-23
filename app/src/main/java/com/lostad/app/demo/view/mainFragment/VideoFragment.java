package com.lostad.app.demo.view.mainFragment;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.lostad.app.base.view.fragment.BaseFragment;
import com.lostad.app.demo.IConst;
import com.lostad.app.demo.MyApplication;
import com.lostad.app.demo.R;
import com.lostad.app.demo.entity.TouchListViewDataMsg;
import com.lostad.app.demo.entity.Video;
import com.lostad.app.demo.util.view.TouchListView;
import com.lostad.app.demo.view.AddCameraActivity;
import com.lostad.app.demo.view.FolderSelectActivity;
import com.lostad.app.demo.view.MainActivity;
import com.lostad.applib.core.MyCallback;
import com.lostad.applib.util.sys.PrefManager;
import com.lostad.applib.util.ui.ContextUtil;
import com.lostad.applib.util.ui.DialogUtil;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import h264.com.VView;

/**
 * @author sszvip@qq.com
 */
public class VideoFragment extends BaseFragment {

    private LinearLayout linearLayout;

    private String videoPath = IConst.PATH_ROOT + IConst.KEY_PATH_VIDEOS; //默认保存位置

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        videoPath = PrefManager.getString(getContext(), IConst.KEY_PATH_VIDEOS, videoPath);

        View rootView = inflater.inflate(R.layout.fragment_video, null);
        linearLayout = (LinearLayout) rootView.findViewById(R.id.line_layout);
        x.view().inject(getActivity());


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
                final File f = (File) demo;
                ViewHolder holder = (ViewHolder) holders;
                holder.tv_title.setText(f.getName());
                holder.tv_desc.setText(f.getPath());
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
                                                break;
                                            case 2: //查看事件
                                                break;
                                            case 3: //查看快照
                                                break;
                                            case 4: //删除相机
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

                    File[] files = new File(videoPath).listFiles();

                    //载入 list
                    //List<Video> videos = db.findAll(Video.class);
                    if (files != null && start < files.length) {
                        for (int i = start; i < files.length; i++) {
                            list.add(files[i]);
                        }
                    }

                } catch (Exception e) {
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
        menu.add(3, 0, 0, "保存地址");
        menu.add(3, 1, 1, "设置");
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getGroupId() == 3) {
            switch (item.getItemId()) {
                case 0:
//                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                    intent.addCategory(Intent.CATEGORY_OPENABLE);
//                    intent.setType("folder /*");//设置类型，我这里是任意类型，任意后缀的可以这样写。
////                    intent.setType("*/*");//设置类型，我这里是任意类型，任意后缀的可以这样写。
////                    intent.setType("image/*");
////                    intent.setType("audio/*"); //选择音频
////                    intent.setType("video/*"); //选择视频 （mp4 3gp 是android支持的视频格式）
////                    intent.setType("video/*;image/*");//同时选择视频和图片
//                    // ContextUtil.toActivtyResult(getActivity(), AddCameraActivity.class);
//                    startActivityForResult(intent, 1);

                    Map<String, Serializable> map = new HashMap<>();
                    map.put("data", videoPath);
                    ContextUtil.toActivtyResult(this, map, FolderSelectActivity.class);
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
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (resultCode == Activity.RESULT_OK) {//是否选择，没选择就不会继续
            // String path = data.getData().getPath();

            String path = data.getExtras().getString("path");
            DialogUtil.showToastCust("已选择:" + path);
            videoPath = path;
            PrefManager.saveString(getContext(), IConst.KEY_PATH_VIDEOS, path);


//            Uri uri = data.getData();//得到uri，后面就是将uri转化成file的过程。
//            String[] proj = {MediaStore.Images.Media.DATA};
//            Cursor actualimagecursor = managedQuery(uri, proj, null, null, null);
//            int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//            actualimagecursor.moveToFirst();
//            String img_path = actualimagecursor.getString(actual_image_column_index);
//            File file = new File(img_path);
        }


        super.onActivityResult(requestCode, resultCode, data);
    }

}
