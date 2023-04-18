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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.zdrv.app.domain.Answer;
import com.zdrv.app.domain.AnswerImg;
import com.zdrv.app.domain.Question;
import com.zdrv.app.helper.SDKUploadHelper;
import com.zdrv.app.service.AnswerService;
import com.zdrv.app.service.QuestionService;

@Controller
public class AnswerController {

	@Autowired
    SDKUploadHelper sdkUploadHelper;

	@Autowired
	private QuestionService questionService;
	@Autowired
	private AnswerService answerService;


	@GetMapping("questions/details/{id}/{addAnswerType}")
	public String addAnswerGet(@PathVariable Integer id,
								@PathVariable String addAnswerType,
								Model model,
								Principal principal) {
		model.addAttribute("addAnswerType", addAnswerType);
		model.addAttribute("questionDetails", questionService.getQuestionById(id));
		model.addAttribute("answer", new Answer());


		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String loginUserId = auth.getName();
		model.addAttribute("loginUserId", loginUserId);

		return "answer";
	}

	@PostMapping("questions/details/{id}/{addAnswerType}")
	public String addAnswerPost(@PathVariable Integer id,
							    @PathVariable String addAnswerType,
								@RequestParam(required = false) String status,
			                    @Valid Answer answer,
			                    Errors errors,
			                    Model model,
								Principal principal) {

		String loginUserId = null;
		if (principal != null) {
			loginUserId = principal.getName();
		}

		boolean attachedFileFlag = true;
		for (MultipartFile mf : answer.getMultipartFile()) {
			attachedFileFlag = mf.isEmpty();
		}

		if(errors.hasErrors()) {

			model.addAttribute("questionDetails", questionService.getQuestionById(id));

			return "answer";

		} else {

			answer.setAnswerUserId(loginUserId);
			answer.setAnswerQuestionId(id);
			answerService.addAnswer(answer);

			if(addAnswerType.equals("addSelfAnswer")) {
			    Question question = questionService.getQuestionById(id);
			    question.setQuestionStatus(status);
			    questionService.updateQuestionStatus(question);
			}

			if(attachedFileFlag == false ) {
				int AI_id = answer.getAnswerId();

				List<MultipartFile> multipartFiles = answer.getMultipartFile();
				for (MultipartFile multipartFile : multipartFiles) {
					String fileName = multipartFile.getOriginalFilename();
					AnswerImg answerImg = new AnswerImg();
					answerImg.setAnswerImgName(fileName);
					answerImg.setAnswerImgAnswerId(AI_id);
					answerService.addAnswerImg(answerImg);

					sdkUploadHelper.saveFile(multipartFile);
				}
			}

		return "redirect:/questions/details/{id}";
		}
	}
}
