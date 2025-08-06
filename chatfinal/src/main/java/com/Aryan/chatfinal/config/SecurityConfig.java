package com.Aryan.chatfinal.config;

import com.Aryan.chatfinal.service.CustomLoginFailureHandler;
import com.Aryan.chatfinal.service.CustomOAuth2SuccessHandler;
import com.Aryan.chatfinal.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;
    private final CustomLoginFailureHandler customLoginFailureHandler;

    public SecurityConfig(CustomUserDetailsService userDetailsService,
                          CustomLoginFailureHandler customLoginFailureHandler) {
        this.userDetailsService = userDetailsService;
        this.customLoginFailureHandler = customLoginFailureHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,CustomOAuth2SuccessHandler oAuth2SuccessHandler) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/register", "/login", "/signup", "/css/**", "/js/**", "/ws/**")
                        .permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/index.html", true)
                        .failureHandler(customLoginFailureHandler) 
                        .permitAll()
                )
                .oauth2Login(oauth -> oauth
                    .loginPage("/login") // same login page
                    .defaultSuccessUrl("/index.html", true)
                        .failureHandler(customLoginFailureHandler) // use the same failure handler
                        .successHandler(oAuth2SuccessHandler)
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .permitAll()
                )
                .userDetailsService(userDetailsService)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
