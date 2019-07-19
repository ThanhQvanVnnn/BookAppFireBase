package com.phungthanhquan.bookapp.Object;

public class ItemSachBanDangDoc {
    private String image_book;
    private String image_user;
    private String book_name;
    private String user_name;
    private String author_name;
    private String publisher_name;
    private Double star_average;
    private String book_id;


    public ItemSachBanDangDoc(String image_book, String image_user, String book_name, String user_name, String author_name, String publisher_name, Double star_average, String book_id) {
        this.image_book = image_book;
        this.image_user = image_user;
        this.book_name = book_name;
        this.user_name = user_name;
        this.author_name = author_name;
        this.publisher_name = publisher_name;
        this.star_average = star_average;
        this.book_id = book_id;
    }

    public ItemSachBanDangDoc() {
    }

    public String getBook_id() {
        return book_id;
    }

    public void setBook_id(String book_id) {
        this.book_id = book_id;
    }

    public String getImage_book() {
        return image_book;
    }

    public void setImage_book(String image_book) {
        this.image_book = image_book;
    }

    public String getImage_user() {
        return image_user;
    }

    public void setImage_user(String image_user) {
        this.image_user = image_user;
    }

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getPublisher_name() {
        return publisher_name;
    }

    public void setPublisher_name(String publisher_name) {
        this.publisher_name = publisher_name;
    }

    public Double getStar_average() {
        return star_average;
    }

    public void setStar_average(Double star_average) {
        this.star_average = star_average;
    }

    @Override
    public String toString() {
        return "ItemSachBanDangDoc{" +
                "image_book='" + image_book + '\'' +
                ", image_user='" + image_user + '\'' +
                ", book_name='" + book_name + '\'' +
                ", user_name='" + user_name + '\'' +
                ", author_name='" + author_name + '\'' +
                ", publisher_name='" + publisher_name + '\'' +
                ", star_average=" + star_average +
                ", book_id='" + book_id + '\'' +
                '}';
    }
}
