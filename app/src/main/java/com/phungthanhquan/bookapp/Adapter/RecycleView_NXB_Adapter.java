package com.phungthanhquan.bookapp.Adapter;

import android.app.Activity;
import android.app.ActivityOptions;
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

import com.phungthanhquan.bookapp.Object.NXB;
import com.phungthanhquan.bookapp.R;
import com.phungthanhquan.bookapp.View.Activity.ListBookToChoice;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecycleView_NXB_Adapter extends RecyclerView.Adapter<RecycleView_NXB_Adapter.ViewHolder> {
    private Context context;
    private List<NXB> dsNXB;

    public RecycleView_NXB_Adapter(Context context, List<NXB> dsNXB) {
        this.context = context;
        this.dsNXB = dsNXB;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_nxbimage_recycleview,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int position) {
        final NXB nhaxuatban = dsNXB.get(position);
        Picasso.get().load(nhaxuatban.getImageNXB()).into(viewHolder.imageNXB, new Callback() {
            @Override
            public void onSuccess() {
                viewHolder.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(Exception e) {

            }
        });
        viewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ListBookToChoice.class);
                ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation((Activity) context,
                        viewHolder.imageNXB,"shareImage");
                intent.putExtra("title",nhaxuatban.getTenNXB());
                intent.putExtra("image",dsNXB.get(position).getImageNXB());
                context.startActivity(intent,activityOptions.toBundle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return dsNXB.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageNXB;
        private CardView layout;
        private ProgressBar progressBar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageNXB = itemView.findViewById(R.id.image_nxb);
            layout = itemView.findViewById(R.id.layout_imageNXB);
            progressBar = itemView.findViewById(R.id.progress_itemnxb);
        }
    }
}
