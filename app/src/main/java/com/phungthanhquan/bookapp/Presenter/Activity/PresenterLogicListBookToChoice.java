package com.phungthanhquan.bookapp.Presenter.Activity;

import com.phungthanhquan.bookapp.Model.Activity.ModelListBookToChoice;
import com.phungthanhquan.bookapp.Object.Marketing;
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
    public void hienThiDanhSach(Boolean album, String id) {
       modelListBookToChoice.getAlbumList(id, album, new ModelListBookToChoice.Callbacks() {
            @Override
            public void getListAlbum(List<Marketing> album_bookCaseList) {
                if(album_bookCaseList.size()!=0){
                    interfaceViewActivityListBookToChoice.hienThiDanhSach(album_bookCaseList);
                }
            }
        });

    }
}
