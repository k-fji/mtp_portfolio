package com.zdrv.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@ComponentScan("com.zdrv.app.helper")
@Configuration
@EnableWebSecurity

public class WebSecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

    @Autowired
    UserDetailsService userDetailsService;

	@Override
    protected void configure(HttpSecurity http) throws Exception {

		http.formLogin()
			.loginPage("/login")
			.loginProcessingUrl("/login")
            .usernameParameter("userId")
            .passwordParameter("pass")
            .defaultSuccessUrl("/mypage")
            .permitAll()
			;
		http.csrf().disable()
			.authorizeRequests()
			.antMatchers("/mypage/**")
			.authenticated()
			.anyRequest()
			.permitAll()
			;
		http.logout()
			.logoutSuccessUrl("/home")
			.permitAll();

	}

		@Bean
		PasswordEncoder passwordEncoder() {
		    return new BCryptPasswordEncoder();
		}

		@Autowired
		void configureAuthenticationManager(AuthenticationManagerBuilder auth) throws Exception {

			auth.userDetailsService(userDetailsService)

		    .passwordEncoder(passwordEncoder());
		}

		// URLに%を許可
		@Bean
		HttpFirewall httpFirewall() {
		    StrictHttpFirewall firewall = new StrictHttpFirewall();
		    firewall.setAllowUrlEncodedPercent(true);
		    return firewall;
		}

		@Override
		public void addResourceHandlers(ResourceHandlerRegistry registry) {
		    registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
		}

		 @Bean
		 public AmazonS3 amazonS3(){
		    return AmazonS3ClientBuilder.standard().build();
	  	 }


}
