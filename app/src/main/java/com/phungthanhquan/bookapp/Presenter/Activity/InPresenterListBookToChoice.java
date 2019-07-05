package com.phungthanhquan.bookapp.Presenter.Activity;

import com.google.firebase.firestore.DocumentSnapshot;

public interface InPresenterListBookToChoice {
    void hienThiDanhSach(Boolean album, String id);
    void hienThiDanhSachLoadMore(String id,Boolean album,DocumentSnapshot documentSnapshot);
}
