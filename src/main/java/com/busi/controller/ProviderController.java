package com.busi.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by forezp on 2019/5/11.
 */

@RestController
public class ProviderController {

    Logger logger= LoggerFactory.getLogger(ProviderController.class);



    @GetMapping("/hi")
    @SentinelResource("hi")
    public String hi(@RequestParam(value = "name",defaultValue = "forezp",required = false)String name){

        return "hi "+name;
    }

}
