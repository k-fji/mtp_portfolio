package com.zdrv.app.service;

import java.util.List;

import com.zdrv.app.domain.Favorite;

public interface FavoriteService {

	List<Favorite> getAllFavorite();

	List<Favorite> getAllFavoriteByUserId(String favoriteUserId);

	void addFavoriteTitle(Favorite favorite);

	void deleteFavoriteTitle(Integer favoriteId);
}
