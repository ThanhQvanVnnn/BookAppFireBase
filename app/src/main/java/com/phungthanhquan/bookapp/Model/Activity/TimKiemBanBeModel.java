package com.phungthanhquan.bookapp.Model.Activity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.phungthanhquan.bookapp.Object.Friend;
import com.phungthanhquan.bookapp.Object.User;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class TimKiemBanBeModel {

    FirebaseFirestore firebaseFirestore;

    public TimKiemBanBeModel() {
        this.firebaseFirestore = FirebaseFirestore.getInstance();
    }

    public interface  CallBackTimKiemModel{
        void mycallback(List<User> userList, List<Friend> friendList);
    }
    public void TimKiemBanBe(final String name, final CallBackTimKiemModel callBackTimKiemModel){
        firebaseFirestore.collection("friend").whereEqualTo("sender_id", FirebaseAuth.getInstance().getUid()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                final List<Friend> friendList = new ArrayList<>();
                for(QueryDocumentSnapshot queryDocumentSnapshot: queryDocumentSnapshots){
                    if(queryDocumentSnapshot.exists()){
                        Friend friend = new Friend();
                        friend.setId(queryDocumentSnapshot.getId());
                        friend.setSender_id(queryDocumentSnapshot.getString("sender_id"));
                        friend.setReceiver_id(queryDocumentSnapshot.getString("receiver_id"));
                        friendList.add(friend);
                    }
                }
                firebaseFirestore.collection("user").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<User> userList = new ArrayList<>();
                        for(QueryDocumentSnapshot queryDocumentSnapshot: queryDocumentSnapshots){
                            if(queryDocumentSnapshot.exists()){
                               User user = new User();
                               user.setName(queryDocumentSnapshot.getString("name"));
                               user.setUser_id(queryDocumentSnapshot.getId());
                               user.setCreate_at(queryDocumentSnapshot.getString("create_at"));
                               userList.add(user);
                            }
                        }
                        List<User> userListTimKiem = new ArrayList<>();
                        for(User user:userList){
                            if(removeAccent(user.getName().toLowerCase()).contains(removeAccent(name.toLowerCase())) && !user.getUser_id().equals(FirebaseAuth.getInstance().getUid())){
                                userListTimKiem.add(user);
                            }
                        }
                        callBackTimKiemModel.mycallback(userListTimKiem,friendList);
                    }
                });
            }
        });
    }
    public static String removeAccent(String s) {

        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("");
    }
}
