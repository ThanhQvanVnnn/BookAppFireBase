package com.phungthanhquan.bookapp.Adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.phungthanhquan.bookapp.Object.BookCase;
import com.phungthanhquan.bookapp.Object.UserRent;
import com.phungthanhquan.bookapp.R;
import com.phungthanhquan.bookapp.View.Activity.BookDetail;
import com.phungthanhquan.bookapp.View.Activity.MainActivity;
import com.phungthanhquan.bookapp.View.Activity.Read;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class Tusach_Adapter extends RecyclerView.Adapter<Tusach_Adapter.ViewHolder> {
    private Context context;
    private List<BookCase> itemBookCaseList;
    private final String FILENAME_BOOKSTORED = "book_dowload";
    private FirebaseFirestore firebaseFirestore;
    Toast toast;
    SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");

    public Tusach_Adapter(Context context, List<BookCase> itemBookCaseList) {
        this.context = context;
        this.itemBookCaseList = itemBookCaseList;
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_book_tusach,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int position) {
            Picasso.get().load(itemBookCaseList.get(position).getBook_image()).into(viewHolder.imageSach);
            firebaseFirestore.collection("book").document(itemBookCaseList.get(position).getBook_id()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()) {
                        Log.d("okokokok", documentSnapshot.toString());
                        viewHolder.tentacgia.setText(documentSnapshot.getString("author_name"));
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("okokokok", e.toString());
                }
            });
            viewHolder.phantram.setText(itemBookCaseList.get(position).getLast_time() + "%");
            viewHolder.phantramprogress.setProgress(itemBookCaseList.get(position).getLast_time());
            File directory;
            ContextWrapper cw = new ContextWrapper(context);
            directory = cw.getDir(FILENAME_BOOKSTORED, Context.MODE_PRIVATE);
            File file = new File(directory, itemBookCaseList.get(position).getBook_id() + ".pdf");
            if (file.exists()) {
                viewHolder.download.setVisibility(View.GONE);
            } else {
                viewHolder.download.setVisibility(View.VISIBLE);
            }
            new Thread(new Runnable() {
                @Override
                public void run() {
                    viewHolder.layout_tusachItem.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (MainActivity.isNetworkConnected(context)) {
                                Intent intent = new Intent(context, BookDetail.class);
                                intent.putExtra("book_id", itemBookCaseList.get(position).getBook_id());
                                intent.putExtra("image", itemBookCaseList.get(position).getBook_image());
                                ActivityOptions options = null;
                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                                    options = ActivityOptions.makeSceneTransitionAnimation((Activity) context,
                                            viewHolder.imageSach, "sharedName");
                                }
                                context.startActivity(intent, options.toBundle());
                            } else {
                                //kiểm tra đã tải về chưa, nếu có cho đọc, còn chưa thì phải bật internet để tải..
                                File directory;
                                ContextWrapper cw = new ContextWrapper(context);
                                directory = cw.getDir(FILENAME_BOOKSTORED, Context.MODE_PRIVATE);
                                File files = new File(directory, itemBookCaseList.get(position).getBook_id() + ".pdf");
                                if (files.exists()) {
                                    Intent intent;
                                    intent = new Intent(context, Read.class);
                                    intent.putExtra("book_id", itemBookCaseList.get(position).getBook_id());
                                    intent.putExtra("image", itemBookCaseList.get(position).getBook_image());
                                    context.startActivity(intent);
                                } else {
                                    showAToast(context.getResources().getString(R.string.openinternet_readbook));
                                }
                            }
                        }
                    });
                }
            }).start();

    }
    @Override
    public int getItemCount() {

        return itemBookCaseList.size();
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
