package com.phungthanhquan.bookapp.View.InterfaceView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.phungthanhquan.bookapp.Object.Marketing;

import java.util.List;

public interface InterfaceViewActivityMarketing {
    void hienThidulieu(List<Marketing> itemBookList, DocumentSnapshot documentSnapshot);
    void hienThiDuLieuLoadMore(List<Marketing> itemBookList, DocumentSnapshot documentSnapshot);
}
