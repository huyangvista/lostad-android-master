package com.lostad.app.demo;

import android.os.Environment;

/**
 * 存放公共变量
 */
public interface IConst {

    //region Base
    String PATH_ROOT = Environment.getExternalStorageDirectory().toString() + "/1_tour";
    String DB_NAME = "tour_0107.db";
    int DB_VER_NUM = 1;
    String URL_BASE = "http://192.168.2.104:8080";//"
    String URL_SERVICE = URL_BASE + "/test/serviceBroker.do";//
    String KEY_GIS_PROVINCE = "province";
    String KEY_GIS_CITY = "city";
    String KEY_GIS_DISTRICT = "district";
    String KEY_GIS_LATITUDE = "latitude";
    String KEY_GIS_LONGTITUDE = "longitude";
    int VALUE_ROWS = 10;//分页，一次加载15条
    //   String  ALIYUN_OSS_AK1            = "";
//   String  ALIYUN_OSS_SK             = "";
//   String  GlobalDefaultHostId       = "oss-cn-qingdao.aliyuncs.com";
//   String  ALIYUN_OSS_URL_ENDPOINT   = "http://"+GlobalDefaultHostId+"/";
    String ALIYUN_OSS_BUCKET_NAME = "test";
    String ALIYUN_OSS_KEY_PREFIX_HEDAD = "pber/head";
    String ALIYUN_OSS_KEY_PREFIX_SHARE = "pber/share";
    String ALIYUN_OSS_KEY_PREFIX_SPORT = "pber/hd";
    String URL_IMG_SPORT = "http://test.oss-cn-hangzhou.aliyuncs.com/pber/hd.jpg";
    //socket广播
    String ACTION_SOCKET_DATA = "action_socket_data";
    String PACKAGE_TYPE_GROUP = "1004_TG";
    String API_PROTOCOL = "";
    String API_LOGIN = "/user/login";
    String API_REGISTER = "/user/register";
    String API_USER_UPDATE = "/user/modify";
    String API_PWD_UPDATE = "/user/modifyPsw";//phone/old/new
    String API_PWD_FIND = "/user/resetPsw";//phone/new
    //endregion

    //region MyVideoApp
    String APP_FIRST = "APP_FIRST";
    String KEY_PATH_VIDEOS = "/Videos";
    String KEY_PATH_IMAGES = "/Images";

    //endregion

    //临时保存变量
    public interface Path {
    }
}
/**
 * http://121.42.25.194:88/vtSell/getGgxxApi
 * <p>
 * http://121.42.25.194:88//vtSell/getSpxxAjava.lang.Stringpi
 * <p>
 * http://121.42.25.194:88//vtSell/getSpPjxxApi
 */