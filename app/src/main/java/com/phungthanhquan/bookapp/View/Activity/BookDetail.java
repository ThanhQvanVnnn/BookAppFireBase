package com.phungthanhquan.bookapp.View.Activity;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.util.Pair;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.phungthanhquan.bookapp.Adapter.Album_NXB_Adapter;
import com.phungthanhquan.bookapp.Adapter.RecycleView_noidungbinhluan_Adapter;
import com.phungthanhquan.bookapp.Model.ConnectAPI.DownloadAPI;
import com.phungthanhquan.bookapp.Model.ConnectAPI.DownloadMethodAPI;
import com.phungthanhquan.bookapp.Model.Room.DbRoomAccess;
import com.phungthanhquan.bookapp.Object.BinhLuan;
import com.phungthanhquan.bookapp.Object.Book;
import com.phungthanhquan.bookapp.Object.BookCase;
import com.phungthanhquan.bookapp.Object.ChuongSach;
import com.phungthanhquan.bookapp.Object.Marketing;
import com.phungthanhquan.bookapp.Object.UserRent;
import com.phungthanhquan.bookapp.Presenter.Activity.PresenterBookDetail;
import com.phungthanhquan.bookapp.R;
import com.phungthanhquan.bookapp.View.InterfaceView.InterfaceViewActivityDetailBook;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import javax.annotation.Nullable;

