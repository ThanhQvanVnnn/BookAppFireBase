package com.phungthanhquan.bookapp.Presenter.Fragment;

import com.phungthanhquan.bookapp.Model.Fragment.DanhMucModel;
import com.phungthanhquan.bookapp.Object.DanhMuc;
import com.phungthanhquan.bookapp.View.InterfaceView.InterfaceViewFragmentDanhMuc;

import java.util.List;

public class PresenterLogicDanhMuc implements PresenterInterfaceDanhMuc {
    private InterfaceViewFragmentDanhMuc interfaceViewFragmentDanhMuc;
    private DanhMucModel danhMucModel;

    public PresenterLogicDanhMuc(InterfaceViewFragmentDanhMuc interfaceViewFragmentDanhMuc) {
        this.interfaceViewFragmentDanhMuc = interfaceViewFragmentDanhMuc;
        danhMucModel = new DanhMucModel();
    }

    @Override
    public void xuliHienThiDanhMuc() {
        List<DanhMuc> danhMucs = danhMucModel.getDanhMuc();
        if(danhMucs.size()!=0){
            interfaceViewFragmentDanhMuc.hienThiDanhMuc(danhMucs);
        }
    }
}
