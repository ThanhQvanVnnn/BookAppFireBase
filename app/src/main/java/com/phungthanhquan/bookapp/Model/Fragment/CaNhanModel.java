package com.phungthanhquan.bookapp.Model.Fragment;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.phungthanhquan.bookapp.Model.Room.DbRoomAccess;
import com.phungthanhquan.bookapp.Object.BookCase;
import com.phungthanhquan.bookapp.Object.User;

import java.util.ArrayList;
import java.util.List;

public class CaNhanModel {
    private FirebaseFirestore firebaseFirestore;

    public CaNhanModel() {
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    public interface callBack{
       void mycallback(User user);
   }


   public void layThongTinUser(final callBack mycallback){

       firebaseFirestore.collection("user").document(FirebaseAuth.getInstance().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
           @Override
           public void onSuccess(DocumentSnapshot documentSnapshot) {
               if (documentSnapshot.exists()) {
                   User user = new User();
                   user.setBudget(documentSnapshot.getDouble("budget"));
                   user.setEmail(documentSnapshot.getString("email"));
                   user.setName(documentSnapshot.getString("name"));
                   user.setUser_id(documentSnapshot.getId());
                   user.setPhone(documentSnapshot.getString("phone"));
                   mycallback.mycallback(user);
               }
           }
       });
   }
   public void layThongtinBookCase(Context context,TuSachModel.TuSachCallback callBackBookcase){
       DbRoomAccess.getInstance(context).getAllBookcaseTask(context,callBackBookcase);
   }
}
