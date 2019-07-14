package com.phungthanhquan.bookapp.Model.Activity;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.phungthanhquan.bookapp.Object.LichSuGiaoDich;

import java.util.ArrayList;
import java.util.List;

public class LichSuGiaoDichModel {
    FirebaseFirestore firebaseFirestore;

    public LichSuGiaoDichModel() {
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    public interface Callback{
        void mycallBack (List<LichSuGiaoDich> lichSuGiaoDiches);
    }
    public void layDsLichSuGiaoDich(final Callback callback){
        firebaseFirestore.collection("transaction_history").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<LichSuGiaoDich> lichSuGiaoDiches = new ArrayList<>();
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot queryDocumentSnapshot: task.getResult()){
                        if(queryDocumentSnapshot.exists()){
                            LichSuGiaoDich lichSuGiaoDich = new LichSuGiaoDich();
                            lichSuGiaoDich.setId(queryDocumentSnapshot.getId());
                            lichSuGiaoDich.setEntity_id(queryDocumentSnapshot.getString("entity_id"));
                            lichSuGiaoDich.setMoney(queryDocumentSnapshot.getDouble("money"));
                            lichSuGiaoDich.setTime(queryDocumentSnapshot.getString("time"));
                            lichSuGiaoDich.setFrom_budget(queryDocumentSnapshot.getString("from_budget"));
                            lichSuGiaoDich.setTransaction_category(queryDocumentSnapshot.getString("transaction_category"));
                            lichSuGiaoDich.setRent_time(queryDocumentSnapshot.getString("rent_time"));
                            lichSuGiaoDich.setUser_id(queryDocumentSnapshot.getString("user_id"));
                            lichSuGiaoDiches.add(lichSuGiaoDich);
                        }
                    }
                    callback.mycallBack(lichSuGiaoDiches);
                }
            }
        });
    }
}
