package com.phungthanhquan.bookapp.Object;

import com.google.firebase.firestore.Exclude;

public class Friend {
    @Exclude
    private String id;
    private String sender_id;
    private String receiver_id;

    public Friend(String id, String sender_id, String receiver_id) {
        this.id = id;
        this.sender_id = sender_id;
        this.receiver_id = receiver_id;
    }

    public Friend(String sender_id, String receiver_id) {
        this.sender_id = sender_id;
        this.receiver_id = receiver_id;
    }

    public Friend() {
    }

    @Exclude
    public String getId() {
        return id;
    }

    @Exclude
    public void setId(String id) {
        this.id = id;
    }

    public String getSender_id() {
        return sender_id;
    }

    public void setSender_id(String sender_id) {
        this.sender_id = sender_id;
    }

    public String getReceiver_id() {
        return receiver_id;
    }

    public void setReceiver_id(String receiver_id) {
        this.receiver_id = receiver_id;
    }
}
