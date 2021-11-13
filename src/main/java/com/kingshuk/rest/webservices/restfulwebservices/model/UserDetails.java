package com.kingshuk.rest.webservices.restfulwebservices.model;

public class UserDetails {

    private String email;
    private String clientId;
    private String userPoolId;
    private CustomAttribute customAttributes;

    public UserDetails() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getUserPoolId() {
        return userPoolId;
    }

    public void setUserPoolId(String userPoolId) {
        this.userPoolId = userPoolId;
    }

    public CustomAttribute getCustomAttributes() {
        return customAttributes;
    }

    public void setCustomAttributes(CustomAttribute customAttributes) {
        this.customAttributes = customAttributes;
    }
}
