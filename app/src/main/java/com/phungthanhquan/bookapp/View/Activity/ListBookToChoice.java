package com.phungthanhquan.bookapp.View.Activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.widget.ImageView;

import com.phungthanhquan.bookapp.Adapter.RecycleView_ItemBook_Adapter;
import com.phungthanhquan.bookapp.Presenter.Activity.PresenterLogicListBookToChoice;
import com.phungthanhquan.bookapp.R;
import com.phungthanhquan.bookapp.View.InterfaceView.InterfaceViewActivityListBookToChoice;
import com.squareup.picasso.Picasso;

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

//    @Override
//    public void hienThiDanhSach(List<ItemBook> itemBookList) {
//        RecycleView_ItemBook_Adapter adapter = new RecycleView_ItemBook_Adapter(this,itemBookList,0);
//        listBookToChoice.setAdapter(adapter);
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3);
//        listBookToChoice.setLayoutManager(gridLayoutManager);
//    }
}
