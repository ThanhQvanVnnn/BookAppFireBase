package com.phungthanhquan.bookapp.Model.Activity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.phungthanhquan.bookapp.Object.User;

public class CapNhatThongTinUserModel {
    FirebaseFirestore firebaseFirestore;
    public CapNhatThongTinUserModel() {
        firebaseFirestore = FirebaseFirestore.getInstance();
    }
    public interface CallBackCapNhat{
        void myCallBack(User user);
    }
    public interface CallBackCapNhatxong{
        void myCallBack();
    }
    public void layThongTinUser(final CallBackCapNhat callBackCapNhat){
        firebaseFirestore.collection("user").document(FirebaseAuth.getInstance().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    User user = new User();
                    user.setPhone(documentSnapshot.getString("phone"));
                    user.setName(documentSnapshot.getString("name"));
                    user.setEmail(documentSnapshot.getString("email"));
                    user.setUser_id(documentSnapshot.getId());
                    user.setBudget(documentSnapshot.getDouble("budget"));
                    user.setBirth_day(documentSnapshot.getString("birth_day"));
                    user.setGender(documentSnapshot.getBoolean("gender"));
                    user.setCreate_at(documentSnapshot.getString("create_at"));
                    callBackCapNhat.myCallBack(user);
                }
            }
        });
    }
    public void CapNhatUser(User User, final CallBackCapNhatxong call){
        firebaseFirestore.collection("user").document(FirebaseAuth.getInstance().getUid()).set(User).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                call.myCallBack();
            }
        });
    }

}
