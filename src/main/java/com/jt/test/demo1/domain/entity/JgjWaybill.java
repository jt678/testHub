package com.jt.test.demo1.domain.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

import javax.annotation.meta.Exclusive;

/**
 * 货运单
 * @TableName jgj_waybill
 */
@TableName(value ="jgj_waybill")
@Data
public class JgjWaybill implements Serializable {
    /**
     * id（主键）
     */
    @TableId
    private Long id;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 修改时间
     */
    @ExcelProperty(value = "检查时间")
    private Date gmtModified;

    /**
     * 创建用户id
     */
    private Long operater;

    /**
     * 车牌号码
     */
    @ExcelProperty(value = "车牌号码")
    private String truckCode;

    /**
     * 始发地名称
     */
    private String placeOriginName;

    /**
     * 始发地
     */
    private String placeOrigin;

    /**
     * 目的地名称
     */
    private String destinationName;

    /**
     * 目的地
     */
    private String destination;

    /**
     * 实载货物
     */
    private String actualCargo;

    /**
     * 货物重量
     */
    private String weight;

    /**
     * 驾驶员姓名
     */
    @Exclusive
    private String driverName;

    /**
     * 驾驶员身份证号码
     */
    private String driverCardCode;

    /**
     * 驾驶员电话
     */
    private String phone;

    /**
     * 行驶证/驾驶证图片
     */
    private String fileId;

    /**
     * 押运员姓名
     */
    private String escortName;

    /**
     * 押运员身份证号码
     */
    private String escortCardCode;

    /**
     * 状态(新增为1，已签到打卡2，已检查3)
     */
    private Integer status;

    /**
     * 系统用户id
     */
    private String userId;

    /**
     * 打卡状态
     */
    private Integer recordStatus;

    /**
     * 预约到站时间
     */
    private String arrivalTime;

    /**
     * 预约检查站id
     */
    private Long pointId;

    /**
     * 检查站名称
     */
    private String pointName;

    /**
     * 现场图片
     */
    private String localPicid;

    /**
     * 经纬度
     */
    private String nautica;

    /**
     * 实载人数
     */
    private String realNumber;

    /**
     * 预计到达开始时间
     */
    private String arrivalStartTime;

    /**
     * 预计达到结束时间
     */
    private String arrivalEndTime;

    /**
     * 车辆类型中文
     */
    @ExcelProperty(value = "车辆类型中文")
    private String vehicleName;

    /**
     * 车辆类型
     */
    private Integer type;

    /**
     * 货物名称
     */
    private String actualName;

    /**
     * 
     */
    @TableField(value="driver_name_2")
    private String driverName2;

    /**
     * 
     */
    @TableField(value="driver_card_code_2")
    private String driverCardCode2;

    /**
     * 
     */
    @TableField(value="phone_2")
    private String phone2;

    /**
     * 
     */
    @TableField(value="driver_name_3")
    private String driverName3;

    /**
     * 
     */
    @TableField(value="driver_card_code_3")
    private String driverCardCode3;

    /**
     * 
     */
    @TableField(value="phone_3")
    private String phone3;

    /**
     * 车牌类型 1.蓝底白字 2.黄底黑字 3.白底黑字 4.绿底黑字 5.黑底白字
     */
    private Integer carType;

    /**
     * 车辆所有人
     */
    private String carHolder;

    /**
     * 是否为接驳车(客运车辆)
     */
    private Integer jiebo;

    /**
     * 民警检查状态
     */
    private Integer checkStatus;

    /**
     * 核载人数
     */
    private Integer carrierCargo;

    /**
     * 免票人数
     */
    private Integer free;

