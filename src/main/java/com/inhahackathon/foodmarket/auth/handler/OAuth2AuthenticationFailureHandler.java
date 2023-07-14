package com.inhahackathon.foodmarket.auth.handler;

import com.inhahackathon.foodmarket.type.dto.ResponseModel;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

public class OAuth2AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
										AuthenticationException exception) throws IOException {
		ResponseModel responseModel = ResponseModel.builder()
			.httpStatus(HttpStatus.UNAUTHORIZED)
			.message("인증되지 않은 사용자이거나 권한이 부족합니다.")
			.build();
		response.setStatus(401);
		response.setContentType("application/json");
		OutputStream outputStream = response.getOutputStream();
		outputStream.write(responseModel.toJson().getBytes());
	}

}
