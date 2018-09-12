package com.smart.novel.bean;

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
    public Long id;
    public String searchKeyWords;
    @Generated(hash = 1354987317)
    public SearchHistoryBean(Long id, String searchKeyWords) {
        this.id = id;
        this.searchKeyWords = searchKeyWords;
    }
    @Generated(hash = 1570282321)
    public SearchHistoryBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getSearchKeyWords() {
        return this.searchKeyWords;
    }
    public void setSearchKeyWords(String searchKeyWords) {
        this.searchKeyWords = searchKeyWords;
    }

}
