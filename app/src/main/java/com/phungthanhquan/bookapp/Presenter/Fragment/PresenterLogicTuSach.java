package com.phungthanhquan.bookapp.Presenter.Fragment;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.phungthanhquan.bookapp.Model.Fragment.ModelFragmentTuSach;
import com.phungthanhquan.bookapp.Object.ItemBookCase;
import com.phungthanhquan.bookapp.View.InterfaceView.InterfaceViewFragmentTuSach;

import java.util.List;

public class PresenterLogicTuSach implements PresenterInterfaceTuSach {
    private InterfaceViewFragmentTuSach interfaceViewFragmentTuSach;
    private ModelFragmentTuSach modelFragmentTuSach;

    public PresenterLogicTuSach(InterfaceViewFragmentTuSach interfaceViewFragmentTuSach) {
        this.interfaceViewFragmentTuSach = interfaceViewFragmentTuSach;
        modelFragmentTuSach = new ModelFragmentTuSach();
    }

    @Override
    public void xulihienthiDSCuaTuSach() {
        List<ItemBookCase> itemBookCases = modelFragmentTuSach.layDsSachTrongTuSach();
        if(itemBookCases.size()!=0){
            interfaceViewFragmentTuSach.hienthiDsSach(itemBookCases);
        }
    }
    public List<ItemBookCase> xuliLoadMore(int soluong, ProgressBar progressBar, RecyclerView recyclerView){
        List<ItemBookCase> itemBookCases = modelFragmentTuSach.layDsSachTrongTuSach();
            recyclerView.setNestedScrollingEnabled(true);
            progressBar.setVisibility(View.GONE);
        return itemBookCases;
    }
}
