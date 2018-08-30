package com.smart.novel.db.bean;

/**
 * Created by JoJo on 2018/8/29.
 * wechat:18510829974
 * description: 阅读历史
 */

public class ReadHistoryBean {

    /**
     * id : 5644
     * name_cn : 黑金总裁，豪门新婚告急
     * author : 黑岛菜
     * content_update_time : 2018-05-27T09:06:15.000Z
     * catagory : 0
     * covor_url : https://www.zhuishu.tw/cover/56/56920.jpg
     * origin_website : https://www.zhuishu.tw
     * is_finished : 0
     * word_count : 0
     * read_count : 0
     * comment : 洛夏烟四年前糊里糊涂的强过他之后，甩给薄君臣两张软妹币作为答谢取悦费！四年后再见，却是……帝国酒店空中西餐厅外走廊，肤白眼靓的娇俏女孩被摁在电梯门口：“小野猫，惹了我，就该负起你的责……”女人眼眸斜睨嗤笑：“怎么负责，你嫁我？”薄刃的嘴角含着浅笑，男人在她耳旁吐着热气：“巧得很，我倒是还真缺一个暖被窝的主！”
     * is_prior_one : 0
     * create_time : 2018-08-20T07:15:59.009Z
     * update_time : 2018-08-20T07:15:59.009Z
     * gender : -1
     * keyword_hit_cnt : 0
     */

    private int id;
    private String name_cn;
    private String author;
    private String content_update_time;
    private int catagory;
    private String covor_url;
    private String origin_website;
    private int is_finished;
    private int word_count;
    private int read_count;
    private String comment;
    private int is_prior_one;
    private String create_time;
    private String update_time;
    private int gender;
    private int keyword_hit_cnt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName_cn() {
        return name_cn;
    }

    public void setName_cn(String name_cn) {
        this.name_cn = name_cn;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent_update_time() {
        return content_update_time;
    }

    public void setContent_update_time(String content_update_time) {
        this.content_update_time = content_update_time;
    }

    public int getCatagory() {
        return catagory;
    }

    public void setCatagory(int catagory) {
        this.catagory = catagory;
    }

    public String getCovor_url() {
        return covor_url;
    }

    public void setCovor_url(String covor_url) {
        this.covor_url = covor_url;
    }

    public String getOrigin_website() {
        return origin_website;
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
        return comment;
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
}
