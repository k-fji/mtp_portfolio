package com.zdrv.app.controller;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.zdrv.app.domain.Answer;
import com.zdrv.app.domain.Comment;
import com.zdrv.app.domain.CommentImg;
import com.zdrv.app.domain.Question;
import com.zdrv.app.helper.SDKUploadHelper;
import com.zdrv.app.service.AnswerService;
import com.zdrv.app.service.CommentService;
import com.zdrv.app.service.QuestionService;

@Controller
public class CommentController {

    @Autowired
    SDKUploadHelper sdkUploadHelper;

	@Autowired
	private QuestionService questionService;
	@Autowired
	private AnswerService answerService;
	@Autowired
	private CommentService commentService;


	@GetMapping("questions/details/{id}/addComment/{posterTypeParam}")
	public String addAnswerGet2(Principal principal
								,@PathVariable Integer id,
								@PathVariable String posterTypeParam,
								Model model) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String loginUserId = auth.getName();
		model.addAttribute("loginUserId", loginUserId);
		model.addAttribute("question", new Question());

		Answer answerDetails;
		answerDetails = answerService.getAnswerWithComments(id);
		model.addAttribute("answerDetails", answerDetails);
		model.addAttribute("questionDetails", questionService.getQuestionById(answerDetails.getAnswerQuestionId()));

		String posterType;
		if (posterTypeParam.equals("questioner")) {
			posterType = "質問者";
		} else {
			posterType = "回答者";
		}
		model.addAttribute("posterType", posterType);
		Comment comment = new Comment();
		comment.setCommentPosterType(posterType);
		model.addAttribute("comment", comment);

		return "comment";
	}


	@PostMapping("questions/details/{id}/addComment/{posterTypeParam}")
	public String addComment2(@PathVariable Integer id,
							  @PathVariable String posterTypeParam,
							  @Valid Comment comment,
							  Errors errors,
							  Model model,
							  @ModelAttribute Question question,
							  Principal principal,
							  RedirectAttributes redirectAttributes) {

		String loginUserId = null;
		if (principal != null) {
			loginUserId = principal.getName();
		}

		boolean attachedFileFlag = true;
		for (MultipartFile mf : comment.getMultipartFile()) {
			attachedFileFlag = mf.isEmpty();
		}

		Answer answerDetails;
		answerDetails = answerService.getAnswerWithComments(id);

		String posterType;
		if (posterTypeParam.equals("questioner")) {
			posterType = "質問者";
		} else {
			posterType = "回答者";
		}

		if (errors.hasErrors()) {
			model.addAttribute("answerDetails", answerDetails);
			model.addAttribute("questionDetails", questionService.getQuestionById(answerDetails.getAnswerQuestionId()));
			comment.setCommentPosterType(posterType);
			return "comment";

		} else {

			comment.setCommentUserId(loginUserId);

			comment.setCommentPosterType(posterType);
			comment.setCommentAnswerId(id);
			commentService.addComment(comment);
			id = answerDetails.getAnswerQuestionId();


			if(posterType.equals("質問者")) {
				question.setQuestionId(id);
				questionService.updateQuestionStatus(question);
			}

			if(attachedFileFlag == false ) {
				int AI_id = comment.getCommentId();

				List<MultipartFile> multipartFiles = comment.getMultipartFile();
				for (MultipartFile multipartFile : multipartFiles) {
					String fileName = multipartFile.getOriginalFilename();
					CommentImg commentImg = new CommentImg();
					commentImg.setCommentImgName(fileName);
					commentImg.setCommentImgCommentId(AI_id);
					commentService.addCommentImg(commentImg);

					sdkUploadHelper.saveFile(multipartFile);
				}
			}
		}

		redirectAttributes.addAttribute("urlId", id);
		return "redirect:/questions/details/{urlId}";
	}
}


