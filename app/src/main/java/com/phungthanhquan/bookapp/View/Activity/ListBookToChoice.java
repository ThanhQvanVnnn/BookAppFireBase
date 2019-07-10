package com.phungthanhquan.bookapp.View.Activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.widget.ImageView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.phungthanhquan.bookapp.Adapter.Album_NXB_Adapter;
import com.phungthanhquan.bookapp.Model.LoadMore.InterfaceLoadMore;
import com.phungthanhquan.bookapp.Model.LoadMore.LoadMoreScroll;
import com.phungthanhquan.bookapp.Object.Marketing;
import com.phungthanhquan.bookapp.Presenter.Activity.PresenterLogicListBookToChoice;
import com.phungthanhquan.bookapp.R;
import com.phungthanhquan.bookapp.View.InterfaceView.InterfaceViewActivityListBookToChoice;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ListBookToChoice extends AppCompatActivity implements InterfaceViewActivityListBookToChoice, InterfaceLoadMore {
    private Toolbar toolbar;
    private RecyclerView listBookToChoice;
    private ImageView collapsingToolbarLayout;
    private ImageView image;
    private PresenterLogicListBookToChoice presenterLogicListBookToChoice;
    private LoadMoreScroll loadMoreScroll;
    private List<Marketing> dsMarketing;
    private DocumentSnapshot lastDocument;
    private Album_NXB_Adapter adapter;
    Boolean album;
    String id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listbooktochoice);
        initControls();

    }

    private void initControls() {
        toolbar = findViewById(R.id.toolbar_listtochoice);
        listBookToChoice = findViewById(R.id.recycle_listallchoice);
        image = findViewById(R.id.image_listbooktochoice);
        Intent intent =getIntent();
        String image_get =intent.getStringExtra("image");
         id = intent.getStringExtra("id");
        String title = intent.getStringExtra("title");
        album = intent.getBooleanExtra("album",false);
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        lastDocument = null;
        Picasso.get().load(image_get).into(image);
        presenterLogicListBookToChoice = new PresenterLogicListBookToChoice(this);
        presenterLogicListBookToChoice.hienThiDanhSach(album,id);
        dsMarketing = new ArrayList<>();
        adapter = new Album_NXB_Adapter(this,dsMarketing);
        listBookToChoice.setAdapter(adapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3);
        listBookToChoice.setLayoutManager(gridLayoutManager);
        loadMoreScroll = new LoadMoreScroll(gridLayoutManager,this,15,lastDocument);
        listBookToChoice.addOnScrollListener(loadMoreScroll);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void hienThiDanhSach(List<Marketing> itemBookList, DocumentSnapshot documentSnapshot) {
       dsMarketing.addAll(itemBookList);
       lastDocument = documentSnapshot;
       adapter.addMoreImage();
       adapter.notifyDataSetChanged();
    }

    @Override
    public void hienThiDanhSachLoadMore(List<Marketing> itemBookList, DocumentSnapshot documentSnapshot) {
        if(itemBookList.size()!=0){
            dsMarketing.addAll(itemBookList);
            adapter.addMoreImage();
            adapter.notifyDataSetChanged();
            lastDocument = documentSnapshot;
        }
    }

    @Override
    public void hienThiLoadMore() {
        presenterLogicListBookToChoice.hienThiDanhSachLoadMore(id,album,lastDocument);
    }
}
