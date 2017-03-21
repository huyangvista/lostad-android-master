package com.lostad.app.base.entity;

import org.xutils.db.annotation.Column;

import java.io.Serializable;

/**
 * Created by Hocean on 2017/3/21.
 */

public class BaseEntity implements Serializable{
    @Column(name="createDate")
    public Double createDate;
    @Column(name="createBy")
    public String createBy;
    @Column(name="updateDate")
    public Double updateDate;
    @Column(name="updateBy")
    public String updateBy;
    @Column(name="remark")
    public String remark;
    @Column(name="delFlag")
    public String delFlag;

    public Double getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Double createDate) {
        this.createDate = createDate;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Double getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Double updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }
}
