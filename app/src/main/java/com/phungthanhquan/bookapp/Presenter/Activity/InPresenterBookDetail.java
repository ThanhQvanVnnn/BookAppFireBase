package com.phungthanhquan.bookapp.Presenter.Activity;

import android.app.Dialog;

import com.phungthanhquan.bookapp.Object.BinhLuan;

public interface InPresenterBookDetail {
    void xuliHienThiSach(String id);
    void xuliHienThiDsDanhGia(String id);
    void xuliHienThidsSachCungTheLoai(String category_id);
    void xuliThemBinhLuan(BinhLuan binhLuan);
}
