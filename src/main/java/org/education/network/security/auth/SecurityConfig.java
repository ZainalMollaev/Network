package org.education.network.security.auth;

import org.education.network.security.auth.filters.JwtAuthorizationFilter;
import org.education.network.security.auth.filters.JwtAuthenticationFilter;
import org.education.network.security.services.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig  {

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http,
                                                       BCryptPasswordEncoder passwordEncoder,
                                                       CustomUserDetailsService userDetailsService)
            throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
        return authenticationManagerBuilder.build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   JwtAuthenticationFilter jwtAuthenticationFilter,
                                                   JwtAuthorizationFilter jwtAuthorizationFilter) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize ->
                authorize
                        .requestMatchers("/network/auth/**", "network/jwt/**").permitAll()
                        .anyRequest().authenticated()
        ).sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        )
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAuthenticationFilter, JwtAuthorizationFilter.class);

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}