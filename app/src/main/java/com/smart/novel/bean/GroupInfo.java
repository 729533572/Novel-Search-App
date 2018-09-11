package com.smart.novel.bean;

/**
 * Created by JoJo on 2018/9/10.
 * wechat:18510829974
 * description: QQ阅读群
 */

public class GroupInfo {

    /**
     * id : 872995601
     * name : 小说搜索神器用户群
     */

    private String key;
    private String name;

    public GroupInfo(String key) {
        this.key = key;
    }

    public String getKey() {
        return key == null ? "" : key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
