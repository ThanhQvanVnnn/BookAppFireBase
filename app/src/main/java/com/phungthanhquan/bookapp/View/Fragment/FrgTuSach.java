package com.phungthanhquan.bookapp.View.Fragment;

import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.phungthanhquan.bookapp.Adapter.Tusach_Adapter;
import com.phungthanhquan.bookapp.Model.LoadMore.InterfaceLoadMore;
import com.phungthanhquan.bookapp.Model.LoadMore.LoadMoreScroll;
import com.phungthanhquan.bookapp.Object.Book;
import com.phungthanhquan.bookapp.Object.BookCase;
import com.phungthanhquan.bookapp.Object.UserRent;
import com.phungthanhquan.bookapp.Presenter.Fragment.PresenterLogicTuSach;
import com.phungthanhquan.bookapp.R;
import com.phungthanhquan.bookapp.View.InterfaceView.InterfaceViewFragmentTuSach;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FrgTuSach extends Fragment implements InterfaceViewFragmentTuSach {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private Tusach_Adapter tusach_adapter;
    private List<BookCase> itemBookCaseList;
    private PresenterLogicTuSach presenterLogicTuSach;
    private List<UserRent> rentList;
    SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
    Toast toast;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tusach,container,false);
        initControls(view);
        refresherData();
        return view;
    }

    private void initControls(View view) {
        recyclerView = view.findViewById(R.id.recycle_tusach);
        swipeRefreshLayout = view.findViewById(R.id.refresh_tusach);
        itemBookCaseList = new ArrayList<>();
        rentList = new ArrayList<>();
        presenterLogicTuSach = new PresenterLogicTuSach(getContext(),this);
        presenterLogicTuSach.layDsUserRent();

    }

    @Override
    public void hienthiDsSach(List<BookCase> itemBookCases) {
        itemBookCaseList.clear();
        itemBookCaseList.addAll(itemBookCases);
        if(rentList.size()!=0){
            if(fortmatStringtoDate(rentList.get(0).getTime_rest()).after(fortmatStringtoDate(dateFormatter.format(new Date())))|| fortmatStringtoDate(rentList.get(0).getTime_rest()).equals(fortmatStringtoDate(dateFormatter.format(new Date())))){
                tusach_adapter.notifyDataSetChanged();
            }else {
                for(BookCase bookCase:itemBookCases){
                    if(!bookCase.getBought()){
                        itemBookCaseList.remove(bookCase);
                    }
                }
                tusach_adapter.notifyDataSetChanged();
            }
        }else {
            tusach_adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void layDanhSachUserRent(List<UserRent> userRentList) {
        rentList.clear();
        rentList = userRentList;
        tusach_adapter = new Tusach_Adapter(getContext(),itemBookCaseList);
        recyclerView.setAdapter(tusach_adapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),3);
        recyclerView.setLayoutManager(gridLayoutManager);
        presenterLogicTuSach.xulihienthiDSCuaTuSach();
    }

    public void refresherData(){
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary)
                ,getResources().getColor(R.color.colorPrimary)
        ,getResources().getColor(R.color.colorAccent));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                    presenterLogicTuSach.xulihienthiDSCuaTuSach();
                    swipeRefreshLayout.setRefreshing(false);
                    }
                },1000);
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        itemBookCaseList.clear();
        presenterLogicTuSach.layDsUserRent();
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
    public Date fortmatStringtoDate(String date){
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date startDate = null;
        try {
            startDate = df.parse(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return startDate;
    }
}
