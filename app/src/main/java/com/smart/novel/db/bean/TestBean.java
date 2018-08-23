package com.smart.novel.db.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by JoJo on 2018/8/23.
 * wechat:18510829974
 * description:
 */
@Entity
public class TestBean {
    @Id
    private String msg;
    private boolean success;

    @Generated(hash = 2108716976)
    public TestBean(String msg, boolean success) {
        this.msg = msg;
        this.success = success;
    }

    @Generated(hash = 2087637710)
    public TestBean() {
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean getSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
