package com.phungthanhquan.bookapp.View.Fragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.phungthanhquan.bookapp.Adapter.ChuongSach_Adapter;
import com.phungthanhquan.bookapp.Object.ChuongSach;
import com.phungthanhquan.bookapp.Presenter.Fragment.PresenterLogicChuongSach;
import com.phungthanhquan.bookapp.R;
import com.phungthanhquan.bookapp.View.InterfaceView.InterfaceViewFragmentChuongSach;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FrgChuongSach extends Fragment implements InterfaceViewFragmentChuongSach {

    private RecyclerView recyclerView;
    private ChuongSach_Adapter chuongSach_adapter;
    private List<ChuongSach> chuongSachList;
    private PresenterLogicChuongSach presenterLogicChuongSach;
    String Book_id;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_chuongsach,container,false);
        recyclerView = view.findViewById(R.id.recycler);
        presenterLogicChuongSach = new PresenterLogicChuongSach(getActivity(),this);
         Book_id = getActivity().getIntent().getStringExtra("book_id");
        chuongSachList = new ArrayList<>();
        chuongSach_adapter = new ChuongSach_Adapter(getContext(),getActivity(),chuongSachList);
        recyclerView.setAdapter(chuongSach_adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setHasFixedSize(false);
        presenterLogicChuongSach.LayChuongSach(Book_id);
        return view;
    }

    @Override
    public void hienThiChuongSach(List<ChuongSach> chuongSachListReturn) {

        Collections.sort(chuongSachListReturn, new Comparator<ChuongSach>() {
            @Override
            public int compare(ChuongSach o1, ChuongSach o2) {
                return o1.getPage_number() - o2.getPage_number();
            }
        });
        chuongSachList.addAll(chuongSachListReturn);
        chuongSach_adapter.notifyDataSetChanged();
    }
}
