package com.phungthanhquan.bookapp.Presenter.Activity;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.firestore.DocumentSnapshot;
import com.phungthanhquan.bookapp.Model.Activity.ModelActivityListDanhMucTatCa;
import com.phungthanhquan.bookapp.Object.Marketing;
import com.phungthanhquan.bookapp.View.InterfaceView.InterfaceViewActivityListBookDanhMucTatCa;

import java.util.List;

public class PresenterLogicListDanhMucTatCa implements InPresenterListDanhMucTatCa {

    private InterfaceViewActivityListBookDanhMucTatCa interfaceViewActivityListBookDanhMucTatCa;
    private ModelActivityListDanhMucTatCa modelActivityListDanhMucTatCa;

    public PresenterLogicListDanhMucTatCa(InterfaceViewActivityListBookDanhMucTatCa interfaceViewActivityListBookDanhMucTatCa) {
        this.interfaceViewActivityListBookDanhMucTatCa = interfaceViewActivityListBookDanhMucTatCa;
        modelActivityListDanhMucTatCa = new ModelActivityListDanhMucTatCa();
    }


    @Override
    public void xuliHienThiChiTietDanhMuc(String id, final SwipeRefreshLayout swipeRefreshLayout) {
        modelActivityListDanhMucTatCa.getDanhSach(id, new ModelActivityListDanhMucTatCa.CallBackss() {
            @Override
            public void myCallBack(List<Marketing> marketingList, DocumentSnapshot documentSnapshot) {
                    interfaceViewActivityListBookDanhMucTatCa.hienthiDanhSachSach(marketingList,documentSnapshot);
                    swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void xuliHienThiChiTietDanhMucLoadMore(String id, DocumentSnapshot documentSnapshot) {
        modelActivityListDanhMucTatCa.getDanhSachLoadMore(id, documentSnapshot, new ModelActivityListDanhMucTatCa.CallBackss() {
            @Override
            public void myCallBack(List<Marketing> marketingList, DocumentSnapshot documentSnapshot) {
                interfaceViewActivityListBookDanhMucTatCa.hienthiDanhSachSachLoadMore(marketingList,documentSnapshot);
            }
        });
    }
}
