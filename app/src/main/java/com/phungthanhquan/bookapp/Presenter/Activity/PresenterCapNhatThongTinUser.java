package com.phungthanhquan.bookapp.Presenter.Activity;

import com.phungthanhquan.bookapp.Model.Activity.CapNhatThongTinUserModel;
import com.phungthanhquan.bookapp.Object.User;
import com.phungthanhquan.bookapp.View.InterfaceView.InterfaceViewActivityCapNhatThongTin;

public class PresenterCapNhatThongTinUser implements InPresenterCapNhatThongTinUser {
    CapNhatThongTinUserModel capNhatThongTinUserModel;
    InterfaceViewActivityCapNhatThongTin interfaceViewActivityCapNhatThongTin;

    public PresenterCapNhatThongTinUser(InterfaceViewActivityCapNhatThongTin interfaceViewActivityCapNhatThongTin) {
        this.interfaceViewActivityCapNhatThongTin = interfaceViewActivityCapNhatThongTin;
        capNhatThongTinUserModel = new CapNhatThongTinUserModel();
    }

    @Override
    public void hienThicapNhatUser() {
        capNhatThongTinUserModel.layThongTinUser(new CapNhatThongTinUserModel.CallBackCapNhat() {
            @Override
            public void myCallBack(User user) {
                interfaceViewActivityCapNhatThongTin.hienthiThongTinUser(user);
            }
        });
    }

    @Override
    public void capNhatUser(User user) {
        capNhatThongTinUserModel.CapNhatUser(user, new CapNhatThongTinUserModel.CallBackCapNhatxong() {
            @Override
            public void myCallBack() {
                interfaceViewActivityCapNhatThongTin.chinhsuaThongTinUser();
            }
        });
    }
}
