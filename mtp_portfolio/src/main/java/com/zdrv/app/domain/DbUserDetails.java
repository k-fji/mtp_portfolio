package com.zdrv.app.domain;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

//ユーザ情報を格納するクラス (springのUserクラスを継承する)
public class DbUserDetails extends User{
	// Springの認証ユーザークラスに、作成したdb用クラスのアカウント情報を保持させる
	private final Account account;

	// Userクラスのコンストラクタを呼び出す。
	// 第一引数はAccountクラスのuserIdとpass、
	// 第二引数はユーザに付与する権限のリスト
	public DbUserDetails(Account account,
	                     Collection<GrantedAuthority> authorities) {
		//最下部(コメントアウトの)コンストラクタの引数に対応
	    super(account.getUserId(), account.getPass(),
	               true, true, true, true, authorities);

	    this.account = account;
	}


	// アカウント情報のgetterを用意。これにより、ログインユーザーのAccountオブジェクトを取得用
    public Account getAccount() {
        return account;
    }

}
