package com.phungthanhquan.bookapp.Model.Fragment;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.phungthanhquan.bookapp.Object.Album;
import com.phungthanhquan.bookapp.Object.Album_BookCase;
import com.phungthanhquan.bookapp.Object.Marketing;

import java.util.ArrayList;
import java.util.List;

public class TrangChuModel {
    private FirebaseFirestore firebaseFirestore;
    public TrangChuModel() {
        firebaseFirestore = FirebaseFirestore.getInstance();
    }


    public interface CallBackSlider {
        void onCallbackSlider(List<Marketing> marketingList);
    }
    public interface CallBackAlbum{
        void onCallbackAlbum(List<Album> albumList);
    }
    // lấy silder:
    public void getSlider(final CallBackSlider myCallback) {
        firebaseFirestore.collection("marketing_bookcase").whereEqualTo("marketing_id","D6elSxzChNa6UctJbDHu").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<Marketing> attractionsList = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Marketing attraction = document.toObject(Marketing.class);
                        Log.d("kiemtraID", attraction.getBook_id()+"");
                        attractionsList.add(attraction);
                    }
                    myCallback.onCallbackSlider(attractionsList);
                }
            }
        });
    }
    // lấy Album_BookCase:
    public void getAlbum(final CallBackAlbum myCallback) {
        firebaseFirestore.collection("album").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<Album> albumBookList = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()){
                        Album album = new Album();
                        album.setId(document.getId());
                        album.setName(document.getString("name"));
                        albumBookList.add(album);
                    }
                        myCallback.onCallbackAlbum(albumBookList);
                }
            }
        });
    }


}
