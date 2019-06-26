package com.phungthanhquan.bookapp.View.Activity;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.phungthanhquan.bookapp.Adapter.RecycleView_ItemBook_Adapter;
import com.phungthanhquan.bookapp.Model.LoadMore.InterfaceLoadMore;
import com.phungthanhquan.bookapp.Model.LoadMore.LoadMoreScroll;
import com.phungthanhquan.bookapp.Object.ItemBook;
import com.phungthanhquan.bookapp.Presenter.Activity.PresenterLogicMarketing;
import com.phungthanhquan.bookapp.R;
import com.phungthanhquan.bookapp.View.InterfaceView.InterfaceViewActivityMarketing;

import java.util.ArrayList;
import java.util.List;

public class MarketingChiTiet extends AppCompatActivity implements InterfaceLoadMore, InterfaceViewActivityMarketing {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<ItemBook> itemBooks;
    private RecycleView_ItemBook_Adapter recycleView_itemBook_adapter;
    private ProgressBar progressBar;
    private LoadMoreScroll loadMoreScroll;
    private PresenterLogicMarketing presenterLogicMarketing;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marketing_chi_tiet);
        initControls();
        refresh();
    }

    private void initControls() {
        Intent intent = getIntent();
        toolbar = findViewById(R.id.toolbar);
        progressBar = findViewById(R.id.progressMarketingChitiet);
        recyclerView = findViewById(R.id.recycle_marketingchitiet);
        swipeRefreshLayout = findViewById(R.id.refresh_marketing);
        String title = intent.getStringExtra("Title");
        toolbar.setTitle(title);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            toolbar.setTitleTextColor(getColor(R.color.white));
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        itemBooks = new ArrayList<>();
        recycleView_itemBook_adapter = new RecycleView_ItemBook_Adapter(this,itemBooks,0);
        recyclerView.setAdapter(recycleView_itemBook_adapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(gridLayoutManager);
        loadMoreScroll = new LoadMoreScroll(gridLayoutManager,this,12);
        recyclerView.addOnScrollListener(loadMoreScroll);
        presenterLogicMarketing = new PresenterLogicMarketing(this);
        presenterLogicMarketing.xuliHienThiChiTietMarketing();
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void hienThiLoadMore(int tongItem) {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setNestedScrollingEnabled(false);
        List<ItemBook> itemBookList = presenterLogicMarketing.getLoadMore(tongItem,progressBar,recyclerView);
        itemBooks.addAll(itemBookList);
        recycleView_itemBook_adapter.notifyDataSetChanged();
    }

    @Override
    public void hienThidulieu(List<ItemBook> itemBookList) {
        itemBooks.addAll(itemBookList);
        recycleView_itemBook_adapter.notifyDataSetChanged();
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
                        itemBooks.clear();
                        presenterLogicMarketing.xuliHienThiChiTietMarketing();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        });
    }
}
