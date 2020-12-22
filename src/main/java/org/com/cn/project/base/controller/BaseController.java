package org.com.cn.project.base.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Param;
import org.com.cn.project.base.enty.User;
import org.com.cn.project.base.service.IUserService;
import org.com.cn.project.global.Page;
import org.com.cn.project.global.ResultMap;
import org.com.cn.project.util.ConstantUtils;
import org.com.cn.project.util.Excel.ExcelTemplate;
import org.com.cn.project.util.Excel.ExcelUtils;
import org.com.cn.project.util.TemplateConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@RequestMapping("base")
@Controller
public class BaseController {

    private final Logger loger = LoggerFactory.getLogger(BaseController.class);
    @Autowired
    private IUserService userService;
    @Autowired
    private TemplateConfig templateConfig;

    @RequestMapping("getList")
    @ResponseBody
    public Object getList(HttpServletRequest request, HttpServletResponse response) {
        List<User> userList = userService.getUserList();
        String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String templatePath = templateConfig.getTemplatePath();
        String templateFileName = templateConfig.getTemplateFileName();
        System.out.println(Thread.currentThread().getContextClassLoader().getResource("").getPath() + "template");
        //模板文件路径
        String templateFilePath = path + templatePath + templateFileName;
        //临时文件名
        String fileName = UUID.randomUUID().toString().replace("-", "") + ".xlsx";
        //临时文件存储的文件夹路径
        String targetPath = path + "/temp/standingbook/";
        //判断文件夹是否存在，不存在则创建
        File pathFile = new File(targetPath);
        if (!pathFile.exists() || !pathFile.isDirectory()) {
            pathFile.mkdirs();
        }
        //临时文件路径
        String targetFilePath = targetPath + fileName;
        //下载时显示文件名
        String newName = "列表" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        boolean flag = ExcelTemplate.generateExcel(userList, User.class, ConstantUtils.TEMPLATE_EXPORT_COLUMN, true, newName, templateFilePath, targetFilePath);
        if (flag) {
            ExcelTemplate.downloadWithDelete(targetFilePath, newName + ".xlsx", response);
        }
        return userList;
    }

    @RequestMapping("ceshi")
    public String ceshi(ModelMap map) {

//        map.addAttribute("name", "测试");
        return "index";
    }

    @RequestMapping("pageOfDataList")
    public String pageOfDataList(ModelMap map){

        return "dataList";
    }

    @RequestMapping("dataList")
    @ResponseBody
    public Object dataList(Page page, @RequestParam("limit") int limit) {
        ConstantUtils.getPage(limit, page);
        List<User> userList = userService.getUserList(page);
        int count = userService.getUserCount(page);
        page.setTotalRecord(count);
        return new ResultMap<List<User>>("", userList, 0, count);
    }


    @RequestMapping(value = "/importData", method = RequestMethod.POST)
    @ResponseBody
    public String importData(HttpServletRequest request) {
        HashMap<String, String> map = new HashMap<>();
        String code = "0";
        String msg = "";

        try {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            MultipartFile file = multipartRequest.getFile("file");
            if (file.getOriginalFilename().endsWith(".xls") || file.getOriginalFilename().endsWith(".xlsx")) {
                Map<String, String> errorMsg = new HashMap<String, String>();
                List<User> excelDataList = ExcelUtils.readExcel(file, ConstantUtils.TEMPLATE_IMPORT_TITLE, ConstantUtils.TEMPLATE_IMPORT_COLUMN, User.class, null, errorMsg, false);
                if (null != excelDataList) {
                    //根据项目预算代码和GRP系统编码比较判断项目是否存在
                    userService.insertUserList(excelDataList);
                    code = "1";
                } else {
                    code = "2";
                    msg = errorMsg.get("msg");
                }
            } else {
                code = "2";
                msg = "请选择正确的文件类型！";
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("code", code);
        map.put("msg", msg);
        return JSON.toJSONString(map);
    }
}
