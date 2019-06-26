package com.phungthanhquan.bookapp.Object;

public class AlbumBook {
    private String ImageAlbum;
    private String TitleAlbum;

    public AlbumBook(String imageAlbum, String titleAlbum) {
        ImageAlbum = imageAlbum;
        TitleAlbum = titleAlbum;
    }

    public String getImageAlbum() {
        return ImageAlbum;
    }

    public void setImageAlbum(String imageAlbum) {
        ImageAlbum = imageAlbum;
    }

    public String getTitleAlbum() {
        return TitleAlbum;
    }

    public void setTitleAlbum(String titleAlbum) {
        TitleAlbum = titleAlbum;
    }
}
