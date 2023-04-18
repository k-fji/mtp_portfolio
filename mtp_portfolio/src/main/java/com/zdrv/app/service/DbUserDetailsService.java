package com.zdrv.app.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zdrv.app.dao.LoginMapper;
import com.zdrv.app.domain.Account;
import com.zdrv.app.domain.DbUserDetails;

@Service
public class DbUserDetailsService implements UserDetailsService {

	   @Autowired
	   LoginMapper loginMapper;

	   @Override
	   @Transactional(readOnly = true)
	   public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		   if (username == null || username.isEmpty()) {
	            throw new UsernameNotFoundException("Username is empty");
	        }

		   Account account = loginMapper.findAccount(username);

	        if (account == null) {
	            throw new UsernameNotFoundException(String.format("User not found ", username));
	        }

	     return new DbUserDetails(account, getAuthorities(account));
	   }


	   private Collection<GrantedAuthority> getAuthorities(Account account) {

	       return AuthorityUtils.createAuthorityList("ROLE_USER");
	   }
}
