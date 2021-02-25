package com.busi.data;

import com.busi.model.SysUser;

/**
 * 描述说明:
 *
 * @author daqing.zheng
 * @version 1.0
 * @className ResEntity
 * @date 2019/11/21 14:50
 */
public class ResEntity<T> extends Response{

    public ResEntity(ReqEntity<SysUser> request){
        this.setRpid("1");
    }

    public ResEntity(Request request) {
        super(request);
    }

    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return super.toString();
    }


}
