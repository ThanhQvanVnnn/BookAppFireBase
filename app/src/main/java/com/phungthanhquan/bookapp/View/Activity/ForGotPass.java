package com.phungthanhquan.bookapp.View.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.phungthanhquan.bookapp.R;

public class ForGotPass extends AppCompatActivity implements View.OnClickListener {
    private TextView exit;
    private Button gui;
    private EditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_got_pass);
        initControls();
        addEventClick();
    }

    private void addEventClick() {
        exit.setOnClickListener(this);
        gui.setOnClickListener(this);
    }

    private void initControls() {
        exit = findViewById(R.id.exit);
        gui = findViewById(R.id.button_gui);
        email = findViewById(R.id.diachiEmail);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.exit:
                finish();
                break;
            case R.id.button_gui:

                break;
        }
    }
}
