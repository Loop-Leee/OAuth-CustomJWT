package com.alec.oauthshushengdemo.config;

import com.alec.oauthshushengdemo.security.JwtAuthenticationProvider;
import com.alec.oauthshushengdemo.security.JwtAuthenticationTokenFilter;
import com.alec.oauthshushengdemo.security.MyAccessDeniedHandler;
import com.alec.oauthshushengdemo.security.MyUnauthorizedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * ClassName: WebSecurityConfig
 * Package: com.alec.oauthshushengdemo.config
 * Description:
 *
 * @Author Alec
 * @Create 2024/7/19 18:31
 * @Version 1.0
 */

@Configuration
@EnableWebSecurity(debug = true)
public class WebSecurityConfig {

    // 添加一个自定义 Filter
    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter(){
        return new JwtAuthenticationTokenFilter();
    }

    // 添加一个 Manager
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }


    // 添加一个自定义 Provider
    @Bean
    public JwtAuthenticationProvider jwtAuthenticationProvider() {
        return new JwtAuthenticationProvider();
    }

    // 添加一个 Encoder
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private MyUnauthorizedHandler myUnauthorizedHandler;

    @Autowired
    private MyAccessDeniedHandler myAccessDeniedHandler;

    // 添加并设置 FilterChain
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        //由于使用的是JWT，这里不需要csrf防护
        httpSecurity.csrf(CsrfConfigurer::disable)
                //基于token，所以不需要session
                .sessionManagement(sessionManagementConfigurer -> sessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorizationRegistry -> authorizationRegistry
                        //允许对于网站静态资源的无授权访问
                        .requestMatchers(HttpMethod.GET, "/user/login", "/home", "/login.html", "/*.html", "/favicon.ico").permitAll()
                        //对登录注册允许匿名访问
                        .requestMatchers("/", "/user/login", "/user/register", "/static/**").permitAll()
                        //跨域请求会先进行一次options请求
                        .requestMatchers(HttpMethod.OPTIONS).permitAll()
                        // 除上面外的所有请求全部需要鉴权认证
                        .anyRequest().authenticated()
                )
                //禁用缓存
                .headers(headersConfigurer -> headersConfigurer
                        .cacheControl(HeadersConfigurer.CacheControlConfig::disable)
                )
                //使用自定义provider
                .authenticationProvider(jwtAuthenticationProvider())
                //添加JWT filter
                .addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class)
                //添加自定义未授权和未登录结果返回
                .exceptionHandling(exceptionConfigurer -> exceptionConfigurer
                        .accessDeniedHandler(myAccessDeniedHandler)
                        .authenticationEntryPoint(myUnauthorizedHandler));


        return httpSecurity.build();
    }



}
