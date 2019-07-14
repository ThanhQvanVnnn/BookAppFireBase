package com.phungthanhquan.bookapp.View.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.phungthanhquan.bookapp.Adapter.TuSach_CaNhanShow_Adapter;
import com.phungthanhquan.bookapp.Object.BookCase;
import com.phungthanhquan.bookapp.Presenter.Activity.PresenterLogicTuSach_CaNhanClick;
import com.phungthanhquan.bookapp.R;
import com.phungthanhquan.bookapp.View.InterfaceView.InterfaceViewActivityTuSach_CaNhanClick;

import java.util.ArrayList;
import java.util.List;

public class TuSach_CaNhanClick extends AppCompatActivity implements InterfaceViewActivityTuSach_CaNhanClick {
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private TuSach_CaNhanShow_Adapter adapter;
    private List<BookCase> bookCaseList;
    private PresenterLogicTuSach_CaNhanClick presenterLogicTuSach_caNhanClick;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tu_sach__ca_nhan_click);
        initControls();
    }

    private void initControls() {
        recyclerView = findViewById(R.id.recycle_hienthi);
        bookCaseList = new ArrayList<>();
        adapter = new TuSach_CaNhanShow_Adapter(bookCaseList,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        recyclerView.setHasFixedSize(false);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.sach_da_doc));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        presenterLogicTuSach_caNhanClick = new PresenterLogicTuSach_CaNhanClick(this);
        presenterLogicTuSach_caNhanClick.laytusach(this);
    }

    @Override
    public void hienThiDanhSachSach(List<BookCase> bookCaseListReturn) {
        bookCaseList.addAll(bookCaseListReturn);
        adapter.notifyDataSetChanged();
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
