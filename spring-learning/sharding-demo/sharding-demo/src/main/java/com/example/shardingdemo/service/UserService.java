package com.example.shardingdemo.service;

import com.example.shardingdemo.mapper.UserMapper;
import com.example.shardingdemo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
    public class UserService {
        @Autowired
        private UserMapper userMapper;

        /**
         * 插入用户信息
         * @param user 用户实体
         */
        public void insert(User user) {
            userMapper.insert(user);
        }

        /**
         * 根据用户ID查询用户信息
         * @param userId 用户ID
         * @return 用户实体
         */
        public User selectById(Long userId) {
            return userMapper.selectById(userId);
        }
    }
