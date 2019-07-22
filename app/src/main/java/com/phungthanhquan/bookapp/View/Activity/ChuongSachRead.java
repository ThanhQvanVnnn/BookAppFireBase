package com.phungthanhquan.bookapp.View.Activity;

import android.content.Intent;
import android.os.Build;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import com.phungthanhquan.bookapp.Adapter.ViewPager_ChuongSach_Adapter;
import com.phungthanhquan.bookapp.R;
import com.phungthanhquan.bookapp.View.Fragment.FrgChuongSach;
import com.phungthanhquan.bookapp.View.Fragment.FrgDauTrang;

import java.util.ArrayList;
import java.util.List;

public class ChuongSachRead extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager pager;
    private Fragment chuongsach,dautrang;
    private List<String> titles;
    private List<Fragment> fragments;
    private ViewPager_ChuongSach_Adapter viewPager_chuongSach_adapter;
    private String tenSach;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chuong_sach);
        initControls();
        Intent intent = getIntent();
        tenSach = intent.getStringExtra("tensach");
        toolbar.setTitle(tenSach);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            toolbar.setTitleTextColor(getColor(R.color.white));
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void initControls() {
        toolbar = findViewById(R.id.toolbar);
        tabLayout = findViewById(R.id.tabbarlayout);
        pager = findViewById(R.id.viewPager);

        fragments = new ArrayList<>();
        chuongsach = new FrgChuongSach();
        dautrang = new FrgDauTrang();
        fragments.add(chuongsach);
        fragments.add(dautrang);
        titles = new ArrayList<>();
        titles.add(getString(R.string.chuongsach));
        titles.add(getString(R.string.trang_da_luu));
        viewPager_chuongSach_adapter = new ViewPager_ChuongSach_Adapter(getSupportFragmentManager(),fragments,titles);
        pager.setAdapter(viewPager_chuongSach_adapter);
        tabLayout.setupWithViewPager(pager);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