import dmax.dialog.SpotsDialog;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BookDetail extends AppCompatActivity implements InterfaceViewActivityDetailBook, View.OnClickListener {
    private ImageView detailbook_image;
    private Toolbar toolbar;
    private TextView tenSach;
    private TextView binhluantext;
    private RatingBar ratingSach;
    private TextView soluongdanhgia;
    private TextView tentacgia;
    private TextView nhaxuatban;
    private TextView sotrang;
    private TextView giatien;
    private TextView menhgia;
    private Button docsach,muasaukhithue;
    private ExpandableTextView noidungSach;
    private Button chiaSeCamNhan;
    private PresenterBookDetail presenterBookDetail;
    private RecyclerView recycle_DsDanhGia;
    private RecyclerView getRecycle_SachCungTheLoai;
    private List<BinhLuan> dsBinhLuan;
    private List<Marketing> dsSachCungTheLoai;
    private Dialog dialogCamNhan;
    private LinearLayout xemThemDanhGia;
    private RecycleView_noidungbinhluan_Adapter recycleView_noidungbinhluan_adapter;
    private Album_NXB_Adapter adapter_sachcungtheloai;
    private SwipeRefreshLayout refreshLayout;
    private NestedScrollView nestedScrollView;

    private ProgressDialog progressDialog;
    private ImageButton imageButtonInternet;
    private ConstraintLayout constraintLayoutInternet;
    private int lenghtFile;
    public AlertDialog loadingDialog;
    Toast toast;
    private final String FILENAME_BOOKSTORED = "book_dowload";
    private String BOOK_ID;
    private String USER_ID;
    private String IMAGE = "";
    private int BINHLUANNUMBER;
    private Double AVERAGE;
    private int CHONSTAR;
    private String book_link;

    private FirebaseFirestore firebaseFirestore;
    private StorageReference storageReference;
    private Book book;
    private BookCase bookTuSach;
    private UserRent userRent;
    private SimpleDateFormat dateFormatter;
    private Call<ResponseBody> call;
    private Callback<ResponseBody> callback;
    private DownloadMethodAPI downloadMethodAPI;
    private DownloadBookFileTask downloadBookFileTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        try {
            InitControls();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        RefreshTrang();
        docsach.setOnClickListener(this);
        chiaSeCamNhan.setOnClickListener(this);
        xemThemDanhGia.setOnClickListener(this);
        nestedScrollView.getParent().requestChildFocus(nestedScrollView, nestedScrollView);
        if (MainActivity.isNetworkConnected(this)) {
            nestedScrollView.setVisibility(View.VISIBLE);
            constraintLayoutInternet.setVisibility(View.GONE);
        } else {
            constraintLayoutInternet.setVisibility(View.VISIBLE);
            nestedScrollView.setVisibility(View.GONE);
        }
        imageButtonInternet.setOnClickListener(this);
        muasaukhithue.setOnClickListener(this);
    }


    private void InitControls() throws ExecutionException, InterruptedException {
        detailbook_image = findViewById(R.id.image_detailbook);
        tenSach = findViewById(R.id.textview_tensach);
        ratingSach = findViewById(R.id.raiting_tong);
        soluongdanhgia = findViewById(R.id.textview_soluongdanhgia);
        docsach = findViewById(R.id.button_docsach);
        noidungSach = findViewById(R.id.expand_text_view);
        tentacgia = findViewById(R.id.texview_tentacgia);
        nhaxuatban = findViewById(R.id.textview_tenNXB);
        binhluantext = findViewById(R.id.binhluan);
        sotrang = findViewById(R.id.textview_sotrang);
        giatien = findViewById(R.id.textview_giatien);
        menhgia = findViewById(R.id.textview_menhgia);
        muasaukhithue = findViewById(R.id.muasaukhithue);
        chiaSeCamNhan = findViewById(R.id.button_chiasecamnhan);
        recycle_DsDanhGia = findViewById(R.id.recycle_danhsachdanhgia);
        getRecycle_SachCungTheLoai = findViewById(R.id.sachcungtheloai);
        xemThemDanhGia = findViewById(R.id.xemthemdanhgia);
        refreshLayout = findViewById(R.id.refresh_ChiTietSach);
        nestedScrollView = findViewById(R.id.nestedScroll);
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCanceledOnTouchOutside(false);
        constraintLayoutInternet = findViewById(R.id.layout_internet_disconnect);
        imageButtonInternet = findViewById(R.id.checkInternet);
        loadingDialog = new SpotsDialog.Builder().setContext(this).build();
        loadingDialog.setMessage(getResources().getString(R.string.vuilongcho));
        dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        Intent intent = getIntent();
        BOOK_ID = intent.getStringExtra("book_id");
        IMAGE = getIntent().getStringExtra("image");
        firebaseFirestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        toolbar = findViewById(R.id.toolbar_bookDetail);
        toolbar.setTitle(R.string.chi_tiet_sach);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        USER_ID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        presenterBookDetail = new PresenterBookDetail(this);
        dsBinhLuan = new ArrayList<>();
        dsSachCungTheLoai = new ArrayList<>();
        adapter_sachcungtheloai = new Album_NXB_Adapter(this, dsSachCungTheLoai);
        getRecycle_SachCungTheLoai.setAdapter(adapter_sachcungtheloai);
        getRecycle_SachCungTheLoai.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        if (!IMAGE.equals("")) {
            Picasso.get().load(IMAGE).resize(150, 200).into(detailbook_image);
        } else {
            storageReference.child("images").child("books").child(BOOK_ID + ".png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Picasso.get().load(uri).resize(150, 200).into(detailbook_image);
                    IMAGE = uri.toString();
                }
            });
        }
        bookTuSach = DbRoomAccess.getInstance(this).getBookCaseByIDTask(this, BOOK_ID);
        userRent = DbRoomAccess.getInstance(this).getUserRentByIDTask(this, USER_ID);
        presenterBookDetail.xuliHienThiSach(BOOK_ID);
        presenterBookDetail.xuliHienThiDsDanhGia(BOOK_ID);
        downloadMethodAPI = DownloadAPI.getApiDownload();
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseFirestore.collection("book").document(BOOK_ID).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (e != null) {

                } else {
                    presenterBookDetail.xuliHienThiSach(BOOK_ID);
                }
            }
        });
        firebaseFirestore.collection("user_bookcase").whereEqualTo("user_id", USER_ID).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {

                } else {
                    try {
                        userRent = DbRoomAccess.getInstance(BookDetail.this).getUserRentByIDTask(BookDetail.this, USER_ID);
                        bookTuSach = DbRoomAccess.getInstance(BookDetail.this).getBookCaseByIDTask(BookDetail.this, BOOK_ID);
                        presenterBookDetail.xuliHienThiSach(BOOK_ID);
                    } catch (ExecutionException e1) {
                        e1.printStackTrace();
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }


                }
            }
        });
        firebaseFirestore.collection("comment").whereEqualTo("book_id", BOOK_ID).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                presenterBookDetail.xuliHienThiDsDanhGia(BOOK_ID);
            }
        });
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void hienThiNoiDungSach(Book boook) {
        book = boook;

        if (book != null) {
            tenSach.setText(book.getName());
            noidungSach.setText(book.getIntroduce());
            ratingSach.setRating(book.getStar_average().floatValue());
            soluongdanhgia.setText(book.getComment_number() + " đánh giá");
            tentacgia.setText(book.getAuthor_name());
            nhaxuatban.setText(book.getPublisher_name());
            DecimalFormat df = new DecimalFormat("###,###.###");
            df.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.ITALY));
            String giatien_format = df.format(book.getPrice());
            sotrang.setText(book.getPage_number() + "");
            muasaukhithue.setText(getString(R.string.mua_ngay) + " " + giatien_format + " Đ");
            BINHLUANNUMBER = book.getComment_number();
            AVERAGE = book.getStar_average();

            if (bookTuSach == null) /*chưa mua sách*/ {
                if (userRent == null) {
                    //nếu chưa mua sách
                    giatien.setText(giatien_format + "");
                } else {
                    if ((fortmatStringtoDate(userRent.getTime_rest()).after(fortmatStringtoDate(dateFormatter.format(new Date()))) || fortmatStringtoDate(userRent.getTime_rest()).equals(fortmatStringtoDate(dateFormatter.format(new Date())))) == false) {
                        giatien.setText(giatien_format + "");

                    } else {
                        giatien.setText(R.string.dathue);
                        giatien.setTextColor(getResources().getColor(R.color.colorPrimary));
                        menhgia.setVisibility(View.INVISIBLE);
                        muasaukhithue.setVisibility(View.VISIBLE);
                    }
                }
            } else /*có thấy trong tủ sách*/ {
                Log.d("bookcasetusach", bookTuSach.toString() + "");
                if (bookTuSach.getBought()) {
                    giatien.setText(R.string.damua);
                    giatien.setTextColor(getResources().getColor(R.color.damuasach));
                    menhgia.setVisibility(View.INVISIBLE);
                    muasaukhithue.setVisibility(View.GONE);
                } else {
                    if ((fortmatStringtoDate(userRent.getTime_rest()).after(fortmatStringtoDate(dateFormatter.format(new Date()))) || fortmatStringtoDate(userRent.getTime_rest()).equals(fortmatStringtoDate(dateFormatter.format(new Date())))) == false) {
                        giatien.setText(giatien_format + "");
                    } else {
                        giatien.setText(R.string.dathue);
                        giatien.setTextColor(getResources().getColor(R.color.colorPrimary));
                        menhgia.setVisibility(View.INVISIBLE);
                        muasaukhithue.setVisibility(View.VISIBLE);
                    }
                }
            }
            menhgia.setPaintFlags(menhgia.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        }
        presenterBookDetail.xuliHienThidsSachCungTheLoai(book.getCategory_id());


    }

    @Override
    public void hienThiDsDanhGia(List<BinhLuan> dsDanhGia) {
        dsBinhLuan = new ArrayList<>();
        if (dsDanhGia.size() > 0) {
            if (dsDanhGia.size() <= 3) {
                dsBinhLuan.addAll(dsDanhGia);
                xemThemDanhGia.setVisibility(View.GONE);
                binhluantext.setVisibility(View.VISIBLE);
                recycle_DsDanhGia.setVisibility(View.VISIBLE);
            } else {
                dsBinhLuan.addAll(dsDanhGia);
                xemThemDanhGia.setVisibility(View.VISIBLE);
                binhluantext.setVisibility(View.VISIBLE);
                recycle_DsDanhGia.setVisibility(View.VISIBLE);
            }
        } else {
            binhluantext.setVisibility(View.GONE);
            xemThemDanhGia.setVisibility(View.GONE);
            recycle_DsDanhGia.setVisibility(View.GONE);
        }
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
        recycleView_noidungbinhluan_adapter = new RecycleView_noidungbinhluan_Adapter(this, dsBinhLuan, false, BOOK_ID);
        recycle_DsDanhGia.setAdapter(recycleView_noidungbinhluan_adapter);
        LinearLayoutManager linearLayout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        linearLayout.setReverseLayout(true);
        recycle_DsDanhGia.setLayoutManager(linearLayout);
        recycle_DsDanhGia.setHasFixedSize(false);
        recycle_DsDanhGia.setNestedScrollingEnabled(false);
    }

    @Override
    public void hienThiDsSachCungTheLoai(List<Marketing> dsSach) {
        dsSachCungTheLoai.clear();
        if (dsSach.size() != 0) {
            dsSachCungTheLoai.addAll(dsSach);
            for (int i = 0; i < dsSach.size(); i++) {
                if (dsSach.get(i).getBook_id().equals(BOOK_ID)) {
                    dsSachCungTheLoai.remove(i);
                }
            }
            Collections.shuffle(dsSachCungTheLoai);
            adapter_sachcungtheloai.addMoreImage();
            adapter_sachcungtheloai.notifyDataSetChanged();
        }
    }

    @Override
    public void hienThiThemBinhLuan() {
        presenterBookDetail.xuliHienThiDsDanhGia(BOOK_ID);
        int binhluan = BINHLUANNUMBER;
        BINHLUANNUMBER += 1;
        AVERAGE = ((binhluan * AVERAGE) + CHONSTAR) / BINHLUANNUMBER;
        ratingSach.setRating(AVERAGE.floatValue());
        soluongdanhgia.setText(BINHLUANNUMBER + " đánh giá");
        book.setComment_number(BINHLUANNUMBER);
        book.setStar_average(AVERAGE);
        firebaseFirestore.collection("book").document(BOOK_ID).set(book).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                dialogCamNhan.dismiss();
                loadingDialog.dismiss();
                showAToast(getString(R.string.binhluanthanhcong));
            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
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
                            if (CHONSTAR == 0) {
                                showAToast(getString(R.string.vuilongnhapsosao));
                            }
                            if (content.equals("")) {
                                showAToast(getString(R.string.notempty));
                            }
                            if (CHONSTAR != 0 && !content.equals("")) {
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
                                presenterBookDetail.xuliThemBinhLuan(binhLuan);
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
            case R.id.xemthemdanhgia:
                if (MainActivity.isNetworkConnected(this)) {
                    intent = new Intent(this, XemThemDanhGia.class);
                    intent.putExtra("book_id", BOOK_ID);
                    intent.putExtra("book_name", book.getName());
                    intent.putExtra("book_image", IMAGE);
                    ActivityOptions options = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                        options = ActivityOptions.makeSceneTransitionAnimation(this,
                                chiaSeCamNhan, "chiasecamnhan");
                    }
                    startActivity(intent, options.toBundle());
                } else {
                    showAToast(getResources().getString(R.string.openinternet));
                }
                break;
            case R.id.button_docsach:
                if (MainActivity.isNetworkConnected(this)) {
                    if (bookTuSach == null)/*không có sách*/ {
                        if (userRent == null) /*kiểm tra thuê - không có*/ {
                            intent = new Intent(BookDetail.this, ChonGoiMuaSach.class);
                            intent.putExtra("name", book.getName());
                            intent.putExtra("image", IMAGE);
                            intent.putExtra("price", book.getPrice());
                            intent.putExtra("book_id", BOOK_ID);
                            startActivity(intent);
                        } else if ((fortmatStringtoDate(userRent.getTime_rest()).after(fortmatStringtoDate(dateFormatter.format(new Date()))) || fortmatStringtoDate(userRent.getTime_rest()).equals(fortmatStringtoDate(dateFormatter.format(new Date())))) == false) /*có thuê nhưng hết hạn*/ {
                            intent = new Intent(BookDetail.this, ChonGoiMuaSach.class);
                            intent.putExtra("name", book.getName());
                            intent.putExtra("image", IMAGE);
                            intent.putExtra("price", book.getPrice());
                            intent.putExtra("book_id", BOOK_ID);
                            startActivity(intent);
                        } else if ((fortmatStringtoDate(userRent.getTime_rest()).after(fortmatStringtoDate(dateFormatter.format(new Date()))) || fortmatStringtoDate(userRent.getTime_rest()).equals(fortmatStringtoDate(dateFormatter.format(new Date())))) == true)/*còn hạn*/ {
                            vaotrangdoc();
                            final BookCase bookthue = new BookCase();
                            bookthue.setBook_id(BOOK_ID);
                            bookthue.setUser_id(USER_ID);
                            bookthue.setBought(false);
                            firebaseFirestore.collection("user_bookcase").add(bookthue).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    bookthue.setId(documentReference.getId());
                                    bookthue.setBook_image(IMAGE);
                                    DbRoomAccess.getInstance(BookDetail.this).insertBookCaseTask(BookDetail.this, bookthue);
                                    try {
                                        userRent = DbRoomAccess.getInstance(BookDetail.this).getUserRentByIDTask(BookDetail.this, USER_ID);
                                        bookTuSach = DbRoomAccess.getInstance(BookDetail.this).getBookCaseByIDTask(BookDetail.this, BOOK_ID);
                                    } catch (ExecutionException e) {
                                        e.printStackTrace();
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });

                        }
                    } else /*có sách này*/ {
                        if (bookTuSach.getBought()) /*đã mua*/ {
                            vaotrangdoc();
                        } else /*thuê hết hạn*/ {
                            if ((fortmatStringtoDate(userRent.getTime_rest()).after(fortmatStringtoDate(dateFormatter.format(new Date()))) || fortmatStringtoDate(userRent.getTime_rest()).equals(fortmatStringtoDate(dateFormatter.format(new Date())))) == false)/*hết hạn*/ {
                                intent = new Intent(BookDetail.this, ChonGoiMuaSach.class);
                                intent.putExtra("name", book.getName());
                                intent.putExtra("image", IMAGE);
                                intent.putExtra("price", book.getPrice());
                                intent.putExtra("book_id", BOOK_ID);
                                startActivity(intent);
                            } else /*còn hạn*/ {
                                vaotrangdoc();
                            }
                        }
                    }

                } else {
                    //nếu chưa mua sách:
                    showAToast(getResources().getString(R.string.openinternet_readbook));
                }
                break;


            case R.id.checkInternet:
                if (MainActivity.isNetworkConnected(this)) {
                    nestedScrollView.setVisibility(View.VISIBLE);
                    constraintLayoutInternet.setVisibility(View.GONE);
                    docsach.setOnClickListener(this);
                    chiaSeCamNhan.setOnClickListener(this);
                    xemThemDanhGia.setOnClickListener(this);
                    nestedScrollView.getParent().requestChildFocus(nestedScrollView, nestedScrollView);
                } else {
                    constraintLayoutInternet.setVisibility(View.VISIBLE);
                    nestedScrollView.setVisibility(View.GONE);
                }
                break;
            case R.id.muasaukhithue:
                intent = new Intent(this,HinhThucThanhToan.class);
                intent.putExtra("book_id",BOOK_ID);
                intent.putExtra("book_name",book.getName());
                intent.putExtra("book_image",IMAGE);
                intent.putExtra("rent_name","v");
                intent.putExtra("rent_id","");
                intent.putExtra("rent_price",book.getPrice());
                intent.putExtra("rent_time",-1);
                startActivity(intent);
                break;
        }
    }


    private void RefreshTrang() {
        refreshLayout.setColorSchemeColors(getResources().getColor(android.R.color.holo_blue_dark)
                , getResources().getColor(android.R.color.holo_blue_light)
                , getResources().getColor(android.R.color.holo_orange_light));
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (MainActivity.isNetworkConnected(BookDetail.this)) {
                    constraintLayoutInternet.setVisibility(View.GONE);
                    nestedScrollView.setVisibility(View.VISIBLE);
                    refreshLayout.setRefreshing(true);
                    dsBinhLuan.clear();
                    presenterBookDetail.xuliHienThiDsDanhGia(BOOK_ID);
                    presenterBookDetail.xuliHienThiSach(BOOK_ID);
                    presenterBookDetail.xuliHienThidsSachCungTheLoai(book.getCategory_id());

                } else {
                    nestedScrollView.setVisibility(View.GONE);
                    constraintLayoutInternet.setVisibility(View.VISIBLE);
                }
                refreshLayout.setRefreshing(false);
            }
        });
    }

    private class DownloadBookFileTask extends AsyncTask<ResponseBody, Pair<Integer, Long>, Boolean> {
        private String bookID;
        private ProgressDialog dialog;

        public DownloadBookFileTask(String bookID, ProgressDialog progressDialog) {
            this.bookID = bookID;
            this.dialog = progressDialog;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage(getString(R.string.taisach));
            dialog.show();

        }

        @Override
        protected Boolean doInBackground(ResponseBody... urls) {
            //Copy you logic to calculate progress and call
            Boolean result = false;
            saveToDisk(result, urls[0], bookID + ".pdf");
            return result;
        }

        protected void onProgressUpdate(Pair<Integer, Long>... progress) {

            Log.d("API123", progress[0].second + " ");

            if (progress[0].first == 100) {
                showAToast(getResources().getString(R.string.taisachthanhcong));
                Intent intent = new Intent(BookDetail.this, Read.class);
                intent.putExtra("book_id", BOOK_ID);
                startActivity(intent);
            }

            if (progress[0].second > 0) {
                int currentProgress = (int) ((double) progress[0].first / (double) progress[0].second * 100);
                dialog.setProgress(currentProgress);
            }

            if (progress[0].first == -1) {
                showAToast(getResources().getString(R.string.taisachthatbai));
            }


        }

        public void doProgress(Pair<Integer, Long> progressDetails) {
            publishProgress(progressDetails);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (dialog.isShowing()) {
                if (result == true) {
                    Intent intent = new Intent(BookDetail.this, Read.class);
                    intent.putExtra("idSach", bookID);
                    startActivity(intent);
                }
                dialog.cancel();
            }
        }
    }

    private void saveToDisk(Boolean result, ResponseBody body, String filename) {
        File pdfFile = null;
        try {

            File directory = null;
            ContextWrapper cw = new ContextWrapper(BookDetail.this);
            directory = cw.getDir(FILENAME_BOOKSTORED, Context.MODE_PRIVATE);
            pdfFile = new File(directory, filename);
//            try{
//                pdfFile.createNewFile();
//            }catch (IOException e){
//                e.printStackTrace();
//            }
            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(pdfFile);
                byte data[] = new byte[4096];
                int count;
                int progress = 0;
                long fileSize = body.contentLength();
                Log.d("kiemtra", "File Size=" + fileSize);
                while ((count = inputStream.read(data)) != -1) {
                    outputStream.write(data, 0, count);
                    progress += count;
                    Pair<Integer, Long> pairs = new Pair<>(progress, fileSize);
                    downloadBookFileTask.doProgress(pairs);
                    Log.d("kiemtra", "Progress: " + progress + "/" + fileSize + " >>>> " + (float) progress / fileSize);
                }

                outputStream.flush();

                Log.d("kiemtra", pdfFile.getParent());
                Pair<Integer, Long> pairs = new Pair<>(100, 100L);
                downloadBookFileTask.doProgress(pairs);
                result = true;
                return;
            } catch (IOException e) {
                e.printStackTrace();
                Pair<Integer, Long> pairs = new Pair<>(-1, Long.valueOf(-1));
                downloadBookFileTask.doProgress(pairs);
                pdfFile.delete();
                Log.d("kiemtra", "Failed to save the file!");
                call.cancel();
                call = null;
                result = false;
                return;
            } finally {
                if (inputStream != null) inputStream.close();
                if (outputStream != null) outputStream.close();
            }
        } catch (IOException e) {
            result = false;
            pdfFile.delete();
            e.printStackTrace();
            Log.d("kiemtra", "Failed to save the file!");
            return;
        }
    }

    private void downloadbookFile(final String bookID, final ProgressDialog progressDialog) {
        call = null;
        String urlBook = book_link;
        call = downloadMethodAPI.downLoadBook(urlBook);
        getChuongSach();
        callback = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Log.d("kiemtra", "Got the body for the file");
                    downloadBookFileTask = new DownloadBookFileTask(bookID, progressDialog);
                    downloadBookFileTask.execute(response.body());

                } else {
                    Log.d("kiemtra", "Connection failed " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                Log.e("kiemtra", "Lỗi retro:" + t.getMessage());
            }
        };
        call.enqueue(callback);
    }

    public int checkBookSize(String urls) {
        int file_size = 0;
        try {
            URL url = new URL(urls);
            URLConnection connection = url.openConnection();
            connection.connect();
            file_size = connection.getContentLength();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file_size;
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

    public Date fortmatStringtoDate(String date) {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date startDate = null;
        try {
            startDate = df.parse(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return startDate;
    }

    public void vaotrangdoc() {
        storageReference.child("pdfs").child(BOOK_ID + ".pdf").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                book_link = uri.toString();
                File directory;
                ContextWrapper cw = new ContextWrapper(BookDetail.this);
                directory = cw.getDir(FILENAME_BOOKSTORED, Context.MODE_PRIVATE);
                final File file = new File(directory, BOOK_ID + ".pdf");
                final int file_size = (int) file.length();
                new AsyncTask<String, Void, Void>() {

                    Intent intent;

                    @Override
                    protected void onPreExecute() {
                        loadingDialog.show();
                        super.onPreExecute();
                    }

                    @Override
                    protected Void doInBackground(String... strings) {
                        lenghtFile = checkBookSize(strings[0]);
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                        loadingDialog.dismiss();
                        if (file.exists() && (file_size == lenghtFile)) {
                            intent = new Intent(BookDetail.this, Read.class);
                            intent.putExtra("book_id", BOOK_ID);
                            intent.putExtra("book_name",book.getName());
                            startActivity(intent);
                        } else if (file.exists() && file_size != lenghtFile) {
                            file.delete();
                            downloadbookFile(BOOK_ID + "", progressDialog);
                        } else {
                            if (call == null) {
                                downloadbookFile(BOOK_ID + "", progressDialog);
                            } else {
                                downloadbookFile(BOOK_ID + "", progressDialog);
                            }
                        }
                    }
                }.execute(book_link);
            }
        });
    }

    public void getChuongSach(){
        firebaseFirestore.collection("chapter").whereEqualTo("book_id",BOOK_ID).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<ChuongSach> chuongSachList = new ArrayList<>();
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot queryDocumentSnapshot:task.getResult()){
                        if(queryDocumentSnapshot.exists()){
                            ChuongSach chuongSach = queryDocumentSnapshot.toObject(ChuongSach.class);
                            chuongSach.setId(queryDocumentSnapshot.getId());
                            chuongSachList.add(chuongSach);
                        }
                    }
                    for(ChuongSach chuongSach:chuongSachList){
                        DbRoomAccess.getInstance(BookDetail.this).insertChapterTask(BookDetail.this,chuongSach);
                    }
                }
            }
        });
    }

}
