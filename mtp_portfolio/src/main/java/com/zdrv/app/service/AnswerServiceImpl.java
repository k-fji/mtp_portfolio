package com.zdrv.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zdrv.app.dao.AnswerDao;
import com.zdrv.app.domain.Answer;
import com.zdrv.app.domain.AnswerImg;

@Service
@Transactional
public class AnswerServiceImpl implements AnswerService {

	@Autowired
	private AnswerDao answerDao;

	@Override
	public void addAnswer(Answer answer) {
		answerDao.insertAnswer(answer);
	}

	@Override
	public Answer getAnswerWithComments(Integer answerId) {
		return answerDao.selectAnswerWithComments(answerId);
	}

	@Override
	public List<Answer> getAnswerWithImg(Integer questionId) {
		return answerDao.selectAnswerWithImg(questionId);
	}

	@Override
	public void addAnswerImg(AnswerImg answerImg) {
		answerDao.insertAnswerImg(answerImg);
	}


// <マイページの回答履歴用(ページネーション)>

	@Override
	public List<Answer> getAnswerHistoryByPage(String answerUserId, int page, int numPerPage) {
		int offset = numPerPage * (page - 1);
		return answerDao.selectAnswerHistoryByPage(answerUserId, offset, numPerPage);
	}

	@Override
	public int getTotalPagesAnswerHistory(String answerUserId, int numPerPage) {
		double totalNum = (double) answerDao.countAnswerHistory(answerUserId);
		return (int) Math.ceil(totalNum / numPerPage);
	}

	@Override
	public List<Answer> getAllAnswerHistory(String answerUserId) {
		return answerDao.selectAllAnswerHistory(answerUserId);
	}

}
