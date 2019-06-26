package com.phungthanhquan.bookapp.Adapter;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.phungthanhquan.bookapp.Object.DanhMuc;
import com.phungthanhquan.bookapp.R;
import com.phungthanhquan.bookapp.View.Activity.ListBookDanhMucTatCa;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DanhMuc_Adapter extends RecyclerView.Adapter<DanhMuc_Adapter.ViewHolder> {
    private Context context;
    private List<DanhMuc> danhMucList;

    public DanhMuc_Adapter(Context context, List<DanhMuc> danhMucList) {
        this.context = context;
        this.danhMucList = danhMucList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_danhmuc_layout,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int position) {
        final DanhMuc danhMuc = danhMucList.get(position);

        Picasso.get().load(danhMuc.getBackGround()).into(viewHolder.background, new Callback() {
            @Override
            public void onSuccess() {
                viewHolder.progressBar.setVisibility(View.GONE);
                viewHolder.view.setVisibility(View.VISIBLE);
                viewHolder.tendanhMuc.setText(danhMuc.getTenDanhMuc());
            }

            @Override
            public void onError(Exception e) {

            }
        });
        viewHolder.layoutDanhMuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ListBookDanhMucTatCa.class);
                intent.putExtra("idDanhMuc",danhMuc.getIdDanhMuc());
                intent.putExtra("tenDanhMuc",danhMuc.getTenDanhMuc());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return danhMucList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView background;
        private TextView tendanhMuc;
        private CardView layoutDanhMuc;
        private ProgressBar progressBar;
        private View view;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            background = itemView.findViewById(R.id.image_background_danhmuc);
            tendanhMuc = itemView.findViewById(R.id.textview_tendanhmuc);
            layoutDanhMuc = itemView.findViewById(R.id.layout_item_danhmuc);
            progressBar = itemView.findViewById(R.id.progress);
            view = itemView.findViewById(R.id.viewDarkBlank);
        }
    }
}
