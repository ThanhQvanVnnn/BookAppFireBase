package com.phungthanhquan.bookapp.Model.Activity;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.phungthanhquan.bookapp.Model.Fragment.DanhMucModel;
import com.phungthanhquan.bookapp.Object.Marketing;

import java.util.ArrayList;
import java.util.List;

public class MarketingModel {
    FirebaseFirestore firebaseFirestore;
    private String id_marketing;
    public MarketingModel(String id) {
        firebaseFirestore = FirebaseFirestore.getInstance();
        this.id_marketing = id;
    }

    public interface CallBacks{
        void GetListBook(List<Marketing> marketingList, DocumentSnapshot documentSnapshot);
    }

    public void getBookList(final CallBacks callBack){
        firebaseFirestore.collection("marketing_bookcase")
                .whereEqualTo("marketing_id",this.id_marketing).limit(15).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        DocumentSnapshot documentSnapshot;
                        List<Marketing> getList = new ArrayList<>();
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot queryDocumentSnapshot:task.getResult()){
                                if(queryDocumentSnapshot.exists()){
                                    Marketing marketing = queryDocumentSnapshot.toObject(Marketing.class);
                                    getList.add(marketing);
                                }
                            }
                            documentSnapshot = task.getResult().getDocuments().get(task.getResult().size()-1);
                            callBack.GetListBook(getList, documentSnapshot);
                        }
                    }
                });
    }
    public void getBookListLoadMore(DocumentSnapshot documentSnapshot, final CallBacks callBacks){
        firebaseFirestore.collection("marketing_bookcase") .whereEqualTo("marketing_id",this.id_marketing)
                .startAfter(documentSnapshot).limit(15).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                DocumentSnapshot documentSnapshot;
                List<Marketing> getList = new ArrayList<>();
               if(task.isSuccessful()){
                   for(QueryDocumentSnapshot queryDocumentSnapshot:task.getResult()){
                       if(queryDocumentSnapshot.exists()){
                           Marketing marketing = queryDocumentSnapshot.toObject(Marketing.class);
                           getList.add(marketing);
                       }
                   }
                   if(task.getResult().size()>0) {
                       documentSnapshot = task.getResult().getDocuments().get(task.getResult().size() - 1);
                       callBacks.GetListBook(getList, documentSnapshot);
                   }

               }
            }
        });
    }
}
