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
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.phungthanhquan.bookapp.Object.Book;
import com.phungthanhquan.bookapp.Object.BookCase;
import com.phungthanhquan.bookapp.Object.Friend;
import com.phungthanhquan.bookapp.Object.ItemSachBanDangDoc;
import com.phungthanhquan.bookapp.R;
import com.phungthanhquan.bookapp.View.Activity.BookDetail;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SachDocCuaBanAdapter extends RecyclerView.Adapter<SachDocCuaBanAdapter.ViewHolder> {
    private Context context;
    private List<BookCase> bookList;
    private FirebaseFirestore firebaseFirestore;
    private StorageReference storageReference;
    private List<ItemSachBanDangDoc> dsbook;

    public SachDocCuaBanAdapter(Context context, List<BookCase> bookList) {
        this.context = context;
        this.bookList = bookList;
        firebaseFirestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        dsbook = new ArrayList<>();
        ItemSachBanDangDoc itemSachBanDangDoc = new ItemSachBanDangDoc();
        itemSachBanDangDoc.setAuthor_name("a");
        dsbook.add(itemSachBanDangDoc);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_sachcuaban, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        if (bookList.size()!=0 && dsbook.get(position).getAuthor_name().equals("a") ) {
            firebaseFirestore.collection("book").document(bookList.get(position).getBook_id()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(final DocumentSnapshot book) {
                    holder.textView_tenSach.setText(book.getString("name"));
                    holder.textView_tentacgia.setText(book.getString("author_name"));
                    holder.ratingBar.setRating(book.getDouble("star_average").floatValue());
                    dsbook.get(position).setBook_id(book.getId());
                    dsbook.get(position).setBook_name(book.getString("name"));
                    dsbook.get(position).setAuthor_name(book.getString("author_name"));
                    dsbook.get(position).setStar_average(book.getDouble("star_average"));
                    storageReference.child("images").child("books").child(bookList.get(position).getBook_id() + ".png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Picasso.get().load(uri).into(holder.imageView_anhSach);
                            dsbook.get(position).setImage_book(uri.toString());
                            storageReference.child("images").child("users").child(bookList.get(position).getUser_id() + ".png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Picasso.get().load(uri).into(holder.imageView_user);
                                    dsbook.get(position).setImage_user(uri.toString());
                                    firebaseFirestore.collection("user").document(bookList.get(position).getUser_id()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                            holder.textView_userName.setText(documentSnapshot.getString("name"));
                                            dsbook.get(position).setUser_name(documentSnapshot.getString("name"));
                                            firebaseFirestore.collection("publisher").document(book.getString("publisher_id")).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                @Override
                                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                    holder.textView_tennxb.setText(documentSnapshot.getString("name"));
                                                    dsbook.get(position).setPublisher_name(documentSnapshot.getString("name"));
                                                }
                                            });
                                        }
                                    });
                                }
                            });
                        }
                    });
                }
            });
        } else {
            holder.textView_tenSach.setText(dsbook.get(position).getBook_name());
            holder.textView_tentacgia.setText(dsbook.get(position).getAuthor_name());
            holder.textView_tennxb.setText(dsbook.get(position).getPublisher_name());
            holder.ratingBar.setRating(dsbook.get(position).getStar_average().floatValue());
            Picasso.get().load(dsbook.get(position).getImage_book()).into(holder.imageView_anhSach);
            Picasso.get().load(dsbook.get(position).getImage_user()).into(holder.imageView_user);
            holder.textView_userName.setText(dsbook.get(position).getUser_name());
        }
        holder.imageView_anhSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BookDetail.class);
                intent.putExtra("image",dsbook.get(position).getImage_book());
                intent.putExtra("book_id", dsbook.get(position).getBook_id());
                ActivityOptions options = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    options = ActivityOptions.makeSceneTransitionAnimation((Activity) context,
                            holder.imageView_anhSach, "sharedName");
                }
                context.startActivity(intent, options.toBundle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView_anhSach;
        private TextView textView_tenSach, textView_tentacgia, textView_tennxb, textView_userName;
        private CircleImageView imageView_user;
        private RatingBar ratingBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView_anhSach = itemView.findViewById(R.id.image_sach);
            textView_tenSach = itemView.findViewById(R.id.textview_tensach);
            textView_tentacgia = itemView.findViewById(R.id.textview_tentacgia);
            textView_tennxb = itemView.findViewById(R.id.textview_tennxb);
            textView_userName = itemView.findViewById(R.id.user_name);
            imageView_user = itemView.findViewById(R.id.image_user);
            ratingBar = itemView.findViewById(R.id.raiting_tong);
        }
    }

    public void addElement(int size) {
        dsbook.clear();
        for (int i = 0; i < size; i++) {
            ItemSachBanDangDoc itemSachBanDangDoc = new ItemSachBanDangDoc();
            itemSachBanDangDoc.setAuthor_name("a");
            dsbook.add(itemSachBanDangDoc);
        }
    }
    public void clearElement(){
        dsbook.clear();
        ItemSachBanDangDoc itemSachBanDangDoc = new ItemSachBanDangDoc();
        itemSachBanDangDoc.setAuthor_name("a");
        dsbook.add(itemSachBanDangDoc);
    }
}
