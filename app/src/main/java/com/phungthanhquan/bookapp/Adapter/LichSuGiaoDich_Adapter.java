package com.phungthanhquan.bookapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.phungthanhquan.bookapp.Object.LichSuGiaoDich;
import com.phungthanhquan.bookapp.R;

import java.util.List;

public class LichSuGiaoDich_Adapter extends RecyclerView.Adapter<LichSuGiaoDich_Adapter.ViewHolder> {
    private Context context;
    private List<LichSuGiaoDich> lichSuGiaoDichList;

    public LichSuGiaoDich_Adapter(Context context, List<LichSuGiaoDich> lichSuGiaoDichList) {
        this.context = context;
        this.lichSuGiaoDichList = lichSuGiaoDichList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_lichsugiaodich_canhan,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LichSuGiaoDich lichSuGiaoDich = lichSuGiaoDichList.get(position);
        if(lichSuGiaoDich.getTransaction_category().equals("v")){
            holder.textView_tieude.setText(context.getString(R.string.mua_sach_thanh_cong));
            if(lichSuGiaoDich.getFrom_budget().equals("budget")){
                holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.dongxu_icon));
            }else {
                holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.paypal_icon));
            }
            String content = context.getString(R.string.content_mua_thanh_cong_vi_1)
                    +" "+ lichSuGiaoDich.getEntity() +"\n"+ context.getString(R.string.content_mua_thanh_cong_vi_2)
                    + " " +lichSuGiaoDich.getMoney().toString()+ " VND";
            holder.textView_content.setText(content);

        }else if(lichSuGiaoDich.getTransaction_category().equals("t")){
            holder.textView_tieude.setText(context.getString(R.string.content_thue_thanh_cong_vi_1));
            if(lichSuGiaoDich.getFrom_budget().equals("budget")){
                holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.dongxu_icon));
            }else {
                holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.paypal_icon));
            }
            String content = context.getString(R.string.content_thue_thanh_cong_vi_2)
                    +" " + lichSuGiaoDich.getRent_time() +"\n"+ context.getString(R.string.content_thue_thanh_cong_vi_3)
                    +" "  + lichSuGiaoDich.getMoney().toString()+ " VND";
            holder.textView_content.setText(content);
        }else if(lichSuGiaoDich.getTransaction_category().equals("n")){
            holder.textView_tieude.setText(context.getString(R.string.nap_tien_thanh_cong));
            if(lichSuGiaoDich.getFrom_budget().equals("budget")){
//                không có
            }else {
                holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.paypal_icon));
                String content = context.getString(R.string.content_nap_thanh_cong_vi_1)+" "+ lichSuGiaoDich.getMoney().toString()+ " VND"
                        +"\n"
                        +context.getString(R.string.content_nap_thanh_cong_vi_2) +" "
                        +lichSuGiaoDich.getEntity();
                holder.textView_content.setText(content);
            }
        }
        holder.textView_time.setText(lichSuGiaoDich.getTime());
    }

    @Override
    public int getItemCount() {
        return lichSuGiaoDichList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView_tieude,textView_content,textView_time;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView_tieude = itemView.findViewById(R.id.tieude);
            textView_content = itemView.findViewById(R.id.content);
            textView_time = itemView.findViewById(R.id.time);
            imageView = itemView.findViewById(R.id.image_cate);
        }
    }
}
