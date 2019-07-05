package com.phungthanhquan.bookapp.Model.Activity;


import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.phungthanhquan.bookapp.Object.BinhLuan;
import com.phungthanhquan.bookapp.Object.Book;
import com.phungthanhquan.bookapp.Object.Marketing;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DetailBookModel {
   private FirebaseFirestore firebaseFirestore;

    public DetailBookModel() {
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    public interface CallBackChiTietSach{
        void LayChiTietSach(Book book);
    }
    public interface CallBackLayCungTheLoai{
        void LayDSSach(List<Marketing> marketingList);
    }
    public interface CallBackLayBinhLuan{
        void LayDSBinhLuan(List<BinhLuan> dsBinhLuan);
    }

    public void layChiTietSach(String id, final CallBackChiTietSach callBackChiTietSach){
        firebaseFirestore.collection("book").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    final Book book = documentSnapshot.toObject(Book.class);
                    firebaseFirestore.collection("publisher").document(book.getPublisher_id()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if(documentSnapshot.exists()){
                                book.setPublisher_name((String) documentSnapshot.get("name"));
                                callBackChiTietSach.LayChiTietSach(book);
                            }
                        }
                    });
                }
            }
        });
    }
    public void layDanhSachCungTheLoai(final String categor_id, final CallBackLayCungTheLoai callBackLayCungTheLoai){
        firebaseFirestore.collection("book").whereEqualTo("category_id",categor_id).limit(15).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
            if(task.isSuccessful()){
                List<Marketing> marketingList = new ArrayList<>();
                for(QueryDocumentSnapshot queryDocumentSnapshot:task.getResult()){
                    Marketing marketing = new Marketing();
                    marketing.setBook_id(queryDocumentSnapshot.getId());
                    marketing.setMarketing_id(categor_id);
                    marketingList.add(marketing);
                }
                callBackLayCungTheLoai.LayDSSach(marketingList);
            }
            }
        });
    }

    public void layDanhSachBinhLuan(String book_id, final CallBackLayBinhLuan callBackLayBinhLuan){
        firebaseFirestore.collection("comment").whereEqualTo("book_id",book_id).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<BinhLuan> binhLuanList = new ArrayList<>();
                for(QueryDocumentSnapshot queryDocumentSnapshot: task.getResult()){
                    if(queryDocumentSnapshot.exists()){
                        BinhLuan binhLuan = queryDocumentSnapshot.toObject(BinhLuan.class);
                        binhLuanList.add(binhLuan);
                    }
                }
                callBackLayBinhLuan.LayDSBinhLuan(binhLuanList);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                List<BinhLuan> binhLuanList = new ArrayList<>();
                callBackLayBinhLuan.LayDSBinhLuan(binhLuanList);
            }
        });
    }
}
