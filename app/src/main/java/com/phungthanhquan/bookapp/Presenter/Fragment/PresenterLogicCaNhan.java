package com.phungthanhquan.bookapp.Presenter.Fragment;

import com.phungthanhquan.bookapp.Model.Fragment.CaNhanModel;
import com.phungthanhquan.bookapp.Object.User;
import com.phungthanhquan.bookapp.View.InterfaceView.InterfaceViewFragmentCaNhan;

public class PresenterLogicCaNhan implements PresenterInterfaceCaNhan {
    private InterfaceViewFragmentCaNhan interfaceViewFragmentCaNhan;
    CaNhanModel caNhanModel;

    public PresenterLogicCaNhan(InterfaceViewFragmentCaNhan interfaceViewFragmentCaNhan) {
        this.interfaceViewFragmentCaNhan = interfaceViewFragmentCaNhan;
        caNhanModel = new CaNhanModel();
    }

    @Override
    public void hienThiThongTinCaNhan() {
        User user = caNhanModel.layDsUser();
        if(user!=null){
            interfaceViewFragmentCaNhan.hienThiThongTinCaNhan(user);
        }
    }
}
