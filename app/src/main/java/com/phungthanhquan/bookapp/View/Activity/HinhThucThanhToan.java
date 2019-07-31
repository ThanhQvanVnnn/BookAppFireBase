package com.phungthanhquan.bookapp.View.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.phungthanhquan.bookapp.Model.ConfigPaypal;
import com.phungthanhquan.bookapp.Model.ConnectAPI.BuildAPI;
import com.phungthanhquan.bookapp.Model.ConnectAPI.DownloadAPI;
import com.phungthanhquan.bookapp.Model.ConnectAPI.DownloadMethodAPI;
import com.phungthanhquan.bookapp.Model.Room.DbRoomAccess;
import com.phungthanhquan.bookapp.Object.BookCase;
import com.phungthanhquan.bookapp.Object.LichSuGiaoDich;
import com.phungthanhquan.bookapp.Object.User;
import com.phungthanhquan.bookapp.Object.UserRent;
import com.phungthanhquan.bookapp.R;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import dmax.dialog.SpotsDialog;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HinhThucThanhToan extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout taiKhoanChinh, taikhoanpaypal;
    private Dialog dialog;
    private TextView Textexit, price_textview, budget_textview, book_name, time_textview,textView_vnd;
    private ImageView book_image;
    private FirebaseFirestore firebaseFirestore;
    String BOOK_ID, BOOK_NAME, IMAGE, RENT_NAME, RENT_ID;
    int RENT_TIME;
    Double RENT_PRICE;
    private User user;
    DecimalFormat df;
    private SimpleDateFormat dateFormatter;
    public AlertDialog loadingDialog;
    Calendar cal;
    Toast toast;
    private Call<ResponseBody> call;
    private Callback<ResponseBody> callback;
    private DownloadMethodAPI downloadMethodAPI;

    public static final int PAYPAL_REQUEST_CODE = 7171;
    private static PayPalConfiguration config = new PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(ConfigPaypal.CLLENT_PAYPAL);
    private PayPalPayment payPalPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hinh_thuc_thanh_toan);
        initControls();
        Intent intent = new Intent(this,PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
        startService(intent);
        getdata();
        taiKhoanChinh.setOnClickListener(this);
        taikhoanpaypal.setOnClickListener(this);
        Textexit.setOnClickListener(this);
    }

    private void getdata() {
        firebaseFirestore.collection("user").document(FirebaseAuth.getInstance().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    user.setBudget(documentSnapshot.getDouble("budget"));
                    user.setEmail(documentSnapshot.getString("email"));
                    user.setName(documentSnapshot.getString("name"));
                    user.setUser_id(documentSnapshot.getId());
                    user.setPhone(documentSnapshot.getString("phone"));
                }
                showData();
            }
        });
    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(this,PayPalService.class));
        super.onDestroy();
    }

    private void showData() {
        price_textview.setText(df.format(RENT_PRICE) + "");
        book_name.setText(BOOK_NAME);
        Picasso.get().load(IMAGE).into(book_image);
        if (RENT_NAME.equals("v")) {
            time_textview.setText(getString(R.string.vinh_vien));
            time_textview.setTextColor(getResources().getColor(R.color.damuasach));
        } else {
            time_textview.setText(RENT_NAME);
            time_textview.setTextColor(getResources().getColor(R.color.colorPrimary));
        }
        budget_textview.setText(df.format(user.getBudget()) + "");
        if (user.getBudget() >= RENT_PRICE) {
            budget_textview.setTextColor(getResources().getColor(R.color.damuasach));
            textView_vnd.setTextColor(getResources().getColor(R.color.damuasach));
        } else {
            textView_vnd.setTextColor(getResources().getColor(R.color.search_background));
            budget_textview.setTextColor(getResources().getColor(R.color.search_background));
        }
    }

    private void initControls() {
        taiKhoanChinh = findViewById(R.id.thanhtoanquataikhoanchinh);
        taikhoanpaypal = findViewById(R.id.thanhtoanquapaypal);
        Textexit = findViewById(R.id.exit_thanhtoan);
        price_textview = findViewById(R.id.book_price);
        budget_textview = findViewById(R.id.budget);
        book_image = findViewById(R.id.image_book);
        book_name = findViewById(R.id.book_name);
        time_textview = findViewById(R.id.thoihan);
        textView_vnd = findViewById(R.id.vnd);
        loadingDialog = new SpotsDialog.Builder().setContext(this).build();
        loadingDialog.setMessage(getResources().getString(R.string.vuilongcho));
        user = new User();
        cal = null;
        df = new DecimalFormat("###,###.###");
        dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        df.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.ITALY));
        Intent intent = getIntent();
        BOOK_ID = intent.getStringExtra("book_id");
        BOOK_NAME = intent.getStringExtra("book_name");
        IMAGE = intent.getStringExtra("book_image");
        RENT_NAME = intent.getStringExtra("rent_name");
        RENT_PRICE = intent.getDoubleExtra("rent_price", 0);
        RENT_ID = intent.getStringExtra("rent_id");
        RENT_TIME = intent.getIntExtra("ren_time", 0);
        cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, RENT_TIME);
        firebaseFirestore = FirebaseFirestore.getInstance();
        downloadMethodAPI = DownloadAPI.getApiDownload(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
                mua.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loadingDialog.show();
                        Double sodu = user.getBudget() - RENT_PRICE;
                        user.setBudget(sodu);
                        firebaseFirestore.collection("user").document(user.getUser_id()).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                BookCase bookCasegetfromDB = null;
                                try {
                                    /* kiểm tra sách có tồn tại trong tủ sách chưa*/   bookCasegetfromDB = DbRoomAccess.getInstance(HinhThucThanhToan.this).getBookCaseByIDTask(HinhThucThanhToan.this,BOOK_ID);
                                } catch (ExecutionException e) {
                                    e.printStackTrace();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                if (RENT_NAME.equals("v")) {
                                    final BookCase bookCase = new BookCase();
                                    bookCase.setBook_id(BOOK_ID);
                                    bookCase.setBought(true);
                                    bookCase.setBook_image(IMAGE);
                                    bookCase.setUser_id(user.getUser_id());
                                    if(bookCasegetfromDB!=null) /*có tồn tại trong tủ sách*/{
                                        final BookCase finalBookCasegetfromDB = bookCasegetfromDB;
                                        firebaseFirestore.collection("user_bookcase").document(bookCasegetfromDB.getId()).set(bookCase).addOnSuccessListener(new OnSuccessListener<Void>() {

                                            @Override
                                            public void onSuccess(Void documentReference) {
                                                bookCase.setId(finalBookCasegetfromDB.getId());
                                                DbRoomAccess.getInstance(HinhThucThanhToan.this).updateBookCaseTask(HinhThucThanhToan.this, bookCase);
                                                //thêm lịch sử giao dịch
                                                LichSuGiaoDich lichSuGiaoDich = new LichSuGiaoDich();
                                                lichSuGiaoDich.setFrom_budget("budget");
                                                lichSuGiaoDich.setMoney(RENT_PRICE);
                                                lichSuGiaoDich.setTransaction_category("v");
                                                lichSuGiaoDich.setUser_id(user.getUser_id());
                                                lichSuGiaoDich.setRent_time("Vĩnh viễn");
                                                lichSuGiaoDich.setEntity(BOOK_NAME);
                                                Date now = new Date();
                                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm - dd/MM/yyyy");
                                                String time = simpleDateFormat.format(now);
                                                Log.d("Timehientai", time);
                                                lichSuGiaoDich.setTime(time);
                                                firebaseFirestore.collection("transaction_history").add(lichSuGiaoDich).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                    @Override
                                                    public void onSuccess(DocumentReference documentReference) {
                                                        dialog.dismiss();
                                                        Intent data = new Intent();
                                                        String text = "Result to be returned....";
                                                        data.setData(Uri.parse(text));
                                                        setResult(RESULT_OK, data);
                                                        finish();
                                                        loadingDialog.dismiss();
                                                    }
                                                });
                                                //kết thúc thêm lịch sử giao dịch

                                            }
                                        });

                                    }else /*chưa tồn tại trong tủ sách*/ {
                                        firebaseFirestore.collection("user_bookcase").add(bookCase).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                                bookCase.setId(documentReference.getId());
                                                DbRoomAccess.getInstance(HinhThucThanhToan.this).insertBookCaseTask(HinhThucThanhToan.this, bookCase);
                                                //thêm lịch sử giao dịch
                                                LichSuGiaoDich lichSuGiaoDich = new LichSuGiaoDich();
                                                lichSuGiaoDich.setFrom_budget("budget");
                                                lichSuGiaoDich.setMoney(RENT_PRICE);
                                                lichSuGiaoDich.setTransaction_category("v");
                                                lichSuGiaoDich.setUser_id(user.getUser_id());
                                                lichSuGiaoDich.setRent_time("Vĩnh viễn");
                                                lichSuGiaoDich.setEntity(BOOK_NAME);
                                                Date now = new Date();
                                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm - dd/MM/yyyy");
                                                String time = simpleDateFormat.format(now);
                                                Log.d("Timehientai", time);
                                                lichSuGiaoDich.setTime(time);
                                                firebaseFirestore.collection("transaction_history").add(lichSuGiaoDich).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                    @Override
                                                    public void onSuccess(DocumentReference documentReference) {
                                                        dialog.dismiss();
                                                        Intent data = new Intent();
                                                        String text = "Result to be returned....";
                                                        data.setData(Uri.parse(text));
                                                        setResult(RESULT_OK, data);
                                                        finish();
                                                        loadingDialog.dismiss();
                                                    }
                                                });
                                                //kết thúc thêm lịch sử giao dịch
                                            }
                                        });
                                    }
                                } else {
                                    final BookCase bookCase = new BookCase();
                                    bookCase.setBook_id(BOOK_ID);
                                    bookCase.setBought(false);
                                    bookCase.setBook_image(IMAGE);
                                    bookCase.setUser_id(user.getUser_id());
                                    if(bookCasegetfromDB==null) /*chưa tồn tại trong tủ sách*/ {
                                        firebaseFirestore.collection("user_bookcase").add(bookCase).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(final DocumentReference documentReference) {
                                                bookCase.setId(documentReference.getId());
                                                final UserRent userrent = new UserRent();
                                                userrent.setRent_id(RENT_ID);
                                                userrent.setUser_id(user.getUser_id());
                                                userrent.setTime_rest((dateFormatter.format(cal.getTime())));

                                                firebaseFirestore.collection("user_rent").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                                    @Override
                                                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                                        boolean dathue = false;
                                                        String id = "";
                                                        for (QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots) {
                                                            if (queryDocumentSnapshot.exists()) {
                                                                if (queryDocumentSnapshot.get("user_id").equals(user.getUser_id())) {
                                                                    id = queryDocumentSnapshot.getId();
                                                                    dathue = true;
                                                                    break;
                                                                }
                                                            }
                                                        }
                                                        if (dathue) {
                                                            final String finalId = id;
                                                            firebaseFirestore.collection("user_rent").document(id).set(userrent).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void aVoid) {
                                                                    userrent.setId(finalId);
                                                                    DbRoomAccess.getInstance(HinhThucThanhToan.this).insertBookCaseTask(HinhThucThanhToan.this, bookCase);
                                                                    DbRoomAccess.getInstance(HinhThucThanhToan.this).updateUserRentTask(HinhThucThanhToan.this, userrent);
                                                                    //thêm lịch sử giao dịch
                                                                    LichSuGiaoDich lichSuGiaoDich = new LichSuGiaoDich();
                                                                    lichSuGiaoDich.setFrom_budget("budget");
                                                                    lichSuGiaoDich.setMoney(RENT_PRICE);
                                                                    lichSuGiaoDich.setTransaction_category("t");
                                                                    lichSuGiaoDich.setUser_id(user.getUser_id());
                                                                    lichSuGiaoDich.setRent_time(RENT_NAME);
                                                                    lichSuGiaoDich.setEntity(BOOK_NAME);
                                                                    Date now = new Date();
                                                                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm - dd/MM/yyyy");
                                                                    String time = simpleDateFormat.format(now);
                                                                    Log.d("Timehientai", time);
                                                                    lichSuGiaoDich.setTime(time);
                                                                    firebaseFirestore.collection("transaction_history").add(lichSuGiaoDich).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                                        @Override
                                                                        public void onSuccess(DocumentReference documentReference) {
                                                                            dialog.dismiss();
                                                                            Intent data = new Intent();
                                                                            String text = "Result to be returned....";
                                                                            data.setData(Uri.parse(text));
                                                                            setResult(RESULT_OK, data);
                                                                            finish();
                                                                            loadingDialog.dismiss();
                                                                        }
                                                                    });
                                                                    //kết thúc thêm lịch sử giao dịch
                                                                }
                                                            });
                                                        } else {
                                                            firebaseFirestore.collection("user_rent").add(userrent).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                                @Override
                                                                public void onSuccess(DocumentReference documentReference1) {
                                                                    userrent.setId(documentReference1.getId());
                                                                    DbRoomAccess.getInstance(HinhThucThanhToan.this).insertBookCaseTask(HinhThucThanhToan.this, bookCase);
                                                                    DbRoomAccess.getInstance(HinhThucThanhToan.this).insertUserRentTask(HinhThucThanhToan.this, userrent);
                                                                    //thêm lịch sử giao dịch
                                                                    LichSuGiaoDich lichSuGiaoDich = new LichSuGiaoDich();
                                                                    lichSuGiaoDich.setFrom_budget("budget");
                                                                    lichSuGiaoDich.setMoney(RENT_PRICE);
                                                                    lichSuGiaoDich.setTransaction_category("t");
                                                                    lichSuGiaoDich.setUser_id(user.getUser_id());
                                                                    lichSuGiaoDich.setRent_time(RENT_NAME);
                                                                    lichSuGiaoDich.setEntity(BOOK_NAME);
                                                                    Date now = new Date();
                                                                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm - dd/MM/yyyy");
                                                                    String time = simpleDateFormat.format(now);
                                                                    Log.d("Timehientai", time);
                                                                    lichSuGiaoDich.setTime(time);
                                                                    firebaseFirestore.collection("transaction_history").add(lichSuGiaoDich).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                                        @Override
                                                                        public void onSuccess(DocumentReference documentReference) {
                                                                            dialog.dismiss();
                                                                            Intent data = new Intent();
                                                                            String text = "Result to be returned....";
                                                                            data.setData(Uri.parse(text));
                                                                            setResult(RESULT_OK, data);
                                                                            finish();
                                                                            loadingDialog.dismiss();
                                                                        }
                                                                    });
                                                                    //kết thúc thêm lịch sử giao dịch
                                                                }
                                                            });
                                                        }
                                                    }
                                                });
                                            }
                                        });
                                    }else /*đã tồn tại trong tủ sách*/{
                                        final BookCase finalBookCasegetfromDB = bookCasegetfromDB;
                                        firebaseFirestore.collection("user_bookcase").document(bookCasegetfromDB.getId()).set(bookCase).addOnSuccessListener(new OnSuccessListener<Void>() {

                                            @Override
                                            public void onSuccess(Void documentReference) {
                                                bookCase.setId(finalBookCasegetfromDB.getId());
                                                DbRoomAccess.getInstance(HinhThucThanhToan.this).updateBookCaseTask(HinhThucThanhToan.this, bookCase);
                                                //thêm lịch sử giao dịch
                                                LichSuGiaoDich lichSuGiaoDich = new LichSuGiaoDich();
                                                lichSuGiaoDich.setFrom_budget("budget");
                                                lichSuGiaoDich.setMoney(RENT_PRICE);
                                                lichSuGiaoDich.setTransaction_category("t");
                                                lichSuGiaoDich.setUser_id(user.getUser_id());
                                                lichSuGiaoDich.setRent_time(RENT_NAME);
                                                lichSuGiaoDich.setEntity(BOOK_NAME);
                                                Date now = new Date();
                                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm - dd/MM/yyyy");
                                                String time = simpleDateFormat.format(now);
                                                Log.d("Timehientai", time);
                                                lichSuGiaoDich.setTime(time);
                                                firebaseFirestore.collection("transaction_history").add(lichSuGiaoDich).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                    @Override
                                                    public void onSuccess(DocumentReference documentReference) {
                                                        dialog.dismiss();
                                                        Intent data = new Intent();
                                                        String text = "Result to be returned....";
                                                        data.setData(Uri.parse(text));
                                                        setResult(RESULT_OK, data);
                                                        finish();
                                                        loadingDialog.dismiss();
                                                    }
                                                });
                                                //kết thúc thêm lịch sử giao dịch
                                            }
                                        });

                                        firebaseFirestore.collection("user_rent").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                            @Override
                                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                                boolean dathue = false;
                                                String id = "";
                                                for (QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots) {
                                                    if (queryDocumentSnapshot.exists()) {
                                                        if (queryDocumentSnapshot.get("user_id").equals(user.getUser_id())) {
                                                            id = queryDocumentSnapshot.getId();
                                                            dathue = true;
                                                            break;
                                                        }
                                                    }
                                                }
                                                final UserRent userrent = new UserRent();
                                                userrent.setRent_id(RENT_ID);
                                                userrent.setUser_id(user.getUser_id());
                                                userrent.setTime_rest((dateFormatter.format(cal.getTime())));
                                                if (dathue) {
                                                    final String finalId = id;
                                                    firebaseFirestore.collection("user_rent").document(id).set(userrent).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            userrent.setId(finalId);
                                                            DbRoomAccess.getInstance(HinhThucThanhToan.this).updateUserRentTask(HinhThucThanhToan.this, userrent);
                                                            //thêm lịch sử giao dịch
                                                            LichSuGiaoDich lichSuGiaoDich = new LichSuGiaoDich();
                                                            lichSuGiaoDich.setFrom_budget("budget");
                                                            lichSuGiaoDich.setMoney(RENT_PRICE);
                                                            lichSuGiaoDich.setTransaction_category("t");
                                                            lichSuGiaoDich.setUser_id(user.getUser_id());
                                                            lichSuGiaoDich.setRent_time(RENT_NAME);
                                                            lichSuGiaoDich.setEntity(BOOK_NAME);
                                                            Date now = new Date();
                                                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm - dd/MM/yyyy");
                                                            String time = simpleDateFormat.format(now);
                                                            Log.d("Timehientai", time);
                                                            lichSuGiaoDich.setTime(time);
                                                            firebaseFirestore.collection("transaction_history").add(lichSuGiaoDich).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                                @Override
                                                                public void onSuccess(DocumentReference documentReference) {
                                                                    dialog.dismiss();
                                                                    Intent data = new Intent();
                                                                    String text = "Result to be returned....";
                                                                    data.setData(Uri.parse(text));
                                                                    setResult(RESULT_OK, data);
                                                                    finish();
                                                                    loadingDialog.dismiss();
                                                                }
                                                            });
                                                            //kết thúc thêm lịch sử giao dịch
                                                        }
                                                    });
                                                } else {
                                                    firebaseFirestore.collection("user_rent").add(userrent).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                        @Override
                                                        public void onSuccess(DocumentReference documentReference1) {
                                                            userrent.setId(documentReference1.getId());
                                                            DbRoomAccess.getInstance(HinhThucThanhToan.this).insertUserRentTask(HinhThucThanhToan.this, userrent);
                                                            //thêm lịch sử giao dịch
                                                            LichSuGiaoDich lichSuGiaoDich = new LichSuGiaoDich();
                                                            lichSuGiaoDich.setFrom_budget("budget");
                                                            lichSuGiaoDich.setMoney(RENT_PRICE);
                                                            lichSuGiaoDich.setTransaction_category("t");
                                                            lichSuGiaoDich.setUser_id(user.getUser_id());
                                                            lichSuGiaoDich.setRent_time(RENT_NAME);
                                                            lichSuGiaoDich.setEntity(BOOK_NAME);
                                                            Date now = new Date();
                                                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm - dd/MM/yyyy");
                                                            String time = simpleDateFormat.format(now);
                                                            Log.d("Timehientai", time);
                                                            lichSuGiaoDich.setTime(time);
                                                            firebaseFirestore.collection("transaction_history").add(lichSuGiaoDich).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                                @Override
                                                                public void onSuccess(DocumentReference documentReference) {
                                                                    dialog.dismiss();
                                                                    Intent data = new Intent();
                                                                    String text = "Result to be returned....";
                                                                    data.setData(Uri.parse(text));
                                                                    setResult(RESULT_OK, data);
                                                                    finish();
                                                                    loadingDialog.dismiss();
                                                                }
                                                            });
                                                            //kết thúc thêm lịch sử giao dịch
                                                        }
                                                    });
                                                }
                                            }
                                        });

                                    }
                                }
                                showAToast(getString(R.string.thanh_toan_thanh_cong));
                            }
                        });
                    }

                });
                dialog.show();
                break;
            case R.id.thanhtoanquapaypal:
