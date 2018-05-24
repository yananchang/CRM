package com.itheima.dao;

import java.util.List;

import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import com.itheima.domain.User;

/**
 * 持久层: 都可以继承HibernateDaoSupport类: 提供了模板类属性,就不用自己手写模板了;
 * @author Yanan Chang
 *
 */
public class UserDaoImpl extends HibernateDaoSupport implements UserDao {

	/**
	 * 通过登录名进行验证
	 */
	public User checkCode(String user_code) {
		List<User> list = (List<User>) this.getHibernateTemplate().find("from User where user_code = ?", user_code);
		if(list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

}
