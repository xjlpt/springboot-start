package org.com.cn.project.global;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GlobalController {

    @RequestMapping("/404.do")
    public Object error_404(){
        return "你要找到的页面，不在了！";
    }
}
