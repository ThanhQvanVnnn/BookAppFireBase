package com.phungthanhquan.bookapp.View.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.phungthanhquan.bookapp.R;

import dmax.dialog.SpotsDialog;

public class ForGotPass extends AppCompatActivity implements View.OnClickListener {
    private TextView exit;
    private Button gui;
    private EditText email;
    public AlertDialog loadingDialog;
    Toast toast;

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
        loadingDialog = new SpotsDialog.Builder().setContext(this).build();
        loadingDialog.setMessage(getResources().getString(R.string.vuilongcho));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.exit:
                finish();
                break;
            case R.id.button_gui:
                FirebaseAuth auth = FirebaseAuth.getInstance();
                String emailAddress = email.getText().toString();
                if (emailAddress.isEmpty()) {
                    showAToast(getResources().getString(R.string.notempty));
                } else {
                    if (!Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()) {
                        showAToast(getString(R.string.matcheremailsdt));
                    } else {
                        loadingDialog.show();
                        auth.sendPasswordResetEmail(emailAddress)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            showAToast(getString(R.string.resetpass));
                                            loadingDialog.dismiss();
                                            Intent intent = new Intent(ForGotPass.this,Login.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }
                                });
                    }
                }
                break;
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
