package com.alec.oauthshushengdemo.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * ClassName: MyUnauthorizedHandler
 * Package: com.alec.oauthshushengdemo.security
 * Description:
 *
 * @Author Alec
 * @Create 2024/7/20 15:32
 * @Version 1.0
 */

@Slf4j
@Component
public class MyUnauthorizedHandler implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.error("Unauthorized error", authException);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().write("认证失败，请完成认证");
        response.getWriter().flush();
    }
}