//                loadingDialog.show();
                processPayPall();
                break;
            case R.id.exit_thanhtoan:
                finish();
                break;
        }
    }

    private void processPayPall()  {
//        String string= callURL("http://dongabank.com.vn/exchange/export");
//
//        downloadMethodAPI.ConvertToDollar();
        Call<String> call = downloadMethodAPI.ConvertToDollar();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

               Log.d("jsonconvert", response.body()+"");

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("jsonconvert", t.getMessage());
            }
        });
//        Double price_usd = RENT_PRICE/23140;
//        payPalPayment = new PayPalPayment(new BigDecimal(String.valueOf(price_usd))
//                ,"USD","Thanh toán Cho STUBO", PayPalPayment.PAYMENT_INTENT_SALE);
//        Intent intent = new Intent(this, PaymentActivity.class);
//        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
//        intent.putExtra(PaymentActivity.EXTRA_PAYMENT,payPalPayment);
//        startActivityForResult(intent,PAYPAL_REQUEST_CODE);
    }
    public static String callURL(String myURL) {
        System.out.println("Requeted URL:" + myURL);
        StringBuilder sb = new StringBuilder();
        URLConnection urlConn = null;
        InputStreamReader in = null;
        try {
            URL url = new URL(myURL);
            urlConn = url.openConnection();
            if (urlConn != null)
                urlConn.setReadTimeout(60 * 1000);
            if (urlConn != null && urlConn.getInputStream() != null) {
                in = new InputStreamReader(urlConn.getInputStream(),
                        Charset.defaultCharset());
                BufferedReader bufferedReader = new BufferedReader(in);
                if (bufferedReader != null) {
                    int cp;
                    while ((cp = bufferedReader.read()) != -1) {
                        sb.append((char) cp);
                    }
                    bufferedReader.close();
                }
            }
            in.close();
        } catch (Exception e) {
            throw new RuntimeException("Exception while calling URL:"+ myURL, e);
        }

        return sb.toString();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == PAYPAL_REQUEST_CODE){
            if(resultCode == RESULT_OK){
                PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if(confirmation !=null){
                    BookCase bookCasegetfromDB = null;
                    try {
                        /* kiểm tra sách có tồn tại trong tủ sách chưa*/   bookCasegetfromDB = DbRoomAccess.getInstance(HinhThucThanhToan.this).getBookCaseByIDTask(HinhThucThanhToan.this,BOOK_ID);
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (RENT_NAME.equals("v")) {
                        final BookCase bookCase = new BookCase();
                        bookCase.setBook_id(BOOK_ID);
                        bookCase.setBought(true);
                        bookCase.setBook_image(IMAGE);
                        bookCase.setUser_id(user.getUser_id());
                        if(bookCasegetfromDB!=null) /*có tồn tại trong tủ sách*/{
                            final BookCase finalBookCasegetfromDB = bookCasegetfromDB;
                            firebaseFirestore.collection("user_bookcase").document(bookCasegetfromDB.getId()).set(bookCase).addOnSuccessListener(new OnSuccessListener<Void>() {

                                @Override
                                public void onSuccess(Void documentReference) {
                                    bookCase.setId(finalBookCasegetfromDB.getId());
                                    DbRoomAccess.getInstance(HinhThucThanhToan.this).updateBookCaseTask(HinhThucThanhToan.this, bookCase);
                                    //thêm lịch sử giao dịch
                                    LichSuGiaoDich lichSuGiaoDich = new LichSuGiaoDich();
                                    lichSuGiaoDich.setFrom_budget("paypal");
                                    lichSuGiaoDich.setMoney(RENT_PRICE);
                                    lichSuGiaoDich.setTransaction_category("v");
                                    lichSuGiaoDich.setUser_id(user.getUser_id());
                                    lichSuGiaoDich.setRent_time("Vĩnh viễn");
                                    lichSuGiaoDich.setEntity(BOOK_NAME);
                                    Date now = new Date();
                                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm - dd/MM/yyyy");
                                    String time = simpleDateFormat.format(now);
                                    Log.d("Timehientai", time);
                                    lichSuGiaoDich.setTime(time);
                                    firebaseFirestore.collection("transaction_history").add(lichSuGiaoDich).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            Intent data = new Intent();
                                            String text = "Result to be returned....";
                                            data.setData(Uri.parse(text));
                                            setResult(RESULT_OK, data);
                                            finish();
                                            loadingDialog.dismiss();
                                        }
                                    });
                                    //kết thúc thêm lịch sử giao dịch
                                }
                            });

                        }else /*chưa tồn tại trong tủ sách*/ {
                            firebaseFirestore.collection("user_bookcase").add(bookCase).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    bookCase.setId(documentReference.getId());
                                    DbRoomAccess.getInstance(HinhThucThanhToan.this).insertBookCaseTask(HinhThucThanhToan.this, bookCase);
                                    //thêm lịch sử giao dịch
                                    LichSuGiaoDich lichSuGiaoDich = new LichSuGiaoDich();
                                    lichSuGiaoDich.setFrom_budget("paypal");
                                    lichSuGiaoDich.setMoney(RENT_PRICE);
                                    lichSuGiaoDich.setTransaction_category("v");
                                    lichSuGiaoDich.setUser_id(user.getUser_id());
                                    lichSuGiaoDich.setRent_time("Vĩnh viễn");
                                    lichSuGiaoDich.setEntity(BOOK_NAME);
                                    Date now = new Date();
                                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm - dd/MM/yyyy");
                                    String time = simpleDateFormat.format(now);
                                    Log.d("Timehientai", time);
                                    lichSuGiaoDich.setTime(time);
                                    firebaseFirestore.collection("transaction_history").add(lichSuGiaoDich).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            Intent data = new Intent();
                                            String text = "Result to be returned....";
                                            data.setData(Uri.parse(text));
                                            setResult(RESULT_OK, data);
                                            finish();
                                            loadingDialog.dismiss();
                                        }
                                    });
                                    //kết thúc thêm lịch sử giao dịch
                                }
                            });
                        }
                    } else {
                        final BookCase bookCase = new BookCase();
                        bookCase.setBook_id(BOOK_ID);
                        bookCase.setBought(false);
                        bookCase.setBook_image(IMAGE);
                        bookCase.setUser_id(user.getUser_id());
                        if(bookCasegetfromDB==null) /*chưa tồn tại trong tủ sách*/ {
                            firebaseFirestore.collection("user_bookcase").add(bookCase).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(final DocumentReference documentReference) {
                                    bookCase.setId(documentReference.getId());
                                    final UserRent userrent = new UserRent();
                                    userrent.setRent_id(RENT_ID);
                                    userrent.setUser_id(user.getUser_id());
                                    userrent.setTime_rest((dateFormatter.format(cal.getTime())));

                                    firebaseFirestore.collection("user_rent").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                        @Override
                                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                            boolean dathue = false;
                                            String id = "";
                                            for (QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots) {
                                                if (queryDocumentSnapshot.exists()) {
                                                    if (queryDocumentSnapshot.get("user_id").equals(user.getUser_id())) {
                                                        id = queryDocumentSnapshot.getId();
                                                        dathue = true;
                                                        break;
                                                    }
                                                }
                                            }
                                            if (dathue) {
                                                final String finalId = id;
                                                firebaseFirestore.collection("user_rent").document(id).set(userrent).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        userrent.setId(finalId);
                                                        DbRoomAccess.getInstance(HinhThucThanhToan.this).insertBookCaseTask(HinhThucThanhToan.this, bookCase);
                                                        DbRoomAccess.getInstance(HinhThucThanhToan.this).updateUserRentTask(HinhThucThanhToan.this, userrent);
                                                        //thêm lịch sử giao dịch
                                                        LichSuGiaoDich lichSuGiaoDich = new LichSuGiaoDich();
                                                        lichSuGiaoDich.setFrom_budget("paypal");
                                                        lichSuGiaoDich.setMoney(RENT_PRICE);
                                                        lichSuGiaoDich.setTransaction_category("t");
                                                        lichSuGiaoDich.setUser_id(user.getUser_id());
                                                        lichSuGiaoDich.setRent_time(RENT_NAME);
                                                        lichSuGiaoDich.setEntity(BOOK_NAME);
                                                        Date now = new Date();
                                                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm - dd/MM/yyyy");
                                                        String time = simpleDateFormat.format(now);
                                                        Log.d("Timehientai", time);
                                                        lichSuGiaoDich.setTime(time);
                                                        firebaseFirestore.collection("transaction_history").add(lichSuGiaoDich).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                            @Override
                                                            public void onSuccess(DocumentReference documentReference) {
                                                                Intent data = new Intent();
                                                                String text = "Result to be returned....";
                                                                data.setData(Uri.parse(text));
                                                                setResult(RESULT_OK, data);
                                                                finish();
                                                                loadingDialog.dismiss();
                                                            }
                                                        });
                                                        //kết thúc thêm lịch sử giao dịch
                                                    }
                                                });
                                            } else {
                                                firebaseFirestore.collection("user_rent").add(userrent).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                    @Override
                                                    public void onSuccess(DocumentReference documentReference1) {
                                                        userrent.setId(documentReference1.getId());
                                                        DbRoomAccess.getInstance(HinhThucThanhToan.this).insertBookCaseTask(HinhThucThanhToan.this, bookCase);
                                                        DbRoomAccess.getInstance(HinhThucThanhToan.this).insertUserRentTask(HinhThucThanhToan.this, userrent);
                                                        //thêm lịch sử giao dịch
                                                        LichSuGiaoDich lichSuGiaoDich = new LichSuGiaoDich();
                                                        lichSuGiaoDich.setFrom_budget("paypal");
                                                        lichSuGiaoDich.setMoney(RENT_PRICE);
                                                        lichSuGiaoDich.setTransaction_category("t");
                                                        lichSuGiaoDich.setUser_id(user.getUser_id());
                                                        lichSuGiaoDich.setRent_time(RENT_NAME);
                                                        lichSuGiaoDich.setEntity(BOOK_NAME);
                                                        Date now = new Date();
                                                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm - dd/MM/yyyy");
                                                        String time = simpleDateFormat.format(now);
                                                        Log.d("Timehientai", time);
                                                        lichSuGiaoDich.setTime(time);
                                                        firebaseFirestore.collection("transaction_history").add(lichSuGiaoDich).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                            @Override
                                                            public void onSuccess(DocumentReference documentReference) {
                                                                Intent data = new Intent();
                                                                String text = "Result to be returned....";
                                                                data.setData(Uri.parse(text));
                                                                setResult(RESULT_OK, data);
                                                                finish();
                                                                loadingDialog.dismiss();
                                                            }
                                                        });
                                                        //kết thúc thêm lịch sử giao dịch
                                                    }
                                                });
                                            }
                                        }
                                    });
                                }
                            });
                        }else /*đã tồn tại trong tủ sách*/{
                            final BookCase finalBookCasegetfromDB = bookCasegetfromDB;
                            firebaseFirestore.collection("user_bookcase").document(bookCasegetfromDB.getId()).set(bookCase).addOnSuccessListener(new OnSuccessListener<Void>() {

                                @Override
                                public void onSuccess(Void documentReference) {
                                    bookCase.setId(finalBookCasegetfromDB.getId());
                                    DbRoomAccess.getInstance(HinhThucThanhToan.this).updateBookCaseTask(HinhThucThanhToan.this, bookCase);
                                    //thêm lịch sử giao dịch
                                    LichSuGiaoDich lichSuGiaoDich = new LichSuGiaoDich();
                                    lichSuGiaoDich.setFrom_budget("paypal");
                                    lichSuGiaoDich.setMoney(RENT_PRICE);
                                    lichSuGiaoDich.setTransaction_category("t");
                                    lichSuGiaoDich.setUser_id(user.getUser_id());
                                    lichSuGiaoDich.setRent_time(RENT_NAME);
                                    lichSuGiaoDich.setEntity(BOOK_NAME);
                                    Date now = new Date();
                                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm - dd/MM/yyyy");
                                    String time = simpleDateFormat.format(now);
                                    Log.d("Timehientai", time);
                                    lichSuGiaoDich.setTime(time);
                                    firebaseFirestore.collection("transaction_history").add(lichSuGiaoDich).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            Intent data = new Intent();
                                            String text = "Result to be returned....";
                                            data.setData(Uri.parse(text));
                                            setResult(RESULT_OK, data);
                                            finish();
                                            loadingDialog.dismiss();
                                        }
                                    });
                                    //kết thúc thêm lịch sử giao dịch
                                }
                            });

                            firebaseFirestore.collection("user_rent").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                    boolean dathue = false;
                                    String id = "";
                                    for (QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots) {
                                        if (queryDocumentSnapshot.exists()) {
                                            if (queryDocumentSnapshot.get("user_id").equals(user.getUser_id())) {
                                                id = queryDocumentSnapshot.getId();
                                                dathue = true;
                                                break;
                                            }
                                        }
                                    }
                                    final UserRent userrent = new UserRent();
                                    userrent.setRent_id(RENT_ID);
                                    userrent.setUser_id(user.getUser_id());
                                    userrent.setTime_rest((dateFormatter.format(cal.getTime())));
                                    if (dathue) {
                                        final String finalId = id;
                                        firebaseFirestore.collection("user_rent").document(id).set(userrent).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                userrent.setId(finalId);
                                                DbRoomAccess.getInstance(HinhThucThanhToan.this).updateUserRentTask(HinhThucThanhToan.this, userrent);
                                                //thêm lịch sử giao dịch
                                                LichSuGiaoDich lichSuGiaoDich = new LichSuGiaoDich();
                                                lichSuGiaoDich.setFrom_budget("paypal");
                                                lichSuGiaoDich.setMoney(RENT_PRICE);
                                                lichSuGiaoDich.setTransaction_category("t");
                                                lichSuGiaoDich.setUser_id(user.getUser_id());
                                                lichSuGiaoDich.setRent_time(RENT_NAME);
                                                lichSuGiaoDich.setEntity(BOOK_NAME);
                                                Date now = new Date();
                                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm - dd/MM/yyyy");
                                                String time = simpleDateFormat.format(now);
                                                Log.d("Timehientai", time);
                                                lichSuGiaoDich.setTime(time);
                                                firebaseFirestore.collection("transaction_history").add(lichSuGiaoDich).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                    @Override
                                                    public void onSuccess(DocumentReference documentReference) {
                                                        Intent data = new Intent();
                                                        String text = "Result to be returned....";
                                                        data.setData(Uri.parse(text));
                                                        setResult(RESULT_OK, data);
                                                        finish();
                                                        loadingDialog.dismiss();
                                                    }
                                                });
                                                //kết thúc thêm lịch sử giao dịch
                                            }
                                        });
                                    } else {
                                        firebaseFirestore.collection("user_rent").add(userrent).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference1) {
                                                userrent.setId(documentReference1.getId());
                                                DbRoomAccess.getInstance(HinhThucThanhToan.this).insertUserRentTask(HinhThucThanhToan.this, userrent);
                                                //thêm lịch sử giao dịch
                                                LichSuGiaoDich lichSuGiaoDich = new LichSuGiaoDich();
                                                lichSuGiaoDich.setFrom_budget("paypal");
                                                lichSuGiaoDich.setMoney(RENT_PRICE);
                                                lichSuGiaoDich.setTransaction_category("t");
                                                lichSuGiaoDich.setUser_id(user.getUser_id());
                                                lichSuGiaoDich.setRent_time(RENT_NAME);
                                                lichSuGiaoDich.setEntity(BOOK_NAME);
                                                Date now = new Date();
                                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm - dd/MM/yyyy");
                                                String time = simpleDateFormat.format(now);
                                                Log.d("Timehientai", time);
                                                lichSuGiaoDich.setTime(time);
                                                firebaseFirestore.collection("transaction_history").add(lichSuGiaoDich).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                    @Override
                                                    public void onSuccess(DocumentReference documentReference) {
                                                        Intent data = new Intent();
                                                        String text = "Result to be returned....";
                                                        data.setData(Uri.parse(text));
                                                        setResult(RESULT_OK, data);
                                                        finish();
                                                        loadingDialog.dismiss();
                                                    }
                                                });
                                                //kết thúc thêm lịch sử giao dịch
                                            }
                                        });
                                    }
                                }
                            });

                        }
                    }
                }
                showAToast(getString(R.string.thanh_toan_thanh_cong));
            }else if(resultCode == Activity.RESULT_CANCELED){
                showAToast(getString(R.string.thanh_toan_huy));
            }
        }else if(resultCode == PaymentActivity.RESULT_EXTRAS_INVALID){
            showAToast(getString(R.string.thanh_toan_that_bai));
        }
    }
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
}
