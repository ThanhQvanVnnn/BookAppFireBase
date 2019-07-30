package com.phungthanhquan.bookapp.Presenter.Activity;

import android.content.Context;

import com.phungthanhquan.bookapp.Model.Activity.KiemTraGoiThueModel;
import com.phungthanhquan.bookapp.Object.Rent;
import com.phungthanhquan.bookapp.Object.UserRent;
import com.phungthanhquan.bookapp.View.InterfaceView.InterfaceViewActivityKiemTraGoiThue;

import java.util.List;

public class PresenterKiemTraGoiThue implements InPresenterKiemTraGoiThue {
    private Context context;
    private KiemTraGoiThueModel kiemTraGoiThueModel;
    private InterfaceViewActivityKiemTraGoiThue interfaceViewActivityKiemTraGoiThue;

    public PresenterKiemTraGoiThue(Context context, InterfaceViewActivityKiemTraGoiThue i) {
        this.context = context;
        kiemTraGoiThueModel = new KiemTraGoiThueModel(context);
        this.interfaceViewActivityKiemTraGoiThue = i;
    }

    @Override
    public void layGoiThue() {
        kiemTraGoiThueModel.LayUserRent(new KiemTraGoiThueModel.CallBackLayUserRent() {
            @Override
            public void MycallBack(UserRent userRent) {
                interfaceViewActivityKiemTraGoiThue.hienThiThoiGianThue(userRent);
            }
        });
    }

    @Override
    public void layDanhSachGoiThue() {
        kiemTraGoiThueModel.LayDanhsachGoiThue(new KiemTraGoiThueModel.CallBackLayListRent() {
            @Override
            public void MycallBack(List<Rent> rentList) {
                interfaceViewActivityKiemTraGoiThue.hienThiDanhSachGoiThue(rentList);
            }
        });
    }
}
