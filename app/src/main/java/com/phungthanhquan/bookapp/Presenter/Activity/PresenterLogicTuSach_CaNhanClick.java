package com.phungthanhquan.bookapp.Presenter.Activity;

import android.content.Context;

import com.phungthanhquan.bookapp.Model.Activity.TuSach_CaNhanClick_Model;
import com.phungthanhquan.bookapp.Model.Fragment.TuSachModel;
import com.phungthanhquan.bookapp.Object.BookCase;
import com.phungthanhquan.bookapp.View.InterfaceView.InterfaceViewActivityTuSach_CaNhanClick;

import java.util.List;

public class PresenterLogicTuSach_CaNhanClick implements InPresenterTuSach_CaNhanClick {
    InterfaceViewActivityTuSach_CaNhanClick interfaceViewActivityTuSach_caNhanClick;
    TuSach_CaNhanClick_Model tuSach_caNhanClick_model;

    public PresenterLogicTuSach_CaNhanClick(InterfaceViewActivityTuSach_CaNhanClick interfaceViewActivityTuSach_caNhanClick) {
        this.interfaceViewActivityTuSach_caNhanClick = interfaceViewActivityTuSach_caNhanClick;
        tuSach_caNhanClick_model = new TuSach_CaNhanClick_Model();
    }

    @Override
    public void laytusach(Context context) {
        tuSach_caNhanClick_model.laytusach(context, new TuSachModel.TuSachCallback() {
            @Override
            public void myCallBack(List<BookCase> dsBookcase) {
                interfaceViewActivityTuSach_caNhanClick.hienThiDanhSachSach(dsBookcase);
            }
        });
    }
}
