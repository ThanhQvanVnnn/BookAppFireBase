package com.phungthanhquan.bookapp.View.Activity;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.phungthanhquan.bookapp.R;

public class HinhThucThanhToan extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout taiKhoanChinh;
    private LinearLayout moMo;
    private LinearLayout viSa;
    private LinearLayout napThe;
    private Dialog dialog;
    private TextView Textexit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hinh_thuc_thanh_toan);
        initControls();
        taiKhoanChinh.setOnClickListener(this);
        moMo.setOnClickListener(this);
        viSa.setOnClickListener(this);
        napThe.setOnClickListener(this);
        Textexit.setOnClickListener(this);
    }

    private void initControls() {
        taiKhoanChinh = findViewById(R.id.thanhtoanquataikhoanchinh);
        moMo = findViewById(R.id.thanhtoanquavidientuMoMo);
        viSa = findViewById(R.id.thanhtoanquaVisa);
        napThe = findViewById(R.id.thanhtoanbangthecao);
        Textexit = findViewById(R.id.exit_thanhtoan);
    }

    @Override
    public void onClick(View v) {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_muasach);
        dialog.setCanceledOnTouchOutside(false);
        TextView thoihan = dialog.findViewById(R.id.textview_thoihanthue);
        TextView giatien = dialog.findViewById(R.id.textview_giatien);
        TextView sodu = dialog.findViewById(R.id.textview_sodutrongtaikhoan);
        TextView tinhtrang = dialog.findViewById(R.id.textview_tinhtrang);
        ImageView anhsach = dialog.findViewById(R.id.image_sach);
        Button mua = dialog.findViewById(R.id.button_mua);
        Button huy = dialog.findViewById(R.id.button_huy);
        huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        switch (v.getId()){
            case R.id.thanhtoanquataikhoanchinh:
                dialog.show();
                break;
            case R.id.thanhtoanquavidientuMoMo:
                Toast.makeText(this, "Momo", Toast.LENGTH_SHORT).show();
                break;
            case R.id.thanhtoanquaVisa:
                Toast.makeText(this, "Visa", Toast.LENGTH_SHORT).show();
                break;
            case R.id.thanhtoanbangthecao:
                Toast.makeText(this, "Thẻ cào", Toast.LENGTH_SHORT).show();
                break;
            case R.id.exit_thanhtoan:
                finish();
                break;
        }
    }
}
