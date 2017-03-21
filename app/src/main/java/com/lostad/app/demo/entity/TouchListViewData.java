package com.lostad.app.demo.entity;

import com.lostad.applib.entity.BaseBeanRsult;

import java.util.List;

/**
 * Tour
 */
public class TouchListViewData extends BaseBeanRsult {
	public List<Object> list;

	private TouchListViewData() {}
	public TouchListViewData(boolean isSuccess, String msg) {
		super(isSuccess,msg);
	}


}
