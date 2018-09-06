package com.smart.novel.bean;

/**
 * Created by JoJo on 2018/9/6.
 * wechat:18510829974
 * description: 网页源码站
 */

public class WebsiteBean {

    /**
     * id : 117
     * origin_website : https://www.zhuishu.tw
     * website_name : 追书网
     */

    private int id;
    private String origin_website;
    private String website_name;
    private boolean isCheck;

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrigin_website() {
        return origin_website;
    }

    public void setOrigin_website(String origin_website) {
        this.origin_website = origin_website;
    }

    public String getWebsite_name() {
        return website_name;
    }

    public void setWebsite_name(String website_name) {
        this.website_name = website_name;
    }
}
