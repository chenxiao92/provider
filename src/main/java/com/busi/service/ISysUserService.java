package com.busi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.busi.model.SysUser;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author caojunjie
 * @since 2020-09-15
 */
public interface ISysUserService extends IService<SysUser> {

    List<SysUser> getList(String username);
    List<SysUser> listAll();
}
