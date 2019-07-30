package com.phungthanhquan.bookapp.View.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.phungthanhquan.bookapp.Object.User;
import com.phungthanhquan.bookapp.Object.UserRent;
import com.phungthanhquan.bookapp.Presenter.Activity.PresenterMuaThemGoiThue;
import com.phungthanhquan.bookapp.R;
import com.phungthanhquan.bookapp.View.InterfaceView.InterfaceViewActivityMuaThemGoiThue;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ThanhToanMuaThemGoiThue extends AppCompatActivity implements View.OnClickListener, InterfaceViewActivityMuaThemGoiThue {

    private Toolbar toolbar;
    private Dialog dialog;
    private TextView textView_tenGoiThue,textView_thoiGian,textView_soTien,textView_sodu,textView_vnd;
    private SimpleDateFormat dateFormatter;
    String RENT_NAME,RENT_ID;
    int RENT_TIME;
    Double RENT_PRICE;
    LinearLayout button_thanhtoanchinh,button_thanhToanPayPal;
    private PresenterMuaThemGoiThue presenterMuaThemGoiThue;
    String thoihansaukhimua;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan_mua_them_goi_thue);
        getData();
        initControls();

    }

    private void getData() {
        Intent get = getIntent();
        RENT_NAME = get.getStringExtra("rent_name");
        RENT_PRICE = get.getDoubleExtra("rent_price",-1);
        RENT_TIME = get.getIntExtra("ren_time",-1);
        RENT_ID = get.getStringExtra("rent_id");
    }

    private void initControls() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.thanh_toan));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        textView_tenGoiThue = findViewById(R.id.tengoithue);
        textView_thoiGian = findViewById(R.id.thoihan);
        textView_soTien = findViewById(R.id.book_price);
        textView_sodu = findViewById(R.id.budget);
        textView_vnd = findViewById(R.id.vnd);
        button_thanhtoanchinh = findViewById(R.id.thanhtoanquataikhoanchinh);
        button_thanhToanPayPal = findViewById(R.id.thanhtoanquapaypal);
        presenterMuaThemGoiThue = new PresenterMuaThemGoiThue(this);
        presenterMuaThemGoiThue.LayUserRent();
    }
    @Override
    public void hienThiThongTinGoiThue(UserRent userRent, User userReturn) {
        user = userReturn;
        try {
            if(dateFormatter.parse(userRent.getTime_rest()).after(new Date()) || dateFormatter.parse(userRent.getTime_rest()).equals(dateFormatter.format(new Date()))){
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(dateFormatter.parse(userRent.getTime_rest()));
                calendar.add(Calendar.MONTH,RENT_TIME);
                thoihansaukhimua = dateFormatter.format(calendar.getTime());
                textView_thoiGian.setText(thoihansaukhimua+ " ( " +userRent.getTime_rest()+" + "+RENT_TIME+" Month )");
            }else {
                Date date = new Date();
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.MONTH, RENT_TIME);
                thoihansaukhimua = dateFormatter.format(cal.getTime());
                String hienTai = dateFormatter.format(date);
                textView_thoiGian.setText(hienTai+" -> "+ thoihansaukhimua);
            }
            textView_tenGoiThue.setText(RENT_NAME);
            textView_soTien.setText(RENT_PRICE+"");
            textView_sodu.setText(user.getBudget()+"");
            if(user.getBudget()>=RENT_PRICE){
                textView_sodu.setTextColor(getResources().getColor(R.color.damuasach));
                textView_vnd.setTextColor(getResources().getColor(R.color.damuasach));
            }else {
                textView_sodu.setTextColor(getResources().getColor(R.color.search_background));
                textView_vnd.setTextColor(getResources().getColor(R.color.search_background));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.thanhtoanquataikhoanchinh:
                dialog = new Dialog(this);
                dialog.setContentView(R.layout.dialog_muasach);
                dialog.setCanceledOnTouchOutside(false);
                TextView thoihan = dialog.findViewById(R.id.textview_thoihanthue);
                TextView giatien = dialog.findViewById(R.id.textview_giatien);
                TextView sodu = dialog.findViewById(R.id.textview_sodutrongtaikhoan);
                ImageView anhsach = dialog.findViewById(R.id.image_sach);
                Button mua = dialog.findViewById(R.id.button_mua);
                Button huy = dialog.findViewById(R.id.button_huy);
                Picasso.get().load(IMAGE).into(anhsach);
                Log.d("rentprice", df.format(RENT_PRICE) + "");
                giatien.setText(df.format(RENT_PRICE) + "");
                if (user.getBudget() >= RENT_PRICE) {
                    sodu.setText(getString(R.string.cothethanhtoan));
                    sodu.setTextColor(getResources().getColor(R.color.damuasach));
                    mua.setEnabled(true);
                    huy.setEnabled(true);
                } else {
                    sodu.setText(getString(R.string.khongdutaikhoan));
                    sodu.setTextColor(getResources().getColor(R.color.search_background));
                    mua.setEnabled(false);
                    huy.setEnabled(true);
                }

                if (RENT_NAME.equals("v")) {
                    thoihan.setText(getString(R.string.vinh_vien));
                    thoihan.setTextColor(getResources().getColor(R.color.damuasach));
                } else {
                    thoihan.setText(dateFormatter.format(cal.getTime()));
                    thoihan.setTextColor(getResources().getColor(R.color.colorPrimary));
                }
                huy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                break;
            case R.id.thanhtoanquapaypal:

                break;
        }

    }

}
