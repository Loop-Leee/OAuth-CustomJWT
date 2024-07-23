package com.alec.oauthshushengdemo.service;

import com.alec.oauthshushengdemo.domain.MyUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author Alec
 * @description 针对表【user】的数据库操作Service
 * @createDate 2024-07-20 16:13:30
 */


public interface UserService extends IService<MyUser> {

    public MyUser getUserByUserName(String username);

}
