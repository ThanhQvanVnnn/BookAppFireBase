package com.phungthanhquan.bookapp.Adapter;

import android.app.Activity;
import android.app.ActivityOptions;
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

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.phungthanhquan.bookapp.Object.NXB;
import com.phungthanhquan.bookapp.R;
import com.phungthanhquan.bookapp.View.Activity.ListBookToChoice;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecycleView_NXB_Adapter extends RecyclerView.Adapter<RecycleView_NXB_Adapter.ViewHolder> {
    private Context context;
    private List<NXB> dsNXB;
    List<String> hinhanhNXB;
    FirebaseFirestore firebaseFirestore;
    StorageReference storageReference;

    public RecycleView_NXB_Adapter(Context context, List<NXB> dsNXB,List<String> hinhanhNXB) {
        this.context = context;
        this.dsNXB = dsNXB;
        this.hinhanhNXB = hinhanhNXB;
        firebaseFirestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
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
        if(hinhanhNXB.get(position).equals("a")) {
            storageReference.child("images").child("publishers").child(dsNXB.get(position).getId() + ".png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    hinhanhNXB.set(position, uri.toString());
                    Picasso.get().load(uri).into(viewHolder.imageNXB, new Callback() {
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
        }else {
            Picasso.get().load(hinhanhNXB.get(position)).into(viewHolder.imageNXB, new Callback() {
                @Override
                public void onSuccess() {
                    viewHolder.progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onError(Exception e) {

                }
            });
        }

        viewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ListBookToChoice.class);
                ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation((Activity) context,
                        viewHolder.imageNXB,"shareImage");
                intent.putExtra("image",hinhanhNXB.get(position));
                intent.putExtra("title", dsNXB.get(position).getName());
                intent.putExtra("id", dsNXB.get(position).getId());
                intent.putExtra("album",false);
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
