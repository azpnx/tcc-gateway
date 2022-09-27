package com.tcc.psigateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
public class SpringSecurityConfig {

    @Autowired
    private JwtAuthenticationFilter authenticationFilter;
    @Bean
    public SecurityWebFilterChain configure(ServerHttpSecurity http){
        return http.authorizeExchange()
                .pathMatchers("/api/security/oauth/**").permitAll()
                .pathMatchers(HttpMethod.POST,"/api/usuarios").permitAll()
                .pathMatchers(HttpMethod.GET,"/api/usuarios/confirmar/**").permitAll()
                .pathMatchers(HttpMethod.GET,"/api/usuarios/confirmar/**").hasAnyRole("ADMIN", "USER")
                .pathMatchers(HttpMethod.POST,  "/api/notes/**").permitAll()
                .pathMatchers(HttpMethod.GET,  "/api/notes/**").permitAll()
                .anyExchange().authenticated()
                .and().addFilterAt(authenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .csrf().disable()
                .build();
    }
}
