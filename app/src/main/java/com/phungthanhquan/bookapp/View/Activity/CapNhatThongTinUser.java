package com.phungthanhquan.bookapp.View.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.UploadTask;
import com.phungthanhquan.bookapp.Object.User;
import com.phungthanhquan.bookapp.Presenter.Activity.PresenterCapNhatThongTinUser;
import com.phungthanhquan.bookapp.R;
import com.phungthanhquan.bookapp.View.Fragment.DatePickerFragment;
import com.phungthanhquan.bookapp.View.InterfaceView.InterfaceViewActivityCapNhatThongTin;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import dmax.dialog.SpotsDialog;

public class CapNhatThongTinUser extends AppCompatActivity implements InterfaceViewActivityCapNhatThongTin, View.OnClickListener {
    private Toolbar toolbar;
    private EditText editText_hoten, editText_sdt, editText_email;
    private TextView textView_NgaySinh;
    private Button button_capnhat, button_chonngay;
    RadioGroup radioGroup_gioiTinh;
    RadioButton radioButton_nam, radioButton_nu;
    PresenterCapNhatThongTinUser presenterCapNhatThongTinUser;
    SharedPreferences shared;
    User user;
    public AlertDialog loadingDialog;
    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cap_nhat_thong_tin_user);
        initControls();
    }

    private void initControls() {
        toolbar = findViewById(R.id.toolbar);
        editText_hoten = findViewById(R.id.edittext_nhapten);
        editText_email = findViewById(R.id.edittext_nhapeamail);
        editText_sdt = findViewById(R.id.edittext_nhapsdt);
        textView_NgaySinh = findViewById(R.id.texview_ngaysinh);
        button_capnhat = findViewById(R.id.button_capnhatthongtin);
        button_chonngay = findViewById(R.id.button_chonngaysinh);
        radioGroup_gioiTinh = findViewById(R.id.gioitinh);
        loadingDialog = new SpotsDialog.Builder().setContext(this).build();
        loadingDialog.setMessage(getResources().getString(R.string.vuilongcho));
        radioButton_nam = findViewById(R.id.nam);
        radioButton_nu = findViewById(R.id.nu);
        shared = getSharedPreferences("User_Info", MODE_PRIVATE);
        toolbar.setTitle(getString(R.string.cap_nhat_thong_tin_user));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        button_capnhat.setOnClickListener(this);
        button_chonngay.setOnClickListener(this);
        presenterCapNhatThongTinUser = new PresenterCapNhatThongTinUser(this);
        presenterCapNhatThongTinUser.hienThicapNhatUser();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void hienthiThongTinUser(User userReturn) {
        user = userReturn;
        if (user != null) {
            editText_sdt.setText(user.getPhone());
            editText_email.setText(user.getEmail());
            editText_hoten.setText(user.getName());
            textView_NgaySinh.setText(user.getBirth_day());
            if (user.getGender() != null) {
                if (user.getGender()) {
                    radioButton_nu.setChecked(false);
                    radioButton_nam.setChecked(true);
                } else {
                    radioButton_nu.setChecked(true);
                    radioButton_nam.setChecked(false);
                }
            }
        }
        Log.d("userInfo", user.toString());
    }

    @Override
    public void chinhsuaThongTinUser() {
        loadingDialog.dismiss();
        finish();
        showAToast(getString(R.string.cap_nhat_thanh_cong));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_capnhatthongtin:
                if (editText_hoten.getText().toString().equals("")) {
                    showAToast(getString(R.string.khong_bo_trong_ho_ten));
                } else {
                    loadingDialog.show();
                    if (radioButton_nam.isChecked()) {
                        user.setGender(true);
                    } else {
                        user.setGender(false);
                    }
                    user.setName(editText_hoten.getText().toString());
                    user.setPhone(editText_sdt.getText().toString());
                    user.setBirth_day(textView_NgaySinh.getText().toString());
                    presenterCapNhatThongTinUser.capNhatUser(user);
                }
                break;
            case R.id.button_chonngaysinh:
                showDatePickerDialog(v);
                break;
        }
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
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
