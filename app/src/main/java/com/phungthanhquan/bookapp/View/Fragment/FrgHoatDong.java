package com.phungthanhquan.bookapp.View.Fragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.phungthanhquan.bookapp.Adapter.SachDocCuaBanAdapter;
import com.phungthanhquan.bookapp.Object.BookCase;
import com.phungthanhquan.bookapp.Object.Friend;
import com.phungthanhquan.bookapp.Object.ItemSachBanDangDoc;
import com.phungthanhquan.bookapp.Presenter.Fragment.PresenterLogicHoatDong;
import com.phungthanhquan.bookapp.R;
import com.phungthanhquan.bookapp.View.Activity.MainActivity;
import com.phungthanhquan.bookapp.View.Activity.TimKiemBan;
import com.phungthanhquan.bookapp.View.InterfaceView.InterfaceViewFragmentHoatDong;

import java.util.ArrayList;
import java.util.List;

public class FrgHoatDong extends Fragment implements View.OnClickListener, InterfaceViewFragmentHoatDong {
    private ImageView button_themban;
    private ConstraintLayout layout_internet;
    ImageButton button_checkInternet;
    private RecyclerView recyclerView_dsSachCuaBan;
    private List<BookCase> itemSachBanDangDocList;
    private SachDocCuaBanAdapter adapter;
    private List<Friend> friendList;
    private PresenterLogicHoatDong presenterLogicHoatDong;
    private int friendIndex = 0;
    FirebaseFirestore firebaseFirestore;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hoatdong,container,false);
        initControls(view);
        EventClick();
        firebaseFirestore.collection("friend").whereEqualTo("sender_id", FirebaseAuth.getInstance().getUid()).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                if(e!=null){

                }else {
                    friendList.clear();
                    friendIndex =0;
                    itemSachBanDangDocList.clear();
                    adapter.addElement(itemSachBanDangDocList.size());
                    adapter.notifyDataSetChanged();
                    for(QueryDocumentSnapshot queryDocumentSnapshot:queryDocumentSnapshots){
                        if(queryDocumentSnapshot.exists()){
                            Friend friend = new Friend();
                            friend.setId(queryDocumentSnapshot.getId());
                            friend.setSender_id(queryDocumentSnapshot.getString("sender_id"));
                            friend.setReceiver_id(queryDocumentSnapshot.getString("receiver_id"));
                            friendList.add(friend);
                        }
                    }
                    if(friendList.size()!=0){
                        presenterLogicHoatDong.LaySachCuaBan(friendList.get(friendIndex));
                    }
                }
            }
        });
        return view;
    }

    private void initControls(View view) {
        button_themban = view.findViewById(R.id.button_themban);
        layout_internet = view.findViewById(R.id.layout_internet_disconnect);
        button_checkInternet = view.findViewById(R.id.checkInternet);
        recyclerView_dsSachCuaBan = view.findViewById(R.id.recycle_dsSachCuaBan);
        firebaseFirestore = FirebaseFirestore.getInstance();
        friendList = new ArrayList<>();
        itemSachBanDangDocList = new ArrayList<>();
        adapter = new SachDocCuaBanAdapter(getContext(),itemSachBanDangDocList);
        recyclerView_dsSachCuaBan.setAdapter(adapter);
        recyclerView_dsSachCuaBan.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
        recyclerView_dsSachCuaBan.setHasFixedSize(false);
        presenterLogicHoatDong = new PresenterLogicHoatDong(this);
    }

    private void EventClick() {
        button_themban.setOnClickListener(this);
        layout_internet.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.button_themban:
                intent = new Intent(getActivity(), TimKiemBan.class);
                MainActivity.navigationView.setVisibility(View.VISIBLE);
                startActivityForResult(intent,111);


                break;
            case R.id.checkInternet:
                InternetConnected();
                break;
        }
    }
    private void InternetConnected() {
        if (MainActivity.isNetworkConnected(getActivity())) {
            layout_internet.setVisibility(View.GONE);
            recyclerView_dsSachCuaBan.setVisibility(View.VISIBLE);
        } else {
            layout_internet.setVisibility(View.VISIBLE);
            recyclerView_dsSachCuaBan.setVisibility(View.GONE);
        }
    }

    @Override
    public void hienThiSachCuaBan(List<BookCase> itemSachBanDangDocs) {
            itemSachBanDangDocList.addAll(itemSachBanDangDocs);
            adapter.addElement(itemSachBanDangDocList.size());
            adapter.notifyDataSetChanged();
        friendIndex++;
        if(friendIndex<friendList.size()) {
            presenterLogicHoatDong.LaySachCuaBan(friendList.get(friendIndex));
        }
    }

    @Override
    public void laydanhsachban(List<Friend> friendListReturn) {
        friendList.clear();
        friendList.addAll(friendListReturn);
        friendIndex = 0;
        if(friendList.size()!=0){
            adapter.addElement(itemSachBanDangDocList.size());
            presenterLogicHoatDong.LaySachCuaBan(friendList.get(friendIndex));
        }
    }


}
