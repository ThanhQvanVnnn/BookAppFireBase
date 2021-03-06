package com.phungthanhquan.bookapp.View.Activity;

import androidx.core.view.MenuItemCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.phungthanhquan.bookapp.Adapter.Album_NXB_Adapter;
import com.phungthanhquan.bookapp.Object.Marketing;
import com.phungthanhquan.bookapp.Presenter.Activity.PresenterLogicSearch;
import com.phungthanhquan.bookapp.R;
import com.phungthanhquan.bookapp.View.InterfaceView.InterfaceViewActivitySearch;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class SearchBook extends AppCompatActivity implements InterfaceViewActivitySearch, SearchView.OnQueryTextListener {
    private Toolbar toolbar;
    private RecyclerView recyclerViewTimKiem;
    private List<Marketing> dsSachTimKiem;
    public AlertDialog loadingDialog;
    private Album_NXB_Adapter recycleViewItemBookAdapter;
    PresenterLogicSearch presenterLogicSearch;
    private SearchView searchView;
    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_book);
        initControls();
    }

    private void initControls() {
        toolbar = findViewById(R.id.toolbar_search);
        recyclerViewTimKiem = findViewById(R.id.recycle_searchbook);
        toolbar.setTitle("");
        dsSachTimKiem = new ArrayList<>();
        setSupportActionBar(toolbar);
        loadingDialog = new SpotsDialog.Builder().setContext(this).build();
        loadingDialog.setMessage(getResources().getString(R.string.dangtaidulieu));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        presenterLogicSearch = new PresenterLogicSearch(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search,menu);
        MenuItem itemSearch = menu.findItem(R.id.it_search);
         searchView = (SearchView) MenuItemCompat.getActionView(itemSearch);
        searchView.setIconified(false);
        searchView.setOnQueryTextListener(this);
       return super.onCreateOptionsMenu(menu);
    }


    @Override
    public void searchSuccess(List<Marketing> dsSachs) {
        Log.d("kiemtra", dsSachs.size()+"");
        dsSachTimKiem.clear();
        dsSachTimKiem.addAll(dsSachs);
        recycleViewItemBookAdapter = new Album_NXB_Adapter(this,dsSachTimKiem) ;
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,3);
        recyclerViewTimKiem.setAdapter(recycleViewItemBookAdapter);
        recyclerViewTimKiem.setLayoutManager(layoutManager);
        loadingDialog.dismiss();
    }

    @Override
    public void searchFail() {
        dsSachTimKiem.clear();
        recycleViewItemBookAdapter = new Album_NXB_Adapter(this,dsSachTimKiem) ;
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,3);
        recyclerViewTimKiem.setAdapter(recycleViewItemBookAdapter);
        recyclerViewTimKiem.setLayoutManager(layoutManager);
        loadingDialog.dismiss();
        showAToast(getString(R.string.khong_tim_thay_ban_be));
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        if(MainActivity.isNetworkConnected(this)) {
            presenterLogicSearch.handlerSearch(s);
            loadingDialog.show();
        }else {
            showAToast(getResources().getString(R.string.openinternet));
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
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
