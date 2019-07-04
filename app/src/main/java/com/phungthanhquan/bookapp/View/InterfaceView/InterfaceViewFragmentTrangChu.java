package com.phungthanhquan.bookapp.View.InterfaceView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.phungthanhquan.bookapp.Object.Album;
import com.phungthanhquan.bookapp.Object.Album_BookCase;
import com.phungthanhquan.bookapp.Object.Marketing;
import com.phungthanhquan.bookapp.Object.NXB;

import java.util.List;

public interface InterfaceViewFragmentTrangChu {
    void hienthislider(List<Marketing> sliderList);
    void hienthidsSachmoi(List<Marketing> dsSachMoi);
    void hienthidsSachKhuyenDoc(List<Marketing> dsSachKhuyenDoc, DocumentSnapshot documentSnapshot);
    void hienthiloadmoreKhuyenDoc(List<Marketing> dsSachKhuyenDoc, DocumentSnapshot documentSnapshot);
    void hienthidsSachVanHocTrongNuoc(List<Marketing> dsSachVanHocTrongNuoc);
    void hienthidsNhaXuatBan(List<NXB> dsNXB);
    void hienthiAlbumSach(List<Album> albumBookCases);
}
