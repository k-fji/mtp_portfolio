package com.zdrv.app.controller;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.zdrv.app.domain.Question;
import com.zdrv.app.domain.QuestionImg;
import com.zdrv.app.helper.SDKUploadHelper;
import com.zdrv.app.service.QuestionService;

@Controller
@RequestMapping("/questions")
public class QuestionController {

    @Autowired
    SDKUploadHelper sdkUploadHelper;

	@Autowired
	private QuestionService questionService;

	private static final int NUM_PER_PAGE = 15;


	@GetMapping("/list")
	public String list(Model model,
						@RequestParam(required = false) String keyword,
					    @RequestParam(name = "page", defaultValue = "1") Integer page) throws Exception {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String loginUserId = auth.getName();
		model.addAttribute("loginUserId", loginUserId);

		model.addAttribute("questions", questionService.getQuestionListByPage(keyword, page, NUM_PER_PAGE));

		model.addAttribute("totalPages", questionService.getTotalPages2(keyword, NUM_PER_PAGE));

		model.addAttribute("totalQuestions", questionService.getKeywordQuestion(keyword));

		model.addAttribute("page", page);

		model.addAttribute("keyword", keyword);

		return "questions/list";
	}


	@GetMapping("/book_title_list")
	public String allBookTitleList(Model model,
								   @RequestParam(required = false) String keyword,
								   @RequestParam(name = "page", defaultValue = "1") Integer page) throws Exception {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String loginUserId = auth.getName();
		model.addAttribute("loginUserId", loginUserId);

		model.addAttribute("questions", questionService.getGroupedBookTitleAndSubCount2(keyword, page, NUM_PER_PAGE));

		model.addAttribute("totalPages", questionService.getTotalPagesOfGroupedBookTitle(keyword, NUM_PER_PAGE));

		model.addAttribute("totalQuestions", questionService.getAllGroupedBookTitleByKeyword(keyword));

		model.addAttribute("page", page);

		model.addAttribute("keyword", keyword);

		return "booktitle_list";
	}


	@GetMapping("/details/{id}")
	public String questionDetailsGet(@PathVariable Integer id,
									 Model model,
									 Principal principal,
									 HttpSession session,
									 HttpServletResponse httpServletResponse,
									 @CookieValue(name = "viewData", required = false, defaultValue = "noCountView") String cookieValue) {

		Cookie cookie = new Cookie("viewData", "viewed");
		cookie.setPath("/questions/details/" + id);
		cookie.setMaxAge(60*60*24);

		httpServletResponse.addCookie(cookie);

		if (!cookieValue.equals("viewed") ) {

		Question question = questionService.getQuestionById(id);
		int pageView = question.getPageViews();
		pageView += 1;
		question.setPageViews(pageView);
		questionService.updateAddPageViews(question);

		}

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String loginUserId = auth.getName();
		model.addAttribute("loginUserId", loginUserId);

		model.addAttribute("questionWithAC", questionService.getQuestionByIdWithAC(id));

		UUID uuid = UUID.randomUUID();
		session.setAttribute("sessionUuid", uuid.toString());
		model.addAttribute("modelUuid", uuid.toString());
		model.addAttribute("id", id);

		return "questions/question_details";
	}


	@PostMapping("/details/{id}")
	public String questionDetailsPost(@PathVariable Integer id,
						  		      Model model,
								 	  RedirectAttributes redirectAttributes,
								 	  HttpSession session,
								 	  @RequestParam String postUuid) {
		redirectAttributes.addAttribute("urlId", id);

		if (!postUuid.equals(session.getAttribute("sessionUuid"))) {
			 return "redirect:/questions/details/{urlId}";
		 }

		session.removeAttribute("sessionUuid");

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String loginUserId = auth.getName();
		model.addAttribute("loginUserId", loginUserId);

		Question question = questionService.getQuestionById(id);
		int numberOfThanks = question.getQuestionThanks();
		numberOfThanks += 1;
		question.setQuestionThanks(numberOfThanks);
		questionService.updateAddQuestionThanks(question);

		model.addAttribute("questionWithAC", questionService.getQuestionByIdWithAC(id));

		return "questions/question_details";
	}


	@GetMapping("/addQuestion")
	public String addQuestionGet(Model model,
			                     Principal principal) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String loginUserId = auth.getName();
		model.addAttribute("loginUserId", loginUserId);

		model.addAttribute("question", new Question());

		return "questions/add";
	}


	@PostMapping("/addQuestion")
	public String addPostQuestion(@Valid Question question,
								  Errors errors,
								  Principal principal){

		String loginUserId = null;
		if (principal != null) {
			loginUserId = principal.getName();
		}

		boolean b = true;
		for (MultipartFile mf : question.getMultipartFile()) {
			b = mf.isEmpty();
		}

		if(errors.hasErrors()) {

			return "questions/add";

		} else {

			question.setQuestionUserId(loginUserId);
			questionService.addQuestion(question);

			if(b == false ) {
				int id = question.getQuestionId();

				List<MultipartFile> multipartFile = question.getMultipartFile();
				for (MultipartFile f : multipartFile) {
					String fileName = f.getOriginalFilename();
					QuestionImg questionImg = new QuestionImg();
					questionImg.setQuestionImgName(fileName);
					questionImg.setQuestionImgQuestionId(id);
					questionService.addQuestionImg(questionImg);

					sdkUploadHelper.saveFile(f);
				}
			}
		}
		return "redirect:/questions/list";
	}

	@GetMapping("/question_answer_comment/delete_all/{questionId}")
	public String deleteQuestionAnswerComment(@PathVariable Integer questionId) {
		questionService.deleteQuestionByIdWithAC(questionId);

		return "redirect:/questions/list";
	}
}
