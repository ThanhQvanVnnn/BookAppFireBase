package com.phungthanhquan.bookapp.View.InterfaceView;

import com.phungthanhquan.bookapp.Object.BookCase;
import com.phungthanhquan.bookapp.Object.UserRent;

import java.util.List;

public interface InterfaceViewFragmentTuSach {
    void hienthiDsSach(List<BookCase> itemBookCases);
    void layDanhSachUserRent(List<UserRent> userRentList);
}
