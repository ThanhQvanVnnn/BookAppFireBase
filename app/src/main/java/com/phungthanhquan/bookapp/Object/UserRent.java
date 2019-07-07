package com.phungthanhquan.bookapp.Object;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.firebase.firestore.Exclude;

@Entity
public class UserRent {
    @PrimaryKey
    @NonNull
    private String id;
    private String rent_id;
    private String time_rest;
    private String user_id;

    @Ignore
    public UserRent(String rent_id, String time_rest, String user_id) {
        this.rent_id = rent_id;
        this.time_rest = time_rest;
        this.user_id = user_id;
    }

    @Ignore
    public UserRent() {
    }

    public UserRent(String id, String rent_id, String time_rest, String user_id) {
        this.id = id;
        this.rent_id = rent_id;
        this.time_rest = time_rest;
        this.user_id = user_id;
    }
    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRent_id() {
        return rent_id;
    }

    public void setRent_id(String rent_id) {
        this.rent_id = rent_id;
    }

    public String getTime_rest() {
        return time_rest;
    }

    public void setTime_rest(String time_rest) {
        this.time_rest = time_rest;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
