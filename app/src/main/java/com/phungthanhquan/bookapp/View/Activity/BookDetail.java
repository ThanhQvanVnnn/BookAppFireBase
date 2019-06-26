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
import android.os.AsyncTask;

import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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

import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.phungthanhquan.bookapp.Adapter.RecycleView_noidungbinhluan_Adapter;
import com.phungthanhquan.bookapp.Object.BinhLuan;
import com.phungthanhquan.bookapp.Object.Book;
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
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import dmax.dialog.SpotsDialog;
import okhttp3.ResponseBody;


public class BookDetail extends AppCompatActivity implements InterfaceViewActivityDetailBook, View.OnClickListener  {
    private ImageView detailbook_image;
    private Toolbar toolbar;
    private TextView tenSach;
    private RatingBar ratingSach;
    private TextView soluongdanhgia;
    private TextView tentacgia;
    private TextView nhaxuatban;
    private TextView ngayphathanh;
    private TextView sotrang;
    private TextView giatien;
    private TextView menhgia;
    private Button docsach;
    private ExpandableTextView noidungSach;
    private Button chiaSeCamNhan;
    private PresenterBookDetail presenterBookDetail;
    private RecyclerView recycle_DsDanhGia;
    private List<BinhLuan> dsBinhLuan;
    private Dialog dialogCamNhan;
    private LinearLayout xemThemDanhGia;
    private RecycleView_noidungbinhluan_Adapter recycleView_noidungbinhluan_adapter;
    private SwipeRefreshLayout refreshLayout;
    private NestedScrollView nestedScrollView;

    private DownloadBookFileTask downloadBookFileTask;
    private ProgressDialog progressDialog;
    private ImageButton imageButtonInternet;
    private ConstraintLayout constraintLayoutInternet;
    private int lenghtFile;
    public  AlertDialog loadingDialog;
    Toast toast;

    private final String FILENAME_BOOKSTORED = "book_dowload";

