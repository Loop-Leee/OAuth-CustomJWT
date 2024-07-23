package com.alec.oauthshushengdemo.mapper;

import com.alec.oauthshushengdemo.domain.MyUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Alec
* @description 针对表【user】的数据库操作Mapper
* @createDate 2024-07-20 16:13:30
* @Entity generator.domain.User
*/

@Mapper
public interface UserMapper extends BaseMapper<MyUser> {
}