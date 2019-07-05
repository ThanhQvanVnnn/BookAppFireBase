package com.phungthanhquan.bookapp.Adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.phungthanhquan.bookapp.Object.BinhLuan;
import com.phungthanhquan.bookapp.Object.User;
import com.phungthanhquan.bookapp.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecycleView_noidungbinhluan_Adapter extends RecyclerView.Adapter<RecycleView_noidungbinhluan_Adapter.ViewHolder> {
    private Context context;
    private List<BinhLuan> binhLuanList;
    private Boolean trangChinh;
    private String book_id;
    FirebaseFirestore firebaseFirestore;
    StorageReference storageReference;
    private List<String> dsHinhAnh;
    private List<String> dsUserName;
    public RecycleView_noidungbinhluan_Adapter(Context context, List<BinhLuan> binhLuanList, Boolean bo,String book_id) {
        this.context = context;
        this.binhLuanList = binhLuanList;
        this.trangChinh = bo;
        firebaseFirestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        this.book_id = book_id;
        dsHinhAnh = new ArrayList<>();
        dsUserName = new ArrayList<>();
        for (int i = 0; i <binhLuanList.size(); i++) {
            dsUserName.add("a");
            dsHinhAnh.add("a");
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_binhluan_recycleview,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int position) {
        if(dsUserName.get(position).equals("a")&&dsHinhAnh.get(position).equals("a")){
            firebaseFirestore.collection("user").document(binhLuanList.get(position).getUser_id()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    dsUserName.set(position, (String) documentSnapshot.get("name"));
                    storageReference.child("images").child("users").child(binhLuanList.get(position).getUser_id()+".png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            dsHinhAnh.set(position,uri.toString());
                            Picasso.get().load(dsHinhAnh.get(position)).into(viewHolder.anhDaiDien);
                            viewHolder.tenNguoiBinhLuan.setText(dsUserName.get(position));
                            viewHolder.soSao.setRating(binhLuanList.get(position).getStar_number());
                            viewHolder.noiDungBinhLuan.setText(binhLuanList.get(position).getContent());
                            viewHolder.ngayBinhLuan.setText(binhLuanList.get(position).getTime());
                        }
                    });
                }
            });
        }else {
            Picasso.get().load(dsHinhAnh.get(position)).into(viewHolder.anhDaiDien);
            viewHolder.tenNguoiBinhLuan.setText(dsUserName.get(position));
            viewHolder.soSao.setRating(binhLuanList.get(position).getStar_number());
            viewHolder.noiDungBinhLuan.setText(binhLuanList.get(position).getContent());
            viewHolder.ngayBinhLuan.setText(binhLuanList.get(position).getTime());
        }

    }

    @Override
    public int getItemCount() {
        if(binhLuanList.size()>3){
            if(this.trangChinh){
                return binhLuanList.size();
            }else {
                return 3;
            }
        }else {
            return binhLuanList.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView anhDaiDien;
        private TextView tenNguoiBinhLuan;
        private RatingBar soSao;
        private TextView noiDungBinhLuan;
        private TextView ngayBinhLuan;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            anhDaiDien = itemView.findViewById(R.id.imageview_anhdaidien);
            tenNguoiBinhLuan = itemView.findViewById(R.id.textview_tennguoibinhluan);
            soSao = itemView.findViewById(R.id.raitingbar_nguoibinhluan);
            noiDungBinhLuan = itemView.findViewById(R.id.textview_noidungnguoibinhluan);
            ngayBinhLuan = itemView.findViewById(R.id.textview_ngaybinhluan);
        }
    }
}
