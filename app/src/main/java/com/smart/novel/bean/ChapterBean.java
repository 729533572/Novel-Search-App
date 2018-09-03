package com.smart.novel.bean;

/**
 * Created by JoJo on 2018/9/2.
 * wechat：18510829974
 * description：小说章节
 */
public class ChapterBean {


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
    private int book_id;
    private String chapter_number;
    private String chapter_name;
    private String chapter_url;
    private String origin_website;
    private int word_count;
    private int read_count;
    private String create_time;
    private String update_time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public String getChapter_number() {
        return "第" + chapter_number + "章";
    }

    public void setChapter_number(String chapter_number) {
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
