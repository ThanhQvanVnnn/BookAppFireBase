package com.phungthanhquan.bookapp.Presenter.Activity;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.firebase.firestore.DocumentSnapshot;

public interface InPresenterListDanhMucTatCa {
    void xuliHienThiChiTietDanhMuc(String id, SwipeRefreshLayout swipeRefreshLayout);
    void xuliHienThiChiTietDanhMucLoadMore(String id, DocumentSnapshot documentSnapshot);
}
