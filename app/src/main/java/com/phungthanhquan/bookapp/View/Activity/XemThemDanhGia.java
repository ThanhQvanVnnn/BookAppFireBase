package com.phungthanhquan.bookapp.View.Activity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.phungthanhquan.bookapp.Adapter.RecycleView_noidungbinhluan_Adapter;
import com.phungthanhquan.bookapp.Object.BinhLuan;
import com.phungthanhquan.bookapp.Presenter.Activity.PresenterLogicXemThemDanhGia;
import com.phungthanhquan.bookapp.R;
import com.phungthanhquan.bookapp.View.InterfaceView.InterfaceViewActivityDanhSachDanhGia;

import java.util.ArrayList;
import java.util.List;

public class XemThemDanhGia extends AppCompatActivity implements InterfaceViewActivityDanhSachDanhGia, View.OnClickListener {
    private Toolbar toolbar;
    private Button chiaSeCamNhan;
    private RecyclerView recyclerView_danhGia;
    private List<BinhLuan> dsBinhLuan;
    RecycleView_noidungbinhluan_Adapter recycleView_noidungbinhluan_adapter;
    private Dialog dialogCamNhan;
    PresenterLogicXemThemDanhGia presenterLogicXemThemDanhGia;
    String BOOK_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xem_them_danh_gia);
        BOOK_ID = getIntent().getStringExtra("book_id");
        initControls();
        dsBinhLuan = new ArrayList<>();
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        presenterLogicXemThemDanhGia.xuliHienThi(BOOK_ID);
        chiaSeCamNhan.setOnClickListener(this);
    }

    private void initControls() {
        toolbar = findViewById(R.id.toolbar);
        chiaSeCamNhan = findViewById(R.id.button_chiasecamnhan);
        recyclerView_danhGia = findViewById(R.id.recycle_tongdanhsachdanhgia);
        presenterLogicXemThemDanhGia = new PresenterLogicXemThemDanhGia(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void hienThiDanhSach(List<BinhLuan> binhLuanList) {
        dsBinhLuan.addAll(binhLuanList);
        recycleView_noidungbinhluan_adapter = new RecycleView_noidungbinhluan_Adapter(this,binhLuanList,true,BOOK_ID);
        recyclerView_danhGia.setAdapter(recycleView_noidungbinhluan_adapter);
        recyclerView_danhGia.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView_danhGia.setHasFixedSize(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_chiasecamnhan:
                dialogCamNhan = new Dialog(this);
                dialogCamNhan.setContentView(R.layout.dialog_danhgia);
                TextView txtClose = dialogCamNhan.findViewById(R.id.textview_cancel);
                txtClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogCamNhan.dismiss();
                    }
                });
                dialogCamNhan.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialogCamNhan.show();
                break;
        }
    }
}
