package com.xiaostudy.shiro_test1.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 2019/6/9
 * Time: 0:51
 * Description: No Description
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * @author xiaobu
     * @date 2019/1/18 13:51
     * @param registry  registry
     * @descprition  等价于 http://localhost:9001/1.txt 依次在static upload目录下找1.txt文件
     * @version 1.0
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }

}