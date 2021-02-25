package com.busi.data;


/**
 * 描述说明:
 *
 * @author daqing.zheng
 * @version 1.0
 * @className ReqEntity
 * @date 2019/11/22 14:25
 */
public class ReqEntity<T> extends Request {
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
