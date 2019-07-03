package com.phungthanhquan.bookapp.Presenter.Activity;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.phungthanhquan.bookapp.Model.Activity.ModelActivityListDanhMucTatCa;
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
    public void xuliHienThiChiTietDanhMuc() {
//        List<ItemBook> ds = modelActivityListDanhMucTatCa.layDanhSachSach();
//        if (ds.size()!=0){
//            interfaceViewActivityListBookDanhMucTatCa.hienthiDanhSachChitiet(ds);
//        }
    }
//    public List<ItemBook> getLoadMore(int soluong, ProgressBar progressBar, RecyclerView recyclerView){
//        List<ItemBook> itemBooks = modelActivityListDanhMucTatCa.layDanhSachSach();
//        recyclerView.setNestedScrollingEnabled(true);
//        progressBar.setVisibility(View.GONE);
//        return itemBooks;
//    }
}
