package com.phungthanhquan.bookapp.View.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnSuccessListener;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.phungthanhquan.bookapp.Model.ConfigPaypal;
import com.phungthanhquan.bookapp.Object.LichSuGiaoDich;
import com.phungthanhquan.bookapp.Object.User;
import com.phungthanhquan.bookapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NapTaiKhoan extends AppCompatActivity implements View.OnClickListener {

    private EditText editText_nhaptien;
    Double money;
    DecimalFormat df;
    private LinearLayout button_paypal, linearLayout_idthanhtoan, linearLayout_sotiendathanhtoan, linearLayout_trangthai;

    public static final int PAYPAL_REQUEST_CODE = 7171;
    private static PayPalConfiguration config = new PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(ConfigPaypal.CLLENT_PAYPAL);
    private PayPalPayment payPalPayment;
    Toast toast;
    User user;
    FirebaseFirestore firebaseFirestore;
    private TextView thongtinthanhtoantext, sodutaikhoan, trangthaithanhtoan, sotiendathanhtoan, idthanhtoan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nap_tai_khoan);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.nap_tai_khoan));
        df = new DecimalFormat("###,###.###");
        df.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.ITALY));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        initControls();
        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(intent);
        button_paypal.setOnClickListener(this);
        firebaseFirestore.collection("user").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    user.setBudget(documentSnapshot.getDouble("budget"));
                    user.setEmail(documentSnapshot.getString("email"));
                    user.setName(documentSnapshot.getString("name"));
                    user.setUser_id(documentSnapshot.getId());
                    user.setPhone(documentSnapshot.getString("phone"));
                    sodutaikhoan.setText(df.format(user.getBudget()) + " VND");
                }
            }
        });

    }

    private void initControls() {
        editText_nhaptien = findViewById(R.id.nhaptien);
        button_paypal = findViewById(R.id.thanhtoanquapaypal);
        linearLayout_idthanhtoan = findViewById(R.id.layout_mathanhtoan);
        linearLayout_sotiendathanhtoan = findViewById(R.id.layout_sodu);
        linearLayout_trangthai = findViewById(R.id.layout_trangthaithanhtoan);
        sodutaikhoan = findViewById(R.id.sodu_user);
        trangthaithanhtoan = findViewById(R.id.trangthai_thanhtoan);
        sotiendathanhtoan = findViewById(R.id.sotien_thanhtoan);
        idthanhtoan = findViewById(R.id.id_thanhtoan);
        thongtinthanhtoantext = findViewById(R.id.thongtinthanhtoan);
        firebaseFirestore = FirebaseFirestore.getInstance();
        user = new User();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.thanhtoanquapaypal:
                if (editText_nhaptien.getText().toString().equals("")) {
                    showAToast(getString(R.string.notempty));
                } else {
                    money = Double.valueOf(editText_nhaptien.getText().toString());
                    processPayPall(money);
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }

    private void processPayPall(Double money) {
        Double price_usd = money / 23140;
        payPalPayment = new PayPalPayment(new BigDecimal(String.valueOf(price_usd))
                , "USD", getString(R.string.nap_cho_tai_khoan) + " " + FirebaseAuth.getInstance().getCurrentUser().getEmail(), PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payPalPayment);
        startActivityForResult(intent, PAYPAL_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == PAYPAL_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                editText_nhaptien.setText("");
                final PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirmation != null) {
                    thongtinthanhtoantext.setVisibility(View.VISIBLE);
                    linearLayout_idthanhtoan.setVisibility(View.VISIBLE);
                    linearLayout_sotiendathanhtoan.setVisibility(View.VISIBLE);
                    linearLayout_trangthai.setVisibility(View.VISIBLE);
                    Double sotien = user.getBudget() + money;
                    sodutaikhoan.setText(df.format(sotien) + " VND");
                    user.setBudget(sotien);
                    firebaseFirestore.collection("user").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                        }
                    });
                    String paymentDetail = null;
                    JSONObject jsonObject = null;
                    String id = "";
                    String state = "";
                    String moneyreturn = "";
                    try {
                        Double price_usd = money / 23140;
                        paymentDetail = confirmation.toJSONObject().toString(4);
                        JSONObject jsonObjectChinh = new JSONObject(paymentDetail);
                        jsonObject = jsonObjectChinh.getJSONObject("response");
                        id = jsonObject.getString("id");
                        state = jsonObject.getString("state");
                        moneyreturn = df.format(money) + " VND";
                        //thêm lịch sử giao dịch
                        LichSuGiaoDich lichSuGiaoDich = new LichSuGiaoDich();
                        lichSuGiaoDich.setFrom_budget("paypal");
                        lichSuGiaoDich.setMoney(money);
                        lichSuGiaoDich.setTransaction_category("n");
                        lichSuGiaoDich.setUser_id(user.getUser_id());
                        lichSuGiaoDich.setEntity(id);
                        Date now = new Date();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm - dd/MM/yyyy");
                        String time = simpleDateFormat.format(now);
                        Log.d("Timehientai", time);
                        lichSuGiaoDich.setTime(time);
                        firebaseFirestore.collection("transaction_history").add(lichSuGiaoDich).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {

                            }
                        });
                        //kết thúc thêm lịch sử giao dịch

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    trangthaithanhtoan.setText(state);
                    sotiendathanhtoan.setText(moneyreturn);
                    idthanhtoan.setText(id);
                    showAToast(getString(R.string.thanh_toan_thanh_cong));
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                showAToast(getString(R.string.thanh_toan_huy));
                editText_nhaptien.setText("");
            }
        } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
            showAToast(getString(R.string.thanh_toan_that_bai));
            editText_nhaptien.setText("");
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

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
