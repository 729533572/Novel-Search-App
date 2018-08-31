package com.smart.novel.bean;

/**
 * Created by JoJo on 2018/8/30.
 * wechat:18510829974
 * description: 热门搜索词
 */

public class HotSearchBean {

    /**
     * id : 29
     * search_keyword : 重生
     * count : 3
     * create_time : 2018-08-27T16:13:01.634Z
     * update_time : 2018-08-28T15:09:02.160Z
     */

    private int id;
    private String search_keyword;
    private int count;
    private String create_time;
    private String update_time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSearch_keyword() {
        return search_keyword;
    }

    public void setSearch_keyword(String search_keyword) {
        this.search_keyword = search_keyword;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }
}
