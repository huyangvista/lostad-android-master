package com.lostad.app.demo.view.mainFragment.joblog;

import java.util.List;

/**
 * Created by Hocean on 2016-09-09.
 */
public class Page {
    public static class TbiJobLog {
        public String id;
        public boolean isNewRecord;
        public String createBy;
        public String createDate;
        public String updateBy;
        public String updateDate;

        //工号
        public String jobNo;
        //部门
        public String department;
        //姓名
        public String userName;
        //日期
        //@DateTimeFormat(pattern="yyyy-MM-dd")
        public String jobDate;
        //开始时间
        public String startTime;
        //结束时间
        public String endTime;
        //工作分类
        public String jobType;
        //工作内容
        public String job;
        //工作方式
        public String jobStyle;
        //工作结果
        public String jobResult;
        //所需时间
        public String requiredTime;
        //独立/协作
        public String oneTwo;

        //确认人
        public String confirmPerson;
        //备注
        public String remark;
        //状态
        public String logStatus;


    }


    int pageCount;
    int pageTotal;
    int pageSize;
    int pageNum;

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getPageTotal() {
        return pageTotal;
    }

    public void setPageTotal(int pageTotal) {
        this.pageTotal = pageTotal;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public List<TbiJobLog> getList() {
        return list;
    }

    public void setList(List<TbiJobLog> list) {
        this.list = list;
    }

    List<TbiJobLog> list;
}