package com.zdrv.app.domain;

import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class FormUser {

	// 登録フォームで入力された情報を一時的に受けるクラス。(更にUserクラスに渡してDBへ格納される)
	// RegisterControllerで使用
	@Size(min=1, max=20, message = "ユーザーIDは{min}～{max}文字以内で指定して下さい")
	private String inputUserId;

	@Size(min=4, max=8, message = "パスワードは{min}～{max}文字以内で指定して下さい")
	private String inputPass;

}
