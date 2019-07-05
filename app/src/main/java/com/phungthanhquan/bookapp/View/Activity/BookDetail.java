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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.phungthanhquan.bookapp.Adapter.Album_NXB_Adapter;
import com.phungthanhquan.bookapp.Adapter.RecycleView_noidungbinhluan_Adapter;
import com.phungthanhquan.bookapp.Object.BinhLuan;
import com.phungthanhquan.bookapp.Object.Book;
import com.phungthanhquan.bookapp.Object.Marketing;
import com.phungthanhquan.bookapp.Presenter.Activity.PresenterBookDetail;
import com.phungthanhquan.bookapp.R;
import com.phungthanhquan.bookapp.View.InterfaceView.InterfaceViewActivityDetailBook;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import dmax.dialog.SpotsDialog;
import okhttp3.ResponseBody;


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
    private Button docsach;
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

    private DownloadBookFileTask downloadBookFileTask;
    private ProgressDialog progressDialog;
    private ImageButton imageButtonInternet;
    private ConstraintLayout constraintLayoutInternet;
    private int lenghtFile;
    public AlertDialog loadingDialog;
    Toast toast;

    private final String FILENAME_BOOKSTORED = "book_dowload";
    private String BOOK_ID;
    private String IMAGE ="";

    private FirebaseFirestore firebaseFirestore;
    private StorageReference storageReference;
    private Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        InitControls();
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
    }

    private void InitControls() {
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

        presenterBookDetail = new PresenterBookDetail(this);
        dsBinhLuan = new ArrayList<>();
        dsSachCungTheLoai = new ArrayList<>();
        adapter_sachcungtheloai = new Album_NXB_Adapter(this,dsSachCungTheLoai);
        getRecycle_SachCungTheLoai.setAdapter(adapter_sachcungtheloai);
        getRecycle_SachCungTheLoai.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        if(!IMAGE.equals("") ) {
            Picasso.get().load(IMAGE).resize(150, 200).into(detailbook_image);
        }else {
            storageReference.child("images").child("books").child(BOOK_ID+".png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Picasso.get().load(uri).resize(150, 200).into(detailbook_image);
                }
            });
        }
        presenterBookDetail.xuliHienThiSach(BOOK_ID);
        presenterBookDetail.xuliHienThiDsDanhGia(BOOK_ID);

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
            ratingSach.setRating(book.getStar_average());
            soluongdanhgia.setText(book.getComment_number() + " đánh giá");
            tentacgia.setText(book.getAuthor_name());
            nhaxuatban.setText(book.getPublisher_name());
            sotrang.setText(book.getPage_number() + "");
            DecimalFormat df = new DecimalFormat("###,###.###");
            df.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.ITALY));
            String giatien_format = df.format(book.getPrice());
            //nếu đã mua sách thì set giá tiền bằng đã mua, set chữ màu xanh lá cây........

            //nếu chưa mua sách
            giatien.setText(giatien_format + "");
            menhgia.setPaintFlags(menhgia.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        }
        presenterBookDetail.xuliHienThidsSachCungTheLoai(book.getCategory_id());
    }

    @Override
    public void hienThiDsDanhGia(List<BinhLuan> dsDanhGia) {
        if(dsDanhGia.size()>0) {
            if(dsDanhGia.size()==3){
                xemThemDanhGia.setVisibility(View.GONE);
            }
            dsBinhLuan.addAll(dsDanhGia);
        }else {
            binhluantext.setVisibility(View.GONE);
            xemThemDanhGia.setVisibility(View.GONE);
            recycle_DsDanhGia.setVisibility(View.GONE);
        }
        recycleView_noidungbinhluan_adapter = new RecycleView_noidungbinhluan_Adapter(this, dsBinhLuan,false,BOOK_ID);
        recycle_DsDanhGia.setAdapter(recycleView_noidungbinhluan_adapter);
        recycle_DsDanhGia.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recycle_DsDanhGia.setHasFixedSize(false);
        recycle_DsDanhGia.setNestedScrollingEnabled(false);
    }

    @Override
    public void hienThiDsSachCungTheLoai(List<Marketing> dsSach) {
        if(dsSach.size()!=0) {
            dsSachCungTheLoai.addAll(dsSach);
            for(int i = 0;i<dsSach.size();i++){
                if(dsSach.get(i).getBook_id().equals(BOOK_ID)) {
                    dsSachCungTheLoai.remove(i);
                }
            }
            Collections.shuffle(dsSachCungTheLoai);
            adapter_sachcungtheloai.addMoreImage();
            adapter_sachcungtheloai.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.button_chiasecamnhan:
                if (MainActivity.isNetworkConnected(this)) {
                    dialogCamNhan = new Dialog(this);
                    dialogCamNhan.setContentView(R.layout.dialog_danhgia);
                    TextView txtClose = dialogCamNhan.findViewById(R.id.textview_cancel);
                    txtClose.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogCamNhan.dismiss();
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
                    intent.putExtra("book_id",BOOK_ID);
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
                    //nếu chưa mua sách:

                    ///nếu đã mua sách:
                    File directory;
                    ContextWrapper cw = new ContextWrapper(BookDetail.this);
                    directory = cw.getDir(FILENAME_BOOKSTORED, Context.MODE_PRIVATE);
                    final File file = new File(directory, BOOK_ID + ".pdf");
                    final int file_size = (int) file.length();

//                    new AsyncTask<String, Void, Void>() {
//
//                        Intent intent;
//
//                        @Override
//                        protected void onPreExecute() {
//                            loadingDialog.show();
//                            super.onPreExecute();
//                        }
//
//                        @Override
//                        protected Void doInBackground(String... strings) {
//                            lenghtFile = checkBookSize(strings[0]);
//                            return null;
//                        }
//
//                        @Override
//                        protected void onPostExecute(Void aVoid) {
//                            super.onPostExecute(aVoid);
//                            loadingDialog.dismiss();
//                            if (file.exists() && (file_size == lenghtFile)) {
//                                intent = new Intent(BookDetail.this, Read.class);
//                                intent.putExtra("idSach", "id0");
//                                startActivity(intent);
//                            } else if (file.exists() && file_size != lenghtFile) {
//                                file.delete();
//                                downloadbookFile(ChiTietSach.getId_sach() + "", progressDialog);
//                            } else {
//                                if (call == null) {
//                                    downloadbookFile(ChiTietSach.getId_sach() + "", progressDialog);
//                                } else {
//                                    downloadbookFile(ChiTietSach.getId_sach() + "", progressDialog);
//                                }
//                            }
//                        }
//                    }.execute("https://sachvui.com/sachvui-686868666888/ebooks/2016/pdf/Sachvui.Com-quang-ganh-lo-di-va-vui-song.pdf");
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
//            saveToDisk(result,urls[0], bookID+".pdf");
            return result;
        }

        protected void onProgressUpdate(Pair<Integer, Long>... progress) {

            Log.d("API123", progress[0].second + " ");

            if (progress[0].first == 100) {
                showAToast(getResources().getString(R.string.taisachthanhcong));
                Intent intent = new Intent(BookDetail.this, Read.class);
                intent.putExtra("idSach", "id0");
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
                    intent.putExtra("idSach", BOOK_ID);
                    startActivity(intent);
                }
                dialog.cancel();
            }
        }
    }
