package com.simpleboard.board.auth.config;

import com.simpleboard.board.auth.presentation.exception.handler.SecurityAccessDeniedHandler;
import com.simpleboard.board.auth.presentation.exception.handler.SecurityFilterEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(
            auth ->
                auth.requestMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**")
                    .permitAll()
                    .anyRequest()
                    .permitAll())
        .formLogin(form -> form.disable())
        .httpBasic(basic -> basic.disable());

    http.exceptionHandling(
        ex ->
            ex.authenticationEntryPoint(new SecurityFilterEntryPoint())
                .accessDeniedHandler(new SecurityAccessDeniedHandler()));

    return http.build();
  }
}
