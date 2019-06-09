package com.xiaostudy.shiro_test1.web.controller;

import com.xiaostudy.shiro_test1.entity.TestRedisEntity;
import com.xiaostudy.shiro_test1.entity.UserEntity;
import com.xiaostudy.shiro_test1.service.TestRedisService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户页面跳转
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 2019/6/8
 * Time: 15:21
 * Description: No Description
 */
@Controller
@RequestMapping("/testRedis")
public class TestRedisController {

    @Autowired
    private TestRedisService testRedisService;

    @RequestMapping("/index")
    public String index() {
        return "testRedis/index";
    }

    @RequestMapping("/all")
    @ResponseBody
    public Map getTestRedisAll() {
        List<TestRedisEntity> redisEntityList = testRedisService.selectAll();
        Map map = new HashMap();
        map.put("redisEntityList", redisEntityList);
        return map;
    }

    @RequestMapping("/pages")
    @ResponseBody
    public Map getTestRedisPages(String page, String rows) {
        List<TestRedisEntity> redisEntityList = testRedisService.selectPages(Integer.parseInt(page), Integer.parseInt(rows));
        int total = testRedisService.selectTotal();
        Map map = new HashMap();
        map.put("redisEntityList", redisEntityList);
        map.put("pages", total/Integer.parseInt(rows)+((total%Integer.parseInt(rows) == 0) ? 0 : 1));
        map.put("total", total);
        map.put("current", Integer.parseInt(page));
        return map;
    }

    @RequestMapping("/getByName")
    @ResponseBody
    public Map getByName(String name) {
        TestRedisEntity testRedisEntity = testRedisService.selectByName(name);
        Map map = new HashMap();
        map.put("redisEntity", testRedisEntity);
        return map;
    }

    @RequestMapping("/get")
    @ResponseBody
    public Map getTestRedisByName(String name) {
        TestRedisEntity testRedisEntity = testRedisService.selectByName(name);
        Map map = new HashMap();
        map.put("testRedisEntity", testRedisEntity);
        return map;
    }

    @RequestMapping("/insert")
    @ResponseBody
    @RequiresPermissions(value = "vip")
    public Map insertTestRedis(TestRedisEntity testRedisEntity) {
        int insert = testRedisService.insert(testRedisEntity);
        Map map = new HashMap();
        map.put("result", insert);
        return map;
    }

    @RequestMapping("/update")
    @ResponseBody
    @RequiresPermissions(value = "vip")
    public Map updateTestRedis(TestRedisEntity testRedisEntity) {
        int update = testRedisService.update(testRedisEntity);
        Map map = new HashMap();
        map.put("result", update);
        return map;
    }

    @RequestMapping("/deleteById")
    @ResponseBody
    @RequiresPermissions(value = "vip")
    public Map deleteById(String id) {
        int deleteById = testRedisService.deleteById(Integer.parseInt(id));
        Map map = new HashMap();
        map.put("result", deleteById);
        return map;
    }

    @RequestMapping("/deleteByName")
    @ResponseBody
    @RequiresPermissions(value = "vip")
    public Map deleteByName(String name) {
        int deleteByName = testRedisService.deleteByName(name);
        Map map = new HashMap();
        map.put("result", deleteByName);
        return map;
    }

    @RequestMapping("/form")
    public String form() {
        return "testRedis/form";
    }
}
