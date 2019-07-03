package com.phungthanhquan.bookapp.Object;



public class Album {
    private String id;
    private String name;

    public Album(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Album() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
