package com.alec.oauthshushengdemo.controller;

import cn.hutool.json.JSONUtil;
import cn.hutool.jwt.JWT;
import com.alec.oauthshushengdemo.model.web.MyConstant;
import com.alec.oauthshushengdemo.model.web.SignInReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: AuthController
 * Package: com.alec.oauthshushengdemo.controller
 * Description:
 *
 * @Author Alec
 * @Create 2024/7/19 17:24
 * @Version 1.0
 */

@RestController
@RequestMapping("/user")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public String register() {
        return "注册成功";
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody SignInReq req) {
        System.out.println("接收到POST请求, 开始登录...");

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword());


        try {
            authenticationManager.authenticate(authenticationToken);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("用户名或密码错误");
        }

        //上一步没有抛出异常说明认证成功，我们向用户颁发jwt令牌
        String token = JWT.create()
                .setPayload("username", req.getUsername())
                .setKey(MyConstant.JWT_SIGN_KEY.getBytes(StandardCharsets.UTF_8))
                .sign();
        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        String result = JSONUtil.toJsonStr(map);
        return ResponseEntity.ok(result);


    }

}
