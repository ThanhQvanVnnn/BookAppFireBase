package com.phungthanhquan.bookapp.Adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.phungthanhquan.bookapp.Object.BookCase;
import com.phungthanhquan.bookapp.R;
import com.phungthanhquan.bookapp.View.Activity.BookDetail;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TuSach_CaNhanShow_Adapter extends RecyclerView.Adapter<TuSach_CaNhanShow_Adapter.ViewHolder> {

    private List<BookCase> bookCaseList;
    private Context context;
    FirebaseFirestore firebaseFirestore;

    public TuSach_CaNhanShow_Adapter(List<BookCase> bookCaseList, Context context) {
        this.bookCaseList = bookCaseList;
        this.context = context;
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_bookcase_canhan, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        firebaseFirestore.collection("book").document(bookCaseList.get(position).getBook_id()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                holder.textView_tensach.setText(documentSnapshot.getString("name"));
                Picasso.get().load(bookCaseList.get(position).getBook_image()).into(holder.imageView_AnhSach);
                holder.textView_phantramdoc.setText(context.getString(R.string.dadoc) + " " + bookCaseList.get(position).getLast_time() + "%");
                if(bookCaseList.get(position).getBought()) /*sách đã mua*/{
                    holder.textView_tinhtrang.setText(context.getString(R.string.damua));
                    holder.textView_tinhtrang.setTextColor(context.getResources().getColor(R.color.damuasach));
                }else /*sách chưa mua*/ {
                    holder.textView_tinhtrang.setText(context.getString(R.string.dathue));
                    holder.textView_tinhtrang.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                }
                holder.imageView_AnhSach.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, BookDetail.class);
                        intent.putExtra("image",bookCaseList.get(position).getBook_image());
                        intent.putExtra("book_id", bookCaseList.get(position).getBook_id());
                        ActivityOptions options = null;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                            options = ActivityOptions.makeSceneTransitionAnimation((Activity) context,
                                    holder.imageView_AnhSach, "sharedName");
                        }
                        context.startActivity(intent, options.toBundle());
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookCaseList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView_tensach, textView_tinhtrang, textView_phantramdoc;
        private ImageView imageView_AnhSach;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView_tensach = itemView.findViewById(R.id.tensach);
            textView_tinhtrang = itemView.findViewById(R.id.mua_thue);
            textView_phantramdoc = itemView.findViewById(R.id.phantramdoc);
            imageView_AnhSach = itemView.findViewById(R.id.image_book);
        }
    }
}
