package com.yutongdxTop.LaborDispatching.domain.vo;

public class FreetimeVo {
    private Integer staffId;

    private String name;

    private String sex;

    private String identity;

    private String type;

    private String freeTimeBegin;

    private String freeTimeEnd;

    public Integer getStaffId() {
        return staffId;
    }

    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity == null ? null : identity.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getFreeTimeBegin() {
        return freeTimeBegin;
    }

    public void setFreeTimeBegin(String freeTimeBegin) {
        this.freeTimeBegin = freeTimeBegin == null ? null : freeTimeBegin.trim();
    }

    public String getFreeTimeEnd() {
        return freeTimeEnd;
    }

    public void setFreeTimeEnd(String freeTimeEnd) {
        this.freeTimeEnd = freeTimeEnd == null ? null : freeTimeEnd.trim();
    }
}