package com.phungthanhquan.bookapp.Presenter.Activity;

import com.phungthanhquan.bookapp.Model.Activity.LichSuGiaoDichModel;
import com.phungthanhquan.bookapp.Object.LichSuGiaoDich;
import com.phungthanhquan.bookapp.View.InterfaceView.InterfaceViewActivityLichSuGiaoDich;

import java.util.List;

public class PresenterLichSuGiaoDich implements InPresenterLichSuGiaoDich {
    InterfaceViewActivityLichSuGiaoDich interfaceViewActivityLichSuGiaoDich;
    LichSuGiaoDichModel lichSuGiaoDichModel;

    public PresenterLichSuGiaoDich(InterfaceViewActivityLichSuGiaoDich interfaceViewActivityLichSuGiaoDich) {
        this.interfaceViewActivityLichSuGiaoDich = interfaceViewActivityLichSuGiaoDich;
        lichSuGiaoDichModel = new LichSuGiaoDichModel();
    }

    @Override
    public void layDsLichSuGiaoDich() {
        lichSuGiaoDichModel.layDsLichSuGiaoDich(new LichSuGiaoDichModel.Callback() {
            @Override
            public void mycallBack(List<LichSuGiaoDich> lichSuGiaoDiches) {
                interfaceViewActivityLichSuGiaoDich.hienThiDanhSachGiaoDich(lichSuGiaoDiches);
            }
        });
    }
}
