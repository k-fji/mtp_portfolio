package com.zdrv.app.domain;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class User {
	// 登録フォームで一時取得したFormUserクラスの情報を更に受けて、DBに渡すクラス。
	// RegisterControllerで使用
	private Integer	 id;

	@NotBlank
	private String userId;

	@NotBlank
	private String pass;

}
