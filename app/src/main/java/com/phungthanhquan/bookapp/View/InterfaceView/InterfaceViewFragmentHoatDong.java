package com.phungthanhquan.bookapp.View.InterfaceView;

import com.phungthanhquan.bookapp.Object.BookCase;
import com.phungthanhquan.bookapp.Object.Friend;
import com.phungthanhquan.bookapp.Object.ItemSachBanDangDoc;
import com.phungthanhquan.bookapp.Object.User;

import java.util.List;

public interface InterfaceViewFragmentHoatDong {
    void hienThiSachCuaBan(List<BookCase> itemSachBanDangDocs);
    void laydanhsachban(List<Friend> friendList);
}
