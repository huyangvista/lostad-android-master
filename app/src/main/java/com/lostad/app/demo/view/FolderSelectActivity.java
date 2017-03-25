package com.lostad.app.demo.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lostad.app.base.view.component.BaseHisActivity;
import com.lostad.app.demo.R;
import com.lostad.app.demo.util.view.FileSelectView;
import com.lostad.app.demo.util.view.folder.CallbackBundle;
import com.lostad.app.demo.util.view.folder.OpenFileDialog;
import com.lostad.applib.util.ui.ContextUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hocean on 2017/3/20.
 *
 */
public class FolderSelectActivity extends BaseHisActivity {

    FileSelectView fsv;

    @Override
    public Bundle setResult(Bundle bundle) {
        bundle.putString("path",fsv.getPath());
        return bundle;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_folder_select);

        String defPath = ContextUtil.getActivtyExtra(this).getString("data");


        Map<String, Integer> images = new HashMap<String, Integer>();
        // 下面几句设置各文件类型的图标， 需要你先把图标添加到资源文件夹
        images.put(OpenFileDialog.sRoot, R.mipmap.filedialog_root);    // 根目录图标
        images.put(OpenFileDialog.sParent, R.mipmap.filedialog_folder_up);    //返回上一层的图标
        images.put(OpenFileDialog.sFolder, R.mipmap.filedialog_folder);    //文件夹图标
        images.put("wav", R.mipmap.filedialog_wavfile);    //wav文件图标
        images.put(OpenFileDialog.sEmpty, R.mipmap.filedialog_root);
        fsv = new FileSelectView(this, 0, new CallbackBundle() {
            @Override
            public void callback(Bundle bundle) {

            }
        }, ".wav;",
                images,defPath);

        setBodyContentView(fsv);



    }
}
