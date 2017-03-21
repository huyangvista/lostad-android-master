package com.lostad.app.demo.entity;

import com.lostad.app.base.entity.BaseEntity;

import org.xutils.db.annotation.Column;

/**
 * Created by Hocean on 2017/3/21.
 */

public class Video  extends BaseEntity {
    @Column(name = "id", isId = true, autoGen = true)
    public long id;

    //连接
    @Column(name="uid")
    public String uid;
    @Column(name="username")
    public String username;
    @Column(name="password")
    public String password;
    @Column(name="macname")
    public String macname;


    //类型
    @Column(name="tag")
    public String tag;
    @Column(name="type")
    public String type;
    @Column(name="txt")
    public String txt;

}
