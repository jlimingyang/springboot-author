package com.lostad.app.common.vo;

import java.util.List;

/**
 * $.ajax后需要接受的JSON 一体机接口返回值
 * 
 * @author sszvip
 * 
 */
public class JsonRe {
	private int error = 0;// 是(0),否(1),没有结果集(2) ,凭证不正常（-1）
	private String msg = "操作成功";// 提示信息
	private Object obj = null;// 其他信息
    private boolean gzip = false; //是否使用压缩模式
	public JsonRe() {
	}

	public JsonRe(int error, String msg, Object obj) {
		this.error = error;
		this.msg = msg;
		this.obj = obj;
//    未查询到数据，返回空的list,非异常情况，
//		if (obj instanceof List) {
//			if (((List) obj).size() <= 0) {
//				this.success = 2;
//				this.msg = "未查询到结果集";
//			}
//		}
	}
	public JsonRe(int error, String msg, Object obj,boolean isGzip) {
		this.error = error;
		this.msg = msg;
		this.obj = obj;
		this.gzip = isGzip;
//    未查询到数据，返回空的list,非异常情况，
//		if (obj instanceof List) {
//			if (((List) obj).size() <= 0) {
//				this.success = 2;
//				this.msg = "未查询到结果集";
//			}
//		}
	}
	public JsonRe(int error, String msg, Object obj,String token) {
		this.error = error;
		this.msg = msg;
		this.obj = obj;
	}
	

	
	public boolean isGzip() {
		return gzip;
	}

	public void setGzip(boolean gzip) {
		this.gzip = gzip;
	}


	/**
	 * @return the error
	 */
	public int getError() {
		return error;
	}

	/**
	 * @param error the error to set
	 */
	public void setError(int error) {
		this.error = error;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

}
