package com.phungthanhquan.bookapp.Adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.phungthanhquan.bookapp.R;
import com.phungthanhquan.bookapp.View.Activity.BookDetail;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RecycleView_ItemBook_Adapter extends RecyclerView.Adapter<RecycleView_ItemBook_Adapter.ViewHolder> {
    private Context context;
//    private List<ItemBook> dsSach;
//    private List<ItemBook> dsRandom;
    private Random randomc;
    private int type_layout;

//    public RecycleView_ItemBook_Adapter(Context context, List<ItemBook> dsSach,int type_layout) {
//        this.context = context;
//        this.dsSach = dsSach;
//        randomc = new Random();
//        dsRandom = new ArrayList<>();
//        this.type_layout = type_layout;
//    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        if(type_layout == 0) {
             view = LayoutInflater.from(context).inflate(R.layout.item_book_recycleview, viewGroup, false);
        }else if(type_layout ==1 ) {
             view = LayoutInflater.from(context).inflate(R.layout.item_book_vertical_layout, viewGroup, false);
        }else {
            view = LayoutInflater.from(context).inflate(R.layout.item_danhmuc_chitiet, viewGroup, false);
        }
        ViewHolder viewHolder =  new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int position) {

//        viewHolder.titleSach.setText(dsSach.get(position).getTitle());
//        Picasso.get().load(dsSach.get(position).getUrlImage()).into(viewHolder.imageSach, new Callback() {
//            @Override
//            public void onSuccess() {
//                viewHolder.progressBar.setVisibility(View.GONE);
//            }
//
//            @Override
//            public void onError(Exception e) {
//
//            }
//        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                viewHolder.layout_itemBook.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, BookDetail.class);
//                        intent.putExtra("image",dsSach.get(position).getUrlImage());
                        ActivityOptions options = null;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                            options = ActivityOptions.makeSceneTransitionAnimation((Activity) context,
                                    viewHolder.imageSach,"sharedName");
                        }
                        context.startActivity(intent,options.toBundle());
                    }
                });
            }
        }).start();
        //click itembook go to detail book

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageSach;
        private TextView titleSach;
        private ProgressBar progressBar;
        private LinearLayout layout_itemBook;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageSach = itemView.findViewById(R.id.item_imagebook);
            titleSach = itemView.findViewById(R.id.item_titlebook);
            progressBar = itemView.findViewById(R.id.progress_itembook);
            layout_itemBook = itemView.findViewById(R.id.layout_itembook);
        }
    }

}
