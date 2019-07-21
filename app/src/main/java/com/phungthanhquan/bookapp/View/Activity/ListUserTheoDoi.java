package com.phungthanhquan.bookapp.View.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

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
    List<Friend> userCheckFriendList;
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
        userCheckFriendList = new ArrayList<>();
        presenterFriendCaNhan = new PresenterFriendCaNhan(this);
        presenterFriendCaNhan.layListFriend();
    }

    @Override
    public void hienthilist(List<Friend> friendListReturn) {
       if(TITLE.equals(getString(R.string.nguoi_theo_doi))){
           adapter = new TheoDoiAdapter(this,friendList,userCheckFriendList,true);
           friendList.addAll(friendListReturn);
           adapter.addElement(friendList.size());
       }else {
           adapter = new TheoDoiAdapter(this,friendList,userCheckFriendList,false);
           friendList.addAll(userCheckFriendList);
           adapter.addElement(friendList.size());
       }
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        recyclerView.setHasFixedSize(false);

    }

    @Override
    public void laydanhsachban(List<Friend> friendList) {
        userCheckFriendList.addAll(friendList);
        presenterFriendCaNhan.layListFriendTheoDoi();
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
