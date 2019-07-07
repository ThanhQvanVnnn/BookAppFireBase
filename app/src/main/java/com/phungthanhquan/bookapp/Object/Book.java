package com.phungthanhquan.bookapp.Object;


import com.google.firebase.firestore.Exclude;

public class Book {
    private String id;
    private String author_name;
    private String category_id;
    private String publisher_name;
    private int comment_number;
    private String introduce;
    private String name;
    private int page_number;
    private Double price;
    private String publisher_id;
    private Double star_average;

    public String getPublisher_name() {
        return publisher_name;
    }

    public void setPublisher_name(String publisher_name) {
        this.publisher_name = publisher_name;
    }

    public Book() {
    }

    public Book(String author_name, String category_id, int comment_number, String introduce, String name, int page_number, Double price, String publisher_id, Double star_average) {
        this.author_name = author_name;
        this.category_id = category_id;
        this.comment_number = comment_number;
        this.introduce = introduce;
        this.name = name;
        this.page_number = page_number;
        this.price = price;
        this.publisher_id = publisher_id;
        this.star_average = star_average;
    }

    @Exclude
    public String getId() {
        return id;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public int getComment_number() {
        return comment_number;
    }

    public void setComment_number(int comment_number) {
        this.comment_number = comment_number;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPage_number() {
        return page_number;
    }

    public void setPage_number(int page_number) {
        this.page_number = page_number;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getPublisher_id() {
        return publisher_id;
    }

    public void setPublisher_id(String publisher_id) {
        this.publisher_id = publisher_id;
    }

    public Double getStar_average() {
        return star_average;
    }

    public void setStar_average(Double star_average) {
        this.star_average = star_average;
    }
}
