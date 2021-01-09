package org.com.cn.project.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "template")
public class TemplateConfig {

    private String templatePath;
    private String templateFileName;
    private String templateInOutPutName;

    public String getTemplatePath() {
        return templatePath;
    }

    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }

    public String getTemplateFileName() {
        return templateFileName;
    }

    public void setTemplateFileName(String templateFileName) {
        this.templateFileName = templateFileName;
    }

    public String getTemplateInOutPutName() {
        return templateInOutPutName;
    }

    public void setTemplateInOutPutName(String templateInOutPutName) {
        this.templateInOutPutName = templateInOutPutName;
    }
}
