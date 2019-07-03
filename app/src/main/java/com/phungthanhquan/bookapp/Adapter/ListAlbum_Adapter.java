package com.phungthanhquan.bookapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.viewpager.widget.PagerAdapter;

import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.phungthanhquan.bookapp.Object.Album;
import com.phungthanhquan.bookapp.Object.Album_BookCase;
import com.phungthanhquan.bookapp.R;
import com.phungthanhquan.bookapp.View.Activity.ListBookToChoice;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by reale on 4/20/2017.
 */

public class ListAlbum_Adapter extends PagerAdapter {

    List<Album> albumBookCaseList;
    Context context;
    LayoutInflater layoutInflater;
    private StorageReference firebaseStorage;

    public ListAlbum_Adapter(List<Album> lstImages, Context context) {
        this.albumBookCaseList = lstImages;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        firebaseStorage = FirebaseStorage.getInstance().getReference();
    }

    @Override
    public int getCount() {
     return albumBookCaseList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        View view = layoutInflater.inflate(R.layout.item_albumbook,container,false);
        final ImageView imageView = view.findViewById(R.id.image_album);
        final TextView  title = view.findViewById(R.id.title_album);
        final ProgressBar progressBar = view.findViewById(R.id.progress);
        firebaseStorage.child("images").child("albums").child(albumBookCaseList.get(position).getId()+".png").getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.d("album", albumBookCaseList.size()+"");
                        Picasso.get().load(uri).into(imageView, new Callback() {
                            @Override
                            public void onSuccess() {
                                progressBar.setVisibility(View.GONE);
                            }

                            @Override
                            public void onError(Exception e) {

                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.d("album", exception.toString());
            }
        });

        title.setText(albumBookCaseList.get(position).getName());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ListBookToChoice.class);
                Pair<View, String> p1 = Pair.create((View)imageView, "shareImage");
                Pair<View, String> p2 = Pair.create((View)title, "shareTitle");
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation((Activity)context, p1, p2);
                intent.putExtra("image", albumBookCaseList.get(position).getId()+".png");
                intent.putExtra("title", albumBookCaseList.get(position).getName());
                context.startActivity(intent, options.toBundle());
            }
        });
        container.addView(view);
        return view;
    }


}
