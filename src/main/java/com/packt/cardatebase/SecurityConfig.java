package com.packt.cardatebase;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.packt.cardatebase.service.UserDetailServiceImpl;

//빈등록
@Configuration
//기본 웹 보안 구성을 비활성화하고
//자체 구성을 정의하기위한 어노테이션 
//SpringSecuriryFilterChain이 자동으로 포함됨
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		UrlBasedCorsConfigurationSource source =
				new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
		config.setAllowedMethods(Arrays.asList("*"));
		config.setAllowedHeaders(Arrays.asList("*"));
		config.setAllowCredentials(false);
		source.registerCorsConfiguration("/**", config);
		return source;
	}
	
	@Autowired
	private UserDetailServiceImpl userDetailService;
	
	//인증필터의존주입
	@Autowired
	private AuthenticationFilter authenticationFilter;
	//AuthenticationManager를 LoginController클래스에 주입했으므로
	//빈으로 등록해줌
	@Bean
	public AuthenticationManager getAuthenticationManager() throws Exception {
		return authenticationManager();
	}
	
	@Autowired
	private AuthEntryPoint exceptionHandler;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) 
			throws Exception {
		auth.userDetailsService(userDetailService)
		.passwordEncoder(new BCryptPasswordEncoder());
	}

	@Override
	//보호되는 경로와 보호되지 않는 경로를 정의
	//csrf비활성화 
	protected void configure(HttpSecurity http) throws Exception {
//		http.csrf().disable().cors().and()
//		.authorizeRequests().anyRequest().permitAll();
		http.csrf().disable().cors().and()
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
		.authorizeRequests()
		///login엔드 포인트에 대한 요청은 보호하지 않음
		.antMatchers(HttpMethod.POST, "/login").permitAll()
		.antMatchers(HttpMethod.GET, "/tour/*").permitAll()
		//다른요청은 보호됨
		.anyRequest().authenticated().and()
		.exceptionHandling()
		.authenticationEntryPoint(exceptionHandler).and()
		.addFilterBefore(authenticationFilter, 
				UsernamePasswordAuthenticationFilter.class);
	}
	
	
}
