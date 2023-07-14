package com.inhahackathon.foodmarket.auth.service;

import com.inhahackathon.foodmarket.repository.UserRepository;
import com.inhahackathon.foodmarket.type.dto.UserPrincipal;
import com.inhahackathon.foodmarket.type.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = userRepository.findById(Long.parseLong(username));
		if (!user.isPresent()) {
			throw new UsernameNotFoundException(username);
		}
		return UserPrincipal.create(user.get());
	}

}
