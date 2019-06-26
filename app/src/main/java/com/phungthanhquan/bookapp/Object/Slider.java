package com.phungthanhquan.bookapp.Object;

public class Slider {
    private String url;
    private String image;
    public Slider(String url, String image) {
        this.url = url;
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
