package com.zdrv.app.service;

import java.util.List;

import com.zdrv.app.domain.Question;
import com.zdrv.app.domain.QuestionImg;

public interface QuestionService {


	List<Question> getKeywordQuestion(String keyword);

	Question getQuestionById(Integer questionId);

	Question getQuestionByIdWithAC(Integer questionId);

	void addQuestion(Question question);

	void updateQuestionStatus(Question question);

	void updateAddQuestionThanks(Question question);

	void updateAddPageViews(Question question);

	void addQuestionImg(QuestionImg questionImg);

	void deleteQuestionByIdWithAC(Integer questionId);


// ページネーション

	List<Question> getQuestionHistoryByPage(String questionUserId, int page, int numPerPage);

	int getTotalPagesQuestionHistory(String questionUserId, int numPerPage);

	List<Question>  getAllQuestionHistory(String questionUserId);


// 質問一覧用(ページネーション)

	List<Question> getQuestionListByPage(String keyword, int page, int numPerPage) throws Exception;

	int getTotalPages2(String keyword, int numPerPage) throws Exception;


// キーワードありで教材タイトル検索(ページネーション含む)

	List<Question> getGroupedBookTitleAndSubCount2(String keyword, int page, int numPerPage) throws Exception;

	int getTotalPagesOfGroupedBookTitle(String keyword, int numPerPage) throws Exception;

	List<Question> getAllGroupedBookTitleByKeyword(String keyword);

}
