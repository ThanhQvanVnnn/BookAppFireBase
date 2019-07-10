package com.phungthanhquan.bookapp.View.Fragment;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.tabs.TabLayout;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.core.widget.NestedScrollView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;
import com.google.firebase.firestore.DocumentSnapshot;
import com.phungthanhquan.bookapp.Adapter.Album_NXB_Adapter;
import com.phungthanhquan.bookapp.Adapter.ListAlbum_Adapter;
import com.phungthanhquan.bookapp.Adapter.RecycleView_NXB_Adapter;
import com.phungthanhquan.bookapp.Adapter.ViewPager_Slider_Adapter;
import com.phungthanhquan.bookapp.Object.Album;
import com.phungthanhquan.bookapp.Object.Marketing;
import com.phungthanhquan.bookapp.Object.NXB;
import com.phungthanhquan.bookapp.Presenter.Fragment.PresenterTrangChu;
import com.phungthanhquan.bookapp.R;
import com.phungthanhquan.bookapp.View.Activity.MainActivity;
import com.phungthanhquan.bookapp.View.Activity.MarketingChiTiet;
import com.phungthanhquan.bookapp.View.Activity.SearchBook;
import com.phungthanhquan.bookapp.View.InterfaceView.InterfaceViewFragmentTrangChu;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import dmax.dialog.SpotsDialog;


public class FrgTrangChu extends Fragment implements InterfaceViewFragmentTrangChu, View.OnClickListener {

    private TabLayout indicator;
    private RecyclerView hienthiDSSachMoi;
    private RecyclerView hienthiDSSachKhuyenDoc;
    private RecyclerView hienthiDSSachVanHocTrongNuoc;
    private RecyclerView hienthiDSNhaXuatBan;
    private TextView allSachMoi;
    private TextView allSachVanHocTrongNuoc;
    private ImageButton search, checkInternet;
    private ProgressBar progressBarLoadMoreKhuyenDoc;
    private NestedScrollView nestedScrollView;
    private ConstraintLayout layoutInternetDisconnect;
    public AlertDialog loadingDialog;
    private List<Marketing> dsKhuyenDoc;
    private SwipeRefreshLayout swipeRefreshLayout;
    private PresenterTrangChu presenterFragmentTrangChu;

