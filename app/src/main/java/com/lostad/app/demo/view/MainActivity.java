package com.lostad.app.demo.view;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.lostad.app.base.view.BaseActivity;
import com.lostad.app.base.view.fragment.BaseFragment;
import com.lostad.app.demo.R;
import com.lostad.app.demo.util.vdll.math.CompareUtil;
import com.lostad.app.demo.view.mainFragment.CameraFragment;
import com.lostad.app.demo.view.mainFragment.ImageFragment;
import com.lostad.app.demo.view.mainFragment.IntegrationFragment;
import com.lostad.app.demo.view.mainFragment.IntegrationFragment1;
import com.lostad.app.demo.view.mainFragment.IntegrationFragment2;
import com.lostad.app.demo.view.mainFragment.SettingsFragment;
import com.lostad.app.demo.view.mainFragment.VideoFragment;
import com.lostad.applib.util.DialogUtil;
import com.lostad.applib.util.ReflectUtil;
import com.lostad.applib.util.sys.TokenUtil;
import com.zxing.view.CaptureActivity;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import dalvik.system.DexFile;

/**
 * 主要
 * PrefManager
 */
public class MainActivity extends BaseActivity {

    @ViewInject(R.id.toolbar)
    private Toolbar toolbar;

    private FragmentManager fragmentManager;
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_radiogroup);
        x.view().inject(this);
        super.initToolBar(toolbar);
        setTitle(R.string.app_name);

        fragmentManager = getSupportFragmentManager();
        radioGroup = (RadioGroup) findViewById(R.id.rg_tab);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                changeFragment(checkedId);
            }
        });

        //changeFragment(R.id.rb_0);
        changeFragment(R.id.rb_4);

        //toActivty(TestActivity.class);
    }

    private void changeFragment(int checkedId) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        BaseFragment fragment = getInstanceByIndex(checkedId);
        transaction.replace(R.id.content, fragment);
        transaction.commit();
    }


    private void resetColor(int resId) {
        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            RadioButton radio = (RadioButton) radioGroup.getChildAt(i);
            radio.setTextColor(ContextCompat.getColor(this, R.color.txt_gray));
        }
        RadioButton radio = (RadioButton) findViewById(resId);
        radio.setTextColor(ContextCompat.getColor(this, R.color.bg_title));
    }

    public BaseFragment getInstanceByIndex(int resId) {
        BaseFragment fragment = null;

        resetColor(resId);
        switch (resId) {
            case R.id.rb_0:
                fragment = new IntegrationFragment();
                break;
            case R.id.rb_1:
                fragment = new IntegrationFragment1();
                Bundle b1 = new Bundle();
                b1.putString("type", "0");
                fragment.setArguments(b1);
                break;
            case R.id.rb_2:
                fragment = new IntegrationFragment2();
                Bundle b2 = new Bundle();
                b2.putString("type", "1");
                fragment.setArguments(b2);
                break;
            case R.id.rb_3:
                fragment = new SettingsFragment();
                break;

            //vive
            case R.id.rb_4:
                fragment = new CameraFragment();
                break;
            case R.id.rb_5:
                fragment = new ImageFragment();
                break;
            case R.id.rb_6:
                fragment = new VideoFragment();
                break;
            case R.id.rb_7:
                fragment = new SettingsFragment();
                break;


        }
        return fragment;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_settings, menu);
        menu.clear();
        menu.add(0, 0, 0, "扫描二维码样例");
        menu.add(0, 1, 1, "主界面Drawer");
        menu.add(0, 2, 2, "测试");
        menu.add(0, 3, 3, "测试");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if (item.getGroupId() == 0) {
            int id = item.getItemId();
            switch (id) {
                case 0:
                    Intent i = new Intent(this, CaptureActivity.class);
                    startActivityForResult(i, 0);
                    break;
                case 1:
                    i = new Intent(this, DrawerActivity.class);
                    startActivity(i);
                    break;
                case 2:

                    toActivty(TestActivity.class);
                    break;
                case 3:
                {
                    List<String> className = ReflectUtil.getPackageClassByAndroidAll(getBaseContext(), "com.lostad.applib");
                    StringBuilder sb = new StringBuilder("");
                    for (String c : className) {
                        sb.append(c);
                    }
                    String text = sb.toString();
                    String vs = TokenUtil.entryptPassword(text);
                    boolean ss = TokenUtil.validatePassword(text, vs);
                    System.out.printf("" + ss + " | " + text);
                   // DialogUtil.showToastCust("" + ss + " | " + text);
                }

                    StringBuilder sb = new StringBuilder("");
                    List<Class<?>> packageClasses = ReflectUtil.getPackageClasses("com.lostad.app.demo.view");
                    for (Class c : packageClasses) {
                        Field[] fields = ReflectUtil.getFieldsLocal(ReflectUtil.getClassFromName(c.getName()));
                        for (int j = 0; j < fields.length; j++) {
                            Field f = fields[j];
                            String n = f.getName();
                            if (n != null) {
                                sb.append(n);
                            }
                        }
                    }
                    String text = sb.toString();
                    String vs = TokenUtil.entryptPassword(text);
                    boolean ss = TokenUtil.validatePassword(text, vs);
                    List<String> className = getClassName("com");
                    for (String c : className) {
                        text +=c;
                    }
                    //DialogUtil.showToastCust("" + ss + " | " + text);
                    break;
                default:
                    break;
            }

        }
        return super.onOptionsItemSelected(item);
    }


    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_LONG).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }


    public void checkVersionInfo() {
        new Thread() {
//			Version4j v;
//			public void run() {
//				v = SysManger.getInstance().loadVersion();
//				runOnUiThread(new Runnable() {
//					@Override
//					public void run() {
//						if(v.isSuccess()){
//							int code = PhoneUtil.getVersionCode(context);
//							if(code<v.versioninfo.versionCode){
//								if("1".equals(v.versioninfo.mandatory)){
//									toUpdate(v.versioninfo,"强制升级！");
//								}else{
//									showAlert(v.versioninfo);
//								}
//
//							}else{
//								login();
//							}
//						}else{
//							//showToast(v.errorDesc);
//							login();
//						}
//					}
//
//
//				});
//			};
        }.start();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        String vs = data.getExtras().getString("mmm");
//         vs += data.getExtras().getString("msg");
//        vs += ";";
//        try{
//            List<Fragment> list = fragmentManager.getFragments();
//            for (int i = 0; i < list.size(); i++) {
//                (list.get(i)).onActivityResult(requestCode,resultCode,data);
//            }
//        }
        super.onActivityResult(requestCode, resultCode, data);
        // come.setText(data.getExtras().get("back").toString());//获得返回信息，并刷新UI
    }

    public List<String > getClassName(String packageName){
        List<String> classNameList = ReflectUtil.getPackageClassByAndroidAll(getBaseContext(),packageName);
        return  classNameList;
    }
}