    private Book ChiTietSach;

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
        if(MainActivity.isNetworkConnected(this)) {
            nestedScrollView.setVisibility(View.VISIBLE);
            constraintLayoutInternet.setVisibility(View.GONE);
        }else {
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
        ngayphathanh = findViewById(R.id.textview_ngayphathanh);
        nhaxuatban = findViewById(R.id.textview_tenNXB);
        sotrang = findViewById(R.id.textview_sotrang);
        giatien = findViewById(R.id.textview_giatien);
        menhgia = findViewById(R.id.textview_menhgia);
        chiaSeCamNhan = findViewById(R.id.button_chiasecamnhan);
        recycle_DsDanhGia = findViewById(R.id.recycle_danhsachdanhgia);
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
//        Intent intent = getIntent();
//        String idSach = intent.getStringExtra("iD");
//        String urlImage = getIntent().getStringExtra("image");
        toolbar = findViewById(R.id.toolbar_bookDetail);
        toolbar.setTitle(R.string.chi_tiet_sach);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        presenterBookDetail = new PresenterBookDetail(this);
        ChiTietSach = new Book();
        dsBinhLuan = new ArrayList<>();

        recycleView_noidungbinhluan_adapter = new RecycleView_noidungbinhluan_Adapter(this,dsBinhLuan);
        recycle_DsDanhGia.setAdapter(recycleView_noidungbinhluan_adapter);
        recycle_DsDanhGia.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recycle_DsDanhGia.setHasFixedSize(false);
        recycle_DsDanhGia.setNestedScrollingEnabled(false);
        presenterBookDetail.xuliHienThiSach();
        presenterBookDetail.xuliHienThiDsDanhGia();
        chensach(ChiTietSach);

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void hienThiNoiDungSach(Book book) {
        ChiTietSach = book;
    }

    @Override
    public void hienThiDsDanhGia(List<BinhLuan> dsDanhGia) {
        dsBinhLuan.addAll(dsDanhGia);
        recycleView_noidungbinhluan_adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.button_chiasecamnhan:
                if(MainActivity.isNetworkConnected(this)) {
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
                }else {
                 showAToast(getResources().getString(R.string.openinternet));
                }
                break;
            case R.id.xemthemdanhgia:
                if(MainActivity.isNetworkConnected(this)) {
                    intent = new Intent(this, XemThemDanhGia.class);
                    ActivityOptions options = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                        options = ActivityOptions.makeSceneTransitionAnimation(this,
                                chiaSeCamNhan, "chiasecamnhan");
                    }
                    startActivity(intent, options.toBundle());
                }else{
                    showAToast(getResources().getString(R.string.openinternet));
                }
                break;
            case R.id.button_docsach:
                if(MainActivity.isNetworkConnected(this)) {
                    //nếu chưa mua sách:

                    ///nếu đã mua sách:
                    File directory;
                    ContextWrapper cw = new ContextWrapper(BookDetail.this);
                    directory = cw.getDir(FILENAME_BOOKSTORED, Context.MODE_PRIVATE);
                    final File file = new File(directory, ChiTietSach.getId_sach() + ".pdf");
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
                }
                else {
                    //nếu chưa mua sách:
                    showAToast(getResources().getString(R.string.openinternet_readbook));
                }
                break;


            case R.id.checkInternet:
                if(MainActivity.isNetworkConnected(this)){
                    nestedScrollView.setVisibility(View.VISIBLE);
                    constraintLayoutInternet.setVisibility(View.GONE);
                    docsach.setOnClickListener(this);
                    chiaSeCamNhan.setOnClickListener(this);
                    xemThemDanhGia.setOnClickListener(this);
                    nestedScrollView.getParent().requestChildFocus(nestedScrollView, nestedScrollView);
                }else {
                    constraintLayoutInternet.setVisibility(View.VISIBLE);
                    nestedScrollView.setVisibility(View.GONE);
                }
                break;
        }
    }
    public void chensach(final Book book){
        if(book!=null) {
                    Picasso.get().load(book.getHinhanh_sach()).resize(150,200).into(detailbook_image);
                    tenSach.setText(book.getTen_sach());
                    noidungSach.setText(book.getNoidung_sach());
                    ratingSach.setRating(book.getSosao_danhgia());
                    soluongdanhgia.setText(book.getSoluong_danhgia() + " đánh giá");
                    tentacgia.setText(book.getTen_tacgia());
                    nhaxuatban.setText(book.getNXB());
                    ngayphathanh.setText(book.getNgayphathanh());
                    sotrang.setText(book.getSo_trang() + "");
                    DecimalFormat df = new DecimalFormat("###,###.###");
                    df.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.ITALY));
                    String giatien_format = df.format(book.getGiatien_sach());
                    //nếu đã mua sách thì set giá tiền bằng đã mua, set chữ màu xanh lá cây........

                    //nếu chưa mua sách
                    giatien.setText(giatien_format + "");
                    menhgia.setPaintFlags(menhgia.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        }else {

        }
    }
    private void RefreshTrang() {
        refreshLayout.setColorSchemeColors(getResources().getColor(android.R.color.holo_blue_dark)
                ,getResources().getColor(android.R.color.holo_blue_light)
                ,getResources().getColor(android.R.color.holo_orange_light));
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(MainActivity.isNetworkConnected(BookDetail.this)) {
                    constraintLayoutInternet.setVisibility(View.GONE);
                    nestedScrollView.setVisibility(View.VISIBLE);
                    refreshLayout.setRefreshing(true);
                    dsBinhLuan.clear();
                    presenterBookDetail.xuliHienThiDsDanhGia();
                    presenterBookDetail.xuliHienThiSach();

                }else {
                    nestedScrollView.setVisibility(View.GONE);
                    constraintLayoutInternet.setVisibility(View.VISIBLE);
                }
                refreshLayout.setRefreshing(false);
            }});
    }

    private class DownloadBookFileTask extends AsyncTask<ResponseBody, Pair<Integer, Long>, Boolean> {
        private String bookID;
        private ProgressDialog dialog;
        public DownloadBookFileTask(String bookID,ProgressDialog progressDialog) {
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
                    intent.putExtra("idSach", bookID);
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
         int file_size = 0 ;
         try {
             URL url = new URL(urls);
             URLConnection connection = url.openConnection();
             connection.connect();
            file_size= connection.getContentLength();
         } catch (MalformedURLException e) {
             e.printStackTrace();
         } catch (IOException e) {
             e.printStackTrace();
         }

        return file_size;
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
