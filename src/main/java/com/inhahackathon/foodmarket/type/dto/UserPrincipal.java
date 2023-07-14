package com.inhahackathon.foodmarket.type.dto;

import com.inhahackathon.foodmarket.type.entity.User;
import com.inhahackathon.foodmarket.type.etc.OAuthProvider;
import com.inhahackathon.foodmarket.type.etc.Role;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class UserPrincipal implements OAuth2User, UserDetails {

	private final Long userId;
	private String uid;
	private String name;
	private String location;
	private String profileImgUrl;
	private final Role role;
	private final OAuthProvider provider;
	private final Collection<GrantedAuthority> authorities;
	private Map<String, Object> attributes;

	@Override
	public String getPassword() {
		return "[PROTECTED]";
	}

	@Override
	public String getUsername() {
		return Long.toString(userId);
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public Map<String, Object> getAttributes() {
		Map<String, Object> clone = new HashMap<>();
		for (String key : attributes.keySet()) {
			Object value = attributes.get(key);
			clone.put(key, value);
		}
		return clone;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return new ArrayList<>(authorities);
	}

	@Override
	public String getName() {
		return "[" + userId + " : " + name + "]";
	}

	public static UserPrincipal create(User user) {
		List<GrantedAuthority> authorities = user.getRole() == null ?
				new ArrayList<>() : Collections.singletonList(new SimpleGrantedAuthority(user.getRole().name()));

		return UserPrincipal.builder()
				.userId(user.getUserId())
				.uid(user.getUid())
				.name(user.getName())
				.location(user.getLocation())
				.profileImgUrl(user.getProfileImgUrl())
				.role(user.getRole())
				.provider(user.getProvider())
				.authorities(authorities)
				.attributes(new HashMap<>())
				.build();
	}

	public User toUser() {
		User user = User.builder()
				.userId(userId)
				.uid(uid)
				.name(name)
				.location(location)
				.profileImgUrl(profileImgUrl)
				.role(role)
				.provider(provider)
				.build();
		return user;
	}

}
