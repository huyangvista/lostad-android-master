package com.lostad.app.demo.util.view;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lostad.app.demo.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 列表构建
 * Created by Hocean on 2016/11/23.
 */
public class ListViewBuild extends BaseAdapter {
    public static enum ESort{
        sort, // 顺序排列
        shuffle, // 混乱的意思
        reverse; // 倒序排列
    }
    ESort sort = ESort.sort;
    private LayoutInflater mInflater; // 声明一个LayoutInflater对象（其作用是用来实例化布局）
    private List<Parms> parms;  //项参数

    public List<Parms> getParms() {
        return parms;
    }

    public void setParms(List<Parms> parms) {
        this.parms = parms;
    }
    public void addParms(Parms p) {
        parms.add(p);
    }
    public void addObjectParms(Object... o) {
        Parms p = createParms();
        p.adds(o);
        parms.add(p);
    }
    public void addParmsIndex(int index, Parms p) {
        parms.add(index, p);
    }
    public void addObjectParmsIndex(int index, Object... o) {
        Parms p = createParms();
        p.adds(o);
        parms.add(index,p);
    }


    public static Parms createParms(){
        return new Parms();
    }

    public ListViewBuild(Context context){
        mInflater = LayoutInflater.from(context);
        parms = new ArrayList<>();
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return parms.size();// 返回ListView项的长度
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Parms par = parms.get(position);
        convertView = mInflater.inflate(par.getLayoutId(), null);

        for (Map<String, Object> map  :par.getList() ) {
            View view =  convertView.findViewById(Integer.parseInt(map.get("id").toString()));
            if (view instanceof  TextView){
                ((TextView)view).setText(map.get("value").toString());
                if ( map.containsKey("color")   ){
                    ((TextView)view).setTextColor(Integer.parseInt(map.get("color").toString()));
                }
            }
            else if (view instanceof  ImageView){
                ((ImageView)view).setImageResource(Integer.parseInt(map.get("value").toString()));  // android.R.drawable.ic_notification_overlay  android.R.drawable.presence_online
            }
//            Iterator<Map.Entry<String, Object>> it =  map.entrySet().iterator();
//            while (it.hasNext()){
//                Map.Entry<String, Object> en = it.next();
//                String k = en.getKey();
//                Object v = en.getValue();
//
//                if (pages instanceof  TextView){
//                    ((TextView)pages).setText(v.toString());
//                }else if ( pages instanceof ImageView){
//
//                }
//            }
        }
        return convertView;
    }

    public ESort getSort() {
        return sort;
    }

    public void setSort(ESort sort) {
        this.sort = sort;
        switch (sort){
            case sort:
                //Collections.sort(parms); // 顺序排列
                break;
            case reverse:
                Collections.reverse(parms); // 倒序
                break;
            case  shuffle:
                Collections.shuffle(parms); // 随机顺序
                break;
        }
    }

    public void show(ListView lv){
        lv.setAdapter(this);
    }

    public static class Parms {
        private int layoutId = R.layout.fragment_main_listview_item;  //项ID
        private List<Map<String,Object>> list = new ArrayList<>();

        public void adds(Object... v){
            getList().add(createMap(v));
        }
        public void adds(int index, Object... v){
            getList().add(index,createMap(v));
        }

        public int getLayoutId() {
            return layoutId;
        }

        public void setLayoutId(int layoutId) {
            this.layoutId = layoutId;
        }

        @SuppressWarnings("unchecked")
        @SafeVarargs
        public static <K, V> Map<K, V> createMapBase(V... v) {
            Map<K, V> map = new HashMap<>();
            for (int i = 0; i < v.length; i += 2)
                map.put((K) v[i], v[i + 1]);
            return map;
        }
        /**
         * 快速 键值对
         * @return
         */
        public static Map<String, Object> createMap(Object... v) {
            return createMapBase(v);
        }

        public List<Map<String, Object>> getList() {
            return list;
        }

        public void setList(List<Map<String, Object>> list) {
            this.list = list;
        }
    }
}

//demo
/*
        ListView lv = new ListView(pages.getContext());  //列表
        ListViewBuild lvb = new ListViewBuild(pages.getContext());  //列表工具
        ListViewBuild.Parms parms = new ListViewBuild.Parms();  //参数
        parms.setLayoutId(R.layout.fragment_main_listview_item_title);  //设置布局文件
        parms.adds("id","" + R.id.tv_title_main_listview_item_title, "value", "请选择出发时间"); //每行参数
        lvb.getParms().add(parms); //设置到列表工具
        parms = new ListViewBuild.Parms();
        parms.setLayoutId(R.layout.fragment_main_listview_item);
        parms.adds("id","" + R.id.tv_title_main_listview_item, "value", "天津滨海机场");
        lvb.getParms().add(parms);

        parms = new ListViewBuild.Parms();
        parms.setLayoutId(R.layout.fragment_main_listview_item);
        parms.adds("id","" + R.id.tv_title_main_listview_item, "value", "您要去哪","color",0xffff8800);
        parms.adds("id",R.id.img_title_main_listview_item, "value", android.R.drawable.ic_notification_overlay);
        lvb.getParms().add(parms);

        lvb.show(lv);  //显示列表
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() { //列表回调
            @Override
            public void onItemClick(AdapterView<?> parent, View pages, int position, long id) { //滑动页每项侦听
                //DialogBuild.toast(position);
                if (position == 0 ){
                    TimeView.date(MainFragment.this,FRAG_TAG_DATE_PICKER);
                }else if(position == 1){
                    PushMainActivity.that.dragLayout.open();
                }
            }
        });
 */
