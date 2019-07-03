package com.phungthanhquan.bookapp.Presenter.Fragment;

import com.phungthanhquan.bookapp.Model.Fragment.TrangChuModel;
import com.phungthanhquan.bookapp.Object.Album;
import com.phungthanhquan.bookapp.Object.Album_BookCase;
import com.phungthanhquan.bookapp.Object.Marketing;
import com.phungthanhquan.bookapp.Object.NXB;
import com.phungthanhquan.bookapp.View.InterfaceView.InterfaceViewFragmentTrangChu;

import java.util.List;

public class PresenterTrangChu implements PresenterInterfaceTrangChu {

    InterfaceViewFragmentTrangChu interfaceViewFragmentTrangChu;
    TrangChuModel trangChuModel;

    public PresenterTrangChu(InterfaceViewFragmentTrangChu interfaceViewFragmentTrangChu) {
        this.interfaceViewFragmentTrangChu = interfaceViewFragmentTrangChu;
         this.trangChuModel = new TrangChuModel();
    }

    @Override
    public void xulislider() {

        trangChuModel.getSlider(new TrangChuModel.CallBackSlider() {
            @Override
            public void onCallbackSlider(List<Marketing> marketingList) {
                if(marketingList.size()>0){
                    interfaceViewFragmentTrangChu.hienthislider(marketingList);
                }
            }
        });

    }

    @Override
    public void xuliHienthiDsSachMoi() {
        trangChuModel.getSachMoi(new TrangChuModel.CallBackSlider() {
            @Override
            public void onCallbackSlider(List<Marketing> marketingList) {
                if(marketingList.size()>0){
                    interfaceViewFragmentTrangChu.hienthidsSachmoi(marketingList);
                }
            }
        });

    }

//    @Override
//    public void xuliHienthiDsSachKhuyenDoc() {
//        List<ItemBook> dsSachKhuyenDoc = trangChuModel.getDataDsSachKhuyenDoc();
//        if(dsSachKhuyenDoc.size()>0){
//            interfaceViewFragmentTrangChu.hienthidsSachKhuyenDoc(dsSachKhuyenDoc);
//        }
//    }
//    public List<ItemBook> xuliHienThiDsKhuyenDocLoadMore(int tongItem, ProgressBar progressBar, RecyclerView recyclerView){
//        List<ItemBook> dsSachKhuyenDocLoadMore = trangChuModel.getDataDsSachKhuyenDoc();
//        if(dsSachKhuyenDocLoadMore.size()==0) {
//            recyclerView.setNestedScrollingEnabled(true);
//            progressBar.setVisibility(View.INVISIBLE);
//        }
//        return dsSachKhuyenDocLoadMore;
//    }
//
    @Override
    public void xuliHienthiDsSachVanHocTrongNuoc() {
        trangChuModel.getSachVHTN(new TrangChuModel.CallBackSlider() {
            @Override
            public void onCallbackSlider(List<Marketing> marketingList) {
                if(marketingList.size()>0){
                    interfaceViewFragmentTrangChu.hienthidsSachVanHocTrongNuoc(marketingList);
                }
            }
        });
    }

    @Override
    public void xuliHienThiDsNhaXuatBan() {
        trangChuModel.getNXB(new TrangChuModel.CallBackNXB() {
            @Override
            public void onCallbackAlbum(List<NXB> NXBList) {
                if(NXBList.size()>0){
                    interfaceViewFragmentTrangChu.hienthidsNhaXuatBan(NXBList);
                }
            }
        });

    }

    @Override
    public void xuliHienThiAlBumSach() {
        trangChuModel.getAlbum(new TrangChuModel.CallBackAlbum() {
            @Override
            public void onCallbackAlbum(List<Album> albumBookCaseList) {
                if(albumBookCaseList.size()>0){
                    interfaceViewFragmentTrangChu.hienthiAlbumSach(albumBookCaseList);
                }
            }
        });
    }
}
