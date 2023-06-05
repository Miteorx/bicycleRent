package com.example.bicyclerent.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
  @Bean
  @Profile("!basic")
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return http
        .authorizeHttpRequests(authRequests -> authRequests
            .requestMatchers("/", "/login", "/registration").permitAll()
            .anyRequest().authenticated()
        )
        .formLogin(form -> form
            .loginPage("/login").permitAll()
            .defaultSuccessUrl("/")
        )
        .logout(logout -> logout
            .logoutUrl("/logout")
            .logoutSuccessUrl("/login?logout").permitAll()
            .and()
        )
        .build();
  }

  @Bean
  @Profile("basic")
  public SecurityFilterChain basicFilterChain(HttpSecurity http) throws Exception {
    return http
        .authorizeHttpRequests(authRequests -> authRequests
            .requestMatchers("/api/**").hasAuthority("PRIV_API_ACCESS")
            .anyRequest().authenticated()
        )
        .httpBasic(withDefaults())
        .build();
  }


}
