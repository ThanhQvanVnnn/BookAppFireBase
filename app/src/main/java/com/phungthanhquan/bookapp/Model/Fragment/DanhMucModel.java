package com.phungthanhquan.bookapp.Model.Fragment;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.phungthanhquan.bookapp.Object.DanhMuc;

import java.util.ArrayList;
import java.util.List;

public class DanhMucModel {
    FirebaseFirestore firebaseFirestore;

    public DanhMucModel() {
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    public interface CallBack {
        void getDanhMucList(List<DanhMuc> danhMucList);
    }

    public void layDanhSachDanhMuc(final CallBack mycallBack){
        firebaseFirestore.collection("category").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<DanhMuc> danhMucList = new ArrayList<>();
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot documentSnapshot:task.getResult()){
                        if(documentSnapshot.exists()){
                            DanhMuc danhMuc = new DanhMuc();
                            danhMuc.setId(documentSnapshot.getId());
                            danhMuc.setName(documentSnapshot.getString("name"));
                            danhMucList.add(danhMuc);
                        }
                    }
                    mycallBack.getDanhMucList(danhMucList);
                }
            }
        });
    }
}
