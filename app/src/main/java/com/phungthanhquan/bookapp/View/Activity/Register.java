package com.phungthanhquan.bookapp.View.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.phungthanhquan.bookapp.R;

public class Register extends AppCompatActivity implements View.OnClickListener {
    private TextView cancel;
    private EditText userName;
    private EditText passWord;
    private EditText rePassWord;
    private Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initControls();
        adEventClicks();
    }

    private void adEventClicks() {
        cancel.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    private void initControls() {
        cancel = findViewById(R.id.exit);
        userName = findViewById(R.id.userName);
        passWord = findViewById(R.id.passWord);
        rePassWord = findViewById(R.id.rePassWord);
        register = findViewById(R.id.button_register);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.exit:
                finish();
                break;
            case R.id.button_register:

                break;
        }
    }
}
