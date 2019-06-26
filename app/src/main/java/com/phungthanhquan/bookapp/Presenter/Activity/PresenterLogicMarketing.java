package com.phungthanhquan.bookapp.Presenter.Activity;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.phungthanhquan.bookapp.Model.Activity.ModelActivityMarketing;
import com.phungthanhquan.bookapp.Object.ItemBook;
import com.phungthanhquan.bookapp.View.InterfaceView.InterfaceViewActivityMarketing;

import java.util.ArrayList;
import java.util.List;

public class PresenterLogicMarketing implements InPresenterMarketing {

    private InterfaceViewActivityMarketing interfaceViewActivityMarketing;
    private ModelActivityMarketing modelActivityMarketing;

    public PresenterLogicMarketing(InterfaceViewActivityMarketing interfaceViewActivityMarketing) {
        this.interfaceViewActivityMarketing = interfaceViewActivityMarketing;
        modelActivityMarketing = new ModelActivityMarketing();
    }

    @Override
    public void xuliHienThiChiTietMarketing() {
        List<ItemBook> itemBooks = modelActivityMarketing.layDanhSachSach();
        if(itemBooks.size()!=0){
            interfaceViewActivityMarketing.hienThidulieu(itemBooks);
        }
    }

    public List<ItemBook> getLoadMore(int soluong, ProgressBar progressBar, RecyclerView recyclerView){
        List<ItemBook> itemBooks = modelActivityMarketing.layDanhSachSach();
        recyclerView.setNestedScrollingEnabled(true);
        progressBar.setVisibility(View.GONE);
        return itemBooks;
    }
}
