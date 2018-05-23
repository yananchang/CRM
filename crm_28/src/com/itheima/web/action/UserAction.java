package com.itheima.web.action;

import com.itheima.domain.User;
import com.itheima.service.UserService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 用户的控制器
 * @author Yanan Chang
 *
 */
public class UserAction extends ActionSupport implements ModelDriven<User>{

	private User user = new User();
	
	public User getModel() {
		return user;
	}
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	private UserService userService;

}
