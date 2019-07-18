package com.phungthanhquan.bookapp.View.Fragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.phungthanhquan.bookapp.R;
import com.phungthanhquan.bookapp.View.Activity.TimKiemBan;

public class FrgHoatDong extends Fragment implements View.OnClickListener {
    private ImageView button_themban;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hoatdong,container,false);
        initControls(view);
        EventClick();
        return view;
    }

    private void initControls(View view) {
        button_themban = view.findViewById(R.id.button_themban);
    }

    private void EventClick() {
        button_themban.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.button_themban:
                intent = new Intent(getActivity(), TimKiemBan.class);
                startActivity(intent);
                break;
        }
    }
}
