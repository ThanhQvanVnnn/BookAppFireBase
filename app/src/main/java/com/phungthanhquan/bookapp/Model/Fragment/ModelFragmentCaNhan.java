package com.phungthanhquan.bookapp.Model.Fragment;

import com.phungthanhquan.bookapp.Object.User;

import java.util.ArrayList;
import java.util.List;

public class ModelFragmentCaNhan {
    private User user;
    public User layDsUser(){
        user = new User("1","Nguyễn Trần Tuấn"
                ,"https://mangthuvien.net/Uploads/Post/baomi-165417105458-16-ngon-ngu-lap-trinh-giup-it-viet-khong-bao-gio-that-nghiep.jpg"
        ,1500000,15,20,35,19,5);
        return user;
    }
}
