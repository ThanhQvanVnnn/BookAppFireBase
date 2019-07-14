package com.phungthanhquan.bookapp.Presenter.Fragment;

import android.content.Context;

import com.phungthanhquan.bookapp.Model.Fragment.CaNhanModel;
import com.phungthanhquan.bookapp.Model.Fragment.TuSachModel;
import com.phungthanhquan.bookapp.Object.BookCase;
import com.phungthanhquan.bookapp.Object.User;
import com.phungthanhquan.bookapp.View.InterfaceView.InterfaceViewFragmentCaNhan;

import java.util.List;

public class PresenterLogicCaNhan implements PresenterInterfaceCaNhan {
    private InterfaceViewFragmentCaNhan interfaceViewFragmentCaNhan;
    CaNhanModel caNhanModel;

    public PresenterLogicCaNhan(InterfaceViewFragmentCaNhan interfaceViewFragmentCaNhan) {
        this.interfaceViewFragmentCaNhan = interfaceViewFragmentCaNhan;
        caNhanModel = new CaNhanModel();
    }

    @Override
    public void hienThiThongTinCaNhan() {
      caNhanModel.layThongTinUser(new CaNhanModel.callBack() {
          @Override
          public void mycallback(User user) {
              if(user!=null){
                  interfaceViewFragmentCaNhan.hienThiThongTinCaNhan(user);
              }
          }
      });

    }

    @Override
    public void LayTuSach(Context context) {
        caNhanModel.layThongtinBookCase(context, new TuSachModel.TuSachCallback() {
            @Override
            public void myCallBack(List<BookCase> dsBookcase) {
                interfaceViewFragmentCaNhan.hienthithongtintusach(dsBookcase);
            }
        });
    }
}
