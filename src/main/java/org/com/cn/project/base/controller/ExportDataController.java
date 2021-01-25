package org.com.cn.project.base.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.com.cn.project.base.enty.User;
import org.com.cn.project.base.enty.exportData;
import org.com.cn.project.base.service.IExportDataService;
import org.com.cn.project.global.Page;
import org.com.cn.project.global.ResultMap;
import org.com.cn.project.util.ConstantUtils;
import org.com.cn.project.util.Excel.ExcelTemplate;
import org.com.cn.project.util.Excel.ExcelUtils;
import org.com.cn.project.util.TemplateConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
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
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

@RequestMapping("exportData")
@Controller
public class ExportDataController {
    private final Logger loger = LoggerFactory.getLogger(BaseController.class);
    @Autowired
    private TemplateConfig templateConfig;
    @Autowired
    private IExportDataService dataService;

    @RequestMapping("pageOfExportDataList")
    public String pageOfExportDataList(ModelMap map) {

        return "exportDataList";
    }

    @RequestMapping("exportDataList")
    @ResponseBody
    public Object exportDataList(Page page, @RequestParam("limit") int limit) {
        ConstantUtils.getPage(limit, page);
        List<exportData> exportDataList = dataService.getExportDataList(page);
        int count = dataService.getExportDataCount(page);
        page.setTotalRecord(count);
        return new ResultMap<List<exportData>>("", exportDataList, 0, count);
    }

    @RequestMapping(value = "/importInputData", method = RequestMethod.POST)
    @ResponseBody
    public String importInputData(HttpServletRequest request) {
        HashMap<String, String> map = new HashMap<>();
        String code = "0";
        String msg = "";

        try {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            MultipartFile file = multipartRequest.getFile("file");
            if (file.getOriginalFilename().endsWith(".xls") || file.getOriginalFilename().endsWith(".xlsx")) {
                Map<String, String> errorMsg = new HashMap<String, String>();
                List<exportData> excelDataList = ExcelUtils.readExcel(file, ConstantUtils.TEMPLATE_IMPORT_INPUT_EXPORTDATA_TITLE, ConstantUtils.TEMPLATE_IMPORT_INPUT_EXPORTDATA_COLUMN, exportData.class, null, errorMsg, false);
                if (null != excelDataList) {
                    dataService.insertInData(excelDataList);
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

    @RequestMapping(value = "/importOutputData", method = RequestMethod.POST)
    @ResponseBody
    public String importOutputData(HttpServletRequest request) {
        HashMap<String, String> map = new HashMap<>();
        String code = "0";
        String msg = "";

        try {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            MultipartFile file = multipartRequest.getFile("file");
            if (file.getOriginalFilename().endsWith(".xls") || file.getOriginalFilename().endsWith(".xlsx")) {
                Map<String, String> errorMsg = new HashMap<String, String>();
                List<exportData> excelDataList = ExcelUtils.readExcel(file, ConstantUtils.TEMPLATE_IMPORT_OUTPUT_EXPORTDATA_TITLE, ConstantUtils.TEMPLATE_IMPORT_OUTPUT_EXPORTDATA_COLUMN, exportData.class, null, errorMsg, false);
                if (null != excelDataList) {
                    dataService.updateInData(excelDataList);
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

    @RequestMapping("exportInOutData")
    @ResponseBody
    public Object getList(HttpServletRequest request, HttpServletResponse response, exportData data) throws IOException {
        List<exportData> dataList = dataService.getExportDataParams(data);
        HashMap<String,String> map = new HashMap<>();
        JSONObject json = new JSONObject();
        String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String templatePath = templateConfig.getTemplatePath();
        String templateFileName = templateConfig.getTemplateInOutPutName();
        //模板文件路径
//        String templateFilePath = path + templatePath + templateFileName;
//        String templateFilePath = "D:/" + templateFileName;
        InputStream templateFilePath = this.getClass().getClassLoader().getResourceAsStream(templateFileName);
        System.out.println(templateFilePath+"-----测试");

        //临时文件名
        String fileName = UUID.randomUUID().toString().replace("-", "") + ".xlsx";
        //临时文件存储的文件夹路径
//        String targetPath = path + "/temp/standingbook/";
        String targetPath = "D:" + "/temp/";
//        String targetPath = this.getClass().getClassLoader().getResourceAsStream("/temp/standingbook/");
        //判断文件夹是否存在，不存在则创建
        File pathFile = new File(targetPath);
        if (!pathFile.exists() || !pathFile.isDirectory()) {
            pathFile.mkdirs();
        }
        //临时文件路径
        String targetFilePath = targetPath + fileName;
        //下载时显示文件名
        String newName = "列表" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        boolean flag = ExcelTemplate.generateExcel(dataList, exportData.class, ConstantUtils.TEMPLATE_EXPORTDATA_COLUMN, true, newName, templateFilePath.toString(), targetFilePath);
        if (flag) {
            ExcelTemplate.downloadWithDelete(targetFilePath, newName + ".xlsx", response);
            map.put("code","200");

        }
        json = JSON.parseObject(map.toString());
        return json;
    }

}