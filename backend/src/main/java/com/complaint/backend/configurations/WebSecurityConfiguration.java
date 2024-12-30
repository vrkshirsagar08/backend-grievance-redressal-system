package com.complaint.backend.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.StaticHeadersWriter;

import com.complaint.backend.enums.UserRole;
import com.complaint.backend.services.jwt.UserService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfiguration {

    private final UserService userService;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

   /* @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
    security.csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(request -> request.requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/api/admin/**").hasAnyAuthority(UserRole.ADMIN.name())
                .requestMatchers("/api/employee/**").hasAnyAuthority(UserRole.EMPLOYEE.name())
                .anyRequest().authenticated())
            .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(authenticationProvider()).addFilterBefore(
                jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); 
    return security.build();
}
 */ 
@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.cors().and().csrf().disable()
        .authorizeRequests()
        .anyRequest().permitAll(); // For debugging, permit all requests temporarily.
    return http.build();
}


    @Bean
    public PasswordEncoder passwordEncoder () {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider () {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userService. userDetailsService());
        provider. setPasswordEncoder (passwordEncoder ());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
    
}

