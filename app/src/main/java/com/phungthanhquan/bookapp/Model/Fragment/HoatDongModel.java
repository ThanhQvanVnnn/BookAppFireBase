package com.phungthanhquan.bookapp.Model.Fragment;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.phungthanhquan.bookapp.Object.Book;
import com.phungthanhquan.bookapp.Object.BookCase;
import com.phungthanhquan.bookapp.Object.Friend;
import com.phungthanhquan.bookapp.Object.ItemSachBanDangDoc;

import java.util.ArrayList;
import java.util.List;

public class HoatDongModel {
    private FirebaseFirestore firebaseFirestore;
    private StorageReference storageReference;
    private List<BookCase> bookCaseList;

    public HoatDongModel() {
        firebaseFirestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        bookCaseList = new ArrayList<>();

    }

    public interface callBackLayDuLieuDau {
        void myCallBack(List<Friend> friendList);
    }

    public interface callBackHoatDongModel {
        void myCallBack(List<BookCase> ds);
    }

    public void layDanhSachSachBan(final Friend friend, final callBackHoatDongModel callBackHoatDongModel) {
        final List<BookCase> itemSachBanDangDocs = new ArrayList<>();
        firebaseFirestore.collection("user_bookcase").whereEqualTo("user_id", friend.getReceiver_id()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull final Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for(QueryDocumentSnapshot queryDocumentSnapshot:task.getResult()){
                        BookCase bookCase = new BookCase();
                        bookCase.setBook_id(queryDocumentSnapshot.getString("book_id"));
                        bookCase.setUser_id(friend.getReceiver_id());
                        itemSachBanDangDocs.add(bookCase);
                    }
                    callBackHoatDongModel.myCallBack(itemSachBanDangDocs);
                }
            }

        });
    }

    public void layDanhSachBan(final callBackLayDuLieuDau callBackLayDuLieuDau) {
        firebaseFirestore.collection("friend").whereEqualTo("sender_id", FirebaseAuth.getInstance().getUid()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<Friend> friendList1 = new ArrayList<>();
                for (QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots) {
                    if (queryDocumentSnapshot.exists()) {
                        Friend friend = new Friend();
                        friend.setReceiver_id(queryDocumentSnapshot.getString("receiver_id"));
                        friend.setSender_id(FirebaseAuth.getInstance().getUid());
                        friendList1.add(friend);
                    }
                }
                callBackLayDuLieuDau.myCallBack(friendList1);
            }
        });
    }
}
