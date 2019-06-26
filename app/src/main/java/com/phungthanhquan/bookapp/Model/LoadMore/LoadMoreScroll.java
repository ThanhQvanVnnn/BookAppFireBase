package com.phungthanhquan.bookapp.Model.LoadMore;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.phungthanhquan.bookapp.Object.ItemBook;

import java.util.ArrayList;
import java.util.List;

public class LoadMoreScroll extends RecyclerView.OnScrollListener {

    private RecyclerView.LayoutManager layoutManager;
    private InterfaceLoadMore interfaceLoadMore;
    private int tongItem;
    private int itemDaLoad;
    private int itemLoadTruoc;

    public LoadMoreScroll(RecyclerView.LayoutManager layoutManager, InterfaceLoadMore interfaceLoadMore, int itemLoadTruoc) {
        this.layoutManager = layoutManager;
        this.interfaceLoadMore = interfaceLoadMore;
        this.itemLoadTruoc = itemLoadTruoc;
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
        Log.d("kiemtra",itemDaLoad +"-"+tongItem);
        if(tongItem <= (itemLoadTruoc+itemDaLoad)){
            interfaceLoadMore.hienThiLoadMore(tongItem);
        }
    }
    @Override
    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
    }

}
