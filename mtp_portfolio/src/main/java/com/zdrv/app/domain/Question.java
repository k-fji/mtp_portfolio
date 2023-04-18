package com.zdrv.app.domain;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class Question {

	private Integer questionId;

	@NotBlank
	@Size(max = 100, message = "タイトル名は100字以内にしてください")
	private String bookTitle;

	@NotNull(message = "空白は許可されていません")
	private Integer pageNumber; //0も可(特定のページ以外の質問に使用できる)

	@NotBlank
	@Size(max = 100, message = "タイトル名は100字以内にしてください")
	private String questionTitle;

	@NotBlank
	@Size(max = 500, message = "質問は500字以内にしてください")
	private String questionContents;

	private Date questionDate;

	private String questionUserId;

	// 回答数(answerStatus == 0だと赤、answerStatus > 0だと青)
	private Integer numberOfAnswers; // AS number_of_answersで作るテーブルのため対応テーブルはない

	// いいね機能
	private Integer questionThanks;

	// 閲覧数
	private Integer pageViews;

	// 解決・未解決情報
	private String questionStatus;

	// questionテーブルにImgテーブルをJOIN用
	// JOIN img ON question.question_id = img.img_question_id
	private List<QuestionImg> questionImgList;

	// questionテーブルにanswerテーブルをJOIN用
	// JOIN answer ON question.question_id = answer.answer_question_id
	private List<Answer> questionAnswerList;

	// カウント用
	private Integer numberOfQuestions;

	// 画像ファイル一時取得用
	private List<MultipartFile> multipartFile;
}
