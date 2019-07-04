package com.phungthanhquan.bookapp.Presenter.Fragment;

import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.firebase.firestore.DocumentSnapshot;
import com.phungthanhquan.bookapp.Model.Fragment.TrangChuModel;
import com.phungthanhquan.bookapp.Object.Album;
import com.phungthanhquan.bookapp.Object.Album_BookCase;
import com.phungthanhquan.bookapp.Object.Marketing;
import com.phungthanhquan.bookapp.Object.NXB;
import com.phungthanhquan.bookapp.View.InterfaceView.InterfaceViewFragmentTrangChu;

import java.util.ArrayList;
import java.util.List;

public class PresenterTrangChu implements PresenterInterfaceTrangChu {

    InterfaceViewFragmentTrangChu interfaceViewFragmentTrangChu;
    TrangChuModel trangChuModel;

    public PresenterTrangChu(InterfaceViewFragmentTrangChu interfaceViewFragmentTrangChu) {
        this.interfaceViewFragmentTrangChu = interfaceViewFragmentTrangChu;
         this.trangChuModel = new TrangChuModel();
    }

    @Override
    public void xulislider(final SwipeRefreshLayout swipeRefreshLayout) {

        trangChuModel.getSlider(new TrangChuModel.CallBackSlider() {
            @Override
            public void onCallbackSlider(List<Marketing> marketingList) {
                if(marketingList.size()>0){
                    interfaceViewFragmentTrangChu.hienthislider(marketingList);
                    swipeRefreshLayout.setRefreshing(false);
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

    @Override
    public void xuliHienthiDsSachKhuyenDoc() {
        trangChuModel.getSachKhuyenDoc(new TrangChuModel.CallBackKhuyenDoc() {
            @Override
            public void onCallbackSlider(List<Marketing> marketingList, DocumentSnapshot documentSnapshot) {
                if(marketingList.size()>0){
                    interfaceViewFragmentTrangChu.hienthidsSachKhuyenDoc(marketingList,documentSnapshot);
                }
            }
        });

    }
    public void xuliHienThiDsKhuyenDocLoadMore(DocumentSnapshot documentSnapshot){
       trangChuModel.getSachKhuyenDocLoadMore(documentSnapshot, new TrangChuModel.CallBackKhuyenDoc() {
           @Override
           public void onCallbackSlider(List<Marketing> marketingList, DocumentSnapshot documentSnapshots) {
                   interfaceViewFragmentTrangChu.hienthiloadmoreKhuyenDoc(marketingList,documentSnapshots);
           }

       });

    }
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
