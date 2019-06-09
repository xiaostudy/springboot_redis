package com.xiaostudy.shiro_test1.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.xiaostudy.shiro_test1.realm.UserRealm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Shiro配置类
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 2019/6/8
 * Time: 15:06
 * Description: No Description
 */
@Configuration
public class ShiroConfig {

    // 创建自定义 realm
    @Bean
    public UserRealm userRealm() {
        UserRealm userRealm = new UserRealm();
        return userRealm;
    }

    // 创建 SecurityManager 对象
    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm());
        return securityManager;
    }

    // Filter工厂，设置对应的过滤条件和跳转条件
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        /**
         * anon：匿名用户可访问
         * authc：认证用户可访问
         * user：使用rememberMe可访问
         * perms：对应权限可访问
         * role：对应角色权限可访问
         */
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
//        map.put("/resources/**", "anon");//匿名访问静态资源
        map.put("/static/**", "anon");//匿名访问静态资源
        map.put("/statics/**", "anon");//匿名访问静态资源
        map.put("/jquery.jqGrid-4.6.0/**", "anon");//匿名访问静态资源
        map.put("/jquery-ui-1.12.0-rc.2/**", "anon");//匿名访问静态资源
        map.put("/layui/**", "anon");//匿名访问静态资源
        map.put("/external/**", "anon");//匿名访问静态资源
        map.put("/jquery/**", "anon");//匿名访问静态资源
        map.put("/js/**", "anon");//匿名访问静态资源
        map.put("/css/**", "anon");//匿名访问静态资源
        map.put("/images/**", "anon");//匿名访问静态资源
        // 开放登录接口
        map.put("/login", "anon");
//        map.put("/login", "authc");
        // 对登录跳转接口进行释放
        map.put("/error", "anon");
        // 对所有用户认证
        map.put("/**", "authc");
        // 登出
        map.put("/logout", "logout");
        // 登录
        // 注意：这里配置的 /login 是指到 @RequestMapping(value="/login")中的 /login
        shiroFilterFactoryBean.setLoginUrl("/login");
        // 首页
        shiroFilterFactoryBean.setSuccessUrl("/index");
        // 错误页面，认证不通过跳转
        shiroFilterFactoryBean.setUnauthorizedUrl("/error/unAuth");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        return shiroFilterFactoryBean;
    }

    // 加入注解的使用，不加这个，注解不生效
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    // 跟上面的注解配置搭配使用，有时候加了上面的配置后注解不生效，需要加入下面的配置
    @Bean
    @ConditionalOnMissingBean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator app = new DefaultAdvisorAutoProxyCreator();
        app.setProxyTargetClass(true);
        return app;
    }

    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }
}