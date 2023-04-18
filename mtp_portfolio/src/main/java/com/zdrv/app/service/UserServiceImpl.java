package com.zdrv.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zdrv.app.dao.UserDao;
import com.zdrv.app.domain.User;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	UserDao userDao;

	@Autowired
	PasswordEncoder passwordEncoder;


	@Override
	public void registerUser(User user) {

		user.setPass(passwordEncoder.encode(user.getPass()));

		userDao.insertUser(user);
	}

}
