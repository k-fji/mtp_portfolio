package com.zdrv.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.zdrv.app.domain.Favorite;

@Mapper
public interface FavoriteDao {

	List<Favorite> selectAll();

	List<Favorite> selectByUserId(String favoriteUserId);

	void insert(Favorite favorite);

	void delete(Integer favoriteId);
}
