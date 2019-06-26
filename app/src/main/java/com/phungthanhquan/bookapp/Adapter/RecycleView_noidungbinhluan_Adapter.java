package com.phungthanhquan.bookapp.Adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.phungthanhquan.bookapp.Object.BinhLuan;
import com.phungthanhquan.bookapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecycleView_noidungbinhluan_Adapter extends RecyclerView.Adapter<RecycleView_noidungbinhluan_Adapter.ViewHolder> {
    private Context context;
    private List<BinhLuan> binhLuanList;

    public RecycleView_noidungbinhluan_Adapter(Context context, List<BinhLuan> binhLuanList) {
        this.context = context;
        this.binhLuanList = binhLuanList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_binhluan_recycleview,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Picasso.get().load(binhLuanList.get(position).getAnhDaiDien()).into(viewHolder.anhDaiDien);
        viewHolder.tenNguoiBinhLuan.setText(binhLuanList.get(position).getTenNguoiBinhLuan());
        viewHolder.soSao.setRating(binhLuanList.get(position).getNumStart());
        viewHolder.noiDungBinhLuan.setText(binhLuanList.get(position).getNoiDung());
        viewHolder.ngayBinhLuan.setText(binhLuanList.get(position).getNgayBinhLuan());
    }

    @Override
    public int getItemCount() {
        return binhLuanList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView anhDaiDien;
        private TextView tenNguoiBinhLuan;
        private RatingBar soSao;
        private TextView noiDungBinhLuan;
        private TextView ngayBinhLuan;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            anhDaiDien = itemView.findViewById(R.id.imageview_anhdaidien);
            tenNguoiBinhLuan = itemView.findViewById(R.id.textview_tennguoibinhluan);
            soSao = itemView.findViewById(R.id.raitingbar_nguoibinhluan);
            noiDungBinhLuan = itemView.findViewById(R.id.textview_noidungnguoibinhluan);
            ngayBinhLuan = itemView.findViewById(R.id.textview_ngaybinhluan);
        }
    }
}
