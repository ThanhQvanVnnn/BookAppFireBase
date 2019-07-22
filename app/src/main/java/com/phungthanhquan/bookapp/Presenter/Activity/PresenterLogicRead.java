package com.phungthanhquan.bookapp.Presenter.Activity;

import android.content.Context;

import com.phungthanhquan.bookapp.Model.Fragment.ChuongSachModel;
import com.phungthanhquan.bookapp.Object.BookCase;
import com.phungthanhquan.bookapp.Object.ChuongSach;
import com.phungthanhquan.bookapp.Object.DauTrang;
import com.phungthanhquan.bookapp.View.InterfaceView.InterfaceViewActivityRead;
import com.phungthanhquan.bookapp.View.InterfaceView.InterfaceViewFragmentChuongSach;

import java.util.List;

public class PresenterLogicRead implements InPresenterRead {
    private Context context;
    InterfaceViewActivityRead interfaceViewActivityRead;
    ChuongSachModel chuongSachModel ;

    public PresenterLogicRead(Context context,InterfaceViewActivityRead interfaceViewActivityRead) {
        this.interfaceViewActivityRead = interfaceViewActivityRead;
        this.context = context;
        chuongSachModel = new ChuongSachModel(context);
    }
    @Override
    public void LayChuongSach(String book_id) {
        chuongSachModel.LayChuongSach(book_id, new ChuongSachModel.CallBackChuongSach() {
            @Override
            public void layChuongSach(List<ChuongSach> chuongSachList) {
                interfaceViewActivityRead.layChuongSach(chuongSachList);
            }
        });
    }

    @Override
    public void LayDauTrang(String book_id) {
        chuongSachModel.LaydauTrang(book_id, new ChuongSachModel.CallBackDauTrang() {
            @Override
            public void layDauTrang(List<DauTrang> dauTrangList) {
                interfaceViewActivityRead.layDauTrang(dauTrangList);
            }
        });
    }

    @Override
    public void LayBookCase(String book_id) {
        chuongSachModel.LayBookCase(book_id, new ChuongSachModel.CallBackLayBookCase() {
            @Override
            public void layBookCase(BookCase bookcase) {
                interfaceViewActivityRead.layBookCase(bookcase);
            }
        });
    }

}
