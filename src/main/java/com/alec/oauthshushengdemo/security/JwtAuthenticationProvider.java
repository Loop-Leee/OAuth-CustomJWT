package com.alec.oauthshushengdemo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * ClassName: JwtAuthenticationProvider
 * Package: com.alec.oauthshushengdemo.security
 * Description:
 *
 * @Author Alec
 * @Create 2024/7/20 13:07
 * @Version 1.0
 */
public class JwtAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsService userDetailsService;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 获取 authentication 中的 username & password
        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        // 获取 数据源中的 userDetails
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if(userDetails == null){
            throw new UsernameNotFoundException("用户不存在");
        }

        // 密码校验
        if (passwordEncoder.matches(password, userDetails.getPassword())) {
            // 校验成功 -> 返回授权后的authentication
            return new UsernamePasswordAuthenticationToken(username, password, userDetails.getAuthorities());
        }

        // 校验失败 -> 抛出异常
        throw new BadCredentialsException("用户名或密码错误!");

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.equals(authentication);
    }
}
