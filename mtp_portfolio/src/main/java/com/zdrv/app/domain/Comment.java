package com.zdrv.app.domain;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class Comment {

	private Integer commentId;

	@NotBlank
	@Size(max = 500, message = "コメントは500字以内にしてください")
	private String commentContents;

	private Date commentDate;

	// answerテーブルのanswer_idと紐づける用
	private Integer commentAnswerId;

	private String commentUserId;

	// (保留)デフォルトの名前表示用 質問者・回答者
	private String commentPosterType;


	// commentテーブルにImgテーブルをJOIN用
	// JOIN img ON comment.comment_id = img.img_comment_id
	private List<CommentImg> commentImgList;

	// 画像ファイル一時取得用
	private List<MultipartFile> multipartFile;

}
