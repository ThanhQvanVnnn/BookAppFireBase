package com.phungthanhquan.bookapp.View.Fragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.phungthanhquan.bookapp.Adapter.DauTrang_Adapter;
import com.phungthanhquan.bookapp.Object.DauTrang;
import com.phungthanhquan.bookapp.Presenter.Fragment.PresenterLogicDauTrang;
import com.phungthanhquan.bookapp.R;
import com.phungthanhquan.bookapp.View.InterfaceView.InterfaceViewFragmentDauTrang;

import java.util.ArrayList;
import java.util.List;

public class FrgDauTrang extends Fragment implements InterfaceViewFragmentDauTrang {
    private RecyclerView recyclerView;
    private DauTrang_Adapter dauTrang_adapter;
    private List<DauTrang> dauTrangList;
    private String BOOK_ID;
    private PresenterLogicDauTrang presenterLogicDauTrang;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_dautrang, container, false);
        recyclerView = view.findViewById(R.id.recycler);
        Intent intent = getActivity().getIntent();
        BOOK_ID = intent.getStringExtra("book_id");
        dauTrangList = new ArrayList<>();
        dauTrang_adapter = new DauTrang_Adapter(getContext(),getActivity(),dauTrangList);
        recyclerView.setAdapter(dauTrang_adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
        dauTrang_adapter.notifyDataSetChanged();
        presenterLogicDauTrang = new PresenterLogicDauTrang(getContext(),this);
        presenterLogicDauTrang.LayDauTrang(BOOK_ID);
        return view;
    }

    @Override
    public void hienThiDauTrang(List<DauTrang> dauTrangListReturn) {
        dauTrangList.addAll(dauTrangListReturn);
        dauTrang_adapter.notifyDataSetChanged();
    }
}
