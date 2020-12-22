package org.com.cn.project.base.dao;

import org.apache.ibatis.annotations.Param;
import org.com.cn.project.base.enty.User;
import org.com.cn.project.global.Page;

import java.util.List;

public interface UserMapper {
    List<User> getUserList();


    public void insertUserList(@Param("userList") List<User> userList);

    public List<User> getDataList(Page page);

    int getUserCount(Page page);
}
