package com.zdrv.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zdrv.app.dao.CommentDao;
import com.zdrv.app.domain.Comment;
import com.zdrv.app.domain.CommentImg;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentDao commentDao;

	@Override
	public void addComment(Comment comment) {
		commentDao.insertComment(comment);
	}

	@Override
	public List<Comment> getCommentWithImg(Integer answerId) {
		return commentDao.selectCommentWithImg(answerId);
	}

	@Override
	public void addCommentImg(CommentImg commentImg) {
		commentDao.insertCommentImg(commentImg);
	}


}
