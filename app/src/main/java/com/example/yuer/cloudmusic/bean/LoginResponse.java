package com.example.yuer.cloudmusic.bean;

/**
 * Created by Yuer on 2017/6/5.
 */

public class LoginResponse {

    /**
     * sessionToken : yh7xoyj72wulrumysfe9iep8i
     * updatedAt : 2017-06-05T11:04:47.272Z
     * objectId : 59353acf570c35005b5c2147
     * username : 111
     * createdAt : 2017-06-05T11:04:47.272Z
     * emailVerified : false
     * mobilePhoneVerified : false
     */

    private String sessionToken;
    private String updatedAt;
    private String objectId;
    private String username;
    private String createdAt;
    private boolean emailVerified;
    private boolean mobilePhoneVerified;

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public boolean isMobilePhoneVerified() {
        return mobilePhoneVerified;
    }

    public void setMobilePhoneVerified(boolean mobilePhoneVerified) {
        this.mobilePhoneVerified = mobilePhoneVerified;
    }
}
