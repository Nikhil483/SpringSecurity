package com.security.Auth.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static com.security.Auth.security.ApplicationUserRole.*;

@Configuration
@EnableWebSecurity // telling that we are configuring security here
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AppSecurityConfig {

        private final PasswordEncoder passwordEncoder;

        @Autowired
        public AppSecurityConfig(PasswordEncoder passwordEncoder) {
                this.passwordEncoder = passwordEncoder;
        }

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                //we can disable csrf if we are not calling request from web. if not disabled then capture the token sent in cookies. it will be under 'XSRF-Token'.
                // then send token under header of the request. name: X-XSRF-TOKEN
                http.
                        csrf().disable()
                        .authorizeRequests()
                        .antMatchers("index", "/", "/css/**", "/js/**").permitAll()
                        .antMatchers("/api/v1/getStudent/**").hasRole(STUDENT.name())
//                        .antMatchers(HttpMethod.DELETE, "/api/v1/deleteStudent/**").hasAuthority(ApplicationUserPermission.COURSE_WRITE.getPermission())
//                        .antMatchers(HttpMethod.POST, "/api/v1/addStudent/**").hasAuthority(ApplicationUserPermission.COURSE_WRITE.getPermission())
//                        .antMatchers(HttpMethod.PUT, "/api/v1/editStudent/**").hasAuthority(ApplicationUserPermission.COURSE_WRITE.getPermission())
//                        .antMatchers( "/api/v1/getAllStudents").hasAnyRole(ADMIN.name(), ADMINTRAINEE.name())
                        .anyRequest()
                        .authenticated()
                        .and()
                        //.httpBasic();
                        .formLogin()
                        .loginPage("/login").permitAll()
                        .defaultSuccessUrl("/courses")
                        .and()
                        .rememberMe()
                        .and()
                        .logout()
                        .logoutUrl("/logout")
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET")) // REFER this: https://docs.spring.io/spring-security/site/docs/4.2.12.RELEASE/apidocs/org/springframework/security/config/annotation/web/configurers/LogoutConfigurer.html
                                .clearAuthentication(true)
                                .invalidateHttpSession(true)
                                .deleteCookies("JSESSIONID", "remember-me")
                                .logoutSuccessUrl("/login");

                return http.build();
        }

        @Bean
        public UserDetailsService userDetailsService() {
                UserDetails a = User.builder().username("a").password(passwordEncoder.encode("123")).
                        //roles(STUDENT.name())
                        authorities(STUDENT.getGrantedAuthorities())
                        .build();
                UserDetails b = User.builder().username("b").password(passwordEncoder.encode("123")).
                        //roles(ADMIN.name())
                        authorities(ADMIN.getGrantedAuthorities())
                        .build();
                UserDetails c = User.builder().username("c").password(passwordEncoder.encode("123")).
                        //roles(ADMINTRAINEE.name())
                        authorities(ADMINTRAINEE.getGrantedAuthorities())
                        .build();
                return new InMemoryUserDetailsManager(
                        a, b, c
                );
        }
}
