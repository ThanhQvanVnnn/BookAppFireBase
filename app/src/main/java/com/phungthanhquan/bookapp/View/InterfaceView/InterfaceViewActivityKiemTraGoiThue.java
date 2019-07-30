package com.phungthanhquan.bookapp.View.InterfaceView;

import com.phungthanhquan.bookapp.Object.Rent;
import com.phungthanhquan.bookapp.Object.UserRent;

import java.util.List;

public interface InterfaceViewActivityKiemTraGoiThue {
    void hienThiThoiGianThue(UserRent userRent);
    void hienThiDanhSachGoiThue(List<Rent> rentList);
}