    private ListAlbum_Adapter adapterAlbum;
    private ViewPager_Slider_Adapter slider_Adapter;
    private HorizontalInfiniteCycleViewPager pager_album;
    private ViewPager slider;
    private RecycleView_NXB_Adapter adapterNXB;
    private Album_NXB_Adapter adapterVanHocTrongNuoc;
    private Album_NXB_Adapter adapterSachKhuyenDoc;
    private RecyclerView.LayoutManager layoutManagerSachKhuyenDoc;
    private Album_NXB_Adapter adapterSachMoi;
    private DocumentSnapshot documentCuoiCung;
    Toast toast;
    Timer timer;
    List<String> dsHinhAnhKhuyenDoc;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trangchu, container, false);
        InitControls(view);
        RefresherLayout();
        loadingDialog.show();
        InternetConnected();
        return view;
    }

    private void InternetConnected() {
        if (MainActivity.isNetworkConnected(getActivity())) {
            nestedScrollView.setVisibility(View.VISIBLE);
            ActivePresenter();
            layoutInternetDisconnect.setVisibility(View.GONE);
        } else {
            nestedScrollView.setVisibility(View.GONE);
            layoutInternetDisconnect.setVisibility(View.VISIBLE);
            loadingDialog.dismiss();
        }
    }

    private void InitControls(View view) {
        //binding id
        slider = view.findViewById(R.id.pager_slider);
        indicator = view.findViewById(R.id.indicator_slider);
        hienthiDSSachMoi = view.findViewById(R.id.recycle_sachmoi);
        hienthiDSSachKhuyenDoc = view.findViewById(R.id.recycle_sachkhuyendoc);
        hienthiDSSachVanHocTrongNuoc = view.findViewById(R.id.recycle_vanhoctrongnuoc);
        hienthiDSNhaXuatBan = view.findViewById(R.id.recycle_nhaxuatban);
        allSachMoi = view.findViewById(R.id.xemtatca_sachmoi);
        allSachVanHocTrongNuoc = view.findViewById(R.id.xemtatca_vanhoctrongnuoc);
        pager_album = view.findViewById(R.id.horizontal_cycle);
        search = view.findViewById(R.id.search_book);
        swipeRefreshLayout = view.findViewById(R.id.swiperefresh);
        progressBarLoadMoreKhuyenDoc = view.findViewById(R.id.progressLoadMoreKhuyenDoc);
        nestedScrollView = view.findViewById(R.id.scroll_trangchu);
        layoutInternetDisconnect = view.findViewById(R.id.layout_internet_disconnect);
        checkInternet = view.findViewById(R.id.checkInternet);
        loadingDialog = new SpotsDialog.Builder().setContext(getContext()).build();
        loadingDialog.setMessage(getResources().getString(R.string.dangtaidulieu));
        dsKhuyenDoc = new ArrayList<>();
        //onclick
        checkInternet.setOnClickListener(this);
        allSachMoi.setOnClickListener(this);
        allSachVanHocTrongNuoc.setOnClickListener(this);
        search.setOnClickListener(this);
        //presenter logic
        presenterFragmentTrangChu = new PresenterTrangChu(this);
        OnsCroll();
    }


    @Override
    public void hienthislider(final List<Marketing> sliderListReturn) {
        if (timer != null) {
            timer.cancel();
            timer.purge();
            timer = null;
        }
        slider_Adapter = new ViewPager_Slider_Adapter(getActivity(), sliderListReturn);
        slider.setAdapter(slider_Adapter);
        slider_Adapter.notifyDataSetChanged();
        indicator.setupWithViewPager(slider, true);
        int sizesliderList = sliderListReturn.size();
        timer = new Timer();
        timer.scheduleAtFixedRate(new FrgTrangChu.TimeWork(sizesliderList), 4000, 6000);
        loadingDialog.dismiss();
    }

    @Override
    public void hienthidsSachmoi(final List<Marketing> dsSachMoi) {

        adapterSachMoi = new Album_NXB_Adapter(getActivity(),dsSachMoi);
        hienthiDSSachMoi.setAdapter(adapterSachMoi);
        hienthiDSSachMoi.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        hienthiDSSachMoi.setHasFixedSize(true);

    }

    @Override
    public void hienthidsSachKhuyenDoc(final List<Marketing> dsSachKhuyenDocs, DocumentSnapshot documentSnapshot) {
        dsKhuyenDoc.addAll(dsSachKhuyenDocs);
        documentCuoiCung = documentSnapshot;
        adapterSachKhuyenDoc = new Album_NXB_Adapter(getContext(),dsKhuyenDoc);
        hienthiDSSachKhuyenDoc.setAdapter(adapterSachKhuyenDoc);
        layoutManagerSachKhuyenDoc = new GridLayoutManager(getContext(), 3);
        hienthiDSSachKhuyenDoc.setLayoutManager(layoutManagerSachKhuyenDoc);
        hienthiDSSachKhuyenDoc.setHasFixedSize(true);
    }

    @Override
    public void hienthiloadmoreKhuyenDoc(List<Marketing> dsSachKhuyenDoc, DocumentSnapshot documentSnapshot) {
        if(dsSachKhuyenDoc.size()<6){
            progressBarLoadMoreKhuyenDoc.setVisibility(View.INVISIBLE);
            hienthiDSSachKhuyenDoc.setNestedScrollingEnabled(false);
        }
            dsKhuyenDoc.addAll(dsSachKhuyenDoc);
            documentCuoiCung = documentSnapshot;
            adapterSachKhuyenDoc.addMoreImage();
            adapterSachKhuyenDoc.notifyDataSetChanged();
            hienthiDSSachKhuyenDoc.setNestedScrollingEnabled(true);

    }

    @Override
    public void hienthidsSachVanHocTrongNuoc( List<Marketing> dsSachVanHocTrongNuoc) {
        adapterVanHocTrongNuoc = new Album_NXB_Adapter(getContext(), dsSachVanHocTrongNuoc);
        hienthiDSSachVanHocTrongNuoc.setAdapter(adapterVanHocTrongNuoc);
        hienthiDSSachVanHocTrongNuoc.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        hienthiDSSachVanHocTrongNuoc.setHasFixedSize(true);
    }

    @Override
    public void hienthidsNhaXuatBan(final List<NXB> dsNXB) {

        List<String> listAnh = new ArrayList<>();
        for (int i = 0; i < dsNXB.size(); i++) {
            listAnh.add("a");
        }
        adapterNXB = new RecycleView_NXB_Adapter(getActivity(), dsNXB, listAnh);
        hienthiDSNhaXuatBan.setAdapter(adapterNXB);
        hienthiDSNhaXuatBan.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        hienthiDSNhaXuatBan.setHasFixedSize(true);

    }

    @Override
    public void hienthiAlbumSach(final List<Album> albumBookCases) {
        List<String> listAnh = new ArrayList<>();
        for (int i = 0; i < albumBookCases.size(); i++) {
            listAnh.add("a");
        }
        adapterAlbum = new ListAlbum_Adapter(albumBookCases, getContext(), listAnh);
        pager_album.setAdapter(adapterAlbum);
        pager_album.setMediumScaled(true);
        pager_album.setScrollDuration(200);
        pager_album.setMaxPageScale(0.8F);
        pager_album.setMinPageScale(0.5F);
        pager_album.setCenterPageScaleOffset(30.0F);
        pager_album.setMinPageScaleOffset(5.0F);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.xemtatca_sachmoi:
                intent = new Intent(getActivity(), MarketingChiTiet.class);
                intent.putExtra("Title","Sách Mới");
                intent.putExtra("id","1HjY7wPuA7WM2hsmd8NE");
                startActivity(intent);
                break;
            case R.id.xemtatca_vanhoctrongnuoc:
                intent = new Intent(getActivity(), MarketingChiTiet.class);
                intent.putExtra("Title", "Văn học trong nước");
                intent.putExtra("id","nzqndZ3vpgLlJHz4mjy7");
                startActivity(intent);
                break;
            case R.id.search_book:
                if (MainActivity.isNetworkConnected(getContext())) {
                    intent = new Intent(getActivity(), SearchBook.class);
                    getActivity().startActivity(intent);
                } else {
                    showAToast(getContext().getResources().getString(R.string.openinternet));
                }

                break;
            case R.id.checkInternet:
                InternetConnected();
                break;
        }
    }


    private void ActivePresenter() {
        documentCuoiCung = null;
        progressBarLoadMoreKhuyenDoc.setVisibility(View.VISIBLE);
        dsKhuyenDoc.clear();
        presenterFragmentTrangChu.xulislider(swipeRefreshLayout);
        presenterFragmentTrangChu.xuliHienthiDsSachMoi();
        presenterFragmentTrangChu.xuliHienThiAlBumSach();
        presenterFragmentTrangChu.xuliHienthiDsSachVanHocTrongNuoc();
        presenterFragmentTrangChu.xuliHienThiDsNhaXuatBan();
        presenterFragmentTrangChu.xuliHienthiDsSachKhuyenDoc();
    }

    //refresher layout
    public void RefresherLayout() {
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(android.R.color.holo_blue_dark)
                , getResources().getColor(android.R.color.holo_blue_light)
                , getResources().getColor(android.R.color.holo_orange_light));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (MainActivity.isNetworkConnected(getActivity())) {
                    swipeRefreshLayout.setRefreshing(true);
                    InternetConnected();
                } else {
                    InternetConnected();
                }

            }
        });
    }

    //timer run slider
    class TimeWork extends TimerTask {
        int sizesliderList;

        public TimeWork(int sizesliderList) {
            this.sizesliderList = sizesliderList;
        }

        @Override
        public void run() {
            if (getActivity() != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int currentpage = slider.getCurrentItem();
                        if (currentpage < sizesliderList - 1) {
                            slider.setCurrentItem(currentpage + 1);
                        } else slider.setCurrentItem(0);
                    }
                });
            }
        }
    }

    public void OnsCroll() {
        if (nestedScrollView != null) {

            nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                @Override
                public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                    String TAG = "nested_sync";
                    final int sizeListSachKhuyenDoc = dsKhuyenDoc.size();

                    if (scrollY > oldScrollY) {
//                        Log.i(TAG, "Scroll DOWN");
                    }
                    if (scrollY < oldScrollY) {
//                        Log.i(TAG, "Scroll UP");
                    }

                    if (scrollY == 0) {
//                        Log.i(TAG, "TOP SCROLL");
                    }

                    if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                        hienthiDSSachKhuyenDoc.setNestedScrollingEnabled(false);
                        presenterFragmentTrangChu.xuliHienThiDsKhuyenDocLoadMore(documentCuoiCung);
                    }
                }
            });
        }
    }

    public void showAToast(String st) { //"Toast toast" is declared in the class
        try {
            toast.getView().isShown();     // true if visible
            toast.setText(st);
        } catch (Exception e) {         // invisible if exception
            toast = Toast.makeText(getContext(), st, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
        }
        toast.show();  //finally display it
    }
}

