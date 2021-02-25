package com.busi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.busi.dao.SysUserMapper;
import com.busi.model.SysUser;
import com.busi.service.ISysUserService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author caojunjie
 * @since 2020-09-15
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    
    /**
     * @Description：
     * <p>创建人：junjie.cao,  2020/9/16  16:25</p>
     * <p>修改人：junjie.cao,  2020/9/16  16:25</p>
     *
     * @param username
     * @return java.util.List<com.umf.busi.model.SysUser>
     */
    @Override
    public List<SysUser> getList(String username) {

        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
//        queryWrapper.lambda().eq(SysUser::getUsername,username);
        return super.list(queryWrapper);
    }

    @Override
    public List<SysUser> listAll() {
        return super.list(null);
    }
}
