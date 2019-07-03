package com.phungthanhquan.bookapp.Presenter.Activity;

import com.phungthanhquan.bookapp.Model.Activity.DetailBookModel;
import com.phungthanhquan.bookapp.Object.BinhLuan;
import com.phungthanhquan.bookapp.View.InterfaceView.InterfaceViewActivityDanhSachDanhGia;

import java.util.List;

public class PresenterLogicXemThemDanhGia implements InPresenterXemThemDanhGia {
    InterfaceViewActivityDanhSachDanhGia interfaceViewActivityDanhSachDanhGia;
    DetailBookModel detailBookModel;

    public PresenterLogicXemThemDanhGia(InterfaceViewActivityDanhSachDanhGia interfaceViewActivityDanhSachDanhGia) {
        this.interfaceViewActivityDanhSachDanhGia = interfaceViewActivityDanhSachDanhGia;
        detailBookModel = new DetailBookModel();
    }

    @Override
    public void xuliHienThi() {
        List<BinhLuan> binhLuanList = detailBookModel.getListBinhLuan();
        if(binhLuanList.size()!=0){
            interfaceViewActivityDanhSachDanhGia.hienThiDanhSach(binhLuanList);
        }

    }
}
