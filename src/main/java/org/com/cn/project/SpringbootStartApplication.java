package org.com.cn.project;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("org.com.cn.project.*.dao")
@EnableTransactionManagement
public class SpringbootStartApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SpringbootStartApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringbootStartApplication.class, args);
    }

//    public static void main(String[] args) {
//        SpringApplication.run(SpringbootStartApplication.class, args);
//    }

}
