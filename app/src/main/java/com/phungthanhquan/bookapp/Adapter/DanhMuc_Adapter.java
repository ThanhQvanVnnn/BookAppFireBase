package com.phungthanhquan.bookapp.Adapter;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.phungthanhquan.bookapp.Object.DanhMuc;
import com.phungthanhquan.bookapp.R;
import com.phungthanhquan.bookapp.View.Activity.ListBookDanhMucTatCa;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DanhMuc_Adapter extends RecyclerView.Adapter<DanhMuc_Adapter.ViewHolder> {
    private Context context;
    private List<DanhMuc> danhMucList;
    private List<String> hinhanhList;
    FirebaseFirestore firebaseFirestore;
    StorageReference storageReference;

    public DanhMuc_Adapter(Context context, List<DanhMuc> danhMucList, List<String> hinhanhList, List<String> nameDanhMucList) {
        this.context = context;
        this.danhMucList = danhMucList;
        this.hinhanhList = hinhanhList;
        firebaseFirestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_danhmuc_layout,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int position) {
       if(hinhanhList.get(position).equals("a")) {
           storageReference.child("images").child("categores").child(danhMucList.get(position).getId() + ".png")
                   .getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
               @Override
               public void onSuccess(Uri uri) {
                   hinhanhList.set(position,uri.toString());
                   Picasso.get().load(uri).into(viewHolder.background, new Callback() {
                       @Override
                       public void onSuccess() {
                           viewHolder.progressBar.setVisibility(View.GONE);
                           viewHolder.view.setVisibility(View.VISIBLE);
                           viewHolder.tendanhMuc.setText(danhMucList.get(position).getName());
                       }

                       @Override
                       public void onError(Exception e) {

                       }
                   });
               }
           });


       }else {
           Picasso.get().load(hinhanhList.get(position)).into(viewHolder.background, new Callback() {
               @Override
               public void onSuccess() {
                   viewHolder.progressBar.setVisibility(View.GONE);
                   viewHolder.view.setVisibility(View.VISIBLE);
                   viewHolder.tendanhMuc.setText(danhMucList.get(position).getName());
               }

               @Override
               public void onError(Exception e) {

               }
           });
       }
        viewHolder.layoutDanhMuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ListBookDanhMucTatCa.class);
                intent.putExtra("idDanhMuc",danhMucList.get(position).getId());
                intent.putExtra("tenDanhMuc",danhMucList.get(position).getName());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return danhMucList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView background;
        private TextView tendanhMuc;
        private CardView layoutDanhMuc;
        private ProgressBar progressBar;
        private View view;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            background = itemView.findViewById(R.id.image_background_danhmuc);
            tendanhMuc = itemView.findViewById(R.id.textview_tendanhmuc);
            layoutDanhMuc = itemView.findViewById(R.id.layout_item_danhmuc);
            progressBar = itemView.findViewById(R.id.progress);
            view = itemView.findViewById(R.id.viewDarkBlank);
        }
    }
}
