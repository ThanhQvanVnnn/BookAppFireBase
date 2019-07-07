package com.phungthanhquan.bookapp.View.InterfaceView;

import com.phungthanhquan.bookapp.Object.BinhLuan;
import com.phungthanhquan.bookapp.Object.Book;
import com.phungthanhquan.bookapp.Object.Marketing;

import java.util.List;

public interface InterfaceViewActivityDetailBook {
    void hienThiNoiDungSach(Book book);
    void hienThiDsDanhGia(List<BinhLuan> dsDanhGia);
    void hienThiDsSachCungTheLoai(List<Marketing> dsSach);
    void hienThiThemBinhLuan();
}
