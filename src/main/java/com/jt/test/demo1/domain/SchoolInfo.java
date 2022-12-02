package com.jt.test.demo1.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 学校信息
 *
 * @TableName jbx_school_info
 */

@Data
public class SchoolInfo implements Serializable {
    /**
     * 学校id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date gmtCreate;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date gmtModified;

    /**
     * 操作用户
     */
    private String operator;

    /**
     * 学校名称
     */
    private String schoolName;

    /**
     * 地址
     */
    private String address;

    /**
     * 地图坐标
     */
    private String coordinate;

    /**
     * 学校简介
     */
    private String introduction;

    /**
     * 是否删除(0:未删除，1：已删除)
     */
    private Integer deleted;

    /**
     * 学校电话
     */
    private String schoolPhone;

    /**
     * 组织机构代码
     */
    private String orgCode;

    /**
     * 公安部门id
     */
    private Long policeId;

    /**
     * 教育部门id
     */
    private Long educationId;

    /**
     * 市政部门id
     */
    private Long districtId;

    /**
     * 联系人
     */
    private String dutyPerson;

    /**
     * 联系人电话
     */
    private String dutyPhone;

    /**
     * 地址（区）
     */
    private String addrDistrict;

    /**
     * 地址（街道）
     */
    private String addrStreet;

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
        SchoolInfo other = (SchoolInfo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getGmtCreate() == null ? other.getGmtCreate() == null : this.getGmtCreate().equals(other.getGmtCreate()))
                && (this.getGmtModified() == null ? other.getGmtModified() == null : this.getGmtModified().equals(other.getGmtModified()))
                && (this.getOperator() == null ? other.getOperator() == null : this.getOperator().equals(other.getOperator()))
                && (this.getSchoolName() == null ? other.getSchoolName() == null : this.getSchoolName().equals(other.getSchoolName()))
                && (this.getAddress() == null ? other.getAddress() == null : this.getAddress().equals(other.getAddress()))
                && (this.getCoordinate() == null ? other.getCoordinate() == null : this.getCoordinate().equals(other.getCoordinate()))
                && (this.getIntroduction() == null ? other.getIntroduction() == null : this.getIntroduction().equals(other.getIntroduction()))
                && (this.getDeleted() == null ? other.getDeleted() == null : this.getDeleted().equals(other.getDeleted()))
                && (this.getSchoolPhone() == null ? other.getSchoolPhone() == null : this.getSchoolPhone().equals(other.getSchoolPhone()))
                && (this.getOrgCode() == null ? other.getOrgCode() == null : this.getOrgCode().equals(other.getOrgCode()))
                && (this.getPoliceId() == null ? other.getPoliceId() == null : this.getPoliceId().equals(other.getPoliceId()))
                && (this.getEducationId() == null ? other.getEducationId() == null : this.getEducationId().equals(other.getEducationId()))
                && (this.getDistrictId() == null ? other.getDistrictId() == null : this.getDistrictId().equals(other.getDistrictId()))
                && (this.getDutyPerson() == null ? other.getDutyPerson() == null : this.getDutyPerson().equals(other.getDutyPerson()))
                && (this.getDutyPhone() == null ? other.getDutyPhone() == null : this.getDutyPhone().equals(other.getDutyPhone()))
                && (this.getAddrDistrict() == null ? other.getAddrDistrict() == null : this.getAddrDistrict().equals(other.getAddrDistrict()))
                && (this.getAddrStreet() == null ? other.getAddrStreet() == null : this.getAddrStreet().equals(other.getAddrStreet()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getGmtCreate() == null) ? 0 : getGmtCreate().hashCode());
        result = prime * result + ((getGmtModified() == null) ? 0 : getGmtModified().hashCode());
        result = prime * result + ((getOperator() == null) ? 0 : getOperator().hashCode());
        result = prime * result + ((getSchoolName() == null) ? 0 : getSchoolName().hashCode());
        result = prime * result + ((getAddress() == null) ? 0 : getAddress().hashCode());
        result = prime * result + ((getCoordinate() == null) ? 0 : getCoordinate().hashCode());
        result = prime * result + ((getIntroduction() == null) ? 0 : getIntroduction().hashCode());
        result = prime * result + ((getDeleted() == null) ? 0 : getDeleted().hashCode());
        result = prime * result + ((getSchoolPhone() == null) ? 0 : getSchoolPhone().hashCode());
        result = prime * result + ((getOrgCode() == null) ? 0 : getOrgCode().hashCode());
        result = prime * result + ((getPoliceId() == null) ? 0 : getPoliceId().hashCode());
        result = prime * result + ((getEducationId() == null) ? 0 : getEducationId().hashCode());
        result = prime * result + ((getDistrictId() == null) ? 0 : getDistrictId().hashCode());
        result = prime * result + ((getDutyPerson() == null) ? 0 : getDutyPerson().hashCode());
        result = prime * result + ((getDutyPhone() == null) ? 0 : getDutyPhone().hashCode());
        result = prime * result + ((getAddrDistrict() == null) ? 0 : getAddrDistrict().hashCode());
        result = prime * result + ((getAddrStreet() == null) ? 0 : getAddrStreet().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", gmtCreate=").append(gmtCreate);
        sb.append(", gmtModified=").append(gmtModified);
        sb.append(", operator=").append(operator);
        sb.append(", schoolName=").append(schoolName);
        sb.append(", address=").append(address);
        sb.append(", coordinate=").append(coordinate);
        sb.append(", introduction=").append(introduction);
        sb.append(", deleted=").append(deleted);
        sb.append(", schoolPhone=").append(schoolPhone);
        sb.append(", orgCode=").append(orgCode);
        sb.append(", policeId=").append(policeId);
        sb.append(", educationId=").append(educationId);
        sb.append(", districtId=").append(districtId);
        sb.append(", dutyPerson=").append(dutyPerson);
        sb.append(", dutyPhone=").append(dutyPhone);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}