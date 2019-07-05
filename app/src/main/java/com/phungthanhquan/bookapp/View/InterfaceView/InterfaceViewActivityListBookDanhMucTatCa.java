package com.phungthanhquan.bookapp.View.InterfaceView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.phungthanhquan.bookapp.Object.Marketing;

import java.util.List;

public interface InterfaceViewActivityListBookDanhMucTatCa {
    void hienthiDanhSachSach(List<Marketing> itemBooks, DocumentSnapshot documentSnapshot);
    void hienthiDanhSachSachLoadMore(List<Marketing> itemBooks, DocumentSnapshot documentSnapshot);
}
