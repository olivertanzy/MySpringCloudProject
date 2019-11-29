package com.qianlq.eurekaorder.controller;
import com.qianlq.eurekaorder.Etity.Test;
import com.qianlq.eurekaorder.common.message.SenderService;
import com.qianlq.eurekaorder.service.CustomerService;
import feign.Param;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @author qianliqing
 * @date 2018-09-28 下午7:56
 * mail: qianlq0824@gmail.com
 */

@RestController
@RequestMapping(value = "/test")
@Api(value = "测试", description = "测试模块", position = 1)
public class TestController {

    @Autowired
    private CustomerService customerService;
    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Autowired
    RestTemplate restTemplate;

    @ApiOperation(value = "返回用户输入的结果", notes = "返回用户输入的结果")
    @RequestMapping(value = "/result", method = RequestMethod.GET)
    public String test(@RequestParam(value = "text") String text) {
        return text;
    }

    @ApiOperation(value = "测试服务链路追踪", notes = "测试服务链路追踪")
    @RequestMapping(value = "/zipkin", method = RequestMethod.GET)
    public String testCustomer(@RequestParam(value = "text") String text) {
        return restTemplate.getForObject("http://service-customer/test/result?text=" + text, String.class);
    }


    @Autowired
    private SenderService senderService;
/*    @ApiOperation(value = "测试消息驱动", notes = "测试消息驱动")
    @RequestMapping(value = "/stream", method = RequestMethod.GET)
    public void testStream() {
        // 测试广播模式
        senderService.broadcast("同学们集合啦！");
        // 测试Direct模式
        senderService.direct("定点消息");
    }*/

    @ApiOperation(value = "测试服务间调用", notes = "测试服务间调用")
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String testCustomerService(@RequestParam(value = "text") String text) {
        return customerService.testCustomer(text);
    }

    @RequestMapping(value = "/testFinout", method = RequestMethod.POST)
    public String testFinout(@Param("id") String id){
        Test t = new Test();
        t.setAge(id);
        t.setAddress("重庆市渝中区");
        t.setName("测试Finout模式");
        senderService.tesFanout(t);
        return "下单成功";
    }
    @RequestMapping(value = "/testDirect", method = RequestMethod.POST)
    public String testDirect(@Param("id") String id){
        Test t = new Test();
        t.setAge(id);
        t.setAddress("重庆市渝中区");
        t.setName("测试testDirect模式");
        senderService.tesDirect(t);
        return "下单成功";
    }

    @RequestMapping(value = "/testTopic", method = RequestMethod.POST)
    public String testTopic(@Param("id") String id){
        Test t = new Test();
        t.setAge(id);
        t.setAddress("重庆市渝中区");
        t.setName("测试testTopic模式");
        senderService.tesTopic(t);
        return "下单成功";
    }
}
