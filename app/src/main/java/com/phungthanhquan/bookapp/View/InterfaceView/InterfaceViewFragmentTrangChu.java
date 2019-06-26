package com.phungthanhquan.bookapp.View.InterfaceView;

import com.phungthanhquan.bookapp.Object.AlbumBook;
import com.phungthanhquan.bookapp.Object.ItemBook;
import com.phungthanhquan.bookapp.Object.NXB;
import com.phungthanhquan.bookapp.Object.Slider;

import java.util.List;

public interface InterfaceViewFragmentTrangChu {
    void hienthislider(List<Slider> sliderList);
    void hienthidsSachmoi(List<ItemBook> dsSachMoi);
    void hienthidsSachKhuyenDoc(List<ItemBook> dsSachKhuyenDoc);
    void hienthidsSachVanHocTrongNuoc(List<ItemBook> dsSachVanHocTrongNuoc);
    void hienthidsNhaXuatBan(List<NXB> dsNXB);
    void hienthiAlbumSach(List<AlbumBook> albumBooks);
}
