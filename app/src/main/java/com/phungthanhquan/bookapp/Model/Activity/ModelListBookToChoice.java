package com.phungthanhquan.bookapp.Model.Activity;


import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.phungthanhquan.bookapp.Object.Album;
import com.phungthanhquan.bookapp.Object.Album_BookCase;
import com.phungthanhquan.bookapp.Object.Marketing;

import java.util.ArrayList;
import java.util.List;

public class ModelListBookToChoice {

    private FirebaseFirestore firebaseFirestore;

    public ModelListBookToChoice() {
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    public interface Callbacks {
        void getListAlbum(List<Marketing> bookCaseList);
    }


    public void getAlbumList(final String id, final Boolean album, final Callbacks mycallback) {
        if(album) {
            firebaseFirestore.collection("album_bookcase").whereEqualTo("album_id", id).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        List<Marketing> listBook = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Marketing album = document.toObject(Marketing.class);
                            listBook.add(album);
                        }
                        mycallback.getListAlbum(listBook);
                    }
                }
            });
        }
        else {
            firebaseFirestore.collection("book").whereEqualTo("publisher_id",id).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                   if(task.isSuccessful()){
                       List<Marketing> listBook = new ArrayList<>();
                       for (QueryDocumentSnapshot document : task.getResult()) {
                           Marketing nxb = new Marketing();
                           nxb.setBook_id(document.getId());
                           nxb.setMarketing_id(id);
                           listBook.add(nxb);
                       }
                       mycallback.getListAlbum(listBook);
                   }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("kiemtranxb",e.toString());
                }
            });
        }
    }
}
