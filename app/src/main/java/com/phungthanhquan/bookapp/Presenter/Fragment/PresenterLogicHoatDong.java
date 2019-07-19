package com.phungthanhquan.bookapp.Presenter.Fragment;

import android.content.Context;

import com.phungthanhquan.bookapp.Model.Fragment.CaNhanModel;
import com.phungthanhquan.bookapp.Model.Fragment.HoatDongModel;
import com.phungthanhquan.bookapp.Model.Fragment.TuSachModel;
import com.phungthanhquan.bookapp.Object.BookCase;
import com.phungthanhquan.bookapp.Object.Friend;
import com.phungthanhquan.bookapp.Object.ItemSachBanDangDoc;
import com.phungthanhquan.bookapp.Object.User;
import com.phungthanhquan.bookapp.View.InterfaceView.InterfaceViewFragmentCaNhan;
import com.phungthanhquan.bookapp.View.InterfaceView.InterfaceViewFragmentHoatDong;

import java.util.List;

public class PresenterLogicHoatDong implements PresenterInterfaceHoatDong {
    private InterfaceViewFragmentHoatDong interfaceViewFragmentHoatDong;
    private HoatDongModel hoatDongModel;

    public PresenterLogicHoatDong(InterfaceViewFragmentHoatDong interfaceViewFragmentHoatDong) {
        this.interfaceViewFragmentHoatDong = interfaceViewFragmentHoatDong;
        hoatDongModel = new HoatDongModel();
    }

    @Override
    public void LaySachCuaBan(Friend friend) {
        hoatDongModel.layDanhSachSachBan(friend, new HoatDongModel.callBackHoatDongModel() {
            @Override
            public void myCallBack(List<BookCase> ds) {
                interfaceViewFragmentHoatDong.hienThiSachCuaBan(ds);
            }
        });
    }

    @Override
    public void layDanhSachBan() {
        hoatDongModel.layDanhSachBan(new HoatDongModel.callBackLayDuLieuDau() {
            @Override
            public void myCallBack(List<Friend> friendList) {
                interfaceViewFragmentHoatDong.laydanhsachban(friendList);
            }
        });
    }

}
