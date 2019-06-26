package com.phungthanhquan.bookapp.View.Activity;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.phungthanhquan.bookapp.Adapter.RecycleView_ItemBook_Adapter;
import com.phungthanhquan.bookapp.Object.ItemBook;
import com.phungthanhquan.bookapp.Presenter.Activity.PresenterLogicSearch;
import com.phungthanhquan.bookapp.R;
import com.phungthanhquan.bookapp.View.InterfaceView.InterfaceViewActivitySearch;

import java.util.ArrayList;
import java.util.List;

public class SearchBook extends AppCompatActivity implements InterfaceViewActivitySearch, SearchView.OnQueryTextListener {
    private Toolbar toolbar;
    private RecyclerView recyclerViewTimKiem;
    private List<ItemBook> dsSachTimKiem;
    private RecycleView_ItemBook_Adapter recycleViewItemBookAdapter;
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
        setSupportActionBar(toolbar);
//        toolbar.setBackgroundColor(getResources().getColor(R.color.white));
        presenterLogicSearch = new PresenterLogicSearch(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


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
    public void timkiemsachthanhcong(List<ItemBook> dsSachs) {
        dsSachTimKiem = dsSachs;
        recycleViewItemBookAdapter = new RecycleView_ItemBook_Adapter(this,dsSachTimKiem,0);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,3);
        recyclerViewTimKiem.setAdapter(recycleViewItemBookAdapter);
        recyclerViewTimKiem.setLayoutManager(layoutManager);
        recycleViewItemBookAdapter.notifyDataSetChanged();
    }

    @Override
    public void timkiemsachthatbai() {

    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        if(MainActivity.isNetworkConnected(this)) {
            presenterLogicSearch.xuliTimKiem(s);
            searchView.onActionViewCollapsed();
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
