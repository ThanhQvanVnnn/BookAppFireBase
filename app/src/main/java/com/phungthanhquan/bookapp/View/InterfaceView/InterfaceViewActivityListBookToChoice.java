package com.phungthanhquan.bookapp.View.InterfaceView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.phungthanhquan.bookapp.Object.Album_BookCase;
import com.phungthanhquan.bookapp.Object.Marketing;

import java.util.List;

public interface InterfaceViewActivityListBookToChoice {
    void hienThiDanhSach(List<Marketing> itemBookList, DocumentSnapshot documentSnapshot);
    void hienThiDanhSachLoadMore(List<Marketing> itemBookList, DocumentSnapshot documentSnapshot);
}
