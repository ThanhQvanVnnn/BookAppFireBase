package com.phungthanhquan.bookapp.View.Activity;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.phungthanhquan.bookapp.R;

public class ChonGoiMuaSach extends AppCompatActivity implements View.OnClickListener {
    private Button mua;
    private Button thue3;
    private Button thue6;
    private Button thue12;
    private TextView thoat;
    private TextView giathue3;
    private TextView giathue6;
    private TextView giathue12;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chon_goi_mua_sach);
        initControls();
        mua.setOnClickListener(this);
        thue3.setOnClickListener(this);
        thue6.setOnClickListener(this);
        thue12.setOnClickListener(this);
        thoat.setOnClickListener(this);
    }

    private void initControls() {
        mua = findViewById(R.id.button_muale);
        thue3 = findViewById(R.id.button_thue3thang);
        thue6 = findViewById(R.id.button_thue6thang);
        thue12 = findViewById(R.id.button_thue12thang);
        thoat = findViewById(R.id.exit);
        giathue3 = findViewById(R.id.textview_giathue3thang);
        giathue6 = findViewById(R.id.textview_giathue6thang);
        giathue12 = findViewById(R.id.textview_giathue12thang);

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.button_thue3thang:
                intent = new Intent(this,HinhThucThanhToan.class);
                intent.putExtra("thoihan","3thang");
                intent.putExtra("sotien",3000);
                intent.putExtra("userID",3256);
                startActivity(intent);
                break;
            case R.id.button_thue6thang:
                intent = new Intent(this,HinhThucThanhToan.class);
                intent.putExtra("thoihan","3thang");
                intent.putExtra("sotien",3000);
                intent.putExtra("userID",3256);
                startActivity(intent);
                break;
            case R.id.button_thue12thang:
                intent = new Intent(this,HinhThucThanhToan.class);
                intent.putExtra("thoihan","3thang");
                intent.putExtra("sotien",3000);
                intent.putExtra("userID",3256);
                startActivity(intent);
                break;
            case R.id.button_muale:
                intent = new Intent(this,HinhThucThanhToan.class);
                intent.putExtra("thoihan","3thang");
                intent.putExtra("sotien",3000);
                intent.putExtra("userID",3256);
                startActivity(intent);
                break;
            case R.id.exit:
                finish();
                break;
        }
    }
}
