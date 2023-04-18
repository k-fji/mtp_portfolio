package com.zdrv.app.dao;

import org.apache.ibatis.annotations.Mapper;

import com.zdrv.app.domain.Account;

@Mapper
public interface LoginMapper {

	public Account findAccount(String userId);

}
