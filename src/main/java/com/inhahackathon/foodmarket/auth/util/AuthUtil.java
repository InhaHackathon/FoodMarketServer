package com.inhahackathon.foodmarket.auth.util;

import com.inhahackathon.foodmarket.type.dto.UserPrincipal;
import com.inhahackathon.foodmarket.type.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthUtil {

    public static User getAuthenticationInfo() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof UserPrincipal)) {
            return null;
        }
        return ((UserPrincipal) authentication.getPrincipal()).toUser();
    }


    public static Long getAuthenticationInfoUserId() {
        return getAuthenticationInfo().getUserId();
    }

}