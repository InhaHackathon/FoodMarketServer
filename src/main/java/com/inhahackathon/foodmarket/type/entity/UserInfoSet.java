package com.inhahackathon.foodmarket.type.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Entity
@Setter
public class UserInfoSet {

	@Id
	private Long userId;

}
