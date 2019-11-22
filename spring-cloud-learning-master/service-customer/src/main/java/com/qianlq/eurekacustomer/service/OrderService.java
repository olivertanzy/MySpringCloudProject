package com.qianlq.eurekacustomer.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sun.awt.SunHints;

/**
 * @ClassName OrderService
 * @Description TODO
 * @Author Administrator
 * @Date 2019/11/22 14:05
 * @Version
 **/
@Service("orderService")
@FeignClient(value = "service-order")
public interface OrderService {
    /**
     *
     * @param text
     * @return
     */
    @RequestMapping(value = "/test/result", method = RequestMethod.GET)
    String testCustomer(@RequestParam(value = "text") String text);
}
