package com.phungthanhquan.bookapp.Object;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.IgnoreExtraProperties;

import javax.annotation.Nonnull;

@Entity
public class BookCase {
    @PrimaryKey(autoGenerate = false)
    @NonNull
    @Exclude
    private String id;
    private String book_id;
    private String user_id;
    private Boolean isBought;
    @Exclude
    private int last_time;
    @Exclude
    private int last_read;
    @Exclude
    private String book_image;


    public BookCase() {
        this.last_time = 0;
    }


    public BookCase(String book_id, String user_id, Boolean isBought) {
        this.book_id = book_id;
        this.user_id = user_id;
        this.isBought = isBought;
        this.last_time = 0;
        this.last_read = 0;
    }

    public int getLast_read() {
        return last_read;
    }

    public void setLast_read(int last_read) {
        this.last_read = last_read;
    }

    @Exclude
    public int getLast_time() {
        return last_time;
    }

    @Exclude
    public void setLast_time(int last_time) {
        this.last_time = last_time;
    }

    @Exclude
    public String getBook_image() {
        return book_image;
    }

    @Exclude
    public void setBook_image(String book_image) {
        this.book_image = book_image;
    }

    @Exclude
    public String getId() {
        return id;
    }

    @Exclude
    public void setId(String id) {
        this.id = id;
    }

    public String getBook_id() {
        return book_id;
    }

    public void setBook_id(String book_id) {
        this.book_id = book_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public Boolean getBought() {
        return isBought;
    }

    public void setBought(Boolean bought) {
        isBought = bought;
    }

    @Override
    public String toString() {
        return "BookCase{" +
                "id='" + id + '\'' +
                ", book_id='" + book_id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", isBought=" + isBought +
                '}';
    }
}
