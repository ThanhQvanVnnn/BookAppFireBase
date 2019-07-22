package com.phungthanhquan.bookapp.Object;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.firebase.firestore.IgnoreExtraProperties;

import java.io.Serializable;

@Entity
@IgnoreExtraProperties
public class DauTrang{
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;
    private String book_id;
    private String TenChuong;
    private int trang;
    private String DayTime;

    public DauTrang() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DauTrang(String book_id, String tenChuong, int trang, String dayTime) {
        this.book_id = book_id;
        TenChuong = tenChuong;
        this.trang = trang;
        DayTime = dayTime;
    }

    public String getBook_id() {
        return book_id;
    }

    public void setBook_id(String book_id) {
        this.book_id = book_id;
    }

    public String getTenChuong() {
        return TenChuong;
    }

    public void setTenChuong(String tenChuong) {
        TenChuong = tenChuong;
    }

    public int getTrang() {
        return trang;
    }

    public void setTrang(int trang) {
        this.trang = trang;
    }

    public String getDayTime() {
        return DayTime;
    }

    public void setDayTime(String dayTime) {
        DayTime = dayTime;
    }
}
