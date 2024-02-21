package com.sk.skmall.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sk.skmall.domain.base.RoleType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.PrintWriter;

/**
 *
 */
@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터가 스프링 필터체인에 등록된다.
@RequiredArgsConstructor
public class SecurityConfig {

    // password 암호화
    // 해당 메서드의 리턴되는 오브젝트를 IoC로 등록
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .headers((headerConfig) ->
                        headerConfig.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable
                        )
                )
                // 특정 요청 Endpoint를 허용 또는 거부하도록 설정
                .authorizeHttpRequests((authorizeRequests) ->
                        authorizeRequests
                                .requestMatchers("/", "/auth/**").permitAll()
                                .requestMatchers("/swagger-ui/**").permitAll()
                                .requestMatchers("/posts/**", "/api/v1/posts/**").hasRole(RoleType.NEW_CUSTOMER.name())
                                .requestMatchers("/admin/**", "/api/v1/admin/**").hasRole(RoleType.MASTER.name())
                                .anyRequest().permitAll()
                )

                // login 페이지 및 로그인 기능 설정
                .formLogin((auth) ->
                        auth
                                .loginPage("/auth/login")
                                .loginProcessingUrl("auth/login/loginProc")
                                .permitAll()
                                .defaultSuccessUrl("/")
                )

                // 다중 로그인 설정
                .sessionManagement((auth) ->
                        auth
                                .maximumSessions(1) // 하나의 id에 대한 다중 로그인 허용 개수
                                .maxSessionsPreventsLogin(true) // 다중 로그인 회수 초과 시 처리 방법 true:새로운 로그인 차단. false: 기존 세션 하나 삭제
                )

                // 세션 고정 공격 보안 설정
                .sessionManagement((auth) ->
                        auth
                                .sessionFixation().changeSessionId()
                )

                .exceptionHandling((exceptionConfig) ->
                        exceptionConfig.authenticationEntryPoint(unauthorizedEntryPoint).accessDeniedHandler(accessDeniedHandler)
                ); // 401 403 관련 예외처리

        return http.build();
    }

    private final AuthenticationEntryPoint unauthorizedEntryPoint =
            (request, response, authException) -> {
                ErrorResponse fail = new ErrorResponse(HttpStatus.UNAUTHORIZED, "Spring security unauthorized...");
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                String json = new ObjectMapper().writeValueAsString(fail);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                PrintWriter writer = response.getWriter();
                writer.write(json);
                writer.flush();
            };

    private final AccessDeniedHandler accessDeniedHandler =
            (request, response, accessDeniedException) -> {
                ErrorResponse fail = new ErrorResponse(HttpStatus.FORBIDDEN, "Spring security forbidden...");
                response.setStatus(HttpStatus.FORBIDDEN.value());
                String json = new ObjectMapper().writeValueAsString(fail);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                PrintWriter writer = response.getWriter();
                writer.write(json);
                writer.flush();
            };

    @Getter
    @RequiredArgsConstructor
    public class ErrorResponse {

        private final HttpStatus status;
        private final String message;
    }

}
