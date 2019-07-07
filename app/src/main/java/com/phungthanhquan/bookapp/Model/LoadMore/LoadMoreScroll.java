package com.phungthanhquan.bookapp.Model.LoadMore;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentSnapshot;

public class LoadMoreScroll extends RecyclerView.OnScrollListener {

    private RecyclerView.LayoutManager layoutManager;
    private InterfaceLoadMore interfaceLoadMore;
    private int tongItem;
    private int itemDaLoad;
    private int itemLoadTruoc;
    private DocumentSnapshot documentSnapshotLast;

    public LoadMoreScroll(RecyclerView.LayoutManager layoutManager, InterfaceLoadMore interfaceLoadMore, int itemLoadTruoc,DocumentSnapshot documentSnapshot) {
        this.layoutManager = layoutManager;
        this.interfaceLoadMore = interfaceLoadMore;
        this.itemLoadTruoc = itemLoadTruoc;
        this.documentSnapshotLast = documentSnapshot;
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        tongItem = layoutManager.getItemCount();
        if(layoutManager instanceof LinearLayoutManager){
            itemDaLoad = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
        }else if(layoutManager instanceof GridLayoutManager){
            itemDaLoad = ((GridLayoutManager) layoutManager).findFirstVisibleItemPosition();
        }
        if(tongItem <= (itemLoadTruoc+itemDaLoad)){
            interfaceLoadMore.hienThiLoadMore();
        }

    }
    @Override
    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
    }

}
