package com.phungthanhquan.bookapp.Presenter.Activity;

import android.content.Context;

import com.phungthanhquan.bookapp.Model.Activity.MuaThemGoiThemModel;
import com.phungthanhquan.bookapp.Object.User;
import com.phungthanhquan.bookapp.Object.UserRent;
import com.phungthanhquan.bookapp.View.InterfaceView.InterfaceViewActivityMuaThemGoiThue;

public class PresenterMuaThemGoiThue implements InPresenterMuaThemGoiThue {
    InterfaceViewActivityMuaThemGoiThue interfaceViewActivityMuaThemGoiThue;
    MuaThemGoiThemModel muaThemGoiThemModel;

    public PresenterMuaThemGoiThue(InterfaceViewActivityMuaThemGoiThue interfaceViewActivityMuaThemGoiThue) {
        this.interfaceViewActivityMuaThemGoiThue = interfaceViewActivityMuaThemGoiThue;
        muaThemGoiThemModel = new MuaThemGoiThemModel();
    }

    @Override
    public void LayUserRent() {
        muaThemGoiThemModel.LayUserRent(new MuaThemGoiThemModel.CallBackUserRen() {
            @Override
            public void myCallBack(UserRent userRent, User user) {
                interfaceViewActivityMuaThemGoiThue.hienThiThongTinGoiThue(userRent,user);
            }
        });
    }
}
