package com.phungthanhquan.bookapp.Presenter.Activity;

import com.phungthanhquan.bookapp.Model.Activity.ModelActivityDetailBook;
import com.phungthanhquan.bookapp.Object.BinhLuan;
import com.phungthanhquan.bookapp.View.InterfaceView.InterfaceViewActivityDanhSachDanhGia;

import java.util.List;

public class PresenterLogicXemThemDanhGia implements InPresenterXemThemDanhGia {
    InterfaceViewActivityDanhSachDanhGia interfaceViewActivityDanhSachDanhGia;
    ModelActivityDetailBook modelActivityDetailBook;

    public PresenterLogicXemThemDanhGia(InterfaceViewActivityDanhSachDanhGia interfaceViewActivityDanhSachDanhGia) {
        this.interfaceViewActivityDanhSachDanhGia = interfaceViewActivityDanhSachDanhGia;
        modelActivityDetailBook = new ModelActivityDetailBook();
    }

    @Override
    public void xuliHienThi() {
        List<BinhLuan> binhLuanList = modelActivityDetailBook.getListBinhLuan();
        if(binhLuanList.size()!=0){
            interfaceViewActivityDanhSachDanhGia.hienThiDanhSach(binhLuanList);
        }

    }
}