//    private void saveToDisk(Boolean result,ResponseBody body, String filename) {
//        File pdfFile = null;
//        try {
//
//            File directory= null;
//            ContextWrapper cw = new ContextWrapper(BookDetail.this);
//            directory = cw.getDir(FILENAME_BOOKSTORED, Context.MODE_PRIVATE);
//            pdfFile=new File(directory,filename);
////            try{
////                pdfFile.createNewFile();
////            }catch (IOException e){
////                e.printStackTrace();
////            }
//            InputStream inputStream = null;
//            OutputStream outputStream = null;
//
//            try {
//
//                inputStream = body.byteStream();
//                outputStream = new FileOutputStream(pdfFile);
//                byte data[] = new byte[4096];
//                int count;
//                int progress = 0;
//                long fileSize = body.contentLength();
//                Log.d("kiemtra", "File Size=" + fileSize);
//                while ((count = inputStream.read(data)) != -1) {
//                    outputStream.write(data, 0, count);
//                    progress += count;
//                    Pair<Integer, Long> pairs = new Pair<>(progress, fileSize);
//                    downloadBookFileTask.doProgress(pairs);
//                    Log.d("kiemtra", "Progress: " + progress + "/" + fileSize + " >>>> " + (float) progress / fileSize);
//                }
//
//                outputStream.flush();
//
//                Log.d("kiemtra", pdfFile.getParent());
//                Pair<Integer, Long> pairs = new Pair<>(100, 100L);
//                downloadBookFileTask.doProgress(pairs);
//                result = true;
//                return;
//            } catch (IOException e) {
//                e.printStackTrace();
//                Pair<Integer, Long> pairs = new Pair<>(-1, Long.valueOf(-1));
//                downloadBookFileTask.doProgress(pairs);
//                pdfFile.delete();
//                Log.d("kiemtra", "Failed to save the file!");
//                call.cancel();
//                call = null;
//                result = false;
//                return;
//            } finally {
//                if (inputStream != null) inputStream.close();
//                if (outputStream != null) outputStream.close();
//            }
//        } catch (IOException e) {
//            result = false;
//            pdfFile.delete();
//            e.printStackTrace();
//            Log.d("kiemtra", "Failed to save the file!");
//            return;
//        }
//    }

//    private void downloadbookFile(final String bookID, final ProgressDialog progressDialog) {
//        call = null;
//        String urlBook = "/sachvui-686868666888/ebooks/2016/pdf/Sachvui.Com-quang-ganh-lo-di-va-vui-song.pdf";
//    }

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
}
