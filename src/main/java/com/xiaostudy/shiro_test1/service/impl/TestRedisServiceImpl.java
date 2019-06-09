package com.xiaostudy.shiro_test1.service.impl;

import com.xiaostudy.shiro_test1.entity.TestRedisEntity;
import com.xiaostudy.shiro_test1.entity.UserEntity;
import com.xiaostudy.shiro_test1.mapper.TestRedisMapper;
import com.xiaostudy.shiro_test1.mapper.UserMapper;
import com.xiaostudy.shiro_test1.service.TestRedisService;
import com.xiaostudy.shiro_test1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 2019/6/8
 * Time: 14:56
 * Description: No Description
 */
@Service
public class TestRedisServiceImpl implements TestRedisService {

    @Autowired
    private TestRedisMapper testRedisMapper;

    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;

    @Override
    public List<TestRedisEntity> selectAll() {
        List<TestRedisEntity> redisEntityList = (List<TestRedisEntity>) redisTemplate.opsForValue().get("testRedisAll");
        if(null == redisEntityList) {
            redisEntityList = testRedisMapper.selectAll();
            redisTemplate.opsForValue().set("testRedisAll", redisEntityList);
        }
        return redisEntityList;
    }

    @Override
    public List<TestRedisEntity> selectPages(int page, int rows) {
        int current = (page-1)*rows;
        List<TestRedisEntity> redisEntityList = (List<TestRedisEntity>) redisTemplate.opsForValue().get("testRedisByPages_page" + page + "_rows" + rows);
        if(null == redisEntityList) {
            redisEntityList = testRedisMapper.selectPages(current, rows);
            redisTemplate.opsForValue().set("testRedisByPages_page" + page + "_rows" + rows, redisEntityList);
        }
        return redisEntityList;
    }

    @Override
    public int selectTotal() {
        String strTotal = (String) redisTemplate.opsForValue().get("testRedisTotal");
        int total;
        if(null == strTotal) {
            total = testRedisMapper.selectTotal();
            redisTemplate.opsForValue().set("testRedisTotal",String.valueOf(total));
        } else {
            total = Integer.parseInt(strTotal);
        }
        return total;
    }

    @Override
    public TestRedisEntity selectById(int id) {
        TestRedisEntity testRedisEntity = (TestRedisEntity) redisTemplate.opsForValue().get("testRedisById_" + id);
        if(null == testRedisEntity) {
            testRedisEntity = testRedisMapper.selectById(id);
            redisTemplate.opsForValue().set("testRedisById_" + id, testRedisEntity);
        }
        return testRedisEntity;
    }

    @Override
    public TestRedisEntity selectByName(String name) {
        TestRedisEntity testRedisEntity = (TestRedisEntity) redisTemplate.opsForValue().get("testRedisByName_" + name);
        if(null == testRedisEntity) {
            testRedisEntity = testRedisMapper.selectByName(name);
            redisTemplate.opsForValue().set("testRedisByName_" + name, testRedisEntity);
        }
        return testRedisEntity;
    }

    @Override
    public int insert(TestRedisEntity testRedisEntity) {
        int insert = testRedisMapper.insert(testRedisEntity);
        this.closeRedis(insert, "testRedisByPages");
        redisTemplate.opsForValue().set("testRedisTotal",String.valueOf(testRedisMapper.selectTotal()));
        return insert;
    }

    @Override
    public int update(TestRedisEntity testRedisEntity) {
        int update = testRedisMapper.update(testRedisEntity);
        this.closeRedis(update, "testRedisByPages");
        redisTemplate.opsForValue().set("testRedisById_" + testRedisEntity.getId(), testRedisEntity);
        redisTemplate.opsForValue().set("testRedisByName_" + testRedisEntity.getName(), testRedisEntity);
        return update;
    }

    @Override
    public int deleteById(int id) {
        TestRedisEntity testRedisEntity = this.selectById(id);
        int deleteById = testRedisMapper.deleteById(id);
        this.closeRedis(deleteById, "testRedisByPages");
        this.closeRedis(deleteById, "testRedisTotal");
        this.closeRedis(deleteById, "testRedisById_" + id);
        this.closeRedis(deleteById, "testRedisByName_" + testRedisEntity.getName());
        return deleteById;
    }

    @Override
    public int deleteByName(String name) {
        TestRedisEntity testRedisEntity1 = this.selectByName(name);
        int deleteByName = testRedisMapper.deleteByName(name);
        this.closeRedis(deleteByName, "testRedisByPages");
        this.closeRedis(deleteByName, "testRedisTotal");
        this.closeRedis(deleteByName, "testRedisByName_" + name);
        this.closeRedis(deleteByName, "testRedisById_" + testRedisEntity1.getId());
        return deleteByName;
    }

    /**
     * 清空对应实体的缓存
     * @param count
     */
    private void closeRedis(int count, String str) {
        if(0 != count) {
            redisTemplate.delete(redisTemplate.keys(str + "*"));
        }
    }
}