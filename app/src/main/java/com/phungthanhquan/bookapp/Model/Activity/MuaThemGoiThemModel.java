package com.phungthanhquan.bookapp.Model.Activity;

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
import com.phungthanhquan.bookapp.Object.User;
import com.phungthanhquan.bookapp.Object.UserRent;

public class MuaThemGoiThemModel {
    FirebaseFirestore firebaseFirestore;

    public MuaThemGoiThemModel() {
        this.firebaseFirestore = FirebaseFirestore.getInstance();
    }

    public interface CallBackUserRen {
        void myCallBack(UserRent userRent, User user);
    }
    public void LayUserRent(final CallBackUserRen callback){
        firebaseFirestore.collection("user").document(FirebaseAuth.getInstance().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                final User user = new User();
                user.setBudget(documentSnapshot.getDouble("budget"));
                user.setUser_id(FirebaseAuth.getInstance().getUid());
                firebaseFirestore.collection("user_rent").whereEqualTo("user_id", FirebaseAuth.getInstance().getUid()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        UserRent userRent = null;
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot queryDocumentSnapshot:task.getResult()){
                                if(queryDocumentSnapshot.exists()){
                                    userRent = new UserRent();
                                    userRent.setId(queryDocumentSnapshot.getId());
                                    userRent.setUser_id(queryDocumentSnapshot.getString("user_id"));
                                    userRent.setRent_id(queryDocumentSnapshot.getString("rent_id"));
                                    userRent.setTime_rest(queryDocumentSnapshot.getString("time_rest"));
                                }
                            }
                        }
                        callback.myCallBack(userRent,user);
                    }
                });
            }
        });


    }
}