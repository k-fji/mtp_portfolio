package com.zdrv.app.service;

import java.util.List;

import com.zdrv.app.domain.Comment;
import com.zdrv.app.domain.CommentImg;

public interface CommentService {

	void addComment(Comment comment);

	List<Comment> getCommentWithImg(Integer answerId);

	void addCommentImg(CommentImg commentImg);

}
