package com.busi.controller;



import com.busi.data.ReqEntity;
import com.busi.data.ResEntity;
import com.busi.model.SysUser;
import com.busi.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author caojunjie
 * @since 2020-09-15
 */
@RestController
@RequestMapping("/sysUser")
public class SysUserController {


    @Autowired
    ISysUserService sysUserService;

    @RequestMapping(value="/queryUser")
    public ResEntity queryUser(@RequestBody ReqEntity<SysUser> request){
        ResEntity response = new ResEntity(request);
        List<SysUser> list = sysUserService.getList("admin");

        if(!list.isEmpty()){
            response.success();
            response.setData(list);
        }else{
            response.error();
        }
        return response;
    }

}

