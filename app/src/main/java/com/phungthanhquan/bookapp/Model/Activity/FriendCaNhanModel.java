package com.phungthanhquan.bookapp.Model.Activity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.phungthanhquan.bookapp.Object.Friend;

import java.util.ArrayList;
import java.util.List;

public class FriendCaNhanModel {
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth mAuth;

    public FriendCaNhanModel() {
        firebaseFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
    }
    public interface callBaclFriendCanhan{
        void myCallBack(List<Friend> friendList);
    }

    public void getFriendList(final callBaclFriendCanhan callBaclFriendCanhan){
        firebaseFirestore.collection("friend").whereEqualTo("sender_id",mAuth.getUid()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<Friend> friends = new ArrayList<>();
                for (QueryDocumentSnapshot queryDocumentSnapshot:queryDocumentSnapshots){
                    if(queryDocumentSnapshot.exists()){
                        Friend friend = new Friend();
                        friend.setId(queryDocumentSnapshot.getId());
                        friend.setReceiver_id(queryDocumentSnapshot.getString("receiver_id"));
                        friend.setSender_id(mAuth.getUid());
                        friends.add(friend);
                    }
                }
                callBaclFriendCanhan.myCallBack(friends);
            }
        });
    }
    public void getFriendTheoDoiList(final callBaclFriendCanhan callBaclFriendCanhan){
        firebaseFirestore.collection("friend").whereEqualTo("receiver_id",mAuth.getUid()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<Friend> friends = new ArrayList<>();
                for (QueryDocumentSnapshot queryDocumentSnapshot:queryDocumentSnapshots){
                    if(queryDocumentSnapshot.exists()){
                        Friend friend = new Friend();
                        friend.setId(queryDocumentSnapshot.getId());
                        friend.setReceiver_id(mAuth.getUid());
                        friend.setSender_id(queryDocumentSnapshot.getString("receiver_id"));
                        friends.add(friend);
                    }
                }
                callBaclFriendCanhan.myCallBack(friends);
            }
        });
    }
}
