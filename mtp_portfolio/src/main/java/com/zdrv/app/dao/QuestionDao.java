package com.zdrv.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.zdrv.app.domain.Question;
import com.zdrv.app.domain.QuestionImg;

@Mapper
public interface QuestionDao {

	List<Question> selectByKeyword(String keyword);

	Question selectQuestionById(Integer questionId);

	Question selectQuestionByIdWithAC(Integer questionId);

	void insert(Question question);

	void updateStatus(Question question);

	void updateQuestionThanks(Question question);

	void updatePageViews(Question question);

	void insertQuestionImg(QuestionImg questionImg);

	void deleteQuestionByIdWithAC(Integer questionId);


// マイページの質問履歴用(ページネーション)

	List<Question> selectQuestionHistoryByPage(String questionUserId, int offset, int num);

	Long countQuestionHistory(String questionUserId);

	List<Question> selectAllQuestionHistory(String questionUserId);


// <質問用(ページネーション)

	List<Question> selectQuestionListByPage(String keyword, int offset, int num) throws Exception;

	Long countQuestions(String keyword) throws Exception;


// 教材タイトル用(ページネーション含む)

	List<Question> selectGroupedBookTitleAndSubCount2(String keyword, int offset, int num) throws Exception;

	Long countGroupedBookTitleByKeyword(String keyword) throws Exception;

	List<Question> selectAllGroupedBookTitleByKeyword(String keyword);

}
