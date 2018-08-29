package com.smart.novel.db.bean;

import java.util.List;

/**
 * Created by JoJo on 2018/8/29.
 * wechat:18510829974
 * description: 阅读历史
 */

public class ReadHistoryEntity {

    /**
     * status : 0
     * msg : success
     * data : [{"id":1,"book_id":33,"user_id":3,"chapter_number":1,"chapter_name":"第一章离家","is_favored":1,"create_time":"2018-08-25T03:29:45.575Z","update_time":"2018-08-25T04:58:36.633Z","user_book_reading_cnt":8},{"id":5,"book_id":38,"user_id":3,"chapter_number":1,"chapter_name":"第三章挺漂亮一姑娘","is_favored":1,"create_time":"2018-08-25T04:53:44.682Z","update_time":"2018-08-25T04:58:30.378Z","user_book_reading_cnt":3},{"id":6,"book_id":34,"user_id":3,"chapter_number":1,"chapter_name":"第三章挺漂亮一姑娘","is_favored":1,"create_time":"2018-08-25T05:02:02.838Z","update_time":"2018-08-25T05:02:02.838Z","user_book_reading_cnt":1}]
     */

    private int status;
    private String msg;
    private List<DataBean> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * book_id : 33
         * user_id : 3
         * chapter_number : 1
         * chapter_name : 第一章离家
         * is_favored : 1
         * create_time : 2018-08-25T03:29:45.575Z
         * update_time : 2018-08-25T04:58:36.633Z
         * user_book_reading_cnt : 8
         */

        private int id;
        private int book_id;
        private int user_id;
        private int chapter_number;
        private String chapter_name;
        private int is_favored;
        private String create_time;
        private String update_time;
        private int user_book_reading_cnt;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getBook_id() {
            return book_id;
        }

        public void setBook_id(int book_id) {
            this.book_id = book_id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getChapter_number() {
            return chapter_number;
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

        public int getIs_favored() {
            return is_favored;
        }

        public void setIs_favored(int is_favored) {
            this.is_favored = is_favored;
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

        public int getUser_book_reading_cnt() {
            return user_book_reading_cnt;
        }

        public void setUser_book_reading_cnt(int user_book_reading_cnt) {
            this.user_book_reading_cnt = user_book_reading_cnt;
        }
    }
}
