package com.yutongdxTop.LaborDispatching.domain.pojo;

public class Contact {
    private String id;

    private String staffId;

    private String contactDetails;

    private String contactValue;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId == null ? null : staffId.trim();
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