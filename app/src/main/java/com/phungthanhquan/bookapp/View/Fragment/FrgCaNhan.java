package com.phungthanhquan.bookapp.View.Fragment;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.phungthanhquan.bookapp.Model.Room.DbRoomAccess;
import com.phungthanhquan.bookapp.Object.BookCase;
import com.phungthanhquan.bookapp.Object.User;
import com.phungthanhquan.bookapp.Presenter.Fragment.PresenterLogicCaNhan;
import com.phungthanhquan.bookapp.R;
import com.phungthanhquan.bookapp.View.Activity.CapNhatThongTinUser;
import com.phungthanhquan.bookapp.View.Activity.KiemTraGoiThue;
import com.phungthanhquan.bookapp.View.Activity.LichSuGiaoDich;
import com.phungthanhquan.bookapp.View.Activity.ListUserTheoDoi;
import com.phungthanhquan.bookapp.View.Activity.Login;
import com.phungthanhquan.bookapp.View.Activity.MainActivity;
import com.phungthanhquan.bookapp.View.Activity.NapTaiKhoan;
import com.phungthanhquan.bookapp.View.Activity.TuSach_CaNhanClick;
import com.phungthanhquan.bookapp.View.InterfaceView.InterfaceViewFragmentCaNhan;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import dmax.dialog.SpotsDialog;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

