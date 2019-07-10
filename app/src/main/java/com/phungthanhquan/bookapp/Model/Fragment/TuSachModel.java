package com.phungthanhquan.bookapp.Model.Fragment;


import android.content.Context;

import com.phungthanhquan.bookapp.Model.Room.DbRoomAccess;
import com.phungthanhquan.bookapp.Object.Book;
import com.phungthanhquan.bookapp.Object.BookCase;
import com.phungthanhquan.bookapp.Object.UserRent;

import java.util.ArrayList;
import java.util.List;

public class TuSachModel {
    private Context context;
    public TuSachModel(Context context){
        this.context = context;
    }
    public interface TuSachCallback{
         void myCallBack(List<BookCase> dsBookcase);
    }
    public interface UserRentCallback{
        void myCallBack(List<UserRent> dsUserRent);
    }

    public void getBookCase(TuSachCallback callBack){
        DbRoomAccess.getInstance(context).getAllBookcaseTask(context,callBack);
    }

    public void getUserRent(UserRentCallback callBack){
        DbRoomAccess.getInstance(context).getAllUserRentTask(context,callBack);
    }
}
