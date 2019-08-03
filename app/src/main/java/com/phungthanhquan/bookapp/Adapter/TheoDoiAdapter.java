package com.phungthanhquan.bookapp.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.phungthanhquan.bookapp.Object.Friend;
import com.phungthanhquan.bookapp.Object.ItemSachBanDangDoc;
import com.phungthanhquan.bookapp.Object.UserFriendSearchInformation;
import com.phungthanhquan.bookapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import dmax.dialog.SpotsDialog;

public class TheoDoiAdapter extends RecyclerView.Adapter<TheoDoiAdapter.ViewHolder> {
    Toast toast;
    private Context context;
    private List<Friend> friendList,friendOfUser;
    private List<UserFriendSearchInformation> saveInfoList;
    private Boolean theodoi;
    FirebaseFirestore firebaseFirestore;
    StorageReference storageReference;
    public AlertDialog loadingDialog;
    private Dialog dialog;

    public TheoDoiAdapter(Context context, List<Friend> friendList, List<Friend> friendOfUser,Boolean theodoi) {
        this.context = context;
        this.friendList = friendList;
        this.friendOfUser = friendOfUser;
        this.theodoi = theodoi;
        firebaseFirestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        saveInfoList = new ArrayList<>();
        for (int i = 0; i < this.friendList.size(); i++) {
            UserFriendSearchInformation userFriendSearchInformation = new UserFriendSearchInformation();
            userFriendSearchInformation.setUserName("a");
            saveInfoList.add(userFriendSearchInformation);
        }
        loadingDialog = new SpotsDialog.Builder().setContext(context).build();
        loadingDialog.setMessage(context.getResources().getString(R.string.vuilongcho));
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_search_friend,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final String user_id ;
        if(theodoi){
            user_id = friendList.get(position).getSender_id();
        }else {
            user_id = friendList.get(position).getReceiver_id();
        }

        if(saveInfoList.get(position).getUserName().equals("a")) {
            final String finalUser_id = user_id;
            firebaseFirestore.collection("user").document(user_id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    String create_at = context.getString(R.string.ngay_tao)+"\n"+ documentSnapshot.getString("create_at");
                    saveInfoList.get(position).setCreateAt(create_at);
                    saveInfoList.get(position).setUserName(documentSnapshot.getString("name"));
                    storageReference.child("images").child("users").child(finalUser_id +".png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            saveInfoList.get(position).setUserImage(uri.toString());
                            holder.textView_ten.setText(saveInfoList.get(position).getUserName());
                            holder.textView_ngaythamgia.setText(saveInfoList.get(position).getCreateAt());
                            Picasso.get().load(uri).into(holder.circleImageView);
                        }
                    });
                }
            });
        }else {
            holder.textView_ten.setText(saveInfoList.get(position).getUserName());
            holder.textView_ngaythamgia.setText(saveInfoList.get(position).getCreateAt());
            Picasso.get().load(saveInfoList.get(position).getUserImage()).into(holder.circleImageView);
        }
            if (checkFriend(user_id)) {
                holder.button_theodoi.setText(context.getString(R.string.da_theo_doi));
                holder.button_theodoi.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
                holder.button_theodoi.setBackground(context.getResources().getDrawable(R.drawable.background_button_datheodoiban));
            } else /*chưa*/ {
                holder.button_theodoi.setText(context.getString(R.string.theo_doi));
                holder.button_theodoi.setTextColor(context.getResources().getColor(R.color.white));
                holder.button_theodoi.setBackground(context.getResources().getDrawable(R.drawable.background_button_chuatheodoiban));
            }
        final String finalUser_id1 = user_id;
        holder.button_theodoi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadingDialog.show();
                    //kiểm tra có bạn trong danh sách theo dõi hay không?
                    //có
                    if (checkFriend(finalUser_id1)) {
                        dialog = new Dialog(context);
                        dialog.setContentView(R.layout.dialog_huytheodoi);
                        CircleImageView anhdaidien = dialog.findViewById(R.id.anhdaidien);
                        TextView textView_ten = dialog.findViewById(R.id.texview_tennguoidung);
                        Button ketthuc = dialog.findViewById(R.id.ketthuc);
                        Button botheodoi = dialog.findViewById(R.id.botheodoi);
                        Picasso.get().load(saveInfoList.get(position).getUserImage()).into(anhdaidien);
                        textView_ten.setText(saveInfoList.get(position).getUserName());
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
                                firebaseFirestore.collection("friend").document(friendOfUser.get(checkFriendID(finalUser_id1)).getId()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        holder.button_theodoi.setText(context.getString(R.string.theo_doi));
                                        holder.button_theodoi.setTextColor(context.getResources().getColor(R.color.white));
                                        holder.button_theodoi.setBackground(context.getResources().getDrawable(R.drawable.background_button_chuatheodoiban));
                                        int remove = -1;
                                        for (int i = 0; i < friendOfUser.size(); i++) {
                                            friendOfUser.get(i).getReceiver_id().equals(checkFriendID(finalUser_id1));
                                            remove = i;
                                            break;
                                        }
                                        friendOfUser.remove(remove);
                                        showAToast(context.getString(R.string.huy_theo_doi_ban_be) + " " + saveInfoList.get(position).getUserName());
                                        saveInfoList.remove(remove);
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
                        final Friend friend = new Friend(FirebaseAuth.getInstance().getUid(), user_id);
                        firebaseFirestore.collection("friend").add(friend).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                friend.setId(documentReference.getId());
                                friendOfUser.add(friend);
                                holder.button_theodoi.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
                                holder.button_theodoi.setText(context.getString(R.string.da_theo_doi));
                                showAToast(context.getString(R.string.da_theo_doi) + " " + saveInfoList.get(position).getUserName());
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
        return friendList.size();
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
    private Boolean checkFriend(String id){
        for (Friend friend: friendOfUser){
            if(friend.getReceiver_id().equals(id)){
                return true;
            }
        }
        return false;
    }
    private int checkFriendID(String id){
        for (int i = 0; i < friendOfUser.size(); i++) {
            if(friendOfUser.get(i).getReceiver_id().equals(id)){
                return i;
            }
        }
        return -1;
    }
}
