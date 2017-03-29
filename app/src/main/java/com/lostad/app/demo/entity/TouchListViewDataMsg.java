package com.lostad.app.demo.entity;

import com.lostad.applib.entity.BaseBeanRsult;

import java.util.List;

/**
 * Hocean 哈懂列表数据项
 */
public class TouchListViewDataMsg extends BaseBeanRsult {
	public List<Object> list;

	private TouchListViewDataMsg() {}
	public TouchListViewDataMsg(boolean isSuccess, String msg) {
		super(isSuccess,msg);
	}


}
