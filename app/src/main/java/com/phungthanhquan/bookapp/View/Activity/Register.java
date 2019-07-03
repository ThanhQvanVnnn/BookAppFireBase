package com.phungthanhquan.bookapp.View.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.phungthanhquan.bookapp.Object.User;
import com.phungthanhquan.bookapp.R;

import java.io.ByteArrayOutputStream;
import java.util.concurrent.TimeUnit;

import dmax.dialog.SpotsDialog;

public class Register extends AppCompatActivity implements View.OnClickListener {
    private TextView cancel;
    private EditText userName;
    private EditText passWord;
    private EditText rePassWord;
    private Button register;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;
    private  StorageReference root;
    public AlertDialog loadingDialog;
    String code;
    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
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
        loadingDialog = new SpotsDialog.Builder().setContext(this).build();
        loadingDialog.setMessage(getResources().getString(R.string.vuilongcho));
        firebaseFirestore = FirebaseFirestore.getInstance();
         root = FirebaseStorage.getInstance().getReference();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.exit:
                finish();
                break;
            case R.id.button_register:
                String emailsdt = userName.getText().toString();
                String pass = passWord.getText().toString();
                String repass = rePassWord.getText().toString();
                if(emailsdt.isEmpty() || pass.isEmpty()
                    || repass.isEmpty()){
                    showAToast(getString(R.string.notempty));
                }else {
                    if(!Patterns.EMAIL_ADDRESS.matcher(emailsdt).matches() ){
                        showAToast(getString(R.string.matcheremailsdt));
                    }else {
                        if(pass.length()<5 || repass.length()<5){
                            showAToast(getString(R.string.matkhaulonhon5));
                        }else if(!pass.equals(repass)){
                            showAToast(getString(R.string.matkhaunhaplai));
                        }else {
                            loadingDialog.show();
                            if(Patterns.EMAIL_ADDRESS.matcher(emailsdt).matches())
                            mAuth.createUserWithEmailAndPassword(userName.getText().toString(),passWord.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        final String uid = mAuth.getUid();
                                        User userInfo = new User(user.getEmail(),user.getDisplayName(),user.getPhoneNumber(), (float) 0);
                                        userInfo.setUser_id(uid);
                                        firebaseFirestore.collection("user").document(userInfo.getUser_id()).set(userInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                StorageReference imageUser = root.child("images").child("users").child(uid+".png");
                                                Bitmap icon = BitmapFactory.decodeResource(getResources(),R.drawable.user_icon_default);
                                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                                icon.compress(Bitmap.CompressFormat.PNG, 100, baos);
                                                byte[] data = baos.toByteArray();
                                                UploadTask uploadTask = imageUser.putBytes(data);
                                                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                    @Override
                                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                        finish();
                                                        loadingDialog.dismiss();
                                                        showAToast(getString(R.string.dangkithanhcong));
                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Log.d("upload", e.toString());
                                                    }
                                                });



                                            }

                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.d("register", e.toString());
                                            }
                                        });

                                    }else {
                                        loadingDialog.dismiss();
                                        showAToast(getString(R.string.dangkithatbai));
                                    }
                                }
                            });

                        }
                    }
                }
                break;
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
