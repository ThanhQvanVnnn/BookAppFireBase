package com.phungthanhquan.bookapp.Adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.phungthanhquan.bookapp.Object.ItemBookCase;
import com.phungthanhquan.bookapp.R;
import com.phungthanhquan.bookapp.View.Activity.BookDetail;
import com.phungthanhquan.bookapp.View.Activity.MainActivity;
import com.phungthanhquan.bookapp.View.Activity.Read;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;


public class Tusach_Adapter extends RecyclerView.Adapter<Tusach_Adapter.ViewHolder> {
    private Context context;
    private List<ItemBookCase> itemBookCaseList;
    private final String FILENAME_BOOKSTORED = "book_dowload";
    Toast toast;

    public Tusach_Adapter(Context context, List<ItemBookCase> itemBookCaseList) {
        this.context = context;
        this.itemBookCaseList = itemBookCaseList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_book_tusach,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int position) {
         final ItemBookCase itemBookCase = itemBookCaseList.get(position);
        Picasso.get().load(itemBookCase.getUrlImage()).into(viewHolder.imageSach);
        viewHolder.tentacgia.setText(itemBookCase.getTenTacGia());
        viewHolder.phantram.setText((int) itemBookCase.getPhantramdoc()+"%");
        viewHolder.phantramprogress.setProgress((int) itemBookCase.getPhantramdoc());
        File directory;
        ContextWrapper cw = new ContextWrapper(context);
        directory = cw.getDir(FILENAME_BOOKSTORED, Context.MODE_PRIVATE);
         File file=new File(directory,itemBookCase.getBookID()+".pdf");
        if(file.exists()){
            viewHolder.download.setVisibility(View.GONE);
        }else {
            viewHolder.download.setVisibility(View.VISIBLE);
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                viewHolder.layout_tusachItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(MainActivity.isNetworkConnected(context)) {
                            Intent intent = new Intent(context, BookDetail.class);
                            intent.putExtra("image", itemBookCase.getUrlImage());
                            ActivityOptions options = null;
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                                options = ActivityOptions.makeSceneTransitionAnimation((Activity) context,
                                        viewHolder.imageSach, "sharedName");
                            }
                            context.startActivity(intent, options.toBundle());
                        }else {
                            //kiểm tra đã tải về chưa, nếu có cho đọc, còn chưa thì phải bật internet để tải..
                            File directory;
                            ContextWrapper cw = new ContextWrapper(context);
                            directory = cw.getDir(FILENAME_BOOKSTORED, Context.MODE_PRIVATE);
                            File files=new File(directory,itemBookCase.getBookID()+".pdf");
                            if ( files.exists() )
                            {

                                Intent intent;
                                intent = new Intent(context, Read.class);
                                intent.putExtra("idSach","id0");
                                context.startActivity(intent);
                            }
                            else
                            {
                                showAToast(context.getResources().getString(R.string.openinternet_readbook));
                            }
                        }
                    }
                });
            }
        }).start();
    }
    public void showAToast (String st){ //"Toast toast" is declared in the class
        try{ toast.getView().isShown();     // true if visible
            toast.setText(st);
        } catch (Exception e) {         // invisible if exception
            toast = Toast.makeText(context, st,  Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
        }
        toast.show();  //finally display it
    }
    @Override
    public int getItemCount() {
        return itemBookCaseList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageSach;
        private TextView tentacgia;
        private TextView phantram;
        private ProgressBar phantramprogress;
        private FrameLayout download;
        private ConstraintLayout layout_tusachItem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageSach = itemView.findViewById(R.id.imageview_sach);
            tentacgia = itemView.findViewById(R.id.textview_tentacgia);
            phantram = itemView.findViewById(R.id.txtProgress);
            phantramprogress = itemView.findViewById(R.id.progressBar);
            download = itemView.findViewById(R.id.taichua);
            layout_tusachItem = itemView.findViewById(R.id.layout_item_tusach);
        }
    }
}
