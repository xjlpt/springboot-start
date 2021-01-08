package org.com.cn.project.base.service.impl;

import org.com.cn.project.base.dao.UserMapper;
import org.com.cn.project.base.enty.User;
import org.com.cn.project.base.service.IUserService;
import org.com.cn.project.global.Page;
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

    @Override
    public void insertUserList(List<User> userList) {
        userMapper.insertUserList(userList);
    }

    @Override
    public List<User> getUserList(Page page) {
        return userMapper.getDataList(page);
    }

    @Override
    public int getUserCount(Page page) {
        return userMapper.getUserCount(page);
    }

    @Override
    public List<User> getUserListByParms(User user) {
        return userMapper.getUserListByParms(user);
    }


}
