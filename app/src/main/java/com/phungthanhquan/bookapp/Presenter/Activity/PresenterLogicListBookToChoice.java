package com.phungthanhquan.bookapp.Presenter.Activity;

import com.google.firebase.firestore.DocumentSnapshot;
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
            public void getListAlbum(List<Marketing> album_bookCaseList,DocumentSnapshot documentSnapshot) {
                if(album_bookCaseList.size()!=0){
                    interfaceViewActivityListBookToChoice.hienThiDanhSach(album_bookCaseList,documentSnapshot);
                }
            }
        });

    }

    @Override
    public void hienThiDanhSachLoadMore(String id, final Boolean album, DocumentSnapshot documentSnapshot) {
        modelListBookToChoice.getAlbumListLoadMore(id, album, new ModelListBookToChoice.Callbacks() {
            @Override
            public void getListAlbum(List<Marketing> bookCaseList, DocumentSnapshot documentSnapshot) {
                if(bookCaseList.size()!=0){
                    interfaceViewActivityListBookToChoice.hienThiDanhSachLoadMore(bookCaseList,documentSnapshot);
                }
            }
        },documentSnapshot);
    }
}
