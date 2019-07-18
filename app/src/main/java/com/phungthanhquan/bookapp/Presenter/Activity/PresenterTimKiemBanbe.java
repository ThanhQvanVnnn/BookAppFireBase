package com.phungthanhquan.bookapp.Presenter.Activity;

import com.phungthanhquan.bookapp.Model.Activity.TimKiemBanBeModel;
import com.phungthanhquan.bookapp.Object.Friend;
import com.phungthanhquan.bookapp.Object.User;
import com.phungthanhquan.bookapp.View.InterfaceView.InterfaceViewActivityTimKiemBanBe;

import java.util.List;

public class PresenterTimKiemBanbe implements InPresenterTimKiemBanBe {
    private InterfaceViewActivityTimKiemBanBe interfaceViewActivityTimKiemBanBe;
    private TimKiemBanBeModel timKiemBanBeModel;

    public PresenterTimKiemBanbe(InterfaceViewActivityTimKiemBanBe interfaceViewActivityTimKiemBanBe) {
        this.interfaceViewActivityTimKiemBanBe = interfaceViewActivityTimKiemBanBe;
        timKiemBanBeModel = new TimKiemBanBeModel();
    }

    @Override
    public void laydanhsachbanbe(String name) {
        timKiemBanBeModel.TimKiemBanBe(name, new TimKiemBanBeModel.CallBackTimKiemModel() {
            @Override
            public void mycallback(List<User> userList, List<Friend> friendList) {
                interfaceViewActivityTimKiemBanBe.hienThiTimKiem(userList,friendList);
            }
        });
    }
}
