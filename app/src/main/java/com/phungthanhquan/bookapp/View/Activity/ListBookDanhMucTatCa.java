package com.phungthanhquan.bookapp.View.Activity;

import android.content.Intent;
import android.os.Build;


import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.firestore.DocumentSnapshot;
import com.phungthanhquan.bookapp.Adapter.Album_NXB_Adapter;
import com.phungthanhquan.bookapp.Model.LoadMore.InterfaceLoadMore;
import com.phungthanhquan.bookapp.Model.LoadMore.LoadMoreScroll;
import com.phungthanhquan.bookapp.Object.Marketing;
import com.phungthanhquan.bookapp.Presenter.Activity.PresenterLogicListDanhMucTatCa;
import com.phungthanhquan.bookapp.R;
import com.phungthanhquan.bookapp.View.InterfaceView.InterfaceViewActivityListBookDanhMucTatCa;

import java.util.ArrayList;
import java.util.List;

public class ListBookDanhMucTatCa extends AppCompatActivity implements InterfaceViewActivityListBookDanhMucTatCa, InterfaceLoadMore {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private Album_NXB_Adapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<Marketing> itemBookList;
    private ProgressBar progressBar;
    private PresenterLogicListDanhMucTatCa presenterLogicListDanhMucTatCa;
    private LoadMoreScroll loadMoreScroll;
    private DocumentSnapshot lastDocument;
    String ID;
    String NAME;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_book_danh_muc_tat_ca);
        initControls();
        refresh();
    }

    private void initControls() {
        Intent intent = getIntent();
        toolbar = findViewById(R.id.toolbar);
        progressBar = findViewById(R.id.progressDanhMucTatCa);
        recyclerView = findViewById(R.id.recycle_danhmucchitiet);
        swipeRefreshLayout = findViewById(R.id.refresh_danhmuc);
        NAME = intent.getStringExtra("tenDanhMuc");
        ID = intent.getStringExtra("idDanhMuc");
        lastDocument = null;
         toolbar.setTitle(NAME);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            toolbar.setTitleTextColor(getColor(R.color.white));
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        itemBookList = new ArrayList<>();
        adapter = new Album_NXB_Adapter(this,itemBookList);
        recyclerView.setAdapter(adapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(gridLayoutManager);
        presenterLogicListDanhMucTatCa = new PresenterLogicListDanhMucTatCa(this);
        presenterLogicListDanhMucTatCa.xuliHienThiChiTietDanhMuc(ID, swipeRefreshLayout);
        loadMoreScroll = new LoadMoreScroll(gridLayoutManager,this,15,lastDocument);
        recyclerView.addOnScrollListener(loadMoreScroll);
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
    public void refresh(){
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(android.R.color.holo_blue_dark)
                , getResources().getColor(android.R.color.holo_blue_light)
                , getResources().getColor(android.R.color.holo_orange_light));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                itemBookList.clear();
                presenterLogicListDanhMucTatCa.xuliHienThiChiTietDanhMuc(ID,swipeRefreshLayout);
            }
        });
    }


    @Override
    public void hienThiLoadMore() {
        presenterLogicListDanhMucTatCa.xuliHienThiChiTietDanhMucLoadMore(ID,lastDocument);
    }


    @Override
    public void hienthiDanhSachSach(List<Marketing> itemBooks, DocumentSnapshot documentSnapshot) {
        itemBookList.addAll(itemBooks);
        adapter.addMoreImage();
        adapter.notifyDataSetChanged();
        lastDocument = documentSnapshot;
    }

    @Override
    public void hienthiDanhSachSachLoadMore(List<Marketing> itemBooks, DocumentSnapshot documentSnapshot) {
        if(itemBooks.size()<15){
            progressBar.setVisibility(View.INVISIBLE);
        }
        itemBookList.addAll(itemBooks);
        adapter.addMoreImage();
        adapter.notifyDataSetChanged();
        lastDocument = documentSnapshot;
    }
}
