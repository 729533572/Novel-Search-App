package com.smart.novel.bean;

import java.io.Serializable;

/**
 * Created by JoJo on 2018/8/31.
 * wechat:18510829974
 * description: 小说实体-排行榜、搜索结果，阅读历史、我的收藏
 */

public class NovelBean implements Serializable {

    /**
     * id : 256
     * name_cn : 不败天骄
     * author : 火树嘎嘎
     * content_update_time : 2018-08-18T16:23:59.000Z
     * catagory : 0
     * covor_url : https://www.zhuishu.tw/cover/50/50191.jpg
     * origin_website : https://www.zhuishu.tw
     * is_finished : 0
     * word_count : 0
     * read_count : 0
     * comment : 沧海桑田，世界大变！　　这个时代武技缺失，地阶武技都已是难得，但他身怀多种天阶武技。　　这个时代灵药遍地，炼丹师成了最尊贵的职业，而他曾被称为尘丹皇。　　这个时代曾经的一个个绝世天才已然陨落，他却获得新生，带着一个时代的优势崛起成为天骄！　　不败天骄读者群：176370881微信号：huoshugaga1，微博：火树嘎嘎
     * is_prior_one : 0
     * create_time : 2018-08-19T06:41:45.046Z
     * update_time : 2018-08-19T06:41:45.046Z
     * gender : 1
     * keyword_hit_cnt : 0
     * type:
     */
    private String book_id;//小说id
    private String id;
    private String name_cn;
    private String author;
    private String content_update_time;
    private String covor_url;
    private String origin_website;
    private int is_finished; // 0为更新中 1 为已完成更新
    private int word_count;
    private int read_count;
    private String comment;
    private int is_prior_one;
    private String create_time;
    private String update_time;
    private int gender;
    private int keyword_hit_cnt;
    private String like;//是否已收藏
    private String type;//read 阅读记录 like 收藏
    private int total_size;//小说详情-返回的章节总数
    private boolean isEdit;//是否可编辑
    //阅读记录返回的章节信息
    private String chapter_url;
    private String chapter_number;
    private String chapter_name;
    private String category; //所属分类

    public NovelBean(String book_id, String name_cn, String covor_url, String type, String origin_website, String chapter_url, String chapter_number, String chapter_name) {
        this.book_id = book_id;
        this.name_cn = name_cn;
        this.covor_url = covor_url;
        this.type = type;
        this.origin_website = origin_website;
        this.chapter_url = chapter_url;
        this.chapter_number = chapter_number;
        this.chapter_name = chapter_name;
    }

    public String getCategory() {
        return category == null ? "" : category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBook_id() {
        return book_id == null ? "" : book_id;
    }

    public void setBook_id(String book_id) {
        this.book_id = book_id;
    }

    public String getId() {
        return id == null ? "" : id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName_cn() {
        return name_cn == null ? "" : name_cn;
    }

    public void setName_cn(String name_cn) {
        this.name_cn = name_cn;
    }

    public String getAuthor() {
        return author == null ? "" : author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent_update_time() {
        return content_update_time == null ? "" : content_update_time;
    }

    public void setContent_update_time(String content_update_time) {
        this.content_update_time = content_update_time;
    }

    public String getCovor_url() {
        return covor_url == null ? "" : covor_url;
    }

    public void setCovor_url(String covor_url) {
        this.covor_url = covor_url;
    }

    public String getOrigin_website() {
        return origin_website == null ? "" : origin_website;
    }

    public void setOrigin_website(String origin_website) {
        this.origin_website = origin_website;
    }

    public int getIs_finished() {
        return is_finished;
    }

    public void setIs_finished(int is_finished) {
        this.is_finished = is_finished;
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

    public String getComment() {
        return comment == null ? "" : comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getIs_prior_one() {
        return is_prior_one;
    }

    public void setIs_prior_one(int is_prior_one) {
        this.is_prior_one = is_prior_one;
    }

    public String getCreate_time() {
        return create_time == null ? "" : create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getUpdate_time() {
        return update_time == null ? "" : update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getKeyword_hit_cnt() {
        return keyword_hit_cnt;
    }

    public void setKeyword_hit_cnt(int keyword_hit_cnt) {
        this.keyword_hit_cnt = keyword_hit_cnt;
    }

    public String getLike() {
        return like == null ? "" : like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public String getType() {
        return type == null ? "" : type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getTotal_size() {
        return total_size;
    }

    public void setTotal_size(int total_size) {
        this.total_size = total_size;
    }

    public boolean isEdit() {
        return isEdit;
    }

    public void setEdit(boolean edit) {
        isEdit = edit;
    }

    public String getChapter_url() {
        return chapter_url == null ? "" : chapter_url;
    }

    public void setChapter_url(String chapter_url) {
        this.chapter_url = chapter_url;
    }

    public String getChapter_number() {
        return chapter_number == null ? "" : chapter_number;
    }

    public void setChapter_number(String chapter_number) {
        this.chapter_number = chapter_number;
    }

    public String getChapter_name() {
        return chapter_name == null ? "" : chapter_name;
    }

    public void setChapter_name(String chapter_name) {
        this.chapter_name = chapter_name;
    }
}
