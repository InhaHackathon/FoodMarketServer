package com.inhahackathon.foodmarket.config;

import java.io.OutputStream;
import java.util.List;

import com.inhahackathon.foodmarket.auth.filter.TokenAuthFilter;
import com.inhahackathon.foodmarket.auth.handler.OAuth2AuthenticationFailureHandler;
import com.inhahackathon.foodmarket.auth.handler.OAuth2AuthenticationSuccessHandler;
import com.inhahackathon.foodmarket.auth.jwt.AuthTokenProvider;
import com.inhahackathon.foodmarket.auth.service.CustomOAuth2UserService;
import com.inhahackathon.foodmarket.type.dto.ResponseModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
@Configuration
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final AuthTokenProvider authTokenProvider;


    private static final String[] PERMIT_ALL = {
//            "/login/**",
//            "/v2/api-docs",
//            "/swagger-resources",
//            "/swagger-resources/**",
//            "/configuration/ui",
//            "/configuration/security",
//            "/swagger-ui.html",
//            "/webjars/**",
//            "/v3/api-docs/**",
//            "/swagger-ui/**",
//            "/"
            "/**"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
//                .oauth2Login()
//                .userInfoEndpoint()
//                .userService(customOAuth2UserService)
//                .and()
//                .successHandler(oAuth2AuthenticationSuccessHandler())
//                .failureHandler(oAuth2AuthenticationFailureHandler())
//                .and()
                .authorizeHttpRequests()
                .antMatchers(PERMIT_ALL).permitAll()
                .anyRequest().authenticated();

        //Rest API 이기에, Stateless라 CSRF 방어 불필요
        http.csrf().disable();

        http.addFilterBefore(tokenAuthFilter(), UsernamePasswordAuthenticationFilter.class);

        http.cors().configurationSource(corsConfigurationSource());

        http.exceptionHandling()
                .authenticationEntryPoint((request, response, authException) -> {
                    ResponseModel responseModel = ResponseModel.builder()
                            .httpStatus(HttpStatus.UNAUTHORIZED)
                            .message("인증되지 않은 사용자이거나 권한이 부족합니다.")
                            .build();
                    response.setStatus(401);
                    response.setContentType("application/json");
                    OutputStream outputStream = response.getOutputStream();
                    outputStream.write(responseModel.toJson().getBytes());
                });
        return http.build();
    }

    @Bean
    public OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler() {
        return new OAuth2AuthenticationSuccessHandler(authTokenProvider);
    }

    @Bean
    public OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler() {
        return new OAuth2AuthenticationFailureHandler();
    }

    @Bean
    public TokenAuthFilter tokenAuthFilter() {
        return new TokenAuthFilter(authTokenProvider);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.setAllowedOriginPatterns(List.of("*"));
        corsConfiguration.setAllowedMethods(List.of("*"));
        corsConfiguration.setAllowedHeaders(List.of("*"));
        corsConfiguration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;
    }

}
