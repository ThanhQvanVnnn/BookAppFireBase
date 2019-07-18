package com.phungthanhquan.bookapp.View.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.phungthanhquan.bookapp.Adapter.TimKiemBanAdapter;
import com.phungthanhquan.bookapp.Object.Friend;
import com.phungthanhquan.bookapp.Object.User;
import com.phungthanhquan.bookapp.Presenter.Activity.PresenterTimKiemBanbe;
import com.phungthanhquan.bookapp.R;
import com.phungthanhquan.bookapp.View.InterfaceView.InterfaceViewActivityTimKiemBanBe;

import java.util.List;

import dmax.dialog.SpotsDialog;

public class TimKiemBan extends AppCompatActivity implements  SearchView.OnQueryTextListener, InterfaceViewActivityTimKiemBanBe {
    private Toolbar toolbar;
    private SearchView searchView;
    public AlertDialog loadingDialog;
    private TimKiemBanAdapter timKiemBanAdapter;
    private PresenterTimKiemBanbe presenterTimKiemBanbe;
    private RecyclerView recyclerView;
    Toast toast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tim_kiem_ban);
        initControls();
    }

    private void initControls() {
        toolbar = findViewById(R.id.toolbar_search);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        recyclerView = findViewById(R.id.recycle_searchFriend);
        loadingDialog = new SpotsDialog.Builder().setContext(this).build();
        loadingDialog.setMessage(getResources().getString(R.string.dangtaidulieu));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        presenterTimKiemBanbe = new PresenterTimKiemBanbe(this);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        if(MainActivity.isNetworkConnected(this)) {
            presenterTimKiemBanbe.laydanhsachbanbe(query);
            loadingDialog.show();
        }else {
            showAToast(getResources().getString(R.string.openinternet));
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
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


    @Override
    public void hienThiTimKiem(List<User> userList, List<Friend> friendList) {
        if(userList.size()==0){
            showAToast(getString(R.string.khong_tim_thay_ban_be));
        }
         timKiemBanAdapter = new TimKiemBanAdapter(this,userList,friendList,loadingDialog);
         recyclerView.setAdapter(timKiemBanAdapter);
         recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
         recyclerView.setHasFixedSize(false);
         loadingDialog.dismiss();
    }
}
