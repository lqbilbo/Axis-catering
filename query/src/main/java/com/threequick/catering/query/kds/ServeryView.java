package com.threequick.catering.query.kds;

import org.springframework.data.annotation.Id;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ServeryView {

    @Id
    @javax.persistence.Id
    private String identifier;
    private long poiId;
    private String serveryName;
    private String remark;
    private boolean online;
    private boolean offline;
    private String stallIdentifier;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<CookingInfoView> cookingList = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<CookingInfoView> skuList = new ArrayList<>();

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

    public String getServeryName() {
        return serveryName;
    }

    public void setServeryName(String serveryName) {
        this.serveryName = serveryName;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public boolean isOffline() {
        return offline;
    }

    public void setOffline(boolean offline) {
        this.offline = offline;
    }

    public String getStallIdentifier() {
        return stallIdentifier;
    }

    public void setStallIdentifier(String stallIdentifier) {
        this.stallIdentifier = stallIdentifier;
    }

    public List<CookingInfoView> getCookingList() {
        return cookingList;
    }

    public void setCookingList(List<CookingInfoView> cookingList) {
        this.cookingList = cookingList;
    }

    public List<CookingInfoView> getSkuList() {
        return skuList;
    }

    public void setSkuList(List<CookingInfoView> skuList) {
        this.skuList = skuList;
    }
}
