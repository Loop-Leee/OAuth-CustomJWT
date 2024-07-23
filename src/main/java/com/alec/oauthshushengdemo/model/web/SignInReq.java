package com.alec.oauthshushengdemo.model.web;

import jdk.jfr.DataAmount;
import lombok.Data;

/**
 * ClassName: SignInReq
 * Package: com.alec.oauthshushengdemo.model.web
 * Description:
 *
 * @Author Alec
 * @Create 2024/7/19 17:28
 * @Version 1.0
 */

@Data
public class SignInReq {

    private String username;
    private String password;

}
