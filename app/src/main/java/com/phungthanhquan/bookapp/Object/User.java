package com.phungthanhquan.bookapp.Object;

import androidx.annotation.NonNull;

public class User {
    private String user_id;
    private String email;
    private String name;
    private String phone;
    private Double budget;
    private Boolean gender;
    private String birth_day;

    public User(String email, String name, String phone,Double budget, Boolean gender, String birth_day ) {
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.budget = budget;
        this.gender = gender;
        this.birth_day = birth_day;
    }

    public User() {
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Double getBudget() {
        return budget;
    }

    public void setBudget(Double budget) {
        this.budget = budget;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public String getBirth_day() {
        return birth_day;
    }

    public void setBirth_day(String birth_day) {
        this.birth_day = birth_day;
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id='" + user_id + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", budget=" + budget +
                ", gender=" + gender +
                ", birth_day='" + birth_day + '\'' +
                '}';
    }
}
