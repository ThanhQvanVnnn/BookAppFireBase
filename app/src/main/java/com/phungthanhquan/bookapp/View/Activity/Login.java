package com.phungthanhquan.bookapp.View.Activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.phungthanhquan.bookapp.R;

import java.util.Arrays;

import dmax.dialog.SpotsDialog;

public class Login extends AppCompatActivity implements View.OnClickListener, FirebaseAuth.AuthStateListener {
    private ImageView logoLogin;
    private EditText nhap_userName;
    private EditText nhap_passWord;
    private Button login;
    private TextView fotgotPass;
    private TextView register;
    private ImageView facebook;
    private CallbackManager callbackManager;
    private ImageView google;
    public static GoogleSignInClient mGoogleSignInClient;
    private final int RC_SIGN_IN = 111;
    private FirebaseAuth mAuth;
    public AlertDialog loadingDialog;

    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //facebook
        mAuth = FirebaseAuth.getInstance();
        FacebookSdk.sdkInitialize(this);
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        handleFacebookAccessToken(loginResult.getAccessToken());
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
                .requestIdToken(getString(R.string.default_web_client_id))
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
        loadingDialog = new SpotsDialog.Builder().setContext(this).build();
        loadingDialog.setMessage(getResources().getString(R.string.vuilongcho));
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
                        loadingDialog.show();
                        mAuth.signInWithEmailAndPassword(userName, password)
                                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (!task.isSuccessful()) {
                                            // If sign in fails, display a message to the user.
                                            loadingDialog.dismiss();
                                          showAToast(getString(R.string.dangnhapthatbai));
                                        }
                                    }
                                });
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
                    loadingDialog.show();
                    LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));
                    break;
                case R.id.google:
                    loadingDialog.show();
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

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
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
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately

            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                        } else {
                            // If sign in fails, display a message to the user.

                            Log.d("loisai",task.getException().toString());
                            showAToast(getString(R.string.dangnhapthatbai));
                        }
                        loadingDialog.dismiss();
                    }
                });
    }

    private void handleFacebookAccessToken(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {


                        } else {
                            // If sign in fails, display a message to the user.

                            showAToast(getString(R.string.dangnhapthatbai));
                        }
                        loadingDialog.dismiss();

                        // ...
                    }
                });
    }



    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
//        updateUI(currentUser);
        mAuth.addAuthStateListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(this);
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user!=null) {
            SharedPreferences.Editor editor = getSharedPreferences("User_Info", MODE_PRIVATE).edit();
            editor.apply();
            loadingDialog.dismiss();
            Intent intent = new Intent(Login.this, MainActivity.class);
            startActivity(intent);
            Log.d("kiemtrauser", user.getEmail());
            showAToast(getString(R.string.dangnhapthanhcong));
            finish();
        }
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
