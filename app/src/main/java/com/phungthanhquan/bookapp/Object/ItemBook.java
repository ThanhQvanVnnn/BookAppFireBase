package com.phungthanhquan.bookapp.Object;

import android.arch.persistence.room.Entity;

@Entity(tableName = "")
public class ItemBook {
    private String title;
    private String urlImage;
    private String bookID;
    private String tenTacGia;

    public ItemBook(String title, String urlImage, String bookID, String tenTacGia) {
        this.title = title;
        this.urlImage = urlImage;
        this.bookID = bookID;
        this.tenTacGia = tenTacGia;
    }

    public ItemBook(String title, String urlImage, String bookID) {
        this.title = title;
        this.urlImage = urlImage;
        this.bookID = bookID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getBookID() {
        return bookID;
    }

    public void setBookID(String bookID) {
        this.bookID = bookID;
    }

    public String getTenTacGia() {
        return tenTacGia;
    }

    public void setTenTacGia(String tenTacGia) {
        this.tenTacGia = tenTacGia;
    }
}
