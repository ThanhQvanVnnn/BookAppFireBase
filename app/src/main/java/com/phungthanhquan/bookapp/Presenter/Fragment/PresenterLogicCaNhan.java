package com.phungthanhquan.bookapp.Presenter.Fragment;

import com.phungthanhquan.bookapp.Model.Fragment.ModelFragmentCaNhan;
import com.phungthanhquan.bookapp.Object.User;
import com.phungthanhquan.bookapp.View.InterfaceView.InterfaceViewFragmentCaNhan;

public class PresenterLogicCaNhan implements PresenterInterfaceCaNhan {
    private InterfaceViewFragmentCaNhan interfaceViewFragmentCaNhan;
    ModelFragmentCaNhan modelFragmentCaNhan;

    public PresenterLogicCaNhan(InterfaceViewFragmentCaNhan interfaceViewFragmentCaNhan) {
        this.interfaceViewFragmentCaNhan = interfaceViewFragmentCaNhan;
        modelFragmentCaNhan = new ModelFragmentCaNhan();
    }

    @Override
    public void hienThiThongTinCaNhan() {
        User user = modelFragmentCaNhan.layDsUser();
        if(user!=null){
            interfaceViewFragmentCaNhan.hienThiThongTinCaNhan(user);
        }
    }
}
