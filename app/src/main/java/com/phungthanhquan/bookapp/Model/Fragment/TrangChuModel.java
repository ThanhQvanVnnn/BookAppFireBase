package com.phungthanhquan.bookapp.Model.Fragment;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.phungthanhquan.bookapp.Object.Album;
import com.phungthanhquan.bookapp.Object.Album_BookCase;
import com.phungthanhquan.bookapp.Object.Marketing;
import com.phungthanhquan.bookapp.Object.NXB;

import java.util.ArrayList;
import java.util.Collections;
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
    public interface CallBackNXB{
        void onCallbackAlbum(List<NXB> NXBList);
    }
    public interface CallBackKhuyenDoc {
        void onCallbackSlider(List<Marketing> marketingList,DocumentSnapshot documentSnapshot);
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

    // lấy Sách mới:
    public void getSachMoi(final CallBackSlider myCallback) {
        firebaseFirestore.collection("marketing_bookcase").whereEqualTo("marketing_id","1HjY7wPuA7WM2hsmd8NE").limit(8).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<Marketing> attractionsList = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Marketing attraction = document.toObject(Marketing.class);
                        attractionsList.add(attraction);
                    }
                    myCallback.onCallbackSlider(attractionsList);
                }
            }
        });
    }
    // lấy Văn học trong nước:
    public void getSachVHTN(final CallBackSlider myCallback) {
        firebaseFirestore.collection("marketing_bookcase").whereEqualTo("marketing_id","nzqndZ3vpgLlJHz4mjy7").limit(8).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<Marketing> attractionsList = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Marketing attraction = document.toObject(Marketing.class);
                        attractionsList.add(attraction);
                    }
                    myCallback.onCallbackSlider(attractionsList);
                }
            }
        });
    }

    // lấy Sách khuyên đọc:
    public void getSachKhuyenDoc(final CallBackKhuyenDoc myCallback) {
        firebaseFirestore.collection("book").limit(6).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<Marketing> attractionsList = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Marketing attraction = new Marketing();
                        attraction.setMarketing_id("");
                        attraction.setBook_id(document.getId());
                        attractionsList.add(attraction);
                    }
                    myCallback.onCallbackSlider(attractionsList, task.getResult().getDocuments().get(task.getResult().size() - 1));
                }
            }
        });
    }

    // lấy Sách khuyên đọc loadmore:
    public void getSachKhuyenDocLoadMore(DocumentSnapshot documentSnapshot,final CallBackKhuyenDoc myCallback ) {
        firebaseFirestore.collection("book").startAfter(documentSnapshot).limit(6).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Marketing> attractionsList = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Marketing attraction = new Marketing();
                                attraction.setMarketing_id("");
                                attraction.setBook_id(document.getId());
                                attractionsList.add(attraction);
                            }
                            if (task.getResult().size() != 0) {
                                myCallback.onCallbackSlider(attractionsList, task.getResult().getDocuments().get(task.getResult().size() - 1));
                            }
                        }
                    }
                });
    }

    // lấy NXB_BookCase:
    public void getNXB(final CallBackNXB myCallback) {
        firebaseFirestore.collection("publisher").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<NXB> NXBBookList = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()){
                        NXB nxb = new NXB();
                       nxb.setId(document.getId());
                        nxb.setName(document.getString("name"));
                        NXBBookList.add(nxb);
                    }
                    myCallback.onCallbackAlbum(NXBBookList);
                }
            }
        });
    }


}
