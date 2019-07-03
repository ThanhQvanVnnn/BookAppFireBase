package com.phungthanhquan.bookapp.Presenter.Activity;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.phungthanhquan.bookapp.Model.Activity.MarketingModel;
import com.phungthanhquan.bookapp.View.InterfaceView.InterfaceViewActivityMarketing;

import java.util.List;

public class PresenterLogicMarketing implements InPresenterMarketing {

    private InterfaceViewActivityMarketing interfaceViewActivityMarketing;
    private MarketingModel marketingModel;

    public PresenterLogicMarketing(InterfaceViewActivityMarketing interfaceViewActivityMarketing) {
        this.interfaceViewActivityMarketing = interfaceViewActivityMarketing;
        marketingModel = new MarketingModel();
    }

    @Override
    public void xuliHienThiChiTietMarketing() {
//        List<ItemBook> itemBooks = marketingModel.layDanhSachSach();
//        if(itemBooks.size()!=0){
//            interfaceViewActivityMarketing.hienThidulieu(itemBooks);
//        }
    }

//    public List<ItemBook> getLoadMore(int soluong, ProgressBar progressBar, RecyclerView recyclerView){
//        List<ItemBook> itemBooks = marketingModel.layDanhSachSach();
//        recyclerView.setNestedScrollingEnabled(true);
//        progressBar.setVisibility(View.GONE);
//        return itemBooks;
//    }
}
