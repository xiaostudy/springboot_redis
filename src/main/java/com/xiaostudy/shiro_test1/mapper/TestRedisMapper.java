package com.xiaostudy.shiro_test1.mapper;

import com.xiaostudy.shiro_test1.entity.TestRedisEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 2019/6/8
 * Time: 22:07
 * Description: No Description
 */
@Mapper
public interface TestRedisMapper {

    public List<TestRedisEntity> selectAll();

    /**
     * 简单分页
     * <p>@Param的作用是告诉mapper他的参数名，因为存在多个参数</p>
     * @param page
     * @param rows
     * @return
     */
    public List<TestRedisEntity> selectPages(@Param("page") int page, @Param("rows") int rows);

    public int selectTotal();

    public TestRedisEntity selectById(int id);

    public TestRedisEntity selectByName(String name);

    public int insert(TestRedisEntity testRedisEntity);

    public int update(TestRedisEntity testRedisEntity);

    public int deleteById(int id);

    public int deleteByName(String name);
}
