package com.alec.oauthshushengdemo.security;

import cn.hutool.jwt.JWTUtil;
import com.alec.oauthshushengdemo.model.web.MyConstant;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * ClassName: JwtAuthenticationTokenFilter
 * Package: com.alec.oauthshushengdemo.security
 * Description:
 * 拦截http请求，然后检查其header: Authorization携带的 jwt。如果通过了就从jwt中获取用户名，然后到数据库（或者redis）里查询用户信息，然后生成验证通过的UsernamePasswordAuthenticationToken
 *
 * @Author Alec
 * @Create 2024/7/19 17:42
 * @Version 1.0
 */
@Slf4j
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private final String AUTH_HEADER = "Authorization";
    private final String AUTH_HEADER_TYPE = "Bearer";

    @Autowired
    private UserDetailsService userDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 获取 token
        String authHeader = request.getHeader(AUTH_HEADER);
        if(Objects.isNull(authHeader) || !authHeader.startsWith(AUTH_HEADER_TYPE)){
            filterChain.doFilter(request,response);
            return;
        }
        String authToken = authHeader.split(" ")[1];
        log.info("authToken: {}",authToken);

        // 校验 token
        if(JWTUtil.verify(authToken, MyConstant.JWT_SIGN_KEY.getBytes(StandardCharsets.UTF_8))){
            log.info("invalid token");
            filterChain.doFilter(request,response);
            return;
        }

        // 获取用户信息
        String username = (String) JWTUtil.parseToken(authToken).getPayload("username");
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        // 生成验证通过的 Token
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        // 设置认证信息，将验证过的凭证保存在context中方便后续使用
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request,response);
    }
}
