package com.example.magnumsindhu;

public class DataList {
    String loginName;
    String loginId;
    String image;
    String url;

    public DataList(){}

    public DataList(String loginName, String loginId, String image) {
        this.loginName = loginName;
        this.loginId = loginId;
        this.image = image;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}