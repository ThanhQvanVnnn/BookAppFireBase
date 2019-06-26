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
import com.phungthanhquan.bookapp.R;

import java.util.List;

public class FrgDauTrang extends Fragment {
    private RecyclerView recyclerView;
    private DauTrang_Adapter dauTrang_adapter;
    private List<DauTrang> dauTrangList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_dautrang, container, false);
        recyclerView = view.findViewById(R.id.recycler);
        Intent intent = getActivity().getIntent();
        dauTrangList = (List<DauTrang>) intent.getSerializableExtra("listdautrang");
        dauTrang_adapter = new DauTrang_Adapter(getContext(),getActivity(),dauTrangList);
        recyclerView.setAdapter(dauTrang_adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        dauTrang_adapter.notifyDataSetChanged();
        return view;
    }
}
