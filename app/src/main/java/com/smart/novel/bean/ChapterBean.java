package com.smart.novel.bean;

import java.io.Serializable;

/**
 * Created by JoJo on 2018/9/2.
 * wechat：18510829974
 * description：小说章节
 */
public class ChapterBean implements Serializable{


    /**
     * id : 9709967
     * book_id : 33280
     * chapter_number : 252
     * chapter_name : 混迹神雕之龙女控
     * chapter_url : https://www.zhuishu.tw/id23822/1672255.html
     * origin_website : https://www.zhuishu.tw
     * word_count : 0
     * read_count : 0
     * create_time : 2018-08-26T16:29:16.852Z
     * update_time : 2018-08-26T16:29:16.852Z
     */

    private String id;
    private String book_id;
    private String name_cn;//小说名称
    private int chapter_number;
    private String chapter_name;
    private String chapter_url;
    private String origin_website;
    private int word_count;
    private int read_count;
    private String create_time;
    private String update_time;
    private boolean latest; //是否是最新章节
    private int totol_size;//总章节数
    public ChapterBean() {
    }

    public ChapterBean(String book_id, String name_cn,int chapter_number, String chapter_name, String chapter_url, String origin_website) {
        this.book_id = book_id;
        this.name_cn = name_cn;
        this.chapter_number = chapter_number;
        this.chapter_name = chapter_name;
        this.chapter_url = chapter_url;
        this.origin_website = origin_website;
    }

    public int getTotol_size() {
        return totol_size;
    }

    public void setTotol_size(int totol_size) {
        this.totol_size = totol_size;
    }

    public String getName_cn() {
        return name_cn == null ? "" : name_cn;
    }

    public void setName_cn(String name_cn) {
        this.name_cn = name_cn;
    }

    public boolean isLatest() {
        return latest;
    }

    public void setLatest(boolean latest) {
        this.latest = latest;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBook_id() {
        return book_id;
    }

    public void setBook_id(String book_id) {
        this.book_id = book_id;
    }

    public int getChapter_number() {
        return  chapter_number;
    }

    public void setChapter_number(int chapter_number) {
        this.chapter_number = chapter_number;
    }

    public String getChapter_name() {
        return chapter_name;
    }

    public void setChapter_name(String chapter_name) {
        this.chapter_name = chapter_name;
    }

    public String getChapter_url() {
        return chapter_url;
    }

    public void setChapter_url(String chapter_url) {
        this.chapter_url = chapter_url;
    }

    public String getOrigin_website() {
        return origin_website;
    }

    public void setOrigin_website(String origin_website) {
        this.origin_website = origin_website;
    }

    public int getWord_count() {
        return word_count;
    }

    public void setWord_count(int word_count) {
        this.word_count = word_count;
    }

    public int getRead_count() {
        return read_count;
    }

    public void setRead_count(int read_count) {
        this.read_count = read_count;
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
