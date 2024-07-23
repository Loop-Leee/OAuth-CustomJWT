package com.alec.oauthshushengdemo.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
/**
 * ClassName: BcryptCreater
 * Package: com.alec.oauthshushengdemo.utils
 * Description:
 *
 * @Author Alec
 * @Create 2024/7/23 11:09
 * @Version 1.0
 */
public class BcryptCreater {


    BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
    String hashed_password;

    // 对密码进行bcrypt哈希
    public String getPassword(String password) {

        hashed_password = bcrypt.encode(password);
        return hashed_password;

    }

    public static void main(String[] args) {

        BcryptCreater bcryptCreater = new BcryptCreater();
        System.out.println(bcryptCreater.getPassword("password2"));
    }


}
