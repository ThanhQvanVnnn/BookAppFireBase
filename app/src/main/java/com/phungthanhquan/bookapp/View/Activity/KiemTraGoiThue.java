package com.phungthanhquan.bookapp.View.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.phungthanhquan.bookapp.Adapter.GoiThueMuaThem_Adapter;
import com.phungthanhquan.bookapp.Adapter.GoiThue_Adapter;
import com.phungthanhquan.bookapp.Object.Rent;
import com.phungthanhquan.bookapp.Object.UserRent;
import com.phungthanhquan.bookapp.Presenter.Activity.PresenterKiemTraGoiThue;
import com.phungthanhquan.bookapp.R;
import com.phungthanhquan.bookapp.View.InterfaceView.InterfaceViewActivityKiemTraGoiThue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class KiemTraGoiThue extends AppCompatActivity implements InterfaceViewActivityKiemTraGoiThue {
    private Toolbar toolbar;
    private RecyclerView recyclerView_goithue;
    private GoiThueMuaThem_Adapter goiThue_adapter;
    private UserRent USER_RENT= null;
    private TextView textView_thoiGianThueConLai;
    private SimpleDateFormat dateFormatter;
    private PresenterKiemTraGoiThue presenterKiemTraGoiThue;
    private List<Rent> rentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kiem_tra_goi_thue);
        initControls();
    }

    private void initControls() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.goithuecanhan));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        recyclerView_goithue = findViewById(R.id.recyclerview_hienthigoithue);
        textView_thoiGianThueConLai = findViewById(R.id.thoigianthue);
        dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        rentList = new ArrayList<>();
        goiThue_adapter = new GoiThueMuaThem_Adapter(this,rentList);
        recyclerView_goithue.setAdapter(goiThue_adapter);
        recyclerView_goithue.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        recyclerView_goithue.setHasFixedSize(false);
        presenterKiemTraGoiThue = new PresenterKiemTraGoiThue(this,this);
        presenterKiemTraGoiThue.layGoiThue();
        presenterKiemTraGoiThue.layDanhSachGoiThue();
    }

    @Override
    public void hienThiThoiGianThue(UserRent userRent) {
        USER_RENT = userRent;
        if(USER_RENT == null){
            textView_thoiGianThueConLai.setText(getString(R.string.banchuamuagoithue));
            textView_thoiGianThueConLai.setTextColor(getResources().getColor(R.color.red));
        }else{
            try {
                Date userRentTime = dateFormatter.parse(USER_RENT.getTime_rest());
                Date date = new Date();
                //nếu gói thuê còn hạn
                if(userRentTime.after(date)){
                    textView_thoiGianThueConLai.setText(USER_RENT.getTime_rest());
                    textView_thoiGianThueConLai.setTextColor(getResources().getColor(R.color.colorPrimary));
                }else  /*nếu gói thuê hết hạn*/{
                    textView_thoiGianThueConLai.setText(getString(R.string.hethanthue));
                    textView_thoiGianThueConLai.setTextColor(getResources().getColor(R.color.search_background));
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void hienThiDanhSachGoiThue(List<Rent> rentListReturn) {
        Collections.sort(rentListReturn, new Comparator<Rent>() {
            @Override
            public int compare(Rent rent, Rent t1) {
                return rent.getMonth() - t1.getMonth();
            }
        });
        rentList.addAll(rentListReturn);
        goiThue_adapter.notifyDataSetChanged();
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
