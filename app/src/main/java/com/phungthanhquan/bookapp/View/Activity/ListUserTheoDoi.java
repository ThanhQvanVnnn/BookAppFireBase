package com.phungthanhquan.bookapp.View.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.phungthanhquan.bookapp.Adapter.TheoDoiAdapter;
import com.phungthanhquan.bookapp.Adapter.TimKiemBanAdapter;
import com.phungthanhquan.bookapp.Object.Friend;
import com.phungthanhquan.bookapp.Presenter.Activity.PresenterFriendCaNhan;
import com.phungthanhquan.bookapp.R;
import com.phungthanhquan.bookapp.View.InterfaceView.InterfaceViewActivityFriendCaNhan;

import java.util.ArrayList;
import java.util.List;

public class ListUserTheoDoi extends AppCompatActivity implements InterfaceViewActivityFriendCaNhan {
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    String TITLE;
    private TheoDoiAdapter adapter;
    List<Friend> friendList;
    private PresenterFriendCaNhan  presenterFriendCaNhan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_user_theo_doi);
        initControls();
    }

    private void initControls() {
        Intent intent = getIntent();
        toolbar = findViewById(R.id.toolbar);
        TITLE = intent.getStringExtra("title");
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(TITLE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        recyclerView = findViewById(R.id.recycle_FriendList);
        friendList = new ArrayList<>();
        presenterFriendCaNhan = new PresenterFriendCaNhan(this);
        presenterFriendCaNhan.layListFriend();
    }

    @Override
    public void hienthilist(List<Friend> friendListReturn) {
       if(TITLE.equals(getString(R.string.nguoi_theo_doi))){
           adapter = new TheoDoiAdapter(this,friendListReturn,friendList,true);
       }else {
           adapter = new TheoDoiAdapter(this,friendList,friendList,false);
       }
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
    }

    @Override
    public void laydanhsachban(List<Friend> friendListReturn) {
        friendList.addAll(friendListReturn);
        presenterFriendCaNhan.layListFriendTheoDoi();
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
