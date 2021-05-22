package com.yutongdxTop.LaborDispatching.domain.pojo;

public class Contact {
    private Integer id;

    private Integer staffId;

    private String contactDetails;

    private String contactValue;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStaffId() {
        return staffId;
    }

    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }

    public String getContactDetails() {
        return contactDetails;
    }

    public void setContactDetails(String contactDetails) {
        this.contactDetails = contactDetails == null ? null : contactDetails.trim();
    }

    public String getContactValue() {
        return contactValue;
    }

    public void setContactValue(String contactValue) {
        this.contactValue = contactValue == null ? null : contactValue.trim();
    }
}