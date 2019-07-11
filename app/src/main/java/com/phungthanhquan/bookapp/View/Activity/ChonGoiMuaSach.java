package com.phungthanhquan.bookapp.View.Activity;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.phungthanhquan.bookapp.Adapter.GoiThue_Adapter;
import com.phungthanhquan.bookapp.Object.Rent;
import com.phungthanhquan.bookapp.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ChonGoiMuaSach extends AppCompatActivity implements View.OnClickListener {
    private Button mua;
    private TextView thoat,bookName,giaMuaLe;
    private ImageView imageBook;
    private Intent getIntent;
    private String IMAGE,BOOK_ID,BOOK_NAME;
    private Double BOOK_PRICE;
    private RecyclerView recyclerView;
    private List<Rent> rentList;
    private GoiThue_Adapter goiThue_adapter;
    DecimalFormat df;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chon_goi_mua_sach);
        initControls();
        mua.setOnClickListener(this);
        thoat.setOnClickListener(this);
    }

    private void initControls() {
        mua = findViewById(R.id.button_muale);
        thoat = findViewById(R.id.exit);
        bookName = findViewById(R.id.book_name);
        imageBook = findViewById(R.id.image_book);
        giaMuaLe = findViewById(R.id.textview_giaMuaLe);
        df = new DecimalFormat("###,###.###");
        df.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.ITALY));
        firebaseFirestore = FirebaseFirestore.getInstance();
        getIntent = getIntent();
        BOOK_PRICE =  getIntent.getDoubleExtra("price",0);
        BOOK_NAME = getIntent.getStringExtra("name") ;
        IMAGE = getIntent.getStringExtra("image") ;
        BOOK_ID = getIntent.getStringExtra("book_id") ;
        bookName.setText(BOOK_NAME);
        giaMuaLe.setText(df.format(BOOK_PRICE));
        Picasso.get().load(IMAGE).into(imageBook);
        recyclerView = findViewById(R.id.goithue);
        rentList = new ArrayList<>();
        firebaseFirestore.collection("rent").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot queryDocumentSnapshot:task.getResult()){
                        if(queryDocumentSnapshot.exists()){
                            Rent rent = new Rent();
                            rent.setId(queryDocumentSnapshot.getId());
                            rent.setName(queryDocumentSnapshot.getString("name"));
                            Double time = queryDocumentSnapshot.getDouble("time");
                           rent.setMonth(time.intValue());
                            rent.setPrice(queryDocumentSnapshot.getDouble("price"));
                            rentList.add(rent);
                        }
                    }
                    goiThue_adapter = new GoiThue_Adapter(ChonGoiMuaSach.this,rentList,IMAGE,BOOK_NAME,BOOK_ID);
                    recyclerView.setAdapter(goiThue_adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(ChonGoiMuaSach.this,RecyclerView.VERTICAL,false));
                    recyclerView.setHasFixedSize(false);
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.button_muale:
                 intent = new Intent(this, HinhThucThanhToan.class);
                intent.putExtra("book_id",BOOK_ID);
                intent.putExtra("book_name",BOOK_NAME);
                intent.putExtra("book_image",IMAGE);
                intent.putExtra("rent_name","v");
                intent.putExtra("rent_price",BOOK_PRICE);
                startActivity(intent);
                break;
            case R.id.exit:
                finish();
                break;
        }
    }
}
