package com.example.finance.FinanceApplication.config;


import com.example.finance.FinanceApplication.model.User;
import com.example.finance.FinanceApplication.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;



@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final UserRepository userRepository;
    public SecurityConfig(UserRepository userRepository){
        this.userRepository=userRepository;
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf->csrf.disable())
                .authorizeHttpRequests(auth->auth
                        .requestMatchers(HttpMethod.POST, "/users").permitAll()
                        .requestMatchers(HttpMethod.GET,"/dashboard/**").hasAnyRole("VIEWER","ANALYST","ADMIN")
                        .requestMatchers(HttpMethod.POST,"/financial-records/**").hasAnyRole("ANALYST","ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/financial-records/**").hasAnyRole("ANALYST","ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/financial-records/**").hasRole("ADMIN")
                        .requestMatchers("/users/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults());


        return http.build();
    }
    @Bean
    public UserDetailsService userDetailsService(){
        return email->{
            User user= userRepository.findByEmail(email);
            return org.springframework.security.core.userdetails.User
                    .withUsername(user.getEmail())
                    .password(user.getPassword())
                    .roles(user.getRole().toString())
                    .build();
        };
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
