package com.packt.cardatebase.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.packt.cardatebase.domain.User;
import com.packt.cardatebase.domain.UserRepository;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
	@Autowired
	private UserRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = repository.findByUsername(username);
		UserBuilder builder = null;
		//user가 있으면 isPresent 객체가 존재하는지 확인 
		//있으면 true null이면 false리턴
		if(user.isPresent()) {
			User currentUser = user.get();
			builder = org.springframework.security.core.userdetails
					.User.withUsername(username);
			builder.password(currentUser.getPassword());
			builder.roles(currentUser.getRole());
		}else {
			throw new UsernameNotFoundException("user not found");
		}
		//UserDetails타입을 리턴해줌
		return builder.build();
	}
	
}
