/*
package com.qianlq.eurekacustomer.service.impl;

import com.qianlq.eurekacustomer.service.OrderService;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

*/
/**
 * @ClassName OrderServiceImpl
 * @Description TODO
 * @Author Administrator
 * @Date 2019/11/22 14:51
 * @Version
 **//*

@Service
@FeignClient(value = "service-order")
public class OrderServiceImpl implements OrderService {

    @Override
    @RequestMapping(value = "/test/result", method = RequestMethod.GET)
    public String testCustomer(String text) {

        return null;
    }
}
*/
