package com.phungthanhquan.bookapp.View.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.phungthanhquan.bookapp.Model.ConfigPaypal;
import com.phungthanhquan.bookapp.Model.ConnectAPI.DownloadAPI;
import com.phungthanhquan.bookapp.Model.ConnectAPI.DownloadMethodAPI;
import com.phungthanhquan.bookapp.Model.Room.DbRoomAccess;
import com.phungthanhquan.bookapp.Object.BookCase;
import com.phungthanhquan.bookapp.Object.LichSuGiaoDich;
import com.phungthanhquan.bookapp.Object.User;
import com.phungthanhquan.bookapp.Object.UserRent;
import com.phungthanhquan.bookapp.Presenter.Activity.PresenterMuaThemGoiThue;
import com.phungthanhquan.bookapp.R;
import com.phungthanhquan.bookapp.View.InterfaceView.InterfaceViewActivityMuaThemGoiThue;
import com.squareup.picasso.Picasso;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThanhToanMuaThemGoiThue extends AppCompatActivity implements View.OnClickListener, InterfaceViewActivityMuaThemGoiThue {

    private Toolbar toolbar;
    private Dialog dialog;
    private TextView textView_tenGoiThue, textView_thoiGian, textView_soTien, textView_sodu, textView_vnd;
    private SimpleDateFormat dateFormatter;
    String RENT_NAME, RENT_ID;
    int RENT_TIME;
    Double RENT_PRICE;
    LinearLayout button_thanhtoanchinh, button_thanhToanPayPal;
    private PresenterMuaThemGoiThue presenterMuaThemGoiThue;
    String thoihansaukhimua;
    private User user;
    DecimalFormat df;
    FirebaseFirestore firebaseFirestore;
    public AlertDialog loadingDialog;
    UserRent layUserRent = null;
    public static final int PAYPAL_REQUEST_CODE = 7171;
    private static PayPalConfiguration config = new PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(ConfigPaypal.CLLENT_PAYPAL);
    private PayPalPayment payPalPayment;
    private DownloadMethodAPI downloadMethodAPI;
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
        RENT_PRICE = get.getDoubleExtra("rent_price", -1);
        RENT_TIME = get.getIntExtra("ren_time", -1);
        RENT_ID = get.getStringExtra("rent_id");
    }

    private void initControls() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.thanh_toan));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        df = new DecimalFormat("###,###.###");
        textView_tenGoiThue = findViewById(R.id.tengoithue);
        textView_thoiGian = findViewById(R.id.thoihan);
        textView_soTien = findViewById(R.id.book_price);
        textView_sodu = findViewById(R.id.budget);
        textView_vnd = findViewById(R.id.vnd);
        button_thanhtoanchinh = findViewById(R.id.thanhtoanquataikhoanchinh);
        button_thanhtoanchinh.setOnClickListener(this);
        button_thanhToanPayPal = findViewById(R.id.thanhtoanquapaypal);
        button_thanhToanPayPal.setOnClickListener(this);
        presenterMuaThemGoiThue = new PresenterMuaThemGoiThue(this);
        presenterMuaThemGoiThue.LayUserRent();
        firebaseFirestore = FirebaseFirestore.getInstance();
        loadingDialog = new SpotsDialog.Builder().setContext(this).build();
        loadingDialog.setMessage(getResources().getString(R.string.vuilongcho));
        downloadMethodAPI = DownloadAPI.getApiDownload(this);
    }

    @Override
    public void hienThiThongTinGoiThue(UserRent userRent, User userReturn) {
        user = userReturn;
        layUserRent = userRent;
        if(userRent!=null) {
            try {
                if (dateFormatter.parse(userRent.getTime_rest()).after(new Date()) || dateFormatter.parse(userRent.getTime_rest()).equals(dateFormatter.format(new Date()))) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(dateFormatter.parse(userRent.getTime_rest()));
                    calendar.add(Calendar.MONTH, RENT_TIME);
                    thoihansaukhimua = dateFormatter.format(calendar.getTime());
                    textView_thoiGian.setText(thoihansaukhimua + " ( " + userRent.getTime_rest() + " + " + RENT_TIME + " Month )");
                } else {
                    Date date = new Date();
                    Calendar cal = Calendar.getInstance();
                    cal.add(Calendar.MONTH, RENT_TIME);
                    thoihansaukhimua = dateFormatter.format(cal.getTime());
                    String hienTai = dateFormatter.format(date);
                    textView_thoiGian.setText(hienTai + " -> " + thoihansaukhimua);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }else {
            Date date = new Date();
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MONTH, RENT_TIME);
            thoihansaukhimua = dateFormatter.format(cal.getTime());
            String hienTai = dateFormatter.format(date);
            textView_thoiGian.setText(hienTai + " -> " + thoihansaukhimua);
        }
        textView_tenGoiThue.setText(RENT_NAME);
        textView_soTien.setText(RENT_PRICE + "");
        textView_sodu.setText(user.getBudget() + "");
        if (user.getBudget() >= RENT_PRICE) {
            textView_sodu.setTextColor(getResources().getColor(R.color.damuasach));
            textView_vnd.setTextColor(getResources().getColor(R.color.damuasach));
        } else {
            textView_sodu.setTextColor(getResources().getColor(R.color.search_background));
            textView_vnd.setTextColor(getResources().getColor(R.color.search_background));
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.thanhtoanquataikhoanchinh:
                dialog = new Dialog(this);
                dialog.setContentView(R.layout.dialog_muathemgoithue);
                dialog.setCanceledOnTouchOutside(false);
                TextView thoihan = dialog.findViewById(R.id.textview_thoihanthue);
                TextView giatien = dialog.findViewById(R.id.textview_giatien);
                TextView sodu = dialog.findViewById(R.id.textview_sodutrongtaikhoan);
                Button mua = dialog.findViewById(R.id.button_mua);
                Button huy = dialog.findViewById(R.id.button_huy);
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
                thoihan.setText(thoihansaukhimua);
                thoihan.setTextColor(getResources().getColor(R.color.colorPrimary));
                huy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                mua.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Double sodu = user.getBudget() - RENT_PRICE;
                        user.setBudget(sodu);
                        firebaseFirestore.collection("user").document(FirebaseAuth.getInstance().getUid()).update("budget",sodu).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                dialog.dismiss();
                                loadingDialog.show();
                                //thêm lịch sử giao dịch
                                com.phungthanhquan.bookapp.Object.LichSuGiaoDich lichSuGiaoDich = new LichSuGiaoDich();
                                lichSuGiaoDich.setFrom_budget("budget");
                                lichSuGiaoDich.setMoney(RENT_PRICE);
                                lichSuGiaoDich.setTransaction_category("t");
                                lichSuGiaoDich.setUser_id(FirebaseAuth.getInstance().getUid());
                                lichSuGiaoDich.setRent_time(RENT_NAME);
                                lichSuGiaoDich.setEntity("Mua Them Goi Thue");
                                Date now = new Date();
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm - dd/MM/yyyy");
                                String time = simpleDateFormat.format(now);
                                Log.d("Timehientai", time);
                                lichSuGiaoDich.setTime(time);
                                firebaseFirestore.collection("transaction_history").add(lichSuGiaoDich).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        Intent data = new Intent();
                                        String text = thoihansaukhimua;
                                        data.setData(Uri.parse(text));
                                        setResult(RESULT_OK, data);
                                    }
                                });
                                //kết thúc thêm lịch sử giao dịch
                                if(layUserRent!=null) {
                                    UserRent userRent = new UserRent();
                                    userRent.setRent_id(RENT_ID);
                                    userRent.setTime_rest(thoihansaukhimua);
                                    userRent.setId(layUserRent.getId());
                                    userRent.setUser_id(FirebaseAuth.getInstance().getUid());
                                    DbRoomAccess.getInstance(ThanhToanMuaThemGoiThue.this).updateUserRentTask(ThanhToanMuaThemGoiThue.this, userRent);
                                    firebaseFirestore.collection("user_rent").document(layUserRent.getId()).set(userRent).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            loadingDialog.dismiss();
                                            dialog.dismiss();
                                            finish();
                                            showAToast(getString(R.string.thanh_toan_thanh_cong));
                                        }
                                    });
                                }else {
                                    final UserRent userRent = new UserRent();
                                    userRent.setRent_id(RENT_ID);
                                    userRent.setTime_rest(thoihansaukhimua);
                                    userRent.setUser_id(FirebaseAuth.getInstance().getUid());
                                    firebaseFirestore.collection("user_rent").add(userRent).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            userRent.setId(documentReference.getId());
                                            DbRoomAccess.getInstance(ThanhToanMuaThemGoiThue.this).insertUserRentTask(ThanhToanMuaThemGoiThue.this, userRent);
                                            loadingDialog.dismiss();
                                            finish();
                                            showAToast(getString(R.string.thanh_toan_thanh_cong));
                                        }
                                    });
                                }
                            }
                        });
                    }
                });
                dialog.show();
                break;
            case R.id.thanhtoanquapaypal:
                processPayPall();
                break;
        }

    }

    private void processPayPall()  {
        //        String cookie = "";
        Call<String> call = downloadMethodAPI.ConvertToDollar();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                loadingDialog.show();
                String chuoi = response.body();
                int indexStart = chuoi.indexOf("US DOLLAR");
                Log.d("jsonconvert",chuoi);
                int indexend = 0;
                for(int j = indexStart;j<chuoi.length();j++){
                    if(chuoi.charAt(j) == '/' && chuoi.charAt(j+1)=='>'){
                        indexend = j;
                        break;
                    }
                }
                String chuoilayduoclan1 = chuoi.substring(indexStart,indexend);
                Log.d("jsonconvert","chuoi lay duoc lan 1: " +chuoilayduoclan1);
                indexStart = chuoilayduoclan1.indexOf("Transfer");
                String chuoilayduoclan2 = chuoilayduoclan1.substring(indexStart,chuoilayduoclan1.length()-1);
                Log.d("jsonconvert","chuoi lay duoc lan 2: " +chuoilayduoclan2);
                indexStart = chuoilayduoclan2.indexOf("=\"");
                indexend = chuoilayduoclan2.indexOf("\" ");
                String number = chuoilayduoclan2.substring(indexStart+2,indexend);
                Log.d("jsonconvert", number);
                int tigia = Integer.parseInt(number);
                Double price_usd = RENT_PRICE/tigia;
                payPalPayment = new PayPalPayment(new BigDecimal(String.valueOf(price_usd))
                        ,"USD","Thanh toán Cho STUBO", PayPalPayment.PAYMENT_INTENT_SALE);
                Intent intent = new Intent(ThanhToanMuaThemGoiThue.this, PaymentActivity.class);
                intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
                intent.putExtra(PaymentActivity.EXTRA_PAYMENT,payPalPayment);
                startActivityForResult(intent,PAYPAL_REQUEST_CODE);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("jsonconvert", t.getMessage());
            }
        });

    }
    Toast toast;
    public void showAToast(String st) { //"Toast toast" is declared in the class
        try {
            toast.getView().isShown();     // true if visible
            toast.setText(st);
        } catch (Exception e) {         // invisible if exception
            toast = Toast.makeText(this, st, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
        }
        toast.show();  //finally display it
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == PAYPAL_REQUEST_CODE){
            if(resultCode == RESULT_OK){
                //thêm lịch sử giao dịch
                com.phungthanhquan.bookapp.Object.LichSuGiaoDich lichSuGiaoDich = new LichSuGiaoDich();
                lichSuGiaoDich.setFrom_budget("paypal");
                lichSuGiaoDich.setMoney(RENT_PRICE);
                lichSuGiaoDich.setTransaction_category("t");
                lichSuGiaoDich.setUser_id(FirebaseAuth.getInstance().getUid());
                lichSuGiaoDich.setRent_time(RENT_NAME);
                lichSuGiaoDich.setEntity("Mua Them Goi Thue");
                Date now = new Date();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm - dd/MM/yyyy");
                String time = simpleDateFormat.format(now);
                Log.d("Timehientai", time);
                lichSuGiaoDich.setTime(time);
                firebaseFirestore.collection("transaction_history").add(lichSuGiaoDich).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Intent data = new Intent();
                        String text = thoihansaukhimua;
                        data.setData(Uri.parse(text));
                        setResult(RESULT_OK, data);
                    }
                });
                //kết thúc thêm lịch sử giao dịch
                if(layUserRent!=null) {
                    UserRent userRent = new UserRent();
                    userRent.setRent_id(RENT_ID);
                    userRent.setTime_rest(thoihansaukhimua);
                    userRent.setId(layUserRent.getId());
                    userRent.setUser_id(FirebaseAuth.getInstance().getUid());
                    DbRoomAccess.getInstance(ThanhToanMuaThemGoiThue.this).updateUserRentTask(ThanhToanMuaThemGoiThue.this, userRent);
                    firebaseFirestore.collection("user_rent").document(layUserRent.getId()).set(userRent).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            finish();
                        }
                    });
                }else {
                    final UserRent userRent = new UserRent();
                    userRent.setRent_id(RENT_ID);
                    userRent.setTime_rest(thoihansaukhimua);
                    userRent.setUser_id(FirebaseAuth.getInstance().getUid());
                    firebaseFirestore.collection("user_rent").add(userRent).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            userRent.setId(documentReference.getId());
                            DbRoomAccess.getInstance(ThanhToanMuaThemGoiThue.this).insertUserRentTask(ThanhToanMuaThemGoiThue.this, userRent);
                            finish();
                        }
                    });
                }
                showAToast(getString(R.string.thanh_toan_thanh_cong));
            }else if(resultCode == Activity.RESULT_CANCELED){
                showAToast(getString(R.string.thanh_toan_huy));
            }
        }else if(resultCode == PaymentActivity.RESULT_EXTRAS_INVALID){
            showAToast(getString(R.string.thanh_toan_that_bai));
        }
        loadingDialog.dismiss();
    }

}
