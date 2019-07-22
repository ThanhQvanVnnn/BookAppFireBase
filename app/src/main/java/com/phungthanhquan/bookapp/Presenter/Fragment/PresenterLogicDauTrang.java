package com.phungthanhquan.bookapp.Presenter.Fragment;

import android.content.Context;

import com.phungthanhquan.bookapp.Model.Fragment.ChuongSachModel;
import com.phungthanhquan.bookapp.Object.ChuongSach;
import com.phungthanhquan.bookapp.Object.DauTrang;
import com.phungthanhquan.bookapp.View.InterfaceView.InterfaceViewFragmentChuongSach;
import com.phungthanhquan.bookapp.View.InterfaceView.InterfaceViewFragmentDauTrang;

import java.util.List;

public class PresenterLogicDauTrang implements PresenterInterfaceDauTrang {
    private Context context;
    InterfaceViewFragmentDauTrang interfaceViewFragmentDauTrang;
    ChuongSachModel chuongSachModel ;

    public PresenterLogicDauTrang(Context context, InterfaceViewFragmentDauTrang interfaceViewFragmentDauTrang) {
        this.interfaceViewFragmentDauTrang = interfaceViewFragmentDauTrang;
        this.context = context;
         chuongSachModel = new ChuongSachModel(context);
    }

    @Override
    public void LayDauTrang(String book_id) {
        chuongSachModel.LaydauTrang(book_id, new ChuongSachModel.CallBackDauTrang() {
            @Override
            public void layDauTrang(List<DauTrang> dauTrangList) {
                interfaceViewFragmentDauTrang.hienThiDauTrang(dauTrangList);
            }
        });
    }
}
