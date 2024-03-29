package com.qianlq.eurekacustomer.controller;

import com.github.pagehelper.PageInfo;
import com.qianlq.eurekacustomer.common.BaseResult;
import com.qianlq.eurekacustomer.model.dto.UserDto;
import com.qianlq.eurekacustomer.model.entity.UserEntity;
import com.qianlq.eurekacustomer.service.TestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author qianliqing
 * @date 2018-09-28 下午2:37
 * mail: qianlq0824@gmail.com
 * <p>
 * 测试类
 */

@RefreshScope
@RestController
@RequestMapping(value = "/test")
@Api(value = "测试", description = "测试模块", position = 1)
public class TestController {

    @Value("${profile}")
    private String profile;

    //    @Value("${mysql}")
    private String mysql;

    //    @Value("${redis}")
    private String redis;

    @Value("${test.vaue}")
    private String testValue;
    @Autowired
    private TestService testService;
/*    @Autowired
    private RedisCacheService RedisCacheService;*/
    @Autowired
    private RedisTemplate redisTemplate;

    @ApiOperation(value = "返回用户输入的结果", notes = "返回用户输入的结果")
    @RequestMapping(value = "/result", method = RequestMethod.GET)
    public String test(@ApiParam(value = "测试内容", required = true) @RequestParam(value = "text") String text) {
        return testService.test(text);
    }

    @ApiOperation(value = "测试全局异常捕获", notes = "测试全局异常捕获")
    @RequestMapping(value = "/exception", method = RequestMethod.GET)
    public void testException(@ApiParam(value = "测试内容", required = true) @RequestParam(value = "text") String text) {
        testService.testException(text);
    }

    @ApiOperation(value = "返回统一的baseResult", notes = "返回统一的baseResult，推荐使用这种方式，所有的返回结果统一用baseResult组装")
    @RequestMapping(value = "/baseResult", method = RequestMethod.GET)
    public BaseResult<String> testBaseResult(@ApiParam(value = "测试内容", required = true) @RequestParam(value = "text") String text) {
        return testService.testBaseResult(text);
    }

    @ApiOperation(value = "根据用户id获取用户信息", notes = "根据用户id获取用户信息")
    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public BaseResult<UserEntity> testMybatis(@ApiParam(value = "用户id", required = true) @RequestParam(value = "id") String id) {
        return testService.testMybatis(id);
    }

    @ApiOperation(value = "测试redis缓存", notes = "根据用户id获取用户信息")
    @RequestMapping(value = "/cache/find", method = RequestMethod.GET)
    //@Cacheable(value = "User",key="#id")
    public BaseResult<UserEntity> testRedis(@ApiParam(value = "用户id", required = true) @RequestParam(value = "id") String id) {
        return testService.testRedis(id);
    }

    @ApiOperation(value = "读取配置文件", notes = "读取配置文件")
    @RequestMapping(value = "/config", method = RequestMethod.GET)
    public String testConfig() {
        return "mysql:" + mysql + ", redis:" + redis;
    }

    @ApiOperation(value = "测试消息总线", notes = "测试消息总线")
    @RequestMapping(value = "/bus", method = RequestMethod.GET)
    public String testBus() {
        return profile;
    }

    @ApiOperation(value = "测试分页插件", notes = "测试分页插件")
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public BaseResult<PageInfo<UserDto>> testPage(@ApiParam(value = "当前页码", required = true) @RequestParam(value = "pageNum") Integer pageNum,
                                                  @ApiParam(value = "每页长度", required = true) @RequestParam(value = "pageSize") Integer pageSize) {
        return testService.testPageHelper(pageNum, pageSize);
    }

    @ApiOperation(value = "测试MapStruct", notes = "测试MapStruct")
    @RequestMapping(value = "/struct", method = RequestMethod.GET)
    public BaseResult<UserDto> testMapStruct(@ApiParam(value = "id", required = true) @RequestParam(value = "id") String id) {
        return testService.testMapStruct(id);
    }

    @ApiOperation(value = "测试redis缓存", notes = "插入一条用户数据")
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public BaseResult<UserEntity> insertUser(@ApiParam(value = "name", required = true) @RequestParam(value = "name") String name,
                                             @ApiParam(value = "birth", required = true) @RequestParam(value = "birth") String birth) {
        return testService.addUser(name, birth);
    }

    @ApiOperation(value = "测试redis缓存", notes = "更新一条用户数据")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public BaseResult<UserEntity> updateUser(@ApiParam(value = "id", required = true) @RequestParam(value = "id") String id,
                                             @ApiParam(value = "name", required = true) @RequestParam(value = "name") String name,
                                             @ApiParam(value = "birth") @RequestParam(value = "birth", required = false) String birth) {
        return testService.updateUser(id, name, birth);
    }
    @ApiOperation(value = "测试testValue", notes = "测试testValue")
    @RequestMapping(value = "/testValue", method = RequestMethod.GET)
    public void testValue() {
        System.out.println("testValue:"+testValue);
    }

    @RequestMapping(value = "/testValue1", method = RequestMethod.GET)
    public String testRedis() {
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        // 修改map在插入就可以
        operations.set("name","bob");
        Object a = operations.get("name");
        System.out.println("a:"+a);
        return "succese";
    }


}
