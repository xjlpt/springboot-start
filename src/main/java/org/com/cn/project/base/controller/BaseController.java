package org.com.cn.project.base.controller;

import org.com.cn.project.base.enty.User;
import org.com.cn.project.base.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequestMapping("base")
@Controller
public class BaseController {

    private final Logger loger = LoggerFactory.getLogger(BaseController.class);
    @Autowired
    private IUserService userService;

    @RequestMapping("getList")
    @ResponseBody
    public Object getList() {
        List<User> userList = userService.getUserList();
        System.out.println("ceshi");
        loger.info("这是什么什么的日志");
        loger.info("这是什么什么的日志");
        loger.debug("这是什么什么的日志001");
        return userList;
    }

    @RequestMapping("ceshi")
    public String ceshi(ModelMap map) {

        map.addAttribute("name", "测试");
        return "index";
    }


}
