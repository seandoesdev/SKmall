package com.sk.skmall.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sk.skmall.domain.base.RoleType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.io.PrintWriter;
import java.util.List;

/**
 *
 */
@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터가 스프링 필터체인에 등록된다.
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationEvents authenticationEvents;

    // password 암호화
    // 해당 메서드의 리턴되는 오브젝트를 IoC로 등록
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationEventPublisher authenticationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        return new DefaultAuthenticationEventPublisher(applicationEventPublisher);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(true);
        config.setAllowedOrigins(List.of("http://localhost:8090"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setExposedHeaders(List.of("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                // login 페이지 및 로그인 기능 설정
                .formLogin(AbstractHttpConfigurer::disable)

                .logout((logout) ->
                        logout
                                .logoutSuccessUrl("/")
                )

                // http basic 인증 방식 설정
                .httpBasic(AbstractHttpConfigurer::disable)

                .headers((headerConfig) ->
                        headerConfig.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable
                        )
                )

                // 특정 요청 Endpoint를 허용 또는 거부하도록 설정
                .authorizeHttpRequests((authorizeRequests) ->
                        authorizeRequests

                                // swagger
                                .requestMatchers("/swagger-ui/**", "/swagger-resources/**").permitAll()

                                // 인증, 인가
                                .requestMatchers("/api/v1/auth/**").permitAll()

                                // 내 정보
                                .requestMatchers("/api/v1/my/customer/**").hasAnyRole(RoleType.NEW_CUSTOMER.name(),
                                                                                                        RoleType.COMMON_CUSTOMER.name(),
                                                                                                        RoleType.VIP_CUSTOMER.name(),
                                                                                                        RoleType.VVIP_CUSTOMER.name()
                                )

                                // 회원 가입
//                                .requestMatchers("/api/vi/user/**").permitAll()
//
//                                .requestMatchers("/", "/auth/**").permitAll()
//                                .requestMatchers("/register/customer", "/admin/login").permitAll()
//                                .requestMatchers("/posts/**", "/api/v1/posts/**").hasRole(RoleType.NEW_CUSTOMER.name())
//                                .requestMatchers("/admin/**", "/api/v1/admin/**").hasRole(RoleType.MASTER.name())

                                // Master는 모든 api 접근 가능
//                                .requestMatchers("/**").hasRole(RoleType.MASTER.name())

                                .anyRequest().permitAll() // 기본적인 api가 갖춰지면 .hasAnyRole()로 수정 필요
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
