package com.alec.oauthshushengdemo.security;


import cn.hutool.core.bean.BeanUtil;
import com.alec.oauthshushengdemo.domain.MyUser;
import com.alec.oauthshushengdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * ClassName: UserDetailsServiceImpl
 * Package: com.alec.oauthshushengdemo.security
 * Description:
 *
 * @Author Alec
 * @Create 2024/7/20 15:58
 * @Version 1.0
 */

@Primary
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(Objects.isNull(username)) {
            throw new UsernameNotFoundException("用户名不能为空");
        }

        MyUser myUser = userService.getUserByUserName(username);
        // User user = BeanUtil.copyProperties(myUser, User.class);
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        User user = new User(myUser.getUsername(), myUser.getPassword(), authorities);

        return user;
    }
}
