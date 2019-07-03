package com.phungthanhquan.bookapp.Adapter;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.phungthanhquan.bookapp.Object.Marketing;
import com.phungthanhquan.bookapp.R;
import com.phungthanhquan.bookapp.View.Activity.BookDetail;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ViewPager_Slider_Adapter extends PagerAdapter {

    private Context context;
    private List<Marketing> listslider;
    private StorageReference firebaseStorage;

    public ViewPager_Slider_Adapter(Context context, List<Marketing> listslider) {
        this.context = context;
        this.listslider = listslider;
        firebaseStorage = FirebaseStorage.getInstance().getReference();
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View sliderLayout = inflater.inflate(R.layout.viewpager_slider,null);
        final ProgressBar progressBar = sliderLayout.findViewById(R.id.progress);
        final ImageView slider = sliderLayout.findViewById(R.id.pager_slider_image);
        firebaseStorage.child("images").child("banners").child(listslider.get(position).getBook_id()+".png").getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(slider, new Callback() {
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

            }
        });


        slider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BookDetail.class);
                intent.putExtra("book_id",listslider.get(position).getBook_id());
                intent.putExtra("image","");
                context.startActivity(intent);
            }
        });
        container.addView(sliderLayout);
        return sliderLayout;
    }

    @Override
    public int getCount() {
       return listslider.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

}
