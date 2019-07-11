package com.phungthanhquan.bookapp.Object;

public class Rent {
    private String id;
    private String name;
    private Double price;
    private int time;

    public Rent() {
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getMonth() {
        return time;
    }

    public void setMonth(int month) {
        this.time = month;
    }
}
