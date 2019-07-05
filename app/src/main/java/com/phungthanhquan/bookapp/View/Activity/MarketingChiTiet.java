package com.phungthanhquan.bookapp.View.Activity;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentSnapshot;
import com.phungthanhquan.bookapp.Adapter.Album_NXB_Adapter;
import com.phungthanhquan.bookapp.Adapter.RecycleView_ItemBook_Adapter;
import com.phungthanhquan.bookapp.Model.LoadMore.InterfaceLoadMore;
import com.phungthanhquan.bookapp.Model.LoadMore.LoadMoreScroll;
import com.phungthanhquan.bookapp.Object.Marketing;
import com.phungthanhquan.bookapp.Presenter.Activity.PresenterLogicMarketing;
import com.phungthanhquan.bookapp.R;
import com.phungthanhquan.bookapp.View.InterfaceView.InterfaceViewActivityMarketing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MarketingChiTiet extends AppCompatActivity implements InterfaceViewActivityMarketing, InterfaceLoadMore {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<Marketing> itemBooks;
    private String ID_MARKETING;
    private ProgressBar progressBar;
    private LoadMoreScroll loadMoreScroll;
    private PresenterLogicMarketing presenterLogicMarketing;
    private Album_NXB_Adapter adapter;
    private DocumentSnapshot lastDocument;
    GridLayoutManager gridLayoutManager;
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
        gridLayoutManager = new GridLayoutManager(this,3);
        String title = intent.getStringExtra("Title");
        ID_MARKETING = intent.getStringExtra("id");
        Toast.makeText(this, ID_MARKETING, Toast.LENGTH_SHORT).show();
        lastDocument = null;
        toolbar.setTitle(title);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            toolbar.setTitleTextColor(getColor(R.color.white));
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        itemBooks = new ArrayList<>();

        presenterLogicMarketing = new PresenterLogicMarketing(this,ID_MARKETING);
        presenterLogicMarketing.xuliHienThiChiTietMarketing();
        loadMoreScroll = new LoadMoreScroll(gridLayoutManager,this,9,lastDocument);
        recyclerView.addOnScrollListener(loadMoreScroll);

    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }



    @Override
    public void hienThidulieu(List<Marketing> itemBookList,DocumentSnapshot documentSnapshot) {
        lastDocument = documentSnapshot;
        itemBooks.addAll(itemBookList);
        adapter = new Album_NXB_Adapter(this,itemBooks);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(gridLayoutManager);
    }

    @Override
    public void hienThiDuLieuLoadMore(List<Marketing> itemBookList, DocumentSnapshot documentSnapshot) {
            if (itemBookList.size() < 6) {
                progressBar.setVisibility(View.INVISIBLE);
            }
            lastDocument = documentSnapshot;
            itemBooks.addAll(itemBookList);
            adapter.addMoreImage();
            adapter.notifyDataSetChanged();

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
                        presenterLogicMarketing.xuliHienThiChiTietMarketing();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        });
    }


    @Override
    public void hienThiLoadMore() {
        presenterLogicMarketing.getLoadMore(lastDocument);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
