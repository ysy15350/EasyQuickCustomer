package com.ysy15350.easyquickcustomer.login;

import api.base.model.Response;

public interface LoginViewInterface {

	public void userLoginCallBack(Response response);

	public void user_infoCallback(boolean isCache, Response response);

}
