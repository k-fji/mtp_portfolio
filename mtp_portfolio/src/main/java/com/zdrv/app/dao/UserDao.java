package com.zdrv.app.dao;

import org.apache.ibatis.annotations.Mapper;

import com.zdrv.app.domain.User;

@Mapper
public interface UserDao {

	User selectByUserId(String userId);

	void insertUser(User user);

}
