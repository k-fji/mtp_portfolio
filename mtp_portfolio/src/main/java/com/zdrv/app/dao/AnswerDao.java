package com.zdrv.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.zdrv.app.domain.Answer;
import com.zdrv.app.domain.AnswerImg;

@Mapper
public interface AnswerDao {

	void insertAnswer(Answer answer);

	Answer selectAnswerWithComments(Integer answerId);

	List<Answer> selectAnswerWithImg(Integer questionId);

	void insertAnswerImg(AnswerImg answerImg);


// マイページ回答履歴用(ページネーション)

	List<Answer> selectAnswerHistoryByPage(String answerUserId, int offset, int num);

	Long countAnswerHistory(String answerUserId);

	List<Answer> selectAllAnswerHistory(String answerUserId);


}
