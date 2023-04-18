package com.zdrv.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zdrv.app.dao.QuestionDao;
import com.zdrv.app.domain.Question;
import com.zdrv.app.domain.QuestionImg;

@Service
@Transactional
public class QuestionServiceImpl implements QuestionService {

	@Autowired
	private QuestionDao questionDao;

	@Override
	public List<Question> getKeywordQuestion(String keyword) {
		return questionDao.selectByKeyword(keyword);
	}

	@Override
	public Question getQuestionById(Integer questionId) {
		return questionDao.selectQuestionById(questionId);
	}

	@Override
	public Question getQuestionByIdWithAC(Integer questionId) {
		return questionDao.selectQuestionByIdWithAC(questionId);
	}

	@Override
	public void addQuestion(Question question) {
		questionDao.insert(question);
	}

	@Override
	public void updateQuestionStatus(Question question) {
		questionDao.updateStatus(question);
	}

	@Override
	public void updateAddQuestionThanks(Question question) {
		questionDao.updateQuestionThanks(question);
	}

	@Override
	public void updateAddPageViews(Question question) {
		questionDao.updatePageViews(question);
	}

	@Override
	public void addQuestionImg(QuestionImg questionImg) {
		questionDao.insertQuestionImg(questionImg);
	}

	@Override
	public void deleteQuestionByIdWithAC(Integer questionId) {
		questionDao.deleteQuestionByIdWithAC(questionId);
	}


// マイページの質問履歴用(ページネーション)

	@Override
	public List<Question> getQuestionHistoryByPage(String questionUserId, int page, int numPerPage) {
		int offset = numPerPage * (page - 1);
		return questionDao.selectQuestionHistoryByPage(questionUserId, offset, numPerPage);
	}

	@Override
	public int getTotalPagesQuestionHistory(String questionUserId, int numPerPage){
		double totalNum = (double) questionDao.countQuestionHistory(questionUserId);
		return (int) Math.ceil(totalNum / numPerPage);
	}

	@Override
	public List<Question> getAllQuestionHistory(String questionUserId) {
		return questionDao.selectAllQuestionHistory(questionUserId);
	}


// 質問一覧用(ページネーション)

	@Override
	public List<Question> getQuestionListByPage(String keyword, int page, int numPerPage) throws Exception {
		int offset = numPerPage * (page - 1);
		return questionDao.selectQuestionListByPage(keyword, offset, numPerPage);
	}

	@Override
	public int getTotalPages2(String keyword, int numPerPage) throws Exception {
		double totalNum = (double) questionDao.countQuestions(keyword);
		return (int) Math.ceil(totalNum / numPerPage);
	}


// キーワードありで教材タイトル検索(ページネーション含む)

	@Override
	public List<Question> getGroupedBookTitleAndSubCount2(String keyword, int page, int numPerPage) throws Exception {
		int offset = numPerPage * (page - 1);
		return questionDao.selectGroupedBookTitleAndSubCount2(keyword, offset, numPerPage);
	}

	@Override
	public int getTotalPagesOfGroupedBookTitle(String keyword, int numPerPage) throws Exception {
		double totalNum = (double) questionDao.countGroupedBookTitleByKeyword(keyword);
		return (int) Math.ceil(totalNum / numPerPage);
	}

	@Override
	public List<Question> getAllGroupedBookTitleByKeyword(String keyword) {
		return questionDao.selectAllGroupedBookTitleByKeyword(keyword);
	}

}
