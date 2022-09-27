package com.security.Auth.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static com.security.Auth.security.ApplicationUserRole.*;

@Configuration
@EnableWebSecurity // telling that we are configuring security here
public class AppSecurityConfig {

        private final PasswordEncoder passwordEncoder;

        @Autowired
        public AppSecurityConfig(PasswordEncoder passwordEncoder) {
                this.passwordEncoder = passwordEncoder;
        }

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                http.
                        authorizeRequests()
                        .antMatchers("index", "/", "/css/**", "/js/**").permitAll()
                        .antMatchers("/api/**").hasRole(STUDENT.name())
                        .anyRequest()
                        .authenticated()
                        .and()
                        .httpBasic();

                return http.build();
        }

        @Bean
        public UserDetailsService userDetailsService() {
                UserDetails a = User.builder().username("a").password(passwordEncoder.encode("123")).
                        roles(STUDENT.name()).build();
                UserDetails b = User.builder().username("b").password(passwordEncoder.encode("123")).
                        roles(ADMIN.name()).build();
                return new InMemoryUserDetailsManager(
                        a, b
                );
        }
}
