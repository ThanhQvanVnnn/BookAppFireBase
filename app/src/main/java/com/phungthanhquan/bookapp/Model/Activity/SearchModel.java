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

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class SearchModel {

    private FirebaseFirestore firebaseFirestore;

    public SearchModel() {
        this.firebaseFirestore = FirebaseFirestore.getInstance();
    }

    public interface Callback {
        void myCallBack(List<Marketing> marketingList);
    }

    public void getDataSearch(final String kitu, final Callback callback) {
        firebaseFirestore.collection("book").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    List<Book> bookList = new ArrayList<>();
                    for(QueryDocumentSnapshot queryDocumentSnapshot: task.getResult()){
                        if(queryDocumentSnapshot.exists()) {
                            Book book = new Book();
                            book.setId(queryDocumentSnapshot.getId());
                            book.setName(queryDocumentSnapshot.getString("name"));
                            bookList.add(book);
                        }
                    }
                    List<Marketing> marketings = new ArrayList<>();
                    for(Book book:bookList){
                        if(removeAccent(book.getName().toLowerCase()).contains(removeAccent(kitu.toLowerCase()))){
                            Marketing marketing = new Marketing();
                            marketing.setBook_id(book.getId());
                            marketings.add(marketing);
                        }
                    }
                    callback.myCallBack(marketings);
                }
            }
        });
    }
    public static String removeAccent(String s) {

        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("");
    }
}
