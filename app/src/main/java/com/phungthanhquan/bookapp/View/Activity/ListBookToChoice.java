package com.phungthanhquan.bookapp.View.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.widget.ImageView;

import com.google.api.LogDescriptor;
import com.phungthanhquan.bookapp.Adapter.Album_NXB_Adapter;
import com.phungthanhquan.bookapp.Adapter.RecycleView_ItemBook_Adapter;
import com.phungthanhquan.bookapp.Object.Album_BookCase;
import com.phungthanhquan.bookapp.Object.Marketing;
import com.phungthanhquan.bookapp.Presenter.Activity.PresenterLogicListBookToChoice;
import com.phungthanhquan.bookapp.R;
import com.phungthanhquan.bookapp.View.InterfaceView.InterfaceViewActivityListBookToChoice;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ListBookToChoice extends AppCompatActivity implements InterfaceViewActivityListBookToChoice {
    private Toolbar toolbar;
    private RecyclerView listBookToChoice;
    private ImageView collapsingToolbarLayout;
    private ImageView image;
    private PresenterLogicListBookToChoice presenterLogicListBookToChoice;

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
        String id = intent.getStringExtra("id");
        String title = intent.getStringExtra("title");
        Boolean album = intent.getBooleanExtra("album",false);
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Picasso.get().load(image_get).into(image);
        presenterLogicListBookToChoice = new PresenterLogicListBookToChoice(this);
        presenterLogicListBookToChoice.hienThiDanhSach(album,id);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void hienThiDanhSach(List<Marketing> itemBookList) {
        Album_NXB_Adapter adapter = new Album_NXB_Adapter(this,itemBookList);
        listBookToChoice.setAdapter(adapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3);
        listBookToChoice.setLayoutManager(gridLayoutManager);
    }
}
