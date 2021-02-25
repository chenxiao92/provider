package com.busi.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * <p>
 * 
 * </p>
 *
 * @author caojunjie
 * @since 2020-09-15
 */
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

        /**
     * 主键
     */
         @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;

        /**
     * 用户名
     */
         private String username;

        /**
     * 密码
     */
         private String password;

        /**
     * 邮箱
     */
         private String email;

        /**
     * 手机号
     */
         private String mobile;

        /**
     * 状态 0失效 1生效
     */
         private Integer status;

        /**
     * 创建时间
     */
         private LocalDate createTime;

        /**
     * 部门
     */
         private Long dept;

        /**
     * 性别 0男 1女
     */
         private Integer sex;

        /**
     * 地址
     */
         private String address;

        /**
     * 员工编号
     */
         private String employeeid;

        /**
     * 真实姓名
     */
         private String realname;

        /**
     * 生效日期
     */
         private LocalDate efftime;

        /**
     * 失效日期
     */
         private LocalDate losetime;


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocalDate getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDate createTime) {
        this.createTime = createTime;
    }

    public Long getDept() {
        return dept;
    }

    public void setDept(Long dept) {
        this.dept = dept;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmployeeid() {
        return employeeid;
    }

    public void setEmployeeid(String employeeid) {
        this.employeeid = employeeid;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public LocalDate getEfftime() {
        return efftime;
    }

    public void setEfftime(LocalDate efftime) {
        this.efftime = efftime;
    }

    public LocalDate getLosetime() {
        return losetime;
    }

    public void setLosetime(LocalDate losetime) {
        this.losetime = losetime;
    }

    @Override
    public String toString() {
        return "SysUser{" +
        "userId=" + userId +
        ", username=" + username +
        ", password=" + password +
        ", email=" + email +
        ", mobile=" + mobile +
        ", status=" + status +
        ", createTime=" + createTime +
        ", dept=" + dept +
        ", sex=" + sex +
        ", address=" + address +
        ", employeeid=" + employeeid +
        ", realname=" + realname +
        ", efftime=" + efftime +
        ", losetime=" + losetime +
        "}";
    }
}
