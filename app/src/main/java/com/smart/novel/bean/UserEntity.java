package com.smart.novel.bean;

/**
 * Created by JoJo on 2018/6/21.
 * wechat：18510829974
 * description：用户实体
 */
public class UserEntity {
    private String sign;
    private int id;
    private String name;
    private String type;// 返回院所类型:分院,总院,平台
    private String branchName;//院所名称

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getType() {
        return type == null ? "" : type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSign() {
        return sign == null ? "" : sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
