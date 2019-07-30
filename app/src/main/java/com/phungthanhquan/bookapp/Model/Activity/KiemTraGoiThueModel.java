package com.phungthanhquan.bookapp.Model.Activity;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.phungthanhquan.bookapp.Model.Room.DbRoomAccess;
import com.phungthanhquan.bookapp.Object.Rent;
import com.phungthanhquan.bookapp.Object.UserRent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class KiemTraGoiThueModel {
    FirebaseFirestore firebaseFirestore;
    Context context;

    public KiemTraGoiThueModel(Context context) {
        firebaseFirestore = FirebaseFirestore.getInstance();
        this.context = context;
    }

    public interface CallBackLayUserRent{
        void MycallBack(UserRent userRent);
    }
    public interface CallBackLayListRent{
        void MycallBack(List<Rent> rentList);
    }
    public void LayUserRent(final CallBackLayUserRent callback){
       firebaseFirestore.collection("user_rent").whereEqualTo("user_id",FirebaseAuth.getInstance().getUid()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
           @Override
           public void onComplete(@NonNull Task<QuerySnapshot> task) {
               UserRent userRent = null;
               if(task.isSuccessful()){
                   for(QueryDocumentSnapshot queryDocumentSnapshot: task.getResult()){
                       if(queryDocumentSnapshot.exists()){
                          userRent = new UserRent();
                          userRent.setId(queryDocumentSnapshot.getId());
                          userRent.setRent_id(queryDocumentSnapshot.getString("rent_id"));
                          userRent.setUser_id(queryDocumentSnapshot.getString("user_id"));
                          userRent.setTime_rest(queryDocumentSnapshot.getString("time_rest"));
                       }
                   }
                   callback.MycallBack(userRent);
               }
           }
       });
    }

    public void LayDanhsachGoiThue(final CallBackLayListRent mycallback){
        final List<Rent> rentList = new ArrayList<>();
        firebaseFirestore.collection("rent").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot queryDocumentSnapshot:task.getResult()){
                        if(queryDocumentSnapshot.exists()){
                            Rent rent = new Rent();
                            rent.setId(queryDocumentSnapshot.getId());
                            rent.setName(queryDocumentSnapshot.getString("name"));
                            rent.setMonth(queryDocumentSnapshot.getDouble("time").intValue());
                            rent.setPrice(queryDocumentSnapshot.getDouble("price"));
                            rentList.add(rent);
                        }
                    }
                    mycallback.MycallBack(rentList);
                }
            }
        });
    }
}
