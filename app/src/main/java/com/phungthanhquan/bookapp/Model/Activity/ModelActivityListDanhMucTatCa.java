package com.phungthanhquan.bookapp.Model.Activity;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.phungthanhquan.bookapp.Model.Fragment.DanhMucModel;
import com.phungthanhquan.bookapp.Object.Marketing;

import java.util.ArrayList;
import java.util.List;

public class ModelActivityListDanhMucTatCa {
    FirebaseFirestore firebaseFirestore;

    public ModelActivityListDanhMucTatCa() {
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    public interface CallBackss{
        void myCallBack(List<Marketing> marketingList, DocumentSnapshot documentSnapshot);
    }
    public void getDanhSach(String id, final CallBackss callBackss){
        firebaseFirestore.collection("category_bookcase").whereEqualTo("category_id",id).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    List<Marketing> marketingList = new ArrayList<>();
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot queryDocumentSnapshot:task.getResult()){
                                Marketing marketing = new Marketing();
                                marketing.setBook_id(queryDocumentSnapshot.getString("book_id"));
                                marketing.setMarketing_id((String) queryDocumentSnapshot.get("category_id"));
                                marketingList.add(marketing);
                            }
                            if(task.getResult().size()!=0) {
                                DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(task.getResult().size() - 1);
                                callBackss.myCallBack(marketingList, documentSnapshot);
                            }
                        }
                    }
                });
    }
    public void getDanhSachLoadMore(String id,DocumentSnapshot documentSnapshot, final CallBackss callBackss){
        firebaseFirestore.collection("category_bookcase").whereEqualTo("category_id",id).limit(6).startAfter(documentSnapshot).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    List<Marketing> marketingList = new ArrayList<>();
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot queryDocumentSnapshot:task.getResult()){
                                Marketing marketing = new Marketing();
                                marketing.setBook_id(queryDocumentSnapshot.getString("book_id"));
                                marketing.setMarketing_id((String) queryDocumentSnapshot.get("category_id"));
                                marketingList.add(marketing);
                            }
                            if(task.getResult().size()!=0) {
                                DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(task.getResult().size() - 1);
                                callBackss.myCallBack(marketingList, documentSnapshot);
                            }
                        }
                    }
                });
    }
}
