package com.phungthanhquan.bookapp.View.Activity;

import android.content.Context;
import android.net.ConnectivityManager;
import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.phungthanhquan.bookapp.Model.Room.DbRoomAccess;
import com.phungthanhquan.bookapp.Object.BookCase;
import com.phungthanhquan.bookapp.R;
import com.phungthanhquan.bookapp.View.Fragment.FrgCaNhan;
import com.phungthanhquan.bookapp.View.Fragment.FrgDanhMuc;
import com.phungthanhquan.bookapp.View.Fragment.FrgHoatDong;
import com.phungthanhquan.bookapp.View.Fragment.FrgTrangChu;
import com.phungthanhquan.bookapp.View.Fragment.FrgTuSach;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.annotation.Nullable;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView navigationView;
    private Fragment FrgTrangChu = new FrgTrangChu();
    private Fragment FrgDanhMuc = new FrgDanhMuc();
    private Fragment FrgTuSach = new FrgTuSach();
    private Fragment FrgHoatDong = new FrgHoatDong();
    private Fragment FrgCaNhan = new FrgCaNhan();
    private Fragment FrgActive = FrgTrangChu;
    private FirebaseFirestore firebaseFirestore;
    private StorageReference storageReference;
    private int back = 1 ;
    private String USER_ID;

    final FragmentManager fragmentManager =getSupportFragmentManager();
    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InitControls();
        firebaseFirestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        if(isNetworkConnected(this)){
            navigationView.setSelectedItemId(R.id.menu_trangchu);
        }else {
            navigationView.setSelectedItemId(R.id.menu_tusach);
        }
    }

    private void InitControls() {
        navigationView = findViewById(R.id.navigation);
        USER_ID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        fragmentManager.beginTransaction().add(R.id.frame_container,FrgCaNhan,"5").hide(FrgCaNhan).commit();
        fragmentManager.beginTransaction().add(R.id.frame_container,FrgHoatDong,"4").hide(FrgHoatDong).commit();
        fragmentManager.beginTransaction().add(R.id.frame_container,FrgTuSach,"3").hide(FrgTuSach).commit();
        fragmentManager.beginTransaction().add(R.id.frame_container,FrgDanhMuc,"2").hide(FrgDanhMuc).commit();
        fragmentManager.beginTransaction().add(R.id.frame_container,FrgTrangChu,"1").commit();

        navigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
    }
    BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()){
                case R.id.menu_trangchu:
                    back = 1;
                    fragmentManager.beginTransaction().hide(FrgActive).show(FrgTrangChu).commit();
                    FrgActive = FrgTrangChu;
                    return true;
                case R.id.menu_danhmuc:
                    back = 1;
                    fragmentManager.beginTransaction().hide(FrgActive).show(FrgDanhMuc).commit();
                    FrgActive = FrgDanhMuc;
                    return true;
                case R.id.menu_tusach:
                    back = 1;
                    fragmentManager.beginTransaction().hide(FrgActive).show(FrgTuSach).commit();
                    FrgActive = FrgTuSach;
                    return true;
                case R.id.menu_hoatdong:
                    back = 1;
                    fragmentManager.beginTransaction().hide(FrgActive).show(FrgHoatDong).commit();
                    FrgActive = FrgHoatDong;
                    return true;
                case R.id.menu_canhan:
                    back = 1;
                    fragmentManager.beginTransaction().hide(FrgActive).show(FrgCaNhan).commit();
                    FrgActive = FrgCaNhan;
                    return true;
            }
            return false;
        }
    };



    @Override
    public void onBackPressed() {
        if (back == 1) {
          showAToast(getResources().getString(R.string.nhan_back));
        } else if (back > 1) {
            finish();
        }
        back++;
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        firebaseFirestore.collection("bookcase").whereEqualTo("user_id",USER_ID).addSnapshotListener(new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
//                if(e!=null){
//
//                }
//                if(queryDocumentSnapshots.size()!=0){
//                    DbRoomAccess.getInstance(MainActivity.this).deleteAllBookcaseTask(MainActivity.this);
//                    for(final QueryDocumentSnapshot queryDocumentSnapshot: queryDocumentSnapshots){
//                        storageReference.child("images").child("books").child(queryDocumentSnapshot.get("book_id")+".png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                            @Override
//                            public void onSuccess(Uri uri) {
//                                BookCase bookCase = new BookCase();
//                                bookCase.setBook_image(uri.toString());
//                                bookCase.setId(queryDocumentSnapshot.getId());
//                                bookCase.setBought(queryDocumentSnapshot.getBoolean("bought"));
//                                bookCase.setUser_id((String) queryDocumentSnapshot.get("user_id"));
//                                bookCase.setBook_id((String) queryDocumentSnapshot.get("book_id"));
//                                DbRoomAccess.getInstance(MainActivity.this).insertBookCaseTask(MainActivity.this,bookCase);
//                            }
//
//                        }).addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                            }
//                        });
//                    }
//                }
//            }
//        });
//    }

    //kiểm tra kết nối internet
    public static boolean isNetworkConnected(Context c) {
        ConnectivityManager cm = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
    //kiểm tra có thực sự kết nối được với internet chưa
    public static boolean isInternetAvailable() {
        try {
            InetAddress address = InetAddress.getByName("www.google.com");
            return !address.equals("");
        } catch (UnknownHostException e) {
            // Log error
        }
        return false;
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
