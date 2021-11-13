package com.kingshuk.rest.webservices.restfulwebservices.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomAttribute {

    @JsonProperty("custom:adminflag")
    private String customAdminFlag;

    public CustomAttribute() {
    }

    public CustomAttribute(String customAdminFlag) {
        this.customAdminFlag = customAdminFlag;
    }

    public String getCustomAdminFlag() {
        return customAdminFlag;
    }

    public void setCustomAdminFlag(String customAdminFlag) {
        this.customAdminFlag = customAdminFlag;
    }
}
