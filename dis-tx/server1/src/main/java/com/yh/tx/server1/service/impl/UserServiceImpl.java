package com.yh.tx.server1.service.impl;

import com.yh.global.tx.annotations.GlobalTransaction;
import com.yh.tx.server1.dao.UserMapper;
import com.yh.tx.server1.entity.User;
import com.yh.tx.server1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @GlobalTransaction
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insertAndUpdate(String newEmail) {
        User user = new User();
        user.setEmail("ahyanhuan@sina.com");
        user.setPassword("123456");
        user.setPhone("18611862917");
        user.setUsername(UUID.randomUUID().toString());
        user.setUserType(1);
        user.setCreateTime(new Date());
        user.setUserStatus(1);
        user.setCreateUser("yh");
        user.setUpdateTime(new Date());
        user.setUpdateUser("yh");
        userMapper.insert(user);

        user.setId(5L);
        user.setEmail(newEmail);
        user.setUsername(UUID.randomUUID().toString());
        userMapper.updateUser(user);
        return 2;
    }


}
