package com.phungthanhquan.bookapp.Model.Activity;


import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.phungthanhquan.bookapp.Model.Fragment.DanhMucModel;
import com.phungthanhquan.bookapp.Object.Book;
import com.phungthanhquan.bookapp.Object.Marketing;

import java.util.ArrayList;
import java.util.List;

public class SearchModel {

    private FirebaseFirestore firebaseFirestore;

    public SearchModel() {
        this.firebaseFirestore = FirebaseFirestore.getInstance();
    }

    public interface Callback {
        void myCallBack(List<Marketing> marketingList);
    }

    public void getDataSearch(String kitu, final Callback callback) {
        firebaseFirestore.collection("book").orderBy("name").whereGreaterThanOrEqualTo("name",kitu).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    List<Marketing> bookList = new ArrayList<>();
                    for(QueryDocumentSnapshot queryDocumentSnapshot: task.getResult()){
                        if(queryDocumentSnapshot.exists()) {
                            Marketing book = new Marketing();
                            book.setBook_id(queryDocumentSnapshot.getId());
                            bookList.add(book);
                        }
                    }
                    callback.myCallBack(bookList);
                }
            }
        });
    }
}
