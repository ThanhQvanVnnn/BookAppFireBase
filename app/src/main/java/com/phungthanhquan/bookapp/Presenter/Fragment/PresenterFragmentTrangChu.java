package com.phungthanhquan.bookapp.Presenter.Fragment;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.phungthanhquan.bookapp.Model.Fragment.ModelFragmentTrangChu;
import com.phungthanhquan.bookapp.Object.AlbumBook;
import com.phungthanhquan.bookapp.Object.ItemBook;
import com.phungthanhquan.bookapp.Object.NXB;
import com.phungthanhquan.bookapp.Object.Slider;
import com.phungthanhquan.bookapp.View.InterfaceView.InterfaceViewFragmentTrangChu;

import java.util.List;

public class PresenterFragmentTrangChu implements PresenterInterfaceFragmentTrangChu{

    InterfaceViewFragmentTrangChu interfaceViewFragmentTrangChu;
    ModelFragmentTrangChu modelFragmentTrangChu;

    public PresenterFragmentTrangChu(InterfaceViewFragmentTrangChu interfaceViewFragmentTrangChu) {
        this.interfaceViewFragmentTrangChu = interfaceViewFragmentTrangChu;
         this.modelFragmentTrangChu = new ModelFragmentTrangChu();
    }

    @Override
    public void xulislider() {
        List<Slider> sliderList = modelFragmentTrangChu.getDataSlider();
        if(sliderList.size()>0){
            interfaceViewFragmentTrangChu.hienthislider(sliderList);
        }
    }

    @Override
    public void xuliHienthiDsSachMoi() {
        List<ItemBook> dsSachMoi = modelFragmentTrangChu.getDataDsSachMoi();
        if(dsSachMoi.size()>0){
            interfaceViewFragmentTrangChu.hienthidsSachmoi(dsSachMoi);
        }
    }

    @Override
    public void xuliHienthiDsSachKhuyenDoc() {
        List<ItemBook> dsSachKhuyenDoc = modelFragmentTrangChu.getDataDsSachKhuyenDoc();
        if(dsSachKhuyenDoc.size()>0){
            interfaceViewFragmentTrangChu.hienthidsSachKhuyenDoc(dsSachKhuyenDoc);
        }
    }
    public List<ItemBook> xuliHienThiDsKhuyenDocLoadMore(int tongItem, ProgressBar progressBar, RecyclerView recyclerView){
        List<ItemBook> dsSachKhuyenDocLoadMore = modelFragmentTrangChu.getDataDsSachKhuyenDoc();
        if(dsSachKhuyenDocLoadMore.size()==0) {
            recyclerView.setNestedScrollingEnabled(true);
            progressBar.setVisibility(View.INVISIBLE);
        }
        return dsSachKhuyenDocLoadMore;
    }

    @Override
    public void xuliHienthiDsSachVanHocTrongNuoc() {
        List<ItemBook> dsSachVanHocTrongNuoc = modelFragmentTrangChu.getDataDsSachVanHocTrongNuoc();
        if(dsSachVanHocTrongNuoc.size()>0){
            interfaceViewFragmentTrangChu.hienthidsSachVanHocTrongNuoc(dsSachVanHocTrongNuoc);
        }
    }

    @Override
    public void xuliHienThiDsNhaXuatBan() {
        List<NXB> dsNXB = modelFragmentTrangChu.getDataDsNhaXuatBan();
        if(dsNXB.size()>0){
            interfaceViewFragmentTrangChu.hienthidsNhaXuatBan(dsNXB);
        }
    }

    @Override
    public void xuliHienThiAlBumSach() {
        List<AlbumBook> dsAlbum = modelFragmentTrangChu.getDataDsAlBum();
        if (dsAlbum.size()>0){
            interfaceViewFragmentTrangChu.hienthiAlbumSach(dsAlbum);
        }
    }
}
