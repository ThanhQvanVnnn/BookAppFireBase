package com.phungthanhquan.bookapp.Object;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.IgnoreExtraProperties;

import java.io.Serializable;

@Entity
@IgnoreExtraProperties
public class ChuongSach implements Serializable {
    @Exclude
    @PrimaryKey
    @NonNull
    private String id;
    private String book_id;
    private int page_number;
    private String content;

    public ChuongSach(String id, String book_id, int page_number, String content) {
        this.id = id;
        this.book_id = book_id;
        this.page_number = page_number;
        this.content = content;
    }

    public ChuongSach() {
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

    public int getPage_number() {
        return page_number;
    }

    public void setPage_number(int page_number) {
        this.page_number = page_number;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof ChuongSach)) return false;
        ChuongSach chuongSach = (ChuongSach) obj;
        if (this.getPage_number() == chuongSach.getPage_number()) return true;
        return false;
    }
}
