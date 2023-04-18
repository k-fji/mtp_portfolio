package com.zdrv.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zdrv.app.dao.FavoriteDao;
import com.zdrv.app.domain.Favorite;

@Service
@Transactional
public class FavoriteServiceImpl implements FavoriteService {

	@Autowired
	private FavoriteDao favoriteDao;

	@Override
	public List<Favorite> getAllFavorite() {
		return favoriteDao.selectAll();
	}

	@Override
	public List<Favorite> getAllFavoriteByUserId(String favoriteUserId) {
		return favoriteDao.selectByUserId(favoriteUserId);
	}

	@Override
	public void addFavoriteTitle(Favorite favorite) {
		favoriteDao.insert(favorite);
	}

	@Override
	public void deleteFavoriteTitle(Integer favoriteId) {
		favoriteDao.delete(favoriteId);
	}

}
