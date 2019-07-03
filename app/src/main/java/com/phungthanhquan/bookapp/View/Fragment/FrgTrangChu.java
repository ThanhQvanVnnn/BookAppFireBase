package com.phungthanhquan.bookapp.View.Fragment;


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
import com.phungthanhquan.bookapp.Adapter.ListAlbum_Adapter;
import com.phungthanhquan.bookapp.Adapter.RecycleView_ItemBook_Adapter;
import com.phungthanhquan.bookapp.Adapter.RecycleView_NXB_Adapter;
import com.phungthanhquan.bookapp.Adapter.ViewPager_Slider_Adapter;
import com.phungthanhquan.bookapp.Object.Album;
import com.phungthanhquan.bookapp.Object.Album_BookCase;
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


public class FrgTrangChu extends Fragment implements InterfaceViewFragmentTrangChu, View.OnClickListener {

    private TabLayout indicator;
    private ViewPager slider;
    private RecyclerView hienthiDSSachMoi;
    private RecyclerView hienthiDSSachKhuyenDoc;
    private RecyclerView hienthiDSSachVanHocTrongNuoc;
    private RecyclerView hienthiDSNhaXuatBan;
    private TextView allSachMoi;
    private TextView allSachVanHocTrongNuoc;
    private ImageButton search,checkInternet;
    private ProgressBar progressBarLoadMoreKhuyenDoc;
    private NestedScrollView nestedScrollView;
    private ConstraintLayout layoutInternetDisconnect;


    private SwipeRefreshLayout swipeRefreshLayout;
    private PresenterTrangChu presenterFragmentTrangChu;


    private List<Marketing> sliderList;
    private List<Album> albumBookCase;
    private List<NXB> danhSachNXB;
//    private List<ItemBook> danhSachVanHocTrongNuoc;
//    private List<ItemBook> danhSachKhuyenDoc;
//    private List<ItemBook> danhSachSachMoi;

