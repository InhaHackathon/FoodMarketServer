package com.inhahackathon.foodmarket.auth.util;

import com.inhahackathon.foodmarket.auth.exception.UnAuthorizeException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

public class AuthUtil {

    public static User getAuthenticationInfo() {
        return (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static long getAuthenticationInfoSeq() throws UnAuthorizeException {
        try {
            return Long.parseLong(getAuthenticationInfo().getUsername());
        } catch (NumberFormatException | NullPointerException e) {
            throw new UnAuthorizeException();
        }
    }

}