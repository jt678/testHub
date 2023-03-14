package com.jt.test.demo1.domain.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName jgj_appuser
 */
@TableName(value ="jgj_appuser")
@Data
public class JgjAppuser implements Serializable {
    /**
     * 用户ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String userid;

    /**
     * 用户名
     */
    @ExcelProperty(value = "用户名")
    private String username;

    /**
     * 密码
     */
    @ExcelProperty(value = "密码")
    private String password;

    /**
     * 用户状态
     */
    private Integer status;

    /**
     * 真实姓名
     */
    private String realname;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 手机号
     */
    @ExcelProperty(value = "手机号")
    private String mobile;

    /**
     * 身份证号码
     */
    private String cardCode;

    /**
     * 用户类型
     */
    @ExcelProperty(value = "用户类型")
    private Integer userType;

    /**
     * 原始密码
     */
    private String oldpassword;

    /**
     * 手势密码
     */
    private String gestureinfo;

    /**
     * 民警用户所属检查站
     */
    @ExcelProperty(value = "民警用户所属检查站")
    private String checkId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        JgjAppuser other = (JgjAppuser) that;
        return (this.getUserid() == null ? other.getUserid() == null : this.getUserid().equals(other.getUserid()))
            && (this.getUsername() == null ? other.getUsername() == null : this.getUsername().equals(other.getUsername()))
            && (this.getPassword() == null ? other.getPassword() == null : this.getPassword().equals(other.getPassword()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getRealname() == null ? other.getRealname() == null : this.getRealname().equals(other.getRealname()))
            && (this.getEmail() == null ? other.getEmail() == null : this.getEmail().equals(other.getEmail()))
            && (this.getMobile() == null ? other.getMobile() == null : this.getMobile().equals(other.getMobile()))
            && (this.getCardCode() == null ? other.getCardCode() == null : this.getCardCode().equals(other.getCardCode()))
            && (this.getUserType() == null ? other.getUserType() == null : this.getUserType().equals(other.getUserType()))
            && (this.getOldpassword() == null ? other.getOldpassword() == null : this.getOldpassword().equals(other.getOldpassword()))
            && (this.getGestureinfo() == null ? other.getGestureinfo() == null : this.getGestureinfo().equals(other.getGestureinfo()))
            && (this.getCheckId() == null ? other.getCheckId() == null : this.getCheckId().equals(other.getCheckId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getUserid() == null) ? 0 : getUserid().hashCode());
        result = prime * result + ((getUsername() == null) ? 0 : getUsername().hashCode());
        result = prime * result + ((getPassword() == null) ? 0 : getPassword().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getRealname() == null) ? 0 : getRealname().hashCode());
        result = prime * result + ((getEmail() == null) ? 0 : getEmail().hashCode());
        result = prime * result + ((getMobile() == null) ? 0 : getMobile().hashCode());
        result = prime * result + ((getCardCode() == null) ? 0 : getCardCode().hashCode());
        result = prime * result + ((getUserType() == null) ? 0 : getUserType().hashCode());
        result = prime * result + ((getOldpassword() == null) ? 0 : getOldpassword().hashCode());
        result = prime * result + ((getGestureinfo() == null) ? 0 : getGestureinfo().hashCode());
        result = prime * result + ((getCheckId() == null) ? 0 : getCheckId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", userid=").append(userid);
        sb.append(", username=").append(username);
        sb.append(", password=").append(password);
        sb.append(", status=").append(status);
        sb.append(", realname=").append(realname);
        sb.append(", email=").append(email);
        sb.append(", mobile=").append(mobile);
        sb.append(", cardCode=").append(cardCode);
        sb.append(", userType=").append(userType);
        sb.append(", oldpassword=").append(oldpassword);
        sb.append(", gestureinfo=").append(gestureinfo);
        sb.append(", checkId=").append(checkId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}