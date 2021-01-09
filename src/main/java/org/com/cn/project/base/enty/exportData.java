package org.com.cn.project.base.enty;

import java.util.Date;

public class exportData {

    private Integer id;
    private String stringCode;          // 串码
    private String stringCodeStatus;    //串码状态
    private String distributionCounty;  //分配县市
    private Date intputDate;             //入库时间
    private Date outputDate;            //出库时间
    private String remark;              //备用字段
    private String remark1;             //备用字段
    private String remark2;             //备用字段
    private Date remarkDate;            //备用字段
    private Date remarkDate1;           //备用字段

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStringCode() {
        return stringCode;
    }

    public void setStringCode(String stringCode) {
        this.stringCode = stringCode;
    }

    public String getStringCodeStatus() {
        return stringCodeStatus;
    }

    public void setStringCodeStatus(String stringCodeStatus) {
        this.stringCodeStatus = stringCodeStatus;
    }

    public String getDistributionCounty() {
        return distributionCounty;
    }

    public void setDistributionCounty(String distributionCounty) {
        this.distributionCounty = distributionCounty;
    }

    public Date getIntputDate() {
        return intputDate;
    }

    public void setIntputDate(Date intputDate) {
        this.intputDate = intputDate;
    }

    public Date getOutputDate() {
        return outputDate;
    }

    public void setOutputDate(Date outputDate) {
        this.outputDate = outputDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark1() {
        return remark1;
    }

    public void setRemark1(String remark1) {
        this.remark1 = remark1;
    }

    public String getRemark2() {
        return remark2;
    }

    public void setRemark2(String remark2) {
        this.remark2 = remark2;
    }

    public Date getRemarkDate() {
        return remarkDate;
    }

    public void setRemarkDate(Date remarkDate) {
        this.remarkDate = remarkDate;
    }

    public Date getRemarkDate1() {
        return remarkDate1;
    }

    public void setRemarkDate1(Date remarkDate1) {
        this.remarkDate1 = remarkDate1;
    }
}
