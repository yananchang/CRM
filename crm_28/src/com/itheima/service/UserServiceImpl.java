package com.itheima.service;

import org.springframework.transaction.annotation.Transactional;

import com.itheima.dao.UserDao;
import com.itheima.domain.User;

/**
 * 用户的业务层
 * @author Yanan Chang
 *
 */
@Transactional
public class UserServiceImpl implements UserService {

	private UserDao userDao;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	/**
	 * 通过登录名进行验证
	 */
	public User checkCode(String user_code) {
		return userDao.checkCode(user_code);
	}
	
	
}