    private ListAlbum_Adapter adapterAlbum;
    private ViewPager_Slider_Adapter slider_Adapter;
    private HorizontalInfiniteCycleViewPager pager_album;
    private RecycleView_NXB_Adapter adapterNXB;
    private RecycleView_ItemBook_Adapter adapterVanHocTrongNuoc;
    private RecycleView_ItemBook_Adapter adapterSachKhuyenDoc;
    private RecyclerView.LayoutManager layoutManagerSachKhuyenDoc;
    private RecycleView_ItemBook_Adapter adapterSachMoi;
    private Timer timer;
    Toast toast;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trangchu, container, false);
        InitControls(view);
        CreateAdapterAddView();
        InternetConnected();
        RefresherLayout();
        return view;
    }

    private void InternetConnected(){
        if(MainActivity.isNetworkConnected(getActivity())){
            nestedScrollView.setVisibility(View.VISIBLE);
            ActivePresenter();
            layoutInternetDisconnect.setVisibility(View.GONE);
        }else {
            nestedScrollView.setVisibility(View.GONE);
            layoutInternetDisconnect.setVisibility(View.VISIBLE);
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
        //onclick
        checkInternet.setOnClickListener(this);
        allSachMoi.setOnClickListener(this);
        allSachVanHocTrongNuoc.setOnClickListener(this);
        search.setOnClickListener(this);
        //presenter logic
        presenterFragmentTrangChu = new PresenterTrangChu(this);
//        OnsCroll();
    }


    @Override
    public void hienthislider(final List<Marketing> sliderListReturn) {
        sliderList.addAll(sliderListReturn);
        slider_Adapter.notifyDataSetChanged();
        indicator.setupWithViewPager(slider, true);
        int sizesliderList = sliderList.size();
        timer = new Timer();
        timer.scheduleAtFixedRate(new FrgTrangChu.TimeWork(sizesliderList), 4000, 6000);
    }

//    @Override
//    public void hienthidsSachmoi(final List<ItemBook> dsSachMoi) {
//        new Thread() {
//            @Override
//            public void run() {
//                danhSachSachMoi.addAll(dsSachMoi);
//                try {
//                    // code runs in a thread
//                    getActivity().runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            adapterSachMoi.notifyDataSetChanged();
//                        }
//                    });
//                } catch (final Exception ex) {
//                }
//            }
//        }.start();
//
//
//    }
//
//    @Override
//    public void hienthidsSachKhuyenDoc(final List<ItemBook> dsSachKhuyenDoc) {
//        new Thread() {
//            @Override
//            public void run() {
//                danhSachKhuyenDoc.addAll(dsSachKhuyenDoc);
//                try {
//                    // code runs in a thread
//                    getActivity().runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            adapterSachKhuyenDoc.notifyDataSetChanged();
//                        }
//                    });
//                } catch (final Exception ex) {
//                }
//            }
//        }.start();
//
//
//    }
//
//    @Override
//    public void hienthidsSachVanHocTrongNuoc(final List<ItemBook> dsSachVanHocTrongNuoc) {
//        new Thread() {
//            @Override
//            public void run() {
//                danhSachVanHocTrongNuoc.addAll(dsSachVanHocTrongNuoc);
//                try {
//                    // code runs in a thread
//                    getActivity().runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            adapterVanHocTrongNuoc.notifyDataSetChanged();
//                        }
//                    });
//                } catch (final Exception ex) {
//                }
//            }
//        }.start();
//
//
//    }

//    @Override
//    public void hienthidsNhaXuatBan(final List<NXB> dsNXB) {
//        new Thread() {
//            @Override
//            public void run() {
//                danhSachNXB.addAll(dsNXB);
//                try {
//                    // code runs in a thread
//                    getActivity().runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            adapterNXB.notifyDataSetChanged();
//                        }
//                    });
//                } catch (final Exception ex) {
//                }
//            }
//        }.start();
//
//
//    }
//
    @Override
    public void hienthiAlbumSach(final List<Album> albumBookCases) {
        albumBookCase.addAll(albumBookCases);
        pager_album.notifyDataSetChanged();
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
                intent.putExtra("Title","Sách mới");
                startActivity(intent);
                break;
            case R.id.xemtatca_vanhoctrongnuoc:
                intent = new Intent(getActivity(), MarketingChiTiet.class);
                intent.putExtra("Title","Văn học trong nước");
                startActivity(intent);
                break;
            case R.id.search_book:
                if(MainActivity.isNetworkConnected(getContext())){
                    intent = new Intent(getActivity(), SearchBook.class);
                    getActivity().startActivity(intent);
                }else {
                 showAToast(getContext().getResources().getString(R.string.openinternet));
                }

                break;
            case R.id.checkInternet:
                InternetConnected();
                break;
        }
    }

    private void CreateAdapterAddView() {
        //list book
        sliderList = new ArrayList<>();
        albumBookCase = new ArrayList<>();
        danhSachNXB = new ArrayList<>();
//        danhSachVanHocTrongNuoc = new ArrayList<>();
//        danhSachKhuyenDoc = new ArrayList<>();
//        danhSachSachMoi = new ArrayList<>();
        //list adapter
        slider_Adapter = new ViewPager_Slider_Adapter(getActivity(), sliderList);
        adapterAlbum = new ListAlbum_Adapter(albumBookCase, getContext());
        adapterNXB = new RecycleView_NXB_Adapter(getActivity(), danhSachNXB);
//        adapterVanHocTrongNuoc = new RecycleView_ItemBook_Adapter(getContext(), danhSachVanHocTrongNuoc, 0);
//        adapterSachKhuyenDoc = new RecycleView_ItemBook_Adapter(getContext(), danhSachKhuyenDoc, 0);
//        adapterSachMoi = new RecycleView_ItemBook_Adapter(getContext(), danhSachSachMoi, 0);
        //slider
        slider.setAdapter(slider_Adapter);
        //albumBookCase
        pager_album.setAdapter(adapterAlbum);
        //NXB
        hienthiDSNhaXuatBan.setAdapter(adapterNXB);
        hienthiDSNhaXuatBan.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        hienthiDSNhaXuatBan.setHasFixedSize(true);
        //văn học trong nước
        hienthiDSSachVanHocTrongNuoc.setAdapter(adapterVanHocTrongNuoc);
        hienthiDSSachVanHocTrongNuoc.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        hienthiDSSachVanHocTrongNuoc.setHasFixedSize(true);
        //khuyên đọc
        hienthiDSSachKhuyenDoc.setAdapter(adapterSachKhuyenDoc);
        layoutManagerSachKhuyenDoc = new GridLayoutManager(getContext(), 3);
        hienthiDSSachKhuyenDoc.setLayoutManager(layoutManagerSachKhuyenDoc);
        hienthiDSSachKhuyenDoc.setHasFixedSize(true);
        //sách mới
//        adapterSachMoi.setHasStableIds(true);
        hienthiDSSachMoi.setAdapter(adapterSachMoi);
        hienthiDSSachMoi.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        hienthiDSSachMoi.setHasFixedSize(true);
    }

    private void ActivePresenter() {
        presenterFragmentTrangChu.xulislider();
//        presenterFragmentTrangChu.xuliHienthiDsSachMoi();
        presenterFragmentTrangChu.xuliHienThiAlBumSach();
//        presenterFragmentTrangChu.xuliHienthiDsSachVanHocTrongNuoc();
//        presenterFragmentTrangChu.xuliHienThiDsNhaXuatBan();
//        presenterFragmentTrangChu.xuliHienthiDsSachKhuyenDoc();
    }
    //refresher layout
    public void RefresherLayout() {
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(android.R.color.holo_blue_dark)
                , getResources().getColor(android.R.color.holo_blue_light)
                , getResources().getColor(android.R.color.holo_orange_light));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(MainActivity.isNetworkConnected(getActivity())){
                    swipeRefreshLayout.setRefreshing(true);
                    if(timer!=null) {
                        timer.cancel();
                    }
                    sliderList.clear();
                    albumBookCase.clear();
                    danhSachNXB.clear();
//                    danhSachVanHocTrongNuoc.clear();
//                    danhSachKhuyenDoc.clear();
//                    danhSachSachMoi.clear();
                    InternetConnected();
                }else {
                    if(timer!=null) {
                        timer.cancel();
                    }
                    sliderList.clear();
                    albumBookCase.clear();
                    danhSachNXB.clear();
//                    danhSachVanHocTrongNuoc.clear();
//                    danhSachKhuyenDoc.clear();
//                    danhSachSachMoi.clear();
                    InternetConnected();
                }
                swipeRefreshLayout.setRefreshing(false);
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
 // onscroll listener screen
