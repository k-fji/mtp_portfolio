package com.zdrv.app.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Account {

	// ログイン時に必要な会員情報を格納するクラス
	// DbUserDetailsクラスでspringのUserクラスにユーザ情報を格納するために使われる。

	private String userId;

	private String pass;
}
