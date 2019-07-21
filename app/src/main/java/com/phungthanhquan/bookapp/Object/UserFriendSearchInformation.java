package com.phungthanhquan.bookapp.Object;

public class UserFriendSearchInformation {
    private String userName;
    private String userImage;
    private String createAt;

    public UserFriendSearchInformation(String userName, String userImage, String createAt) {
        this.userName = userName;
        this.userImage = userImage;
        this.createAt = createAt;
    }

    public UserFriendSearchInformation() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }
}
