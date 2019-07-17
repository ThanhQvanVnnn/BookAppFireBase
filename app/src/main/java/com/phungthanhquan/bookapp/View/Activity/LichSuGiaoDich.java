package com.phungthanhquan.bookapp.View.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.phungthanhquan.bookapp.Adapter.LichSuGiaoDich_Adapter;
import com.phungthanhquan.bookapp.Presenter.Activity.PresenterLichSuGiaoDich;
import com.phungthanhquan.bookapp.R;
import com.phungthanhquan.bookapp.View.InterfaceView.InterfaceViewActivityLichSuGiaoDich;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LichSuGiaoDich extends AppCompatActivity implements InterfaceViewActivityLichSuGiaoDich {
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private LichSuGiaoDich_Adapter adapter;
    private List<com.phungthanhquan.bookapp.Object.LichSuGiaoDich> lichSuGiaoDichList;
    PresenterLichSuGiaoDich presenterLichSuGiaoDich;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lich_su_giao_dich);
        initControls();
    }

    private void initControls() {
        recyclerView = findViewById(R.id.recycle_hienthi);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.lich_su_giao_dich));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        lichSuGiaoDichList = new ArrayList<>();
        adapter = new LichSuGiaoDich_Adapter(this,lichSuGiaoDichList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        recyclerView.setHasFixedSize(false);
        presenterLichSuGiaoDich = new PresenterLichSuGiaoDich(this);
        presenterLichSuGiaoDich.layDsLichSuGiaoDich();
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void hienThiDanhSachGiaoDich(List<com.phungthanhquan.bookapp.Object.LichSuGiaoDich> lichSuGiaoDiches) {
        lichSuGiaoDichList.addAll(lichSuGiaoDiches);
        sortDates(lichSuGiaoDichList);
        Collections.reverse(lichSuGiaoDichList);
        adapter.notifyDataSetChanged();
    }

    private static void sortDates(List<com.phungthanhquan.bookapp.Object.LichSuGiaoDich> lichSuGiaoDiches) {
        Collections.sort(lichSuGiaoDiches, new Comparator<com.phungthanhquan.bookapp.Object.LichSuGiaoDich>() {
            DateFormat dateFormat = new SimpleDateFormat("HH:mm - dd/MM/yyyy");
            @Override
            public int compare(com.phungthanhquan.bookapp.Object.LichSuGiaoDich o1, com.phungthanhquan.bookapp.Object.LichSuGiaoDich o2) {
                try {
                    return dateFormat.parse(o1.getTime()).compareTo(dateFormat.parse(o2.getTime()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return 1;
            }
        });
    }
}
