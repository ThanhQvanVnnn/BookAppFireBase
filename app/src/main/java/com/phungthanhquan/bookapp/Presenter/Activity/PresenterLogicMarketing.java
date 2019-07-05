package com.phungthanhquan.bookapp.Presenter.Activity;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.firestore.DocumentSnapshot;
import com.phungthanhquan.bookapp.Model.Activity.MarketingModel;
import com.phungthanhquan.bookapp.Object.Marketing;
import com.phungthanhquan.bookapp.View.InterfaceView.InterfaceViewActivityMarketing;

import java.util.List;

public class PresenterLogicMarketing implements InPresenterMarketing {

    private InterfaceViewActivityMarketing interfaceViewActivityMarketing;
    private MarketingModel marketingModel;

    public PresenterLogicMarketing(InterfaceViewActivityMarketing interfaceViewActivityMarketing, String ID) {
        this.interfaceViewActivityMarketing = interfaceViewActivityMarketing;
        marketingModel = new MarketingModel(ID);
    }

    @Override
    public void xuliHienThiChiTietMarketing(final SwipeRefreshLayout swipeRefreshLayout) {

        marketingModel.getBookList(new MarketingModel.CallBacks() {
            @Override
            public void GetListBook(List<Marketing> marketingList, DocumentSnapshot documentSnapshot) {
                if(marketingList.size()>0){
                    interfaceViewActivityMarketing.hienThidulieu(marketingList,documentSnapshot);
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

        });

    }

    public void getLoadMore(DocumentSnapshot documentSnapshot){
         marketingModel.getBookListLoadMore(documentSnapshot, new MarketingModel.CallBacks() {
            @Override
            public void GetListBook(List<Marketing> marketingList, DocumentSnapshot documentSnapshot) {
                 interfaceViewActivityMarketing.hienThiDuLieuLoadMore(marketingList,documentSnapshot);
            }
        });

    }
}
