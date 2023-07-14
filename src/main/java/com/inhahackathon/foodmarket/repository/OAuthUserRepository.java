package com.inhahackathon.foodmarket.repository;

import com.inhahackathon.foodmarket.type.entity.OAuthUser;
import com.inhahackathon.foodmarket.type.etc.OAuthProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OAuthUserRepository extends JpaRepository<OAuthUser, Long> {

	Optional<OAuthUser> findByProviderUserIdAndOap(String providerUserId, OAuthProvider oap);

}
