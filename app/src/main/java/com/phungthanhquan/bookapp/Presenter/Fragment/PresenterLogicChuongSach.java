package com.phungthanhquan.bookapp.Presenter.Fragment;

import android.content.Context;

import com.phungthanhquan.bookapp.Model.Fragment.ChuongSachModel;
import com.phungthanhquan.bookapp.Object.ChuongSach;
import com.phungthanhquan.bookapp.View.InterfaceView.InterfaceViewFragmentChuongSach;

import java.util.List;

public class PresenterLogicChuongSach implements PresenterInterfaceChuongSach {
    private Context context;
    InterfaceViewFragmentChuongSach interfaceViewFragmentChuongSach;
    ChuongSachModel chuongSachModel ;

    public PresenterLogicChuongSach(Context context,InterfaceViewFragmentChuongSach interfaceViewFragmentChuongSach) {
        this.interfaceViewFragmentChuongSach = interfaceViewFragmentChuongSach;
        this.context = context;
         chuongSachModel = new ChuongSachModel(context);
    }

    @Override
    public void LayChuongSach(String book_id) {
        chuongSachModel.LayChuongSach(book_id, new ChuongSachModel.CallBackChuongSach() {
            @Override
            public void layChuongSach(List<ChuongSach> chuongSachList) {
                interfaceViewFragmentChuongSach.hienThiChuongSach(chuongSachList);
            }
        });
    }
}
