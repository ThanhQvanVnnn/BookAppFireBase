package com.phungthanhquan.bookapp.View.Activity;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.phungthanhquan.bookapp.Adapter.RecycleView_ItemBook_Adapter;
import com.phungthanhquan.bookapp.Model.LoadMore.InterfaceLoadMore;
import com.phungthanhquan.bookapp.Model.LoadMore.LoadMoreScroll;
import com.phungthanhquan.bookapp.Object.ItemBook;
import com.phungthanhquan.bookapp.Presenter.Activity.PresenterLogicListDanhMucTatCa;
import com.phungthanhquan.bookapp.R;
import com.phungthanhquan.bookapp.View.InterfaceView.InterfaceViewActivityListBookDanhMucTatCa;

import java.util.ArrayList;
import java.util.List;

public class ListBookDanhMucTatCa extends AppCompatActivity implements InterfaceViewActivityListBookDanhMucTatCa, InterfaceLoadMore {

    private Toolbar toolbar;
    private RecyclerView recyclerView_ds;
    private RecycleView_ItemBook_Adapter recycleView_itemBook_adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<ItemBook> itemBookList;
    private ProgressBar progressBar;
    private PresenterLogicListDanhMucTatCa presenterLogicListDanhMucTatCa;
    private LoadMoreScroll loadMoreScroll;


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
        recyclerView_ds = findViewById(R.id.recycle_danhmucchitiet);
        swipeRefreshLayout = findViewById(R.id.refresh_danhmuc);
        String danhmuc = intent.getStringExtra("tenDanhMuc");
         toolbar.setTitle(danhmuc);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            toolbar.setTitleTextColor(getColor(R.color.white));
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        itemBookList = new ArrayList<>();
        recycleView_itemBook_adapter = new RecycleView_ItemBook_Adapter(this,itemBookList,0);
        recyclerView_ds.setAdapter(recycleView_itemBook_adapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3);
        recyclerView_ds.setLayoutManager(gridLayoutManager);
        loadMoreScroll = new LoadMoreScroll(gridLayoutManager,this,12);
        recyclerView_ds.addOnScrollListener(loadMoreScroll);
        presenterLogicListDanhMucTatCa = new PresenterLogicListDanhMucTatCa(this);
        presenterLogicListDanhMucTatCa.xuliHienThiChiTietDanhMuc();
    }

    @Override
    public void hienthiDanhSachChitiet(List<ItemBook> itemBooks) {
        itemBookList.addAll(itemBooks);
        recycleView_itemBook_adapter.notifyDataSetChanged();
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
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        itemBookList.clear();
                        presenterLogicListDanhMucTatCa.xuliHienThiChiTietDanhMuc();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        });
    }

    @Override
    public void hienThiLoadMore(int tongItem) {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView_ds.setNestedScrollingEnabled(false);
        List<ItemBook> itemBooks = presenterLogicListDanhMucTatCa.getLoadMore(tongItem,progressBar,recyclerView_ds);
        itemBookList.addAll(itemBooks);
        recycleView_itemBook_adapter.notifyDataSetChanged();
    }
}
