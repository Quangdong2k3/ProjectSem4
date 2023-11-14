package com.StoreBook.advice;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.StoreBook.service.MyUserDetailService;

@Configuration

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private MyUserDetailService userDetailService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(requests -> requests
                        .antMatchers("/BookStore/client/**","/images/**", "/register","/login**","/admin/**","/user/**", "/api/register").permitAll()
                        .antMatchers("/BookStore/admin/**").hasRole("ADMIN")
                        .antMatchers("/BookStore/client/mycart","/BookStore/api/cart/**").hasAnyRole("CUSTOMER","ADMIN")
                        .anyRequest().authenticated())
                .formLogin(login -> login
                        .loginPage("/login") // S? d?ng trang ??ng nh?p m?c ??nh
                        .usernameParameter("email")
                        .permitAll())
                        .oauth2Login(oauth2Login -> oauth2Login
                        .loginPage("/login") // Trang đăng nhập tự động được sử dụng khi sử dụng OAuth 2.0
                         // Redirect URL sau khi đăng nhập thành công
                        // .userInfoEndpoint(userInfoEndpoint ->
                        //     userInfoEndpoint
                        //         .userService(oAuth2UserService) // Custom UserService để xử lý thông tin người dùng
                        // )
                    )
                .rememberMe(me -> me

                        .rememberMeParameter("remember-me")
                        .tokenRepository(persistentTokenRepository()))
                .logout(logout -> logout
                        .logoutUrl("/dologout")
                        .clearAuthentication(true)
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
        return new BCryptPasswordEncoder();
    }

}