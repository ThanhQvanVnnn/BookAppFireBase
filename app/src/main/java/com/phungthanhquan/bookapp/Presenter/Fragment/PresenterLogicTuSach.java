package com.phungthanhquan.bookapp.Presenter.Fragment;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;

import com.phungthanhquan.bookapp.Model.Fragment.TuSachModel;
import com.phungthanhquan.bookapp.Object.BookCase;
import com.phungthanhquan.bookapp.Object.UserRent;
import com.phungthanhquan.bookapp.View.InterfaceView.InterfaceViewFragmentTuSach;

import java.util.List;

public class PresenterLogicTuSach implements PresenterInterfaceTuSach {
    private InterfaceViewFragmentTuSach interfaceViewFragmentTuSach;
    private TuSachModel tuSachModel;
    private Context context;

    public PresenterLogicTuSach(Context context,InterfaceViewFragmentTuSach interfaceViewFragmentTuSach) {
        this.interfaceViewFragmentTuSach = interfaceViewFragmentTuSach;
        this.context = context;
        tuSachModel = new TuSachModel(context);
    }

    @Override
    public void xulihienthiDSCuaTuSach() {
        tuSachModel.getBookCase(new TuSachModel.TuSachCallback() {
            @Override
            public void myCallBack(List<BookCase> dsBookcase) {
                interfaceViewFragmentTuSach.hienthiDsSach(dsBookcase);
            }
        });
    }
    public void layDsUserRent(){
        tuSachModel.getUserRent(new TuSachModel.UserRentCallback() {
            @Override
            public void myCallBack(List<UserRent> userRents) {
                interfaceViewFragmentTuSach.layDanhSachUserRent(userRents);
            }
        });
    }
//    public List<ItemBookCase> xuliLoadMore(int soluong, ProgressBar progressBar, RecyclerView recyclerView){
//        List<ItemBookCase> itemBookCases = tuSachModel.layDsSachTrongTuSach();
//            recyclerView.setNestedScrollingEnabled(true);
//            progressBar.setVisibility(View.GONE);
//        return itemBookCases;
//    }
}
