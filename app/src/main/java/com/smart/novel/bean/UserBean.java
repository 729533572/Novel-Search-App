package com.smart.novel.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by JoJo on 2018/6/21.
 * wechat：18510829974
 * description：用户实体
 */
@Entity
public class UserBean {

    /**
     * id : 5
     * user_name : 鱼乐小说用户
     * gender : 1
     * chat_head_uri :
     * phone : 185****9974
     * province :
     * city :
     * last_login_time : 2018-08-31T07:43:21.240Z
     * create_time : 2018-08-31T07:43:21.240Z
     * update_time : 2018-08-31T07:43:21.240Z
     * enable : 1
     */
    @Id
    private int id;
    private String user_name;
    private int gender;
    private String chat_head_uri;
    private String phone;
    private String province;
    private String city;
    private String last_login_time;
    private String create_time;
    private String update_time;
    private int enable;

    @Generated(hash = 1938839444)
    public UserBean(int id, String user_name, int gender, String chat_head_uri,
            String phone, String province, String city, String last_login_time,
            String create_time, String update_time, int enable) {
        this.id = id;
        this.user_name = user_name;
        this.gender = gender;
        this.chat_head_uri = chat_head_uri;
        this.phone = phone;
        this.province = province;
        this.city = city;
        this.last_login_time = last_login_time;
        this.create_time = create_time;
        this.update_time = update_time;
        this.enable = enable;
    }

    @Generated(hash = 1203313951)
    public UserBean() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getChat_head_uri() {
        return chat_head_uri;
    }

    public void setChat_head_uri(String chat_head_uri) {
        this.chat_head_uri = chat_head_uri;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLast_login_time() {
        return last_login_time;
    }

    public void setLast_login_time(String last_login_time) {
        this.last_login_time = last_login_time;
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

    public int getEnable() {
        return enable;
    }

    public void setEnable(int enable) {
        this.enable = enable;
    }
}
