package com.zdrv.app.domain;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class Answer {

	private Integer answerId;

	@NotBlank
	@Size(max = 500, message = "回答は500字以内にしてください")
	private String answerContents;

	private Date answerDate;

	// questionテーブルのquestion_idと紐づけられるように
	private Integer answerQuestionId;

	private String answerUserId;

	// answerテーブルにImgテーブルをJOIN用
	// JOIN img ON answer.answer_id = img.img_answer_id
	private List<AnswerImg> answerImgList;

	// answerテーブルにquestionテーブルを一対一でくっつける(answer履歴)用
	private Question question;

	// answerテーブルにcommentテーブルをくっつける用
	private List<Comment> answerCommentList;


	// 画像ファイル一時取得用
	private List<MultipartFile> multipartFile;
}
