package org.com.cn.project.base.service.impl;

import org.com.cn.project.base.dao.UserMapper;
import org.com.cn.project.base.enty.User;
import org.com.cn.project.base.service.IUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements IUserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public List<User> getUserList() {
        return userMapper.getUserList();
    }
}
