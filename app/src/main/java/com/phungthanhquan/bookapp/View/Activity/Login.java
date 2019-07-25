package com.phungthanhquan.bookapp.View.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
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


import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.phungthanhquan.bookapp.Model.Room.DbRoomAccess;
import com.phungthanhquan.bookapp.Object.BookCase;
import com.phungthanhquan.bookapp.Object.User;
import com.phungthanhquan.bookapp.Object.UserRent;
import com.phungthanhquan.bookapp.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class Login extends AppCompatActivity implements View.OnClickListener, FirebaseAuth.AuthStateListener {
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
    private FirebaseFirestore firebaseFirestore;
    private StorageReference root;
    public AlertDialog loadingDialog;

    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //facebook
        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        root = FirebaseStorage.getInstance().getReference();
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
                        loadingDialog.dismiss();
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Log.d("facebook", exception.toString());
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
        if (MainActivity.isNetworkConnected(Login.this)) {
            switch (v.getId()) {
                case R.id.button_Login:
                    String userName = nhap_userName.getText().toString();
                    String password = nhap_passWord.getText().toString();
                    if (userName.equals("") || password.equals("")) {
                        showAToast(getResources().getString(R.string.notempty));
                    } else {
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
        } else {
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
        mAuth.removeAuthStateListener(this);
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
                loadingDialog.dismiss();
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
                            firebaseFirestore.collection("user").document(mAuth.getCurrentUser().getUid())
                                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();
                                        if (document.exists()) {
                                            mAuth.addAuthStateListener(Login.this);
                                        } else {
                                            //
                                            final FirebaseUser user = mAuth.getCurrentUser();
                                            final String uid = mAuth.getUid();
                                            Date now = new Date();
                                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                            String time = simpleDateFormat.format(now);
                                            User userInfo = new User(user.getEmail(), user.getDisplayName(), user.getPhoneNumber(), (double) 0,null,"01/01/1975",time);
                                            userInfo.setUser_id(uid);
                                            firebaseFirestore.collection("user").document(userInfo.getUser_id()).set(userInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(final Void aVoid) {
                                                    final StorageReference imageUser = root.child("images").child("users").child(uid + ".png");
                                                    new AsyncTask<Void, Void, Bitmap>() {

                                                        @Override
                                                        protected Bitmap doInBackground(Void... voids) {
                                                            Bitmap bitmap = getBitmapFromURL(user.getPhotoUrl().toString()+"?sz=300");
                                                            return bitmap;
                                                        }

                                                        @Override
                                                        protected void onPostExecute(Bitmap bitmap) {
                                                            Bitmap icon = bitmap;
                                                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                                            icon.compress(Bitmap.CompressFormat.PNG, 100, baos);
                                                            byte[] data = baos.toByteArray();
                                                            UploadTask uploadTask = imageUser.putBytes(data);
                                                            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                                @Override
                                                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                                    mAuth.addAuthStateListener(Login.this);
                                                                }
                                                            }).addOnFailureListener(new OnFailureListener() {
                                                                @Override
                                                                public void onFailure(@NonNull Exception e) {
                                                                    Log.d("upload", e.toString());
                                                                }
                                                            });
                                                        }
                                                    }.execute();

                                                }

                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.d("register", e.toString());
                                                }
                                            });

                                            //
                                        }
                                    } else {
                                        Log.d("document", "Failed with: ", task.getException());
                                    }
                                }
                            });

                        } else {
                            // If sign in fails, display a message to the user.

                            Log.d("loisai", task.getException().toString());
                            showAToast(getString(R.string.dangnhapthatbai));
                        }
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
                            firebaseFirestore.collection("user").document(mAuth.getCurrentUser().getUid())
                                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();
                                        if (document.exists()) {
                                            mAuth.addAuthStateListener(Login.this);
                                        } else {
                                            //
                                            final FirebaseUser user = mAuth.getCurrentUser();
                                            final String uid = mAuth.getUid();
                                            Date now = new Date();
                                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                            String time = simpleDateFormat.format(now);
                                            User userInfo = new User(user.getEmail(), user.getDisplayName(), user.getPhoneNumber(), (double) 0,null,"01/01/1975",time);
                                            userInfo.setUser_id(uid);
                                            firebaseFirestore.collection("user").document(userInfo.getUser_id()).set(userInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(final Void aVoid) {
                                                    final StorageReference imageUser = root.child("images").child("users").child(uid + ".png");
                                                    new AsyncTask<Void, Void, Bitmap>() {

                                                        @Override
                                                        protected Bitmap doInBackground(Void... voids) {
                                                            Bitmap bitmap = getBitmapFromURL("https://graph.facebook.com/" + user.getProviderData().get(1).getUid() + "/picture?type=large");
                                                            return bitmap;
                                                        }

                                                        @Override
                                                        protected void onPostExecute(Bitmap bitmap) {
                                                            Bitmap icon = bitmap;
                                                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                                            icon.compress(Bitmap.CompressFormat.PNG, 100, baos);
                                                            byte[] data = baos.toByteArray();
                                                            UploadTask uploadTask = imageUser.putBytes(data);
                                                            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                                @Override
                                                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                                    mAuth.addAuthStateListener(Login.this);
                                                                }
                                                            }).addOnFailureListener(new OnFailureListener() {
                                                                @Override
                                                                public void onFailure(@NonNull Exception e) {
                                                                    Log.d("upload", e.toString());
                                                                }
                                                            });
                                                        }
                                                    }.execute();

                                                }

                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.d("register", e.toString());
                                                    loadingDialog.dismiss();
                                                }
                                            });

                                            //
                                        }
                                    } else {
                                        Log.d("document", "Failed with: ", task.getException());
                                        loadingDialog.dismiss();
                                    }
                                }
                            });

                        } else {
                            // If sign in fails, display a message to the user.
                            loadingDialog.dismiss();
                            showAToast(getString(R.string.dangnhapthatbai));
                        }

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
        final FirebaseUser user = firebaseAuth.getCurrentUser();
        getUserInfo(user);
    }

    private void getUserInfo(final FirebaseUser user) {
        if (user != null) {
            final SharedPreferences.Editor editor = getSharedPreferences("User_Info", MODE_PRIVATE).edit();

            root.child("images").child("users").child(user.getUid()+".png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    editor.putString("name",user.getDisplayName());
                    editor.putString("email",user.getEmail());
                    editor.putString("phone",user.getPhoneNumber());
                    editor.putString("id", user.getUid());
                    editor.putString("image",uri.toString());
                    editor.apply();
                    final String user_id = user.getUid();
                    firebaseFirestore.collection("user_bookcase").whereEqualTo("user_id", user_id).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (final QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                                    if (queryDocumentSnapshot.exists()) {
                                        root.child("images").child("books").child(queryDocumentSnapshot.get("book_id")+".png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                BookCase bookCase = new BookCase();
                                                bookCase.setBook_image(uri.toString());
                                                bookCase.setId(queryDocumentSnapshot.getId());
                                                bookCase.setBought(queryDocumentSnapshot.getBoolean("bought"));
                                                bookCase.setUser_id((String) queryDocumentSnapshot.get("user_id"));
                                                bookCase.setBook_id((String) queryDocumentSnapshot.get("book_id"));
                                                DbRoomAccess.getInstance(Login.this).insertBookCaseTask(Login.this,bookCase);
                                            }

                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                            }
                                        });
                                    }

                                }
                                firebaseFirestore.collection("user_rent").whereEqualTo("user_id",user_id).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        List<UserRent> userRentList = new ArrayList<>();
                                        if(task.isSuccessful()){
                                            for(QueryDocumentSnapshot queryDocumentSnapshot1: task.getResult()){
                                                if(queryDocumentSnapshot1.exists()){
                                                    UserRent userRent = new UserRent();
                                                    userRent.setId(queryDocumentSnapshot1.getId());
                                                    userRent.setRent_id(queryDocumentSnapshot1.getString("rent_id"));
                                                    userRent.setTime_rest(queryDocumentSnapshot1.getString("time_rest"));
                                                    userRent.setUser_id(user_id);
                                                    userRentList.add(userRent);
                                                }
                                            }
                                            for(UserRent userRent: userRentList){
                                                DbRoomAccess.getInstance(Login.this).insertUserRentTask(Login.this,userRent);
                                            }
                                        }
                                        Intent intent = new Intent(Login.this, MainActivity.class);
                                        startActivity(intent);
                                        loadingDialog.dismiss();
                                        finish();
                                        showAToast(getString(R.string.dangnhapthanhcong));
                                    }
                                });
                            }
                        }
                    });
                }
            });


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

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }

}
