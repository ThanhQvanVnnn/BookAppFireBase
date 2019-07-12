package com.phungthanhquan.bookapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.phungthanhquan.bookapp.Object.Rent;
import com.phungthanhquan.bookapp.R;
import com.phungthanhquan.bookapp.View.Activity.HinhThucThanhToan;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

public class GoiThue_Adapter extends RecyclerView.Adapter<GoiThue_Adapter.ViewHolder> {
    private Context context;
    private List<Rent> rentList;
    private String IMAGE,NAME,BOOK_ID;
    DecimalFormat df;

    public GoiThue_Adapter(Context context, List<Rent> rentList,String image,String name,String id) {
        this.context = context;
        this.rentList = rentList;
        this.IMAGE = image;
        this.NAME = name;
        this.BOOK_ID = id;
        df = new DecimalFormat("###,###.###");
        df.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.ITALY));
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_goithue,parent,false);
        return new GoiThue_Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.tengoithue.setText(rentList.get(position).getName());
        holder.giatien.setText(df.format(rentList.get(position).getPrice())+"");
        holder.thanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, HinhThucThanhToan.class);
                intent.putExtra("book_id",BOOK_ID);
                intent.putExtra("book_name",NAME);
                intent.putExtra("book_image",IMAGE);
                intent.putExtra("rent_name",rentList.get(position).getName());
                intent.putExtra("rent_price",rentList.get(position).getPrice());
                intent.putExtra("ren_time",rentList.get(position).getMonth());
                intent.putExtra("rent_id",rentList.get(position).getId());
                ((Activity) context).startActivityForResult(intent,11);
            }
        });
    }

    @Override
    public int getItemCount() {
        return rentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tengoithue,giatien;
        private Button thanhtoan;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tengoithue = itemView.findViewById(R.id.tengoithue);
            giatien = itemView.findViewById(R.id.textview_giagoithue);
            thanhtoan = itemView.findViewById(R.id.button_thanhtoan);
        }
    }
}
