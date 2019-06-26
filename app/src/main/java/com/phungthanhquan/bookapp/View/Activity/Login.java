package com.phungthanhquan.bookapp.View.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;


import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.phungthanhquan.bookapp.R;

import java.util.Arrays;

public class Login extends AppCompatActivity implements View.OnClickListener {
    private ImageView logoLogin;
    private EditText nhap_userName;
    private EditText nhap_passWord;
    private Button login;
    private TextView fotgotPass;
    private TextView register;
    private ImageView facebook;
    private CallbackManager callbackManager;
    private ImageView google;
    private GoogleSignInClient mGoogleSignInClient;
    private final int RC_SIGN_IN = 111;
    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //facebook
        FacebookSdk.sdkInitialize(this);
        AppEventsLogger.activateApp(this);
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });
        //google
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        //
        setContentView(R.layout.activity_login);
        initControls();
        eventClick();

    }

    private void eventClick() {
        login.setOnClickListener(this);
        fotgotPass.setOnClickListener(this);
        register.setOnClickListener(this);
        facebook.setOnClickListener(this);
        google.setOnClickListener(this);
    }

    private void initControls() {
        logoLogin = findViewById(R.id.logo_login);
        nhap_userName = findViewById(R.id.userName);
        nhap_passWord = findViewById(R.id.passWord);
        login = findViewById(R.id.button_Login);
        fotgotPass = findViewById(R.id.texview_fogotPass);
        register = findViewById(R.id.textview_register);
        facebook = findViewById(R.id.facebook);
        google = findViewById(R.id.google);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
         finish();
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        if(MainActivity.isNetworkConnected(Login.this)) {
            switch (v.getId()) {
                case R.id.button_Login:
                    String userName = nhap_userName.getText().toString();
                    String password = nhap_passWord.getText().toString();
                    if(userName.equals("") || password.equals("")){
                       showAToast(getResources().getString(R.string.notempty));
                    }else {
                        ///thông tin user Lấy ở đây

                        //-------------
                        SharedPreferences.Editor editor = getSharedPreferences("User_Info", MODE_PRIVATE).edit();
                        editor.putString("name", "Elena");
                        editor.putInt("idName", 12);
                        editor.apply();
                        intent = new Intent(this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    break;
                case R.id.texview_fogotPass:
                    intent = new Intent(this, ForGotPass.class);
                    startActivity(intent);
                    break;
                case R.id.textview_register:
                    intent = new Intent(this, Register.class);
                    startActivity(intent);
                    break;
                case R.id.facebook:
                    LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));
                    break;
                case R.id.google:
                    signIn();
                    break;
            }
        }else {
            switch (v.getId()) {
                case R.id.button_Login:
                case R.id.texview_fogotPass:
                case R.id.textview_register:
                case R.id.facebook:
                case R.id.google:
                  showAToast(getResources().getString(R.string.openinternet));
                    break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
//            updateUI(null);
        }
    }

    private void updateUI(GoogleSignInAccount account) {
    }
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    public void showAToast (String st){ //"Toast toast" is declared in the class
        try{ toast.getView().isShown();     // true if visible
            toast.setText(st);
        } catch (Exception e) {         // invisible if exception
            toast = Toast.makeText(this, st,  Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
        }
        toast.show();  //finally display it
    }
}
