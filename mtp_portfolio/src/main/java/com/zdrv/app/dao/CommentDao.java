package com.zdrv.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.zdrv.app.domain.Comment;
import com.zdrv.app.domain.CommentImg;

@Mapper
public interface CommentDao {

	void insertComment(Comment comment);

	List<Comment> selectCommentWithImg(Integer answerId);

	void insertCommentImg(CommentImg commentImg);

}
