package com.phungthanhquan.bookapp.Object;

import com.google.firebase.firestore.Exclude;

public class LichSuGiaoDich {
    @Exclude
    private String id;
    private String user_id;
    private String transaction_category;
    private Double money;
    private String rent_time;
    private String from_budget;
    private String time;
    private String entity;

    public LichSuGiaoDich() {
    }

    public LichSuGiaoDich(String id, String user_id, String transaction_category, Double money, String rent_time, String from_budget, String time, String entity) {
        this.id = id;
        this.user_id = user_id;
        this.transaction_category = transaction_category;
        this.money = money;
        this.rent_time = rent_time;
        this.from_budget = from_budget;
        this.time = time;
        this.entity = entity;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    @Exclude
    public String getId() {
        return id;
    }
    @Exclude
    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getTransaction_category() {
        return transaction_category;
    }

    public void setTransaction_category(String transaction_category) {
        this.transaction_category = transaction_category;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public String getRent_time() {
        return rent_time;
    }

    public void setRent_time(String rent_time) {
        this.rent_time = rent_time;
    }

    public String getFrom_budget() {
        return from_budget;
    }

    public void setFrom_budget(String from_budget) {
        this.from_budget = from_budget;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
