package com.phungthanhquan.bookapp.View.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.phungthanhquan.bookapp.Adapter.DanhMuc_Adapter;
import com.phungthanhquan.bookapp.Object.DanhMuc;
import com.phungthanhquan.bookapp.Presenter.Fragment.PresenterLogicDanhMuc;
import com.phungthanhquan.bookapp.R;
import com.phungthanhquan.bookapp.View.Activity.MainActivity;
import com.phungthanhquan.bookapp.View.InterfaceView.InterfaceViewFragmentDanhMuc;

import java.util.ArrayList;
import java.util.List;

public class FrgDanhMuc extends Fragment implements InterfaceViewFragmentDanhMuc {

    private RecyclerView recyclerView_dsSach;
    private DanhMuc_Adapter danhMuc_adapter;
    private PresenterLogicDanhMuc presenterLogicDanhMuc;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ConstraintLayout constraintLayoutCheckInterNet;
    private ImageButton checkInternet;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_danhmuc,container,false);
       initControls(view);
        RefresherLayout();
        return view;
    }

    private void initControls(View view) {
        //assign
        recyclerView_dsSach = view.findViewById(R.id.recycle_danhmuc);
        swipeRefreshLayout = view.findViewById(R.id.swiperefresh);
        constraintLayoutCheckInterNet = view.findViewById(R.id.layout_internet_disconnect);
        checkInternet = view.findViewById(R.id.checkInternet);
        presenterLogicDanhMuc = new PresenterLogicDanhMuc(this);
        //init
        presenterLogicDanhMuc.xuliHienThiDanhMuc();
        InternetConnected();
        checkInternet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InternetConnected();
            }
        });
    }

    private void InternetConnected(){
        if(MainActivity.isNetworkConnected(getActivity())){
          recyclerView_dsSach.setVisibility(View.VISIBLE);
            constraintLayoutCheckInterNet.setVisibility(View.GONE);
        }else {
            recyclerView_dsSach.setVisibility(View.GONE);
            constraintLayoutCheckInterNet.setVisibility(View.VISIBLE);
        }
    }

    //refresher layout
    public void RefresherLayout() {
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(android.R.color.holo_blue_dark)
                , getResources().getColor(android.R.color.holo_blue_light)
                , getResources().getColor(android.R.color.holo_orange_light));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                InternetConnected();
                swipeRefreshLayout.setRefreshing(true);
               presenterLogicDanhMuc.xuliHienThiDanhMuc();
               swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void hienThiDanhMuc(List<DanhMuc> danhMucList) {
        List<String> DanhMucName = new ArrayList<>();
        List<String> DanhMucImg  = new ArrayList<>();
        for (int i = 0; i <danhMucList.size() ; i++) {
            DanhMucImg.add("a");
            DanhMucName.add("a");
        }
        danhMuc_adapter = new DanhMuc_Adapter(getContext(),danhMucList,DanhMucImg,DanhMucName);
        recyclerView_dsSach.setAdapter(danhMuc_adapter);
        recyclerView_dsSach.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
    }
}
