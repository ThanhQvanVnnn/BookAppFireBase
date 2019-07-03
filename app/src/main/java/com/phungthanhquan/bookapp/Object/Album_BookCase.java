package com.phungthanhquan.bookapp.Object;

public class Album_BookCase {
    private String id;
    private String album_id;
    private String album_name;
    private String book_id;

    public Album_BookCase(String id, String album_id, String book_id) {
        this.id = id;
        this.album_id = album_id;
        this.book_id = book_id;
    }

    public Album_BookCase(String id, String album_id, String album_name, String book_id) {
        this.id = id;
        this.album_id = album_id;
        this.album_name = album_name;
        this.book_id = book_id;
    }

    public String getAlbum_name() {
        return album_name;
    }

    public void setAlbum_name(String album_name) {
        this.album_name = album_name;
    }

    public Album_BookCase() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAlbum_id() {
        return album_id;
    }

    public void setAlbum_id(String album_id) {
        this.album_id = album_id;
    }

    public String getBook_id() {
        return book_id;
    }

    public void setBook_id(String book_id) {
        this.book_id = book_id;
    }
}