    /**
     * 检查民警ID
     */
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
        JgjWaybill other = (JgjWaybill) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getGmtCreate() == null ? other.getGmtCreate() == null : this.getGmtCreate().equals(other.getGmtCreate()))
            && (this.getGmtModified() == null ? other.getGmtModified() == null : this.getGmtModified().equals(other.getGmtModified()))
            && (this.getOperater() == null ? other.getOperater() == null : this.getOperater().equals(other.getOperater()))
            && (this.getTruckCode() == null ? other.getTruckCode() == null : this.getTruckCode().equals(other.getTruckCode()))
            && (this.getPlaceOriginName() == null ? other.getPlaceOriginName() == null : this.getPlaceOriginName().equals(other.getPlaceOriginName()))
            && (this.getPlaceOrigin() == null ? other.getPlaceOrigin() == null : this.getPlaceOrigin().equals(other.getPlaceOrigin()))
            && (this.getDestinationName() == null ? other.getDestinationName() == null : this.getDestinationName().equals(other.getDestinationName()))
            && (this.getDestination() == null ? other.getDestination() == null : this.getDestination().equals(other.getDestination()))
            && (this.getActualCargo() == null ? other.getActualCargo() == null : this.getActualCargo().equals(other.getActualCargo()))
            && (this.getWeight() == null ? other.getWeight() == null : this.getWeight().equals(other.getWeight()))
            && (this.getDriverName() == null ? other.getDriverName() == null : this.getDriverName().equals(other.getDriverName()))
            && (this.getDriverCardCode() == null ? other.getDriverCardCode() == null : this.getDriverCardCode().equals(other.getDriverCardCode()))
            && (this.getPhone() == null ? other.getPhone() == null : this.getPhone().equals(other.getPhone()))
            && (this.getFileId() == null ? other.getFileId() == null : this.getFileId().equals(other.getFileId()))
            && (this.getEscortName() == null ? other.getEscortName() == null : this.getEscortName().equals(other.getEscortName()))
            && (this.getEscortCardCode() == null ? other.getEscortCardCode() == null : this.getEscortCardCode().equals(other.getEscortCardCode()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getRecordStatus() == null ? other.getRecordStatus() == null : this.getRecordStatus().equals(other.getRecordStatus()))
            && (this.getArrivalTime() == null ? other.getArrivalTime() == null : this.getArrivalTime().equals(other.getArrivalTime()))
            && (this.getPointId() == null ? other.getPointId() == null : this.getPointId().equals(other.getPointId()))
            && (this.getPointName() == null ? other.getPointName() == null : this.getPointName().equals(other.getPointName()))
            && (this.getLocalPicid() == null ? other.getLocalPicid() == null : this.getLocalPicid().equals(other.getLocalPicid()))
            && (this.getNautica() == null ? other.getNautica() == null : this.getNautica().equals(other.getNautica()))
            && (this.getRealNumber() == null ? other.getRealNumber() == null : this.getRealNumber().equals(other.getRealNumber()))
            && (this.getArrivalStartTime() == null ? other.getArrivalStartTime() == null : this.getArrivalStartTime().equals(other.getArrivalStartTime()))
            && (this.getArrivalEndTime() == null ? other.getArrivalEndTime() == null : this.getArrivalEndTime().equals(other.getArrivalEndTime()))
            && (this.getVehicleName() == null ? other.getVehicleName() == null : this.getVehicleName().equals(other.getVehicleName()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getActualName() == null ? other.getActualName() == null : this.getActualName().equals(other.getActualName()))
            && (this.getDriverName2() == null ? other.getDriverName2() == null : this.getDriverName2().equals(other.getDriverName2()))
            && (this.getDriverCardCode2() == null ? other.getDriverCardCode2() == null : this.getDriverCardCode2().equals(other.getDriverCardCode2()))
            && (this.getPhone2() == null ? other.getPhone2() == null : this.getPhone2().equals(other.getPhone2()))
            && (this.getDriverName3() == null ? other.getDriverName3() == null : this.getDriverName3().equals(other.getDriverName3()))
            && (this.getDriverCardCode3() == null ? other.getDriverCardCode3() == null : this.getDriverCardCode3().equals(other.getDriverCardCode3()))
            && (this.getPhone3() == null ? other.getPhone3() == null : this.getPhone3().equals(other.getPhone3()))
            && (this.getCarType() == null ? other.getCarType() == null : this.getCarType().equals(other.getCarType()))
            && (this.getCarHolder() == null ? other.getCarHolder() == null : this.getCarHolder().equals(other.getCarHolder()))
            && (this.getJiebo() == null ? other.getJiebo() == null : this.getJiebo().equals(other.getJiebo()))
            && (this.getCheckStatus() == null ? other.getCheckStatus() == null : this.getCheckStatus().equals(other.getCheckStatus()))
            && (this.getCarrierCargo() == null ? other.getCarrierCargo() == null : this.getCarrierCargo().equals(other.getCarrierCargo()))
            && (this.getFree() == null ? other.getFree() == null : this.getFree().equals(other.getFree()))
            && (this.getCheckId() == null ? other.getCheckId() == null : this.getCheckId().equals(other.getCheckId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getGmtCreate() == null) ? 0 : getGmtCreate().hashCode());
        result = prime * result + ((getGmtModified() == null) ? 0 : getGmtModified().hashCode());
        result = prime * result + ((getOperater() == null) ? 0 : getOperater().hashCode());
        result = prime * result + ((getTruckCode() == null) ? 0 : getTruckCode().hashCode());
        result = prime * result + ((getPlaceOriginName() == null) ? 0 : getPlaceOriginName().hashCode());
        result = prime * result + ((getPlaceOrigin() == null) ? 0 : getPlaceOrigin().hashCode());
        result = prime * result + ((getDestinationName() == null) ? 0 : getDestinationName().hashCode());
        result = prime * result + ((getDestination() == null) ? 0 : getDestination().hashCode());
        result = prime * result + ((getActualCargo() == null) ? 0 : getActualCargo().hashCode());
        result = prime * result + ((getWeight() == null) ? 0 : getWeight().hashCode());
        result = prime * result + ((getDriverName() == null) ? 0 : getDriverName().hashCode());
        result = prime * result + ((getDriverCardCode() == null) ? 0 : getDriverCardCode().hashCode());
        result = prime * result + ((getPhone() == null) ? 0 : getPhone().hashCode());
        result = prime * result + ((getFileId() == null) ? 0 : getFileId().hashCode());
        result = prime * result + ((getEscortName() == null) ? 0 : getEscortName().hashCode());
        result = prime * result + ((getEscortCardCode() == null) ? 0 : getEscortCardCode().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getRecordStatus() == null) ? 0 : getRecordStatus().hashCode());
        result = prime * result + ((getArrivalTime() == null) ? 0 : getArrivalTime().hashCode());
        result = prime * result + ((getPointId() == null) ? 0 : getPointId().hashCode());
        result = prime * result + ((getPointName() == null) ? 0 : getPointName().hashCode());
        result = prime * result + ((getLocalPicid() == null) ? 0 : getLocalPicid().hashCode());
        result = prime * result + ((getNautica() == null) ? 0 : getNautica().hashCode());
        result = prime * result + ((getRealNumber() == null) ? 0 : getRealNumber().hashCode());
        result = prime * result + ((getArrivalStartTime() == null) ? 0 : getArrivalStartTime().hashCode());
        result = prime * result + ((getArrivalEndTime() == null) ? 0 : getArrivalEndTime().hashCode());
        result = prime * result + ((getVehicleName() == null) ? 0 : getVehicleName().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getActualName() == null) ? 0 : getActualName().hashCode());
        result = prime * result + ((getDriverName2() == null) ? 0 : getDriverName2().hashCode());
        result = prime * result + ((getDriverCardCode2() == null) ? 0 : getDriverCardCode2().hashCode());
        result = prime * result + ((getPhone2() == null) ? 0 : getPhone2().hashCode());
        result = prime * result + ((getDriverName3() == null) ? 0 : getDriverName3().hashCode());
        result = prime * result + ((getDriverCardCode3() == null) ? 0 : getDriverCardCode3().hashCode());
        result = prime * result + ((getPhone3() == null) ? 0 : getPhone3().hashCode());
        result = prime * result + ((getCarType() == null) ? 0 : getCarType().hashCode());
        result = prime * result + ((getCarHolder() == null) ? 0 : getCarHolder().hashCode());
        result = prime * result + ((getJiebo() == null) ? 0 : getJiebo().hashCode());
        result = prime * result + ((getCheckStatus() == null) ? 0 : getCheckStatus().hashCode());
        result = prime * result + ((getCarrierCargo() == null) ? 0 : getCarrierCargo().hashCode());
        result = prime * result + ((getFree() == null) ? 0 : getFree().hashCode());
        result = prime * result + ((getCheckId() == null) ? 0 : getCheckId().hashCode());
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
        sb.append(", operater=").append(operater);
        sb.append(", truckCode=").append(truckCode);
        sb.append(", placeOriginName=").append(placeOriginName);
        sb.append(", placeOrigin=").append(placeOrigin);
        sb.append(", destinationName=").append(destinationName);
        sb.append(", destination=").append(destination);
        sb.append(", actualCargo=").append(actualCargo);
        sb.append(", weight=").append(weight);
        sb.append(", driverName=").append(driverName);
        sb.append(", driverCardCode=").append(driverCardCode);
        sb.append(", phone=").append(phone);
        sb.append(", fileId=").append(fileId);
        sb.append(", escortName=").append(escortName);
        sb.append(", escortCardCode=").append(escortCardCode);
        sb.append(", status=").append(status);
        sb.append(", userId=").append(userId);
        sb.append(", recordStatus=").append(recordStatus);
        sb.append(", arrivalTime=").append(arrivalTime);
        sb.append(", pointId=").append(pointId);
        sb.append(", pointName=").append(pointName);
        sb.append(", localPicid=").append(localPicid);
        sb.append(", nautica=").append(nautica);
        sb.append(", realNumber=").append(realNumber);
        sb.append(", arrivalStartTime=").append(arrivalStartTime);
        sb.append(", arrivalEndTime=").append(arrivalEndTime);
        sb.append(", vehicleName=").append(vehicleName);
        sb.append(", type=").append(type);
        sb.append(", actualName=").append(actualName);
        sb.append(", driverName2=").append(driverName2);
        sb.append(", driverCardCode2=").append(driverCardCode2);
        sb.append(", phone2=").append(phone2);
        sb.append(", driverName3=").append(driverName3);
        sb.append(", driverCardCode3=").append(driverCardCode3);
        sb.append(", phone3=").append(phone3);
        sb.append(", carType=").append(carType);
        sb.append(", carHolder=").append(carHolder);
        sb.append(", jiebo=").append(jiebo);
        sb.append(", checkStatus=").append(checkStatus);
        sb.append(", carrierCargo=").append(carrierCargo);
        sb.append(", free=").append(free);
        sb.append(", checkId=").append(checkId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}