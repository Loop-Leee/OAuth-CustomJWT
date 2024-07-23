package com.alec.oauthshushengdemo.service.impl;

import com.alec.oauthshushengdemo.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.alec.oauthshushengdemo.domain.MyUser;
import com.alec.oauthshushengdemo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author Alec
* @description 针对表【user】的数据库操作Service实现
* @createDate 2024-07-20 16:13:30
*/

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, MyUser> implements UserService {

    @Autowired
    private UserMapper baseMapper;

    public MyUser getUserByUserName(String username){
        return baseMapper.selectOne(new QueryWrapper<MyUser>().eq("username", username));
//        return baseMapper.getUserByUserName(username);
    }

}




