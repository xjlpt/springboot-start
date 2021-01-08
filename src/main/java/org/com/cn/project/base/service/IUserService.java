package org.com.cn.project.base.service;

import org.com.cn.project.base.enty.User;
import org.com.cn.project.global.Page;

import java.util.List;

public interface IUserService {
    List<User> getUserList();

    void insertUserList(List<User> userList);

    List<User> getUserList(Page page);

    int getUserCount(Page page);
    List<User> getUserListByParms(User user);
}
