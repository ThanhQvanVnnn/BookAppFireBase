package com.phungthanhquan.bookapp.Object;

public class Marketing {
    private String id;
    private String marketing_id;
    private String book_id;

    public Marketing() {
    }

    public Marketing(String marketing_id, String book_id) {
        this.marketing_id = marketing_id;
        this.book_id = book_id;
    }

    public String getMarketing_id() {
        return marketing_id;
    }

    public void setMarketing_id(String marketing_id) {
        this.marketing_id = marketing_id;
    }

    public String getBook_id() {
        return book_id;
    }

    public void setBook_id(String book_id) {
        this.book_id = book_id;
    }
}
