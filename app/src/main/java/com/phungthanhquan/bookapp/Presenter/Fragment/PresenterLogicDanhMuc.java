package com.phungthanhquan.bookapp.Presenter.Fragment;

import com.phungthanhquan.bookapp.Model.Fragment.ModelFragmentDanhMuc;
import com.phungthanhquan.bookapp.Object.DanhMuc;
import com.phungthanhquan.bookapp.View.InterfaceView.InterfaceViewFragmentDanhMuc;

import java.util.List;

public class PresenterLogicDanhMuc implements PresenterInterfaceDanhMuc {
    private InterfaceViewFragmentDanhMuc interfaceViewFragmentDanhMuc;
    private ModelFragmentDanhMuc modelFragmentDanhMuc;

    public PresenterLogicDanhMuc(InterfaceViewFragmentDanhMuc interfaceViewFragmentDanhMuc) {
        this.interfaceViewFragmentDanhMuc = interfaceViewFragmentDanhMuc;
        modelFragmentDanhMuc = new ModelFragmentDanhMuc();
    }

    @Override
    public void xuliHienThiDanhMuc() {
        List<DanhMuc> danhMucs = modelFragmentDanhMuc.getDanhMuc();
        if(danhMucs.size()!=0){
            interfaceViewFragmentDanhMuc.hienThiDanhMuc(danhMucs);
        }
    }
}
