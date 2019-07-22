package com.phungthanhquan.bookapp.View.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;


import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.phungthanhquan.bookapp.Model.Room.DbRoomAccess;
import com.phungthanhquan.bookapp.Object.BookCase;
import com.phungthanhquan.bookapp.Object.BookRead;
import com.phungthanhquan.bookapp.Object.ChuongSach;
import com.phungthanhquan.bookapp.Object.DauTrang;
import com.phungthanhquan.bookapp.Presenter.Activity.PresenterLogicRead;
import com.phungthanhquan.bookapp.R;
import com.phungthanhquan.bookapp.View.InterfaceView.InterfaceViewActivityRead;

import java.io.File;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class Read extends AppCompatActivity implements View.OnClickListener, InterfaceViewActivityRead {

    private PDFView pdfView;
    private Boolean isUp;
    private LinearLayout bottom, header;
    private ImageView dautrang;
    private Toolbar toolbar;
    private SeekBar seekBar;
    private TextView tenSach;
    private TextView phanTramDoc;
    private TextView trangHienTai;
    private TextView tenChuong;
    private ImageView next, previous;
    private List<ChuongSach> chuongSachList;
    Boolean isNightMode, IsVertical, IsDauTrang;
    int numberPage, currentPage;
    private String chuongHienTai;
    private List<DauTrang> dauTrangList;
    private final String FILENAME_BOOKSTORED = "book_dowload";
    private String BOOK_ID,BOOK_NAME;
    private BookCase BOOK_CASE;
    private PresenterLogicRead presenterLogicRead;
    private long startTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        initControls();
    }
    @Override
    public void layChuongSach(List<ChuongSach> chuongSachListReturn) {
        Collections.sort(chuongSachListReturn, new Comparator<ChuongSach>() {
            @Override
            public int compare(ChuongSach o1, ChuongSach o2) {
                return o1.getPage_number() - o2.getPage_number();
            }
        });
        chuongSachList.addAll(chuongSachListReturn);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        File directory;
        ContextWrapper cw = new ContextWrapper(this);
        directory = cw.getDir(FILENAME_BOOKSTORED, Context.MODE_PRIVATE);
        File file=new File(directory,BOOK_ID+".pdf");
        pdfView.fromFile(file)
                .enableSwipe(false) // allows to block changing pages using swipe
                .swipeHorizontal(true)
                .enableDoubletap(false)
                .defaultPage(0)
                .onLoad(new OnLoadCompleteListener() {
                    @Override
                    public void loadComplete(int nbPages) {
                        numberPage = pdfView.getPageCount();
                        int num = numberPage - 1;
                        seekBar.setMax(num);
                    }
                })
                .enableAnnotationRendering(false) // render annotations (such as comments, colors or forms)
                .enableAntialiasing(false) // improve rendering a little bit on low-res screens
                // spacing between pages in dp. To define spacing color, set view background
                .spacing(0)
                .autoSpacing(true) // add dynamic spacing to fit each page on its own on the screen
                .pageSnap(true) // snap pages to screen boundaries
                .pageFling(true) // make a fling change only a single page like ViewPager
                .nightMode(false) // toggle night mode
                .load();
        bottom.setVisibility(View.INVISIBLE);
        header.setVisibility(View.INVISIBLE);
        dautrang.setVisibility(View.INVISIBLE);
        pdfView.setOnClickListener(this);
        next.setOnClickListener(this);
        previous.setOnClickListener(this);
        dautrang.setOnClickListener(this);
        phanTramDoc.setText(seekBar.getProgress() + "%");
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progresss;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progresss = progress * 100 / (numberPage - 1);
                currentPage = progress;
                if (currentPage == 0) {
                    previous.setVisibility(View.INVISIBLE);
                    previous.setEnabled(false);
                } else {
                    previous.setVisibility(View.VISIBLE);
                    previous.setEnabled(true);
                }
                if (currentPage == (seekBar.getMax())) {
                    next.setVisibility(View.INVISIBLE);
                    next.setEnabled(false);
                } else {
                    next.setVisibility(View.VISIBLE);
                    next.setEnabled(true);
                }
                phanTramDoc.setText(progresss + "%");
                pdfView.jumpTo(progress, true);
                trangHienTai.setText(getString(R.string.page) + " " + (currentPage + 1) + "/" + numberPage + " - ");
                CheckExit(chuongSachList, currentPage);
                kiemTraDauTrang();
                startTime = System.currentTimeMillis();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        tenSach.setText(BOOK_NAME);
        currentPage = pdfView.getCurrentPage();
        trangHienTai.setText(getString(R.string.page) + " " + (currentPage + 1) + "/" + numberPage + " - ");
        CheckExit(chuongSachList, currentPage+1);
    }

    @Override
    public void layDauTrang(List<DauTrang> dauTrangListReturn) {
        dauTrangList.addAll(dauTrangListReturn);
        presenterLogicRead.LayChuongSach(BOOK_ID);
    }

    @Override
    public void layBookCase(BookCase bookCase) {
        BOOK_CASE = bookCase;
        presenterLogicRead.LayDauTrang(BOOK_ID);
    }

    private void initControls() {
        pdfView = findViewById(R.id.pdfView);
        bottom = findViewById(R.id.tool_bottom);
        header = findViewById(R.id.tool_header);
        toolbar = findViewById(R.id.toolbar);
        next = findViewById(R.id.imageNext);
        previous = findViewById(R.id.imagePreVious);
        tenSach = findViewById(R.id.tensach);
        tenChuong = findViewById(R.id.tenChuong);
        seekBar = findViewById(R.id.seekBar);
        trangHienTai = findViewById(R.id.trang);
        phanTramDoc = findViewById(R.id.phantram);
        dautrang = findViewById(R.id.dautrang);
        BOOK_ID = getIntent().getStringExtra("book_id");
        isUp = false;
        isNightMode = false;
        IsVertical = false;
        IsDauTrang = false;
        chuongSachList = new ArrayList<>();
        dauTrangList = new ArrayList<>();
        startTime = System.currentTimeMillis();
        BOOK_NAME = getIntent().getStringExtra("book_name");
        presenterLogicRead = new PresenterLogicRead(this,this);
        presenterLogicRead.LayBookCase(BOOK_ID);
    }


    public void slideHideHeader(View view) {
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                0,  // fromYDelta
                -view.getHeight());                // toYDelta
        animate.setDuration(100);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        view.setEnabled(false);
    }

    public void slideShowHeader(View view) {
        view.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                -view.getHeight(),                 // fromYDelta
                0); // toYDelta
        animate.setDuration(100);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }

    public void slideHideStart(View view) {
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                view.getWidth(),                 // toXDelta
                0,  // fromYDelta
                0);                // toYDelta
        animate.setDuration(100);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        view.setEnabled(false);
    }

    public void slideShowStart(View view) {
        view.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(
                view.getWidth(),                 // fromXDelta
                0,                 // toXDelta
                0,                 // fromYDelta
                0); // toYDelta
        animate.setDuration(100);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }

    public void CheckExit(List<ChuongSach> chuongSaches, int currentPages) {
        int size = chuongSaches.size();
        for (int i = 0; i <= size - 1; i++) {
            if (i < size - 1) {
                if (currentPages >= chuongSaches.get(i).getPage_number() && currentPages < chuongSaches.get(i + 1).getPage_number()) {
                    chuongHienTai = chuongSaches.get(i).getContent();
                    tenChuong.setText(chuongHienTai);
                }
            } else if (currentPages >= chuongSaches.get(i).getPage_number()) {
                chuongHienTai = chuongSaches.get(i).getContent();
                tenChuong.setText(chuongHienTai);
            }
        }
    }

    public void slideUpBottom(View view) {
        view.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                view.getHeight(),  // fromYDelta
                0);                // toYDelta
        animate.setDuration(100);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }

    public void slideDownBottom(View view) {
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                0,                 // fromYDelta
                view.getHeight()); // toYDelta
        animate.setDuration(100);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }

    @Override
    public void onClick(View v) {
        currentPage = pdfView.getCurrentPage();
        switch (v.getId()) {
            case R.id.pdfView:
                isUp = !isUp;
                if (isUp) {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    getSupportActionBar().setDisplayShowHomeEnabled(true);
                    dautrang.setEnabled(true);
                    seekBar.setEnabled(true);
                    slideShowHeader(header);
                    slideUpBottom(bottom);
                    slideShowStart(dautrang);
                } else {
                    dautrang.setEnabled(false);
                    seekBar.setEnabled(false);
                    slideDownBottom(bottom);
                    slideHideHeader(header);
                    slideHideStart(dautrang);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                    getSupportActionBar().setDisplayShowHomeEnabled(false);
                }

                break;
            case R.id.imageNext:
                if (currentPage == (numberPage - 1)) {
                    next.setVisibility(View.INVISIBLE);
                    next.setEnabled(false);
                    pdfView.jumpTo(currentPage, true);
                } else if (currentPage < numberPage) {
                    previous.setEnabled(true);
                    previous.setVisibility(View.VISIBLE);
                    currentPage = currentPage + 1;
                    pdfView.jumpTo(currentPage, true);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        seekBar.setProgress(currentPage, true);
                    }
                    trangHienTai.setText(getString(R.string.page) + " " + (currentPage + 1) + "/" + numberPage + "  - ");
                    CheckExit(chuongSachList, currentPage);
                    kiemTraDauTrang();
                }
                startTime = System.currentTimeMillis();

                break;
            case R.id.imagePreVious:
                if (currentPage == 0) {
                    previous.setEnabled(false);
                    previous.setVisibility(View.INVISIBLE);
                    pdfView.jumpTo(currentPage, true);
                } else if (currentPage > 0) {
                    next.setVisibility(View.VISIBLE);
                    next.setEnabled(true);
                    currentPage = currentPage - 1;
                    pdfView.jumpTo(currentPage, true);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        seekBar.setProgress(currentPage, true);
                    }
                    trangHienTai.setText(getString(R.string.page) + " " + (currentPage + 1) + "/" + numberPage + " - ");
                    CheckExit(chuongSachList, currentPage);
                    kiemTraDauTrang();
                }
                startTime = System.currentTimeMillis();

                break;
            case R.id.dautrang:
                IsDauTrang = !IsDauTrang;
                if (IsDauTrang) {
                    dautrang.setImageResource(R.drawable.ic_star_yellow_24dp);
                    themDauTrang();
                } else {
                    dautrang.setImageResource(R.drawable.ic_star_border_blue_24dp);
                    xoaDauTrang();
                }
                break;
        }
    }

    public Boolean kiemtraTonTai(int page, List<DauTrang> dauTrangList) {
        for (DauTrang dauTrang : dauTrangList) {
            if (dauTrang.getTrang() == page) {
                return true;
            }
        }
        return false;
    }

    public void kiemTraDauTrang() {
        if (kiemtraTonTai(currentPage + 1, dauTrangList)) {
            IsDauTrang = true;
            dautrang.setImageResource(R.drawable.ic_star_yellow_24dp);
        } else {
            IsDauTrang = false;
            dautrang.setImageResource(R.drawable.ic_star_border_blue_24dp);
        }
    }

    public void themDauTrang() {
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("E");
        String thu = ft.format(dNow);
        Calendar calendar = Calendar.getInstance();
        String currentDay = thu +", "+getString(R.string.ngay)+" "+ DateFormat.getDateInstance().format(calendar.getTime());
        DauTrang dauTrang = new DauTrang(BOOK_ID,chuongHienTai, currentPage + 1, currentDay);
        DbRoomAccess.getInstance(this).insertDauTrangTask(this,dauTrang);
        dauTrangList.add(dauTrang);
    }

    public void xoaDauTrang() {
        for (Iterator<DauTrang> iterator = dauTrangList.iterator(); iterator.hasNext(); ) {
            DauTrang value = iterator.next();
            if (value.getTrang() == (currentPage + 1)) {
                iterator.remove();
                DbRoomAccess.getInstance(this).deleteDauTrangTask(this,currentPage+1);
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_read, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (isUp) {
            switch (item.getItemId()) {
                case R.id.nightmode:
                    isNightMode = !isNightMode;
                    if (isNightMode) {
                        pdfView.setNightMode(isNightMode);
                        item.setIcon(R.drawable.ic_nightmode_24dp);
                        next.setImageResource(R.drawable.next_white_24);
                        previous.setImageResource(R.drawable.previous_white_24);
                        pdfView.loadPages();
                    } else {
                        pdfView.setNightMode(isNightMode);
                        item.setIcon(R.drawable.ic_sunny_24dp);
                        next.setImageResource(R.drawable.next_black_24);
                        previous.setImageResource(R.drawable.previous_black_24);

                        pdfView.loadPages();
                    }

                    break;
                case R.id.oritaintion:
                    IsVertical = !IsVertical;
                    pdfView.setSwipeVertical(IsVertical);
                    pdfView.loadPages();
                    pdfView.jumpTo(currentPage, true);
                    break;
                case R.id.chuongsach:
                    Intent intent = new Intent(this, ChuongSachRead.class);
                    intent.putExtra("tensach",BOOK_NAME);
                    intent.putExtra("book_id",BOOK_ID);
                    startActivityForResult(intent, 100);
                    break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (resultCode == Activity.RESULT_OK) {
                int result = data.getIntExtra("trang", currentPage);
                currentPage = result - 1 ;
                pdfView.jumpTo(currentPage , true);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    seekBar.setProgress(currentPage, true);
                }
                trangHienTai.setText(getString(R.string.page) + " " + (currentPage + 1) + "/" + numberPage + " - ");
                int progresss = currentPage * 100 / (numberPage - 1);
                phanTramDoc.setText(progresss + "%");
                CheckExit(chuongSachList, currentPage+1);
                startTime = System.currentTimeMillis();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        long time = System.currentTimeMillis() - startTime;
        long seconds = time /1000;
        if(seconds>10){
            int progresss = currentPage * 100 / (numberPage - 1);
            if(progresss>BOOK_CASE.getLast_time()){
                BOOK_CASE.setLast_time(progresss);
                DbRoomAccess.getInstance(this).updateBookCaseTask(this,BOOK_CASE);
            }
        }
    }

}
