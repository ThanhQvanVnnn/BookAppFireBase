package com.phungthanhquan.bookapp.Model.Activity;


import android.content.Context;

import com.phungthanhquan.bookapp.Model.Fragment.TuSachModel;
import com.phungthanhquan.bookapp.Model.Room.DbRoomAccess;


public class TuSach_CaNhanClick_Model {


    public void laytusach(Context context, TuSachModel.TuSachCallback callBack){
        DbRoomAccess.getInstance(context).getAllBookcaseTask(context,callBack);
    }

}
