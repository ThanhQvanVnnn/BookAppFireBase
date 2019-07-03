package com.phungthanhquan.bookapp.Presenter.Activity;

import com.phungthanhquan.bookapp.Model.Activity.ModelListBookToChoice;
import com.phungthanhquan.bookapp.View.InterfaceView.InterfaceViewActivityListBookToChoice;

import java.util.List;

public class PresenterLogicListBookToChoice implements InPresenterListBookToChoice {

    private InterfaceViewActivityListBookToChoice interfaceViewActivityListBookToChoice;
    private ModelListBookToChoice modelListBookToChoice;

    public PresenterLogicListBookToChoice(InterfaceViewActivityListBookToChoice interfaceViewActivityListBookToChoice) {
        this.interfaceViewActivityListBookToChoice = interfaceViewActivityListBookToChoice;
        modelListBookToChoice = new ModelListBookToChoice();
    }

    @Override
    public void hienThiDanhSach() {
//        List<ItemBook> list = modelListBookToChoice.getdsSach();
//        if(list.size()!=0){
//            interfaceViewActivityListBookToChoice.hienThiDanhSach(list);
//        }
    }
}
