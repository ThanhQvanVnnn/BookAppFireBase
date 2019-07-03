package com.phungthanhquan.bookapp.Presenter.Fragment;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.phungthanhquan.bookapp.Model.Fragment.TuSachModel;
import com.phungthanhquan.bookapp.View.InterfaceView.InterfaceViewFragmentTuSach;

import java.util.List;

public class PresenterLogicTuSach implements PresenterInterfaceTuSach {
    private InterfaceViewFragmentTuSach interfaceViewFragmentTuSach;
    private TuSachModel tuSachModel;

    public PresenterLogicTuSach(InterfaceViewFragmentTuSach interfaceViewFragmentTuSach) {
        this.interfaceViewFragmentTuSach = interfaceViewFragmentTuSach;
        tuSachModel = new TuSachModel();
    }

    @Override
    public void xulihienthiDSCuaTuSach() {
//        List<ItemBookCase> itemBookCases = tuSachModel.layDsSachTrongTuSach();
//        if(itemBookCases.size()!=0){
//            interfaceViewFragmentTuSach.hienthiDsSach(itemBookCases);
//        }
    }
//    public List<ItemBookCase> xuliLoadMore(int soluong, ProgressBar progressBar, RecyclerView recyclerView){
//        List<ItemBookCase> itemBookCases = tuSachModel.layDsSachTrongTuSach();
//            recyclerView.setNestedScrollingEnabled(true);
//            progressBar.setVisibility(View.GONE);
//        return itemBookCases;
//    }
}
