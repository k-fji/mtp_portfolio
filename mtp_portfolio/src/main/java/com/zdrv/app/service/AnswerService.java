package com.zdrv.app.service;

import java.util.List;

import com.zdrv.app.domain.Answer;
import com.zdrv.app.domain.AnswerImg;

public interface AnswerService {

	void addAnswer(Answer answer);

	Answer getAnswerWithComments(Integer answerId);

	List<Answer> getAnswerWithImg(Integer questionId);

	void addAnswerImg(AnswerImg answerImg);


	List<Answer> getAnswerHistoryByPage(String answerUserId, int page, int numPerPage);

	public int getTotalPagesAnswerHistory(String answerUserId, int numPerPage);

	public List<Answer>  getAllAnswerHistory(String answerUserId);

}
