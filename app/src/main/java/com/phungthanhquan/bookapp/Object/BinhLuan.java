package com.phungthanhquan.bookapp.Object;



import com.google.firebase.Timestamp;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BinhLuan {
    private String book_id;
    private String content;
    private int star_number;
    private String time;
    private String user_id;

    public BinhLuan(String book_id, String content, int star_number, String time, String user_id) {
        this.book_id = book_id;
        this.content = content;
        this.star_number = star_number;
        this.time = time;
        this.user_id = user_id;
    }

    public BinhLuan() {
    }

    public String getBook_id() {
        return book_id;
    }

    public void setBook_id(String book_id) {
        this.book_id = book_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStar_number() {
        return star_number;
    }

    public void setStar_number(int star_number) {
        this.star_number = star_number;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }


}
