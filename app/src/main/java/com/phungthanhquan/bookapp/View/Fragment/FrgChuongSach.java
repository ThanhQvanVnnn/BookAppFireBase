package com.phungthanhquan.bookapp.View.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.phungthanhquan.bookapp.Adapter.ChuongSach_Adapter;
import com.phungthanhquan.bookapp.Object.ChuongSach;
import com.phungthanhquan.bookapp.R;

import java.util.List;

public class FrgChuongSach extends Fragment {

    private RecyclerView recyclerView;
    private ChuongSach_Adapter chuongSach_adapter;
    private List<ChuongSach> chuongSachList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_chuongsach,container,false);
        recyclerView = view.findViewById(R.id.recycler);
        Intent intent = getActivity().getIntent();
        chuongSachList = (List<ChuongSach>) intent.getSerializableExtra("listChuongSach");
        chuongSach_adapter = new ChuongSach_Adapter(getContext(),getActivity(),chuongSachList);
        recyclerView.setAdapter(chuongSach_adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        chuongSach_adapter.notifyDataSetChanged();
        return view;
    }
}