public class FrgCaNhan extends Fragment implements View.OnClickListener, InterfaceViewFragmentCaNhan {
    private ImageView anhdaidienBackGround;
    private CircleImageView anhdaidien;
    private TextView tenNguoidung;
    private TextView sotientrongTaiKhoan;
    private TextView tongsoSach;
    private LinearLayout nguoitheodoi;
    private LinearLayout nguoidangtheodoi;
    private LinearLayout sachdadoc;
    private LinearLayout naptaikhoan;
    private LinearLayout lichsugiaodich;
    private LinearLayout dangxuat;
    private LinearLayout capnhatthongtin;
    private LinearLayout kiemtragoithue;
    private TextView songuoitheodoi;
    private TextView songuoidangtheodoi;
    private Dialog dialogChonAnhDaiDien;
    private TextView sosachdadoc;
    public AlertDialog loadingDialog;
    private PresenterLogicCaNhan presenterLogicCaNhan;
    private final int SELECT_IMAGE = 100;
    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private FirebaseAuth mAuth;
    Toast toast;
    SharedPreferences shared;
    StorageReference storageReference;
    FirebaseFirestore firebaseFirestore;
    User user;
    DecimalFormat df;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_canhan,container,false);
        initControls(view);
        setOnclickEvent();
        shared = getContext().getSharedPreferences("User_Info", MODE_PRIVATE);
        storageReference = FirebaseStorage.getInstance().getReference();
        user = new User();

        mAuth = FirebaseAuth.getInstance();
        presenterLogicCaNhan.hienThiThongTinCaNhan();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
       firebaseFirestore.collection("user").document(FirebaseAuth.getInstance().getUid()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
           @Override
           public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot, @javax.annotation.Nullable FirebaseFirestoreException e) {
               if(e!=null){

               }else {
                   tenNguoidung.setText(documentSnapshot.getString("name"));
                   sotientrongTaiKhoan.setText(df.format(documentSnapshot.getDouble("budget")) + " VND");
               }
           }
       });
       firebaseFirestore.collection("friend").whereEqualTo("sender_id",mAuth.getUid()).addSnapshotListener(new EventListener<QuerySnapshot>() {
           @Override
           public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
               if(e != null){

               }else {
                   int soluongtheodoi = 0;
                   for (QueryDocumentSnapshot queryDocumentSnapshot:queryDocumentSnapshots){
                       if(queryDocumentSnapshot.exists()){
                           soluongtheodoi++;
                       }
                   }
                   songuoidangtheodoi.setText(soluongtheodoi+"");
               }
           }
       });

        firebaseFirestore.collection("friend").whereEqualTo("receiver_id",mAuth.getUid()).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                if(e != null){

                }else {
                    int soluongtheodoi = 0;
                    for (QueryDocumentSnapshot queryDocumentSnapshot:queryDocumentSnapshots){
                        if(queryDocumentSnapshot.exists()){
                            soluongtheodoi++;
                        }
                    }
                    songuoitheodoi.setText(soluongtheodoi+"");
                }
            }
        });

       firebaseFirestore.collection("user_bookcase").whereEqualTo("user_id",FirebaseAuth.getInstance().getUid()).addSnapshotListener(new EventListener<QuerySnapshot>() {
           @Override
           public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
               if(e!=null){

               }else {
                   int tongsach = 0;
                   int sachmua = 0;
                   for(QueryDocumentSnapshot queryDocumentSnapshot:queryDocumentSnapshots){
                       if(queryDocumentSnapshot.exists()){
                           if(queryDocumentSnapshot.getBoolean("bought")){
                               sachmua++;
                           }
                               tongsach++;
                       }
                   }
                   tongsoSach.setText(sachmua+"");
                   sosachdadoc.setText(tongsach+"");
               }
           }
       });
    }

    private void setOnclickEvent() {
        nguoitheodoi.setOnClickListener(this);
        nguoidangtheodoi.setOnClickListener(this);
        sachdadoc.setOnClickListener(this);
        naptaikhoan.setOnClickListener(this);
        lichsugiaodich.setOnClickListener(this);
        capnhatthongtin.setOnClickListener(this);
        dangxuat.setOnClickListener(this);
        anhdaidien.setOnClickListener(this);
        kiemtragoithue.setOnClickListener(this);
    }

    private void initControls(View view) {
        anhdaidienBackGround = view.findViewById(R.id.image_background_anhdaidien);
        anhdaidien = view.findViewById(R.id.image_anhdaidien);
        tenNguoidung = view.findViewById(R.id.tenAccount);
        sotientrongTaiKhoan = view.findViewById(R.id.sotientrongtaikhoan);
        tongsoSach = view.findViewById(R.id.tongsosach);
        nguoitheodoi = view.findViewById(R.id.nguoitheodoi);
        nguoidangtheodoi = view.findViewById(R.id.nguoidangtheodoi);
        sachdadoc = view.findViewById(R.id.sachdadoc);
        naptaikhoan = view.findViewById(R.id.naptaikhoan);
        lichsugiaodich = view.findViewById(R.id.lichsugiaodich);
        capnhatthongtin = view.findViewById(R.id.capnhatthongtincanhan);
        dangxuat = view.findViewById(R.id.dangxuat);
        songuoitheodoi = view.findViewById(R.id.soluong_nguoitheodoi);
        songuoidangtheodoi = view.findViewById(R.id.soluong_nguoidangtheodoi);
        sosachdadoc = view.findViewById(R.id.soluong_sachdadoc);
        kiemtragoithue = view.findViewById(R.id.kiemtragoithue);
        firebaseFirestore =  FirebaseFirestore.getInstance();
         df = new DecimalFormat("###,###.###");
        df.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.ITALY));
        presenterLogicCaNhan = new PresenterLogicCaNhan(this);
        presenterLogicCaNhan.LayTuSach(getContext());
        loadingDialog = new SpotsDialog.Builder().setContext(getContext()).build();
        loadingDialog.setMessage(getResources().getString(R.string.vuilongcho));
    }
    @Override
    public void hienThiThongTinCaNhan(User user) {
        Picasso.get().load(shared.getString("image", String.valueOf(R.drawable.user_icon_default))).into(anhdaidienBackGround);
        Picasso.get().load(shared.getString("image",String.valueOf(R.drawable.user_icon_default))).into(anhdaidien);
        tenNguoidung.setText(user.getName());
        sotientrongTaiKhoan.setText(df.format(user.getBudget()) + " VND");
    }

    @Override
    public void hienthithongtintusach(List<BookCase> bookCaseList) {
        int sachdamua = 0;
        sosachdadoc.setText(bookCaseList.size()+"");
        for(BookCase bookCase:bookCaseList){
            if(bookCase.getBought()){
                sachdamua++;
            }
        }
        tongsoSach.setText(sachdamua+"");
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        if (MainActivity.isNetworkConnected(getActivity())) {
            switch (v.getId()){
                case R.id.image_anhdaidien:
                    dialogChonAnhDaiDien = new Dialog(getContext());
                    dialogChonAnhDaiDien.setContentView(R.layout.dialog_chonanhdaidien);
                    Button button_thuvien = dialogChonAnhDaiDien.findViewById(R.id.chonanhtuthuvien);
                    Button button_mayanh = dialogChonAnhDaiDien.findViewById(R.id.chuptumayanh);
                    button_thuvien.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent();
                            intent.setType("image/*");
                            intent.setAction(Intent.ACTION_GET_CONTENT);
                            startActivityForResult(Intent.createChooser(intent, "Select Picture"),SELECT_IMAGE);
                        }
                    });
                    button_mayanh.setOnClickListener(new View.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.M)
                        @Override
                        public void onClick(View v) {
                            if (getActivity().checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                            {
                                requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                            }
                            else
                            {
                                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(cameraIntent, CAMERA_REQUEST);
                            }
                        }
                    });
                    dialogChonAnhDaiDien.show();
                    break;
                case R.id.capnhatthongtincanhan:
                    intent = new Intent(getContext(), CapNhatThongTinUser.class);
                    startActivity(intent);
                    break;
                case R.id.nguoitheodoi:
                    intent = new Intent(getContext(), ListUserTheoDoi.class);
                    intent.putExtra("title",getString(R.string.nguoi_theo_doi));
                    startActivity(intent);
                    break;
                case R.id.nguoidangtheodoi:
                    intent = new Intent(getContext(), ListUserTheoDoi.class);
                    intent.putExtra("title",getString(R.string.nguoi_dang_theo_doi));
                    startActivity(intent);
                    break;
                case R.id.sachdadoc:
                    intent = new Intent(getContext(), TuSach_CaNhanClick.class);
                    startActivity(intent);
                    break;
                case R.id.naptaikhoan:
                    intent = new Intent(getContext(), NapTaiKhoan.class);
                    startActivity(intent);
                    break;
                case R.id.lichsugiaodich:
                    intent = new Intent(getContext(), LichSuGiaoDich.class);
                    startActivity(intent);
                    break;
                case R.id.dangxuat:
                    loadingDialog.show();
                    final Dialog dialogDangXuat = new Dialog(getContext());
                    dialogDangXuat.setContentView(R.layout.dialog_dangxuat);
                    Button button_kethuc = dialogDangXuat.findViewById(R.id.ket_thuc);
                    Button button_dangxuat = dialogDangXuat.findViewById(R.id.dang_xuat);
                    button_kethuc.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogDangXuat.dismiss();
                            loadingDialog.dismiss();
                        }
                    });

                    button_dangxuat.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mAuth.signOut();
                            String packetName = getActivity().getPackageName();
                            DbRoomAccess.getInstance(getContext()).deleteAllBookcaseTask(getContext());
                            DbRoomAccess.getInstance(getContext()).deleteAllUserRentTask(getContext());
                            File f = new File(
                                    "/data/data/" + packetName + "/shared_prefs/User_Info.xml");
                            f.delete();
                            if(Login.mGoogleSignInClient!=null) {
                                Login.mGoogleSignInClient.signOut().addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Intent intent = new Intent(getContext(), Login.class);
                                        startActivity(intent);
                                        loadingDialog.dismiss();
                                        dialogDangXuat.dismiss();
                                        getActivity().finish();
                                    }
                                });
                            }else
                            if(LoginManager.getInstance()!=null){
                                Intent intent = new Intent(getContext(), Login.class);
                                startActivity(intent);
                                LoginManager.getInstance().logOut();
                                loadingDialog.dismiss();
                                dialogDangXuat.dismiss();
                                getActivity().finish();
                            }else {
                                Intent intent = new Intent(getContext(), Login.class);
                                startActivity(intent);
                                LoginManager.getInstance().logOut();
                                loadingDialog.dismiss();
                                dialogDangXuat.dismiss();
                                getActivity().finish();
                            }
                        }
                    });
                    dialogDangXuat.show();
                    break;
                case R.id.kiemtragoithue:
                    intent = new Intent(getContext(), KiemTraGoiThue.class);
                    startActivity(intent);
                    break;
            }
        }else {
            showAToast(getString(R.string.openinternet));
        }

    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_IMAGE) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    try {
                        Bitmap a = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                        Bitmap bitmap = a.createScaledBitmap(a, 300, 300, true);
                        anhdaidien.setImageBitmap(bitmap);
                        anhdaidienBackGround.setImageBitmap(bitmap);
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                        byte[] datas = baos.toByteArray();
                        dialogChonAnhDaiDien.dismiss();
                        UploadTask uploadTask = storageReference.child("images").child("users").child(FirebaseAuth.getInstance().getUid()+".png").putBytes(datas);
                        uploadTask.addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle unsuccessful uploads
                            }
                        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                                // ...
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED)  {
                Toast.makeText(getActivity(), "Canceled", Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == CAMERA_REQUEST  && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            Bitmap bitmap = imageBitmap.createScaledBitmap(imageBitmap, 300, 300, true);
                anhdaidien.setImageBitmap(bitmap);
                anhdaidienBackGround.setImageBitmap(bitmap);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] datas = baos.toByteArray();
                dialogChonAnhDaiDien.dismiss();
                UploadTask uploadTask = storageReference.child("images").child("users").child(FirebaseAuth.getInstance().getUid()+".png").putBytes(datas);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                        // ...
                    }
                });
        }
    }

    public void showAToast (String st){ //"Toast toast" is declared in the class
        try{ toast.getView().isShown();     // true if visible
            toast.setText(st);
        } catch (Exception e) {         // invisible if exception
            toast = Toast.makeText(getContext(), st ,  Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
        }
        toast.show();  //finally display it
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                dialogChonAnhDaiDien.dismiss();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
            else
            {
                dialogChonAnhDaiDien.dismiss();
            }
        }

    }
}
