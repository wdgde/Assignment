package com.mytutorial.model;

import java.io.Serializable;

public class Login implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userId;
    private String userPwd;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    @Override
    public String toString() {
        return "Login [userId=" + userId + ", userPwd=" + userPwd + "]";
    }

}