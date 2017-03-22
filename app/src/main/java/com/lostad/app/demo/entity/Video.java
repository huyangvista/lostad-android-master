package com.lostad.app.demo.entity;

import com.lostad.app.base.entity.BaseEntity;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by Hocean on 2017/3/21.
 */
@Table(name="Video")
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMacname() {
        return macname;
    }

    public void setMacname(String macname) {
        this.macname = macname;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }
}