//    public void OnsCroll() {
//        if (nestedScrollView != null) {
//
//            nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
//                @Override
//                public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
////                    String TAG = "nested_sync";
//                    final int sizeListSachKhuyenDoc = danhSachKhuyenDoc.size();
//
//                    if (scrollY > oldScrollY) {
////                        Log.i(TAG, "Scroll DOWN");
//                    }
//                    if (scrollY < oldScrollY) {
////                        Log.i(TAG, "Scroll UP");
//                    }
//
//                    if (scrollY == 0) {
////                        Log.i(TAG, "TOP SCROLL");
//                    }
//
//                    if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
//                        hienthiDSSachKhuyenDoc.setNestedScrollingEnabled(false);
//                        progressBarLoadMoreKhuyenDoc.setVisibility(View.VISIBLE);
//                        Handler handler = new Handler();
//                        handler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                List<ItemBook> dsSachLayVe = new ArrayList<>();
//                                dsSachLayVe = presenterFragmentTrangChu.xuliHienThiDsKhuyenDocLoadMore(sizeListSachKhuyenDoc,
//                                        progressBarLoadMoreKhuyenDoc, hienthiDSSachKhuyenDoc);
//                                if (dsSachLayVe.size() != 0) //check for scroll down
//                                {
//                                    danhSachKhuyenDoc.addAll(dsSachLayVe);
//                                    adapterSachKhuyenDoc.notifyDataSetChanged();
//                                }
//
//                            }
//                        }, 1000);
////                        Log.i(TAG, "BOTTOM SCROLL");
//                    }
//                }
//            });
//        }
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }
    public void showAToast (String st){ //"Toast toast" is declared in the class
        try{ toast.getView().isShown();     // true if visible
            toast.setText(st);
        } catch (Exception e) {         // invisible if exception
            toast = Toast.makeText(getContext(), st,  Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
        }
        toast.show();  //finally display it
    }
}

