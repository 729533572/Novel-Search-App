package com.smart.novel.db.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by JoJo on 2018/8/30.
 * wechat:18510829974
 * description: 搜索历史实体——本地缓存
 */
@Entity
public class SearchHistoryBean {
    @Id(autoincrement = true)
    public Long searchId;
    public String searchKeyWords;

    @Generated(hash = 1122525751)
    public SearchHistoryBean(Long searchId, String searchKeyWords) {
        this.searchId = searchId;
        this.searchKeyWords = searchKeyWords;
    }

    @Generated(hash = 1570282321)
    public SearchHistoryBean() {
    }

    public Long getSearchId() {
        return this.searchId;
    }

    public void setSearchId(Long searchId) {
        this.searchId = searchId;
    }

    public String getSearchKeyWords() {
        return this.searchKeyWords;
    }

    public void setSearchKeyWords(String searchKeyWords) {
        this.searchKeyWords = searchKeyWords;
    }
}
