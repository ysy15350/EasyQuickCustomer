package com.ysy15350.easyquickcustomer.author;

import api.base.model.Response;

public interface RegisterViewInterface {

	public void getTelVerifyCallback(Response response);

	/**
	 * 注册回调
	 * 
	 * @param response
	 */
	public void registerCallback(Response response);

	public void userLoginCallBack(Response response);

}
