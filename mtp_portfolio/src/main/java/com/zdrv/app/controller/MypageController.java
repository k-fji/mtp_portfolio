package com.zdrv.app.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.zdrv.app.domain.Favorite;
import com.zdrv.app.domain.Question;
import com.zdrv.app.service.AnswerService;
import com.zdrv.app.service.FavoriteService;
import com.zdrv.app.service.QuestionService;

@Controller
@RequestMapping("/mypage")
public class MypageController {

	private static final int NUM_PER_PAGE = 15;

	@Autowired
	private QuestionService questionService;
	@Autowired
	private FavoriteService favoriteService;
	@Autowired
	private AnswerService answerService;


	@RequestMapping("")
	public String showMypage(Model model, Principal principal) {

		String loginUserId = principal.getName();

		model.addAttribute("favorites", favoriteService.getAllFavoriteByUserId(loginUserId));

		model.addAttribute("loginUserId", loginUserId);

		return "mypage";
	}


	@GetMapping("/favorite_edit")
	public String favoriteListGet(Model model,
								  @RequestParam(required = false) String keyword,
								  Principal principal,
								  @RequestParam(name = "page", defaultValue = "1") Integer page) throws Exception {
		List<Question> questions;

		questions = questionService.getGroupedBookTitleAndSubCount2(keyword, page, NUM_PER_PAGE);

		if (keyword == null) {
			questions.clear();
		}

		model.addAttribute("questions", questions);

		model.addAttribute("totalPages", questionService.getTotalPagesOfGroupedBookTitle(keyword, NUM_PER_PAGE));

		model.addAttribute("totalQuestions", questionService.getAllGroupedBookTitleByKeyword(keyword));

		model.addAttribute("page", page);

		model.addAttribute("keyword", keyword);

		String loginUserId = principal.getName();
		model.addAttribute("favorites", favoriteService.getAllFavoriteByUserId(loginUserId));

		model.addAttribute("loginUserId", loginUserId);

		return "favorite_edit";
	}


	@GetMapping("/favorite_edit/add/{favoriteTitle}")
	public String addFavoriteTitle(@PathVariable String favoriteTitle,
								   Principal principal) throws Exception {

		Favorite favorite = new Favorite();
		favorite.setFavoriteTitle(favoriteTitle);

		String loginUserId = principal.getName();
		favorite.setFavoriteUserId(loginUserId);

		favoriteService.addFavoriteTitle(favorite);

		return "redirect:/mypage/favorite_edit";
	}


	@GetMapping("/favorite_edit/delete/{favoriteId}")
	public String deleteFavoriteTitle(@PathVariable Integer favoriteId) {
		favoriteService.deleteFavoriteTitle(favoriteId);

		return "redirect:/mypage/favorite_edit";
	}


	@GetMapping("/question_history")
	public String questionHistory(Model model,
								  Principal principal,
								  @RequestParam(name = "page", defaultValue = "1") Integer page) throws Exception {
		String loginUserId = principal.getName();

		model.addAttribute("questionHistoryList", questionService.getQuestionHistoryByPage(loginUserId, page, NUM_PER_PAGE));

		model.addAttribute("totalPages", questionService.getTotalPagesQuestionHistory(loginUserId, NUM_PER_PAGE));

		model.addAttribute("totalQuestions", questionService.getAllQuestionHistory(loginUserId));

		model.addAttribute("page", page);

		model.addAttribute("loginUserId", loginUserId);

		return "question_history";
	}


	@GetMapping("/answer_history")
	public String answerHistory(Model model,
								Principal principal,
								@RequestParam(name = "page", defaultValue = "1") Integer page) throws Exception {
		String loginUserId = principal.getName();

		model.addAttribute("answerHistoryList", answerService.getAnswerHistoryByPage(loginUserId, page, NUM_PER_PAGE));

		model.addAttribute("totalPages", answerService.getTotalPagesAnswerHistory(loginUserId, NUM_PER_PAGE));

		model.addAttribute("totalAnswers", answerService.getAllAnswerHistory(loginUserId));

		model.addAttribute("page", page);

		model.addAttribute("loginUserId", loginUserId);

		return "answer_history";
	}

}
