package com.phungthanhquan.bookapp.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.phungthanhquan.bookapp.Object.Friend;
import com.phungthanhquan.bookapp.Object.User;
import com.phungthanhquan.bookapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TimKiemBanAdapter extends RecyclerView.Adapter<TimKiemBanAdapter.ViewHolder> {

    private Context context;
    private List<User> userList;
    private List<Friend> friendList;
    private List<String> urlImage;
    StorageReference storageReference;
    FirebaseFirestore firebaseFirestore;
    private AlertDialog loadingDialog;
    private Dialog dialog;
    Toast toast;

    public TimKiemBanAdapter(Context context,List<User> userList, List<Friend> friendList, AlertDialog loadingDialog) {
        this.context = context;
        this.userList = userList;
        this.friendList = friendList;
        storageReference = FirebaseStorage.getInstance().getReference();
        this.loadingDialog = loadingDialog;
        this.loadingDialog.setMessage(context.getString(R.string.vuilongcho));
        urlImage = new ArrayList<>();
        for(int i=0;i<userList.size();i++){
            urlImage.add("a");
        }
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_search_friend,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.textView_ten.setText(userList.get(position).getName());
        String create_at = context.getString(R.string.ngay_tao)+"\n"+ userList.get(position).getCreate_at();
        holder.textView_ngaythamgia.setText(create_at);
        //lấy hình ảnh từ id user
        if(urlImage.get(position).equals("a")) {
            storageReference.child("images").child("users").child(userList.get(position).getUser_id()+".png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    urlImage.set(position,uri.toString());
                    Picasso.get().load(uri.toString()).into(holder.circleImageView);
                }
            });
        }else {
            Picasso.get().load(urlImage.get(position)).into(holder.circleImageView);
        }
        //kết thúc lấy hình ảnh từ id user
        //kiểm tra đã theo dõi chưa ?
        //có
        if(checkFriend(userList.get(position).getUser_id())){
            holder.button_theodoi.setText(context.getString(R.string.da_theo_doi));
            holder.button_theodoi.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
            holder.button_theodoi.setBackground(context.getResources().getDrawable(R.drawable.background_button_datheodoiban));
        }else /*chưa*/  {
            holder.button_theodoi.setText(context.getString(R.string.theo_doi));
            holder.button_theodoi.setTextColor(context.getResources().getColor(R.color.white));
            holder.button_theodoi.setBackground(context.getResources().getDrawable(R.drawable.background_button_chuatheodoiban));
        }
        holder.button_theodoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingDialog.show();
                //kiểm tra có bạn trong danh sách theo dõi hay không?
                //có
                if(checkFriend(userList.get(position).getUser_id())){
                   dialog = new Dialog(context);
                    dialog.setContentView(R.layout.dialog_huytheodoi);
                    CircleImageView anhdaidien = dialog.findViewById(R.id.anhdaidien);
                    TextView textView_ten = dialog.findViewById(R.id.texview_tennguoidung);
                    Button ketthuc = dialog.findViewById(R.id.ketthuc);
                    Button botheodoi = dialog.findViewById(R.id.botheodoi);
                    Picasso.get().load(urlImage.get(position)).into(anhdaidien);
                    textView_ten.setText(userList.get(position).getName());
                    ketthuc.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            loadingDialog.dismiss();
                        }
                    });
                    botheodoi.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            firebaseFirestore.collection("friend").document(friendList.get(checkFriendID(userList.get(position).getUser_id())).getId()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    holder.button_theodoi.setText(context.getString(R.string.theo_doi));
                                    holder.button_theodoi.setTextColor(context.getResources().getColor(R.color.white));
                                    holder.button_theodoi.setBackground(context.getResources().getDrawable(R.drawable.background_button_chuatheodoiban));
                                    friendList.remove(position);
                                    showAToast(context.getString(R.string.huy_theo_doi_ban_be)+" "+ userList.get(position).getName());
                                    loadingDialog.dismiss();

                                }
                            });

                        }
                    });
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.show();
                    Window window = dialog.getWindow();
                    window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                }
                //kết thúc xử lí có
                //không có trong danh sách theo dõi
                else {
                    final Friend friend = new Friend(FirebaseAuth.getInstance().getUid(),userList.get(position).getUser_id());
                    firebaseFirestore.collection("friend").add(friend).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            friend.setId(documentReference.getId());
                            friendList.add(friend);
                            holder.button_theodoi.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
                            holder.button_theodoi.setText(context.getString(R.string.da_theo_doi));
                            showAToast(context.getString(R.string.da_theo_doi)+" "+ userList.get(position).getName() );
                            holder.button_theodoi.setBackground(context.getResources().getDrawable(R.drawable.background_button_datheodoiban));
                    loadingDialog.dismiss();
                        }
                    });
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView circleImageView;
        private TextView textView_ten,textView_ngaythamgia;
        private Button button_theodoi;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.anhdaidien);
            textView_ten = itemView.findViewById(R.id.textview_ten);
            textView_ngaythamgia = itemView.findViewById(R.id.textview_ngaythamgia);
            button_theodoi = itemView.findViewById(R.id.button_theodoi);
        }
    }
    private Boolean checkFriend(String id){
        for (Friend friend: friendList){
            if(friend.getReceiver_id().equals(id)){
                return true;
            }
        }
        return false;
    }

    private int checkFriendID(String id){
        for (int i = 0; i < friendList.size(); i++) {
            if(friendList.get(i).getReceiver_id().equals(id)){
                return i;
            }
        }
        return -1;
    }
    public void showAToast(String st) { //"Toast toast" is declared in the class
        try {
            toast.getView().isShown();     // true if visible
            toast.setText(st);
        } catch (Exception e) {         // invisible if exception
            toast = Toast.makeText(context, st, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
        }
        toast.show();  //finally display it
    }
}
