package com.StoreBook.advice;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.StoreBook.service.CustomOAuth2User;
import com.StoreBook.service.CustomOAuth2UserService;
import com.StoreBook.service.MyUserDetailService;
import com.StoreBook.service.UserService;

import org.springframework.context.annotation.Lazy;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
        @Autowired
        private DataSource dataSource;
        @Autowired
        private MyUserDetailService userDetailService;

        @Autowired
        private  CustomOAuth2UserService oauthuserService;

        private final UserService userSer;

        public WebSecurityConfig(@Lazy UserService u) {
                this.userSer = u;
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
                auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
                http
                                .authorizeRequests(requests -> requests
                                                .antMatchers("/BookStore/client/shop", "/images/**", "/register",
                                                                "/login*", "/login/**",
                                                                "/admin/**", "/user/**", "/api/register",
                                                                "/BookStore/client/book/{id}/detail",
                                                                "/BookStore/api/review/getAll/{book_id}")
                                                .permitAll()
                                                .anyRequest().authenticated())
                                .formLogin(login -> login
                                                .loginPage("/login") // S? d?ng trang ??ng nh?p m?c ??nh
                                                .usernameParameter("email")
                                                .permitAll())

                                .oauth2Login(oauth2Login -> oauth2Login
                                                .loginPage("/login") // Trang đăng nhập tự động được sử dụng khi sử dụng
                                                                     // OAuth 2.0
                                                // Redirect URL sau khi đăng nhập thành công
                                                .userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint
                                                                .userService(oauthuserService).and()
                                                                .successHandler(new AuthenticationSuccessHandler() {

                                                                        @Override
                                                                        public void onAuthenticationSuccess(
                                                                                        HttpServletRequest request,
                                                                                        HttpServletResponse response,
                                                                                        Authentication authentication)
                                                                                        throws IOException,
                                                                                        ServletException {

                                                                                CustomOAuth2User oauthUser = (CustomOAuth2User) authentication
                                                                                                .getPrincipal();
                                                                                userSer.processOAuthPostLogin(
                                                                                                oauthUser.getEmail(),
                                                                                                oauthUser.getName());

                                                                                response.sendRedirect(
                                                                                                "/BookStore/client/");
                                                                        }
                                                                }) // Custom UserService để xử lý thông tin người dùng
                                                ))
                                .rememberMe(me -> me

                                                .rememberMeParameter("remember-me")
                                                .tokenRepository(persistentTokenRepository()))
                                .logout(logout -> logout
                                                .logoutUrl("/logout")
                                                .clearAuthentication(true)
                                                .logoutSuccessUrl("/login?logout")
                                                .invalidateHttpSession(true)
                                                .deleteCookies("JSESSIONID")
                                                .permitAll())
                                .csrf(csrf -> csrf.disable());

                ;
        }

        @Bean
        public PersistentTokenRepository persistentTokenRepository() {
                JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
                jdbcTokenRepository.setDataSource(dataSource);
                return jdbcTokenRepository;
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                BCryptPasswordEncoder b = new BCryptPasswordEncoder();
                System.out.println(b);
                return b;
        }

}