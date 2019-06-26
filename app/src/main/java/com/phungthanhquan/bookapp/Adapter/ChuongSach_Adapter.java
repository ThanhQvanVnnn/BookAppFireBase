package com.phungthanhquan.bookapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.phungthanhquan.bookapp.Object.ChuongSach;
import com.phungthanhquan.bookapp.R;

import java.util.List;

public class ChuongSach_Adapter extends RecyclerView.Adapter<ChuongSach_Adapter.ViewHolder> {
    private Context context;
    private Activity activity;
    private List<ChuongSach> chuongSachList;

    public ChuongSach_Adapter(Context context, Activity activity, List<ChuongSach> chuongSachList) {
        this.context = context;
        this.activity = activity;
        this.chuongSachList = chuongSachList;
    }

    @NonNull
    @Override
    public ChuongSach_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_chuongsach,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChuongSach_Adapter.ViewHolder viewHolder, int i) {
        final ChuongSach chuongSach = chuongSachList.get(i);
        viewHolder.textView.setText(chuongSach.getTenChuongSach());
        viewHolder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("trang",chuongSach.getTrang());
                activity.setResult(Activity.RESULT_OK,returnIntent);
                activity.finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return chuongSachList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tenChuongSach);
        }
    }
}
