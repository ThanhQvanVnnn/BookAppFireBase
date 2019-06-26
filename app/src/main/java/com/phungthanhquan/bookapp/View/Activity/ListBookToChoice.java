package com.phungthanhquan.bookapp.View.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.phungthanhquan.bookapp.Adapter.RecycleView_ItemBook_Adapter;
import com.phungthanhquan.bookapp.Object.ItemBook;
import com.phungthanhquan.bookapp.Presenter.Activity.PresenterLogicListBookToChoice;
import com.phungthanhquan.bookapp.R;
import com.phungthanhquan.bookapp.View.InterfaceView.InterfaceViewActivityListBookToChoice;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

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
        String title = intent.getStringExtra("title");
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Picasso.get().load(image_get).into(image);
        presenterLogicListBookToChoice = new PresenterLogicListBookToChoice(this);
        presenterLogicListBookToChoice.hienThiDanhSach();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void hienThiDanhSach(List<ItemBook> itemBookList) {
        RecycleView_ItemBook_Adapter adapter = new RecycleView_ItemBook_Adapter(this,itemBookList,0);
        listBookToChoice.setAdapter(adapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3);
        listBookToChoice.setLayoutManager(gridLayoutManager);
    }
}
