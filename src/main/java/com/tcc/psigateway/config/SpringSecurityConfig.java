package com.tcc.psigateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.HttpBasicServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authentication.HttpStatusServerEntryPoint;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@EnableWebFluxSecurity
public class SpringSecurityConfig {

    private static final String FRONTEND_LOCALHOST = "http://localhost:3000";
    private static final String FRONTEND_STAGING = "http://localhost:3000";
    @Autowired
    private JwtAuthenticationFilter authenticationFilter;
    @Bean
    public SecurityWebFilterChain configure(ServerHttpSecurity http){
        return http.authorizeExchange()
                .pathMatchers("/v3/api-docs/**", "/swagger-resources/**", "/swagger-ui/**", "/swagger-ui.html", "/webjars/**").permitAll()
                .pathMatchers("/api/security/oauth/**").permitAll()
                .pathMatchers(HttpMethod.POST,"/api/usuarios", "/api/usuarios/resetPassword", "/api/usuarios/resetPassword", "/api/consulta/twilio/room", "/api/consulta/twilio/token").permitAll()
                .pathMatchers(HttpMethod.GET,"/api/usuarios/confirmar/**", "/api/usuarios/changePassword").permitAll()
                .pathMatchers(HttpMethod.PUT,"/api/usuarios/updatePassword").permitAll()
                .pathMatchers(HttpMethod.GET,"/api/consulta/**", "/api/consulta/professional/**").permitAll()
                .pathMatchers(HttpMethod.GET,"/api/usuarios/allPatients", "/api/usuarios/allProfessionals").permitAll()
                .pathMatchers(HttpMethod.GET,"/api/usuarios/confirmar/**").hasAnyRole("ADMIN", "USER")
                .pathMatchers(HttpMethod.PUT,  "/api/notes/**", "/api/notes/feedback", "/api/notes/feedback/**").permitAll()
                .pathMatchers(HttpMethod.GET,  "/api/notes/**", "/api/notes/feedback", "/api/notes/feedback/**").permitAll()
                .anyExchange().authenticated()
                .and().addFilterAt(authenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .csrf().disable()
                .build();
    }

    @Bean
    CorsConfigurationSource corsConfiguration() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.applyPermitDefaultValues();
        corsConfig.addAllowedMethod(HttpMethod.PUT);
        corsConfig.addAllowedMethod(HttpMethod.POST);
        corsConfig.addAllowedMethod(HttpMethod.GET);
        corsConfig.addAllowedMethod(HttpMethod.DELETE);
        corsConfig.setAllowedOrigins(Arrays.asList(FRONTEND_LOCALHOST, FRONTEND_STAGING));

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);
        return source;
    }
}
