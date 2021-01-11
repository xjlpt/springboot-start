package org.com.cn.project.com.cn.project;

import org.com.cn.project.SpringbootStartApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.ValueOperations;

import javax.annotation.Resource;

@SpringBootTest(classes = SpringbootStartApplication.class)
public class RedisTemplateTest {

    @Resource
//    private RedisTemplate<String,String> redisTemplate;

    @Test
    public void SetRedisValue() throws Exception{
//        ValueOperations<String,String > ops = redisTemplate.opsForValue();
//        ops.set("name","enjoy");
//        System.out.println(ops.get("name"));

    }

}
