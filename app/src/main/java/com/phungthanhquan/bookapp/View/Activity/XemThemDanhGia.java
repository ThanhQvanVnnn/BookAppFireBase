package com.phungthanhquan.bookapp.View.Activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.phungthanhquan.bookapp.Adapter.RecycleView_noidungbinhluan_Adapter;
import com.phungthanhquan.bookapp.Object.BinhLuan;
import com.phungthanhquan.bookapp.Object.Book;
import com.phungthanhquan.bookapp.Object.Marketing;
import com.phungthanhquan.bookapp.Presenter.Activity.PresenterBookDetail;
import com.phungthanhquan.bookapp.Presenter.Activity.PresenterLogicXemThemDanhGia;
import com.phungthanhquan.bookapp.R;
import com.phungthanhquan.bookapp.View.InterfaceView.InterfaceViewActivityDanhSachDanhGia;
import com.phungthanhquan.bookapp.View.InterfaceView.InterfaceViewActivityDetailBook;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.annotation.Nullable;

import dmax.dialog.SpotsDialog;

public class XemThemDanhGia extends AppCompatActivity implements InterfaceViewActivityDetailBook, View.OnClickListener {
    private Toolbar toolbar;
    private Button chiaSeCamNhan;
    private RecyclerView recyclerView_danhGia;
    private List<BinhLuan> dsBinhLuan;
    RecycleView_noidungbinhluan_Adapter recycleView_noidungbinhluan_adapter;
    private Dialog dialogCamNhan;
    private ImageView imageView;
    PresenterBookDetail presenterLogicXemThemDanhGia;
    String BOOK_ID;
    Book book;
    Toast toast;
    public AlertDialog loadingDialog;
    int CHONSTAR;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xem_them_danh_gia);
        BOOK_ID = getIntent().getStringExtra("book_id");
        initControls();
        dsBinhLuan = new ArrayList<>();
        String name = getIntent().getStringExtra("book_name");
        String image = getIntent().getStringExtra("book_image");
        Picasso.get().load(image).into(imageView);
        toolbar.setTitle(name);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        firebaseFirestore.collection("book").document(BOOK_ID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                book = documentSnapshot.toObject(Book.class);
            }
        });

        presenterLogicXemThemDanhGia.xuliHienThiDsDanhGia(BOOK_ID);
        chiaSeCamNhan.setOnClickListener(this);
    }

    private void initControls() {
        toolbar = findViewById(R.id.toolbar);
        chiaSeCamNhan = findViewById(R.id.button_chiasecamnhan);
        recyclerView_danhGia = findViewById(R.id.recycle_tongdanhsachdanhgia);
        presenterLogicXemThemDanhGia = new PresenterBookDetail(this);
        loadingDialog = new SpotsDialog.Builder().setContext(this).build();
        loadingDialog.setMessage(getResources().getString(R.string.vuilongcho));
        firebaseFirestore = FirebaseFirestore.getInstance();
        imageView = findViewById(R.id.image_sach);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
    @Override
    protected void onStart() {
        super.onStart();
        firebaseFirestore.collection("comment").whereEqualTo("book_id",BOOK_ID).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                presenterLogicXemThemDanhGia.xuliHienThiDsDanhGia(BOOK_ID);
            }
        });
        firebaseFirestore.collection("book").document(BOOK_ID).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if(e!=null){

                }else {
                    book = documentSnapshot.toObject(book.getClass());
                }
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_chiasecamnhan:
                if (MainActivity.isNetworkConnected(this)) {
                    dialogCamNhan = new Dialog(this);
                    dialogCamNhan.setContentView(R.layout.dialog_danhgia);
                    final RatingBar sosao = dialogCamNhan.findViewById(R.id.raiting_chonsao);
                    final EditText noidung = dialogCamNhan.findViewById(R.id.edittext_nhapnoidung);
                    TextView txtClose = dialogCamNhan.findViewById(R.id.textview_cancel);
                    txtClose.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogCamNhan.dismiss();
                        }
                    });
                    Button danhgia = dialogCamNhan.findViewById(R.id.button_guidanhgia);
                    danhgia.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            CHONSTAR = (int) sosao.getRating();
                            String content = noidung.getText().toString();
                            if(CHONSTAR ==0){
                                showAToast(getString(R.string.vuilongnhapsosao));
                            }
                            if(content.equals("")){
                                showAToast(getString(R.string.notempty));
                            }
                            if(CHONSTAR !=0 && !content.equals("")){
                                BinhLuan binhLuan = new BinhLuan();
                                binhLuan.setBook_id(BOOK_ID);
                                binhLuan.setContent(content);
                                binhLuan.setStar_number(CHONSTAR);
                                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                Date date = new Date();
                                String dateString = dateFormat.format(date);
                                binhLuan.setTime(dateString);
                                binhLuan.setUser_id(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                loadingDialog.setMessage(getString(R.string.vuilongcho));
                                loadingDialog.show();
                                presenterLogicXemThemDanhGia.xuliThemBinhLuan(binhLuan);
                            }
                        }
                    });
                    dialogCamNhan.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialogCamNhan.show();
                    dialogCamNhan.setCanceledOnTouchOutside(false);
                } else {
                    showAToast(getResources().getString(R.string.openinternet));
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

    @Override
    public void hienThiNoiDungSach(Book book) {

    }

    @Override
    public void hienThiDsDanhGia(List<BinhLuan> dsDanhGia) {
        dsBinhLuan.clear();
        dsBinhLuan.addAll(dsDanhGia);
        Collections.sort(dsBinhLuan, new Comparator<BinhLuan>() {
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

            public int compare(BinhLuan o1, BinhLuan o2) {
                Date date1 = null;
                Date date2 = null;
                try {
                    date1 = df.parse(o1.getTime());
                    date2 = df.parse(o2.getTime());
                    return df.parse(o1.getTime()).compareTo(df.parse(o2.getTime()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return date1.compareTo(date2);
            }
        });
        recycleView_noidungbinhluan_adapter = new RecycleView_noidungbinhluan_Adapter(this,dsBinhLuan,true,BOOK_ID);
        recyclerView_danhGia.setAdapter(recycleView_noidungbinhluan_adapter);
        recyclerView_danhGia.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView_danhGia.setHasFixedSize(false);
    }

    @Override
    public void hienThiDsSachCungTheLoai(List<Marketing> dsSach) {

    }

    @Override
    public void hienThiThemBinhLuan() {
        int binhluan = book.getComment_number();
        book.setComment_number(book.getComment_number()+1);
        Double trungbinh = ((binhluan*book.getStar_average())+CHONSTAR)/(binhluan+1);
        book.setStar_average(trungbinh);
        firebaseFirestore.collection("book").document(BOOK_ID).set(book).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                presenterLogicXemThemDanhGia.xuliHienThiDsDanhGia(BOOK_ID);
            }
        });
        dialogCamNhan.dismiss();
        loadingDialog.dismiss();
    }
}
