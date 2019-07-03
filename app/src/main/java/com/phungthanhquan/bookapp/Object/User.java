package com.phungthanhquan.bookapp.Object;

public class User {
    private String user_id;
    private String email;
    private String name;
    private String phone;
    private Float budget;

    public User(String email, String name, String phone,Float budget ) {
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.budget = budget;
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

    public Float getBudget() {
        return budget;
    }

    public void setBudget(Float budget) {
        this.budget = budget;
    }
}
