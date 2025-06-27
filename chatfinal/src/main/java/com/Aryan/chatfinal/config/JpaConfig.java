package com.Aryan.chatfinal.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.Aryan.chatfinal.repository")
public class JpaConfig {
}
