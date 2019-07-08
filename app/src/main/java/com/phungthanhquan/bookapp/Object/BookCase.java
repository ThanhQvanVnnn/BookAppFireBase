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


    public BookCase() {
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
