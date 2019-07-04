package com.phungthanhquan.bookapp.View.Fragment;

import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.phungthanhquan.bookapp.Adapter.Tusach_Adapter;
import com.phungthanhquan.bookapp.Model.LoadMore.InterfaceLoadMore;
import com.phungthanhquan.bookapp.Model.LoadMore.LoadMoreScroll;
import com.phungthanhquan.bookapp.Presenter.Fragment.PresenterLogicTuSach;
import com.phungthanhquan.bookapp.R;
import com.phungthanhquan.bookapp.View.InterfaceView.InterfaceViewFragmentTuSach;

import java.util.ArrayList;
import java.util.List;

public class FrgTuSach extends Fragment implements InterfaceViewFragmentTuSach {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private Tusach_Adapter tusach_adapter;
//    private List<ItemBookCase> itemBookCaseList;
    private PresenterLogicTuSach presenterLogicTuSach;
    private LoadMoreScroll loadMoreScroll;
    private ProgressBar progressBarLoadMore;
    Toast toast;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tusach,container,false);
        initControls(view);
        refresherData();
        return view;
    }

    private void initControls(View view) {
        recyclerView = view.findViewById(R.id.recycle_tusach);
        swipeRefreshLayout = view.findViewById(R.id.refresh_tusach);
        progressBarLoadMore = view.findViewById(R.id.loadmoreProgress);
//        itemBookCaseList = new ArrayList<>();
//        tusach_adapter = new Tusach_Adapter(getContext(),itemBookCaseList);
        recyclerView.setAdapter(tusach_adapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),3);
        recyclerView.setLayoutManager(gridLayoutManager);
//        loadMoreScroll = new LoadMoreScroll(gridLayoutManager,this,9);
        recyclerView.addOnScrollListener(loadMoreScroll);
        presenterLogicTuSach = new PresenterLogicTuSach(this);
        presenterLogicTuSach.xulihienthiDSCuaTuSach();
    }

//    @Override
//    public void hienthiDsSach(List<ItemBookCase> itemBookCases) {
//        itemBookCaseList.addAll(itemBookCases);
//        tusach_adapter.notifyDataSetChanged();
//    }
    public void refresherData(){
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary)
                ,getResources().getColor(R.color.colorPrimary)
        ,getResources().getColor(R.color.colorAccent));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                    itemBookCaseList.clear();
                    presenterLogicTuSach.xulihienthiDSCuaTuSach();
                    swipeRefreshLayout.setRefreshing(false);
                    }
                },1000);
            }
        });
    }

//    @Override
//    public void hienThiLoadMore(final int tongItem) {
//        recyclerView.setNestedScrollingEnabled(false);
//        progressBarLoadMore.setVisibility(View.VISIBLE);
////        List<ItemBookCase> dsSachLayVe = presenterLogicTuSach.xuliLoadMore(tongItem,progressBarLoadMore,recyclerView);
////        itemBookCaseList.addAll(dsSachLayVe);
//        tusach_adapter.notifyDataSetChanged();
//    }

    @Override
    public void onResume() {
        super.onResume();
//        itemBookCaseList.clear();
        presenterLogicTuSach.xulihienthiDSCuaTuSach();
    }
    public void showAToast (String st){ //"Toast toast" is declared in the class
        try{ toast.getView().isShown();     // true if visible
            toast.setText(st);
        } catch (Exception e) {         // invisible if exception
            toast = Toast.makeText(getContext(), st,  Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
        }
        toast.show();  //finally display it
    }
}
