package org.com.cn.project.com.cn.project;

import org.com.cn.project.SpringbootStartApplication;
import org.com.cn.project.base.dao.UserMapper;
import org.com.cn.project.base.enty.User;
import org.json.JSONArray;
import org.json.JSONString;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.annotation.security.RunAs;
import java.util.List;

@SpringBootTest(classes = SpringbootStartApplication.class)
public class BaseTest {

    @Resource
    private UserMapper userMapper;

    @Test
    public void test(){
        List<User> userList = userMapper.getUserList();
        System.out.println(userList);

    }



}
