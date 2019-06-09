package com.xiaostudy.shiro_test1.service;

import com.xiaostudy.shiro_test1.entity.TestRedisEntity;
import com.xiaostudy.shiro_test1.entity.UserEntity;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 2019/6/8
 * Time: 14:55
 * Description: No Description
 */
public interface TestRedisService {

    public List<TestRedisEntity> selectAll();

    public List<TestRedisEntity> selectPages(int page, int rows);

    public int selectTotal();

    public TestRedisEntity selectById(int id);

    public TestRedisEntity selectByName(String name);

    public int insert(TestRedisEntity testRedisEntity);

    public int update(TestRedisEntity testRedisEntity);

    public int deleteById(int id);

    public int deleteByName(String name);
}

