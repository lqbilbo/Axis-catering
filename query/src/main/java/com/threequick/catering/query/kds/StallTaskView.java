package com.threequick.catering.query.kds;

import org.springframework.data.annotation.Id;

import javax.persistence.Entity;

@Entity
public class StallTaskView {

    @Id
    @javax.persistence.Id
    private String identifier;
    private long poiId;
    private long orderId;
    private String stallTaskName;
    private String remark;

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getStallTaskName() {
        return stallTaskName;
    }

    public void setStallTaskName(String stallTaskName) {
        this.stallTaskName = stallTaskName;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public long getPoiId() {
        return poiId;
    }

    public void setPoiId(long poiId) {
        this.poiId = poiId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
