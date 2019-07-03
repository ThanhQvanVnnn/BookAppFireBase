package com.phungthanhquan.bookapp.Adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.phungthanhquan.bookapp.Object.Marketing;
import com.phungthanhquan.bookapp.R;
import com.phungthanhquan.bookapp.View.Activity.BookDetail;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Album_NXB_Adapter extends RecyclerView.Adapter<Album_NXB_Adapter.ViewHolder> {
    private Context context;
    private List<Marketing> listBook;
    private List<String> dsHinhAnh;
    FirebaseFirestore firebaseFirestore;
    StorageReference storageReference;

    public Album_NXB_Adapter(Context context, List<Marketing> dsSach, List<String> dsHinhAnh) {
        this.context = context;
        this.listBook = dsSach;
        this.dsHinhAnh = dsHinhAnh;
        firebaseFirestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
    }

    @NonNull
    @Override
    public Album_NXB_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.item_book_recycleview, viewGroup, false);
        Album_NXB_Adapter.ViewHolder viewHolder = new Album_NXB_Adapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final Album_NXB_Adapter.ViewHolder viewHolder, final int position) {
        String id_book = listBook.get(position).getBook_id();
        firebaseFirestore.collection("book").document(id_book).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                viewHolder.titleSach.setText((CharSequence) document.get("name"));
                            }
                        }
                    }
                });
        storageReference.child("images").child("books").child(id_book + ".png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                dsHinhAnh.set(position,uri.toString());
                Picasso.get().load(uri).into(viewHolder.imageSach, new Callback() {
                    @Override
                    public void onSuccess() {
                        viewHolder.progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
            }
        });

        viewHolder.layout_itemBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BookDetail.class);
                intent.putExtra("image",dsHinhAnh.get(position));
                intent.putExtra("book_id", listBook.get(position).getBook_id());
                ActivityOptions options = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    options = ActivityOptions.makeSceneTransitionAnimation((Activity) context,
                            viewHolder.imageSach, "sharedName");
                }
                context.startActivity(intent, options.toBundle());
            }
        });


    }

    @Override
    public int getItemCount() {
        return listBook.size();
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