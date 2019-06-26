package com.phungthanhquan.bookapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.phungthanhquan.bookapp.Object.DauTrang;
import com.phungthanhquan.bookapp.R;

import java.util.List;

public class DauTrang_Adapter extends RecyclerView.Adapter<DauTrang_Adapter.ViewHolder> {

    private Context context;
    private Activity activity;
    private List<DauTrang> dauTrangList;

    public DauTrang_Adapter(Context context, Activity activity, List<DauTrang> dauTrangList) {
        this.context = context;
        this.activity = activity;
        this.dauTrangList = dauTrangList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
      View view = LayoutInflater.from(context).inflate(R.layout.item_dautrang,viewGroup,false);
      return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final DauTrang dauTrang = dauTrangList.get(i);
        viewHolder.chuongsach.setText(dauTrang.getTenChuong());
        viewHolder.page.setText(context.getString(R.string.page)+": "+ dauTrang.getTrang());
        viewHolder.time.setText(dauTrang.getDayTime());
        viewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("trang",dauTrang.getTrang());
                activity.setResult(Activity.RESULT_OK,returnIntent);
                activity.finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return dauTrangList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView chuongsach;
        private TextView page;
        private TextView time;
        private LinearLayout layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            chuongsach = itemView.findViewById(R.id.ten_chuong);
            page = itemView.findViewById(R.id.page);
            time = itemView.findViewById(R.id.date);
            layout = itemView.findViewById(R.id.dautrang_layout);
        }
    }
}
