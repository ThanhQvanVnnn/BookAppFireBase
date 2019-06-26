package com.phungthanhquan.bookapp.Adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.phungthanhquan.bookapp.Object.AlbumBook;
import com.phungthanhquan.bookapp.R;
import com.phungthanhquan.bookapp.View.Activity.ListBookToChoice;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by reale on 4/20/2017.
 */

public class ListAlbum_Adapter extends PagerAdapter {

    List<AlbumBook> lstImages;
    Context context;
    LayoutInflater layoutInflater;

    public ListAlbum_Adapter(List<AlbumBook> lstImages, Context context) {
        this.lstImages = lstImages;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
     return lstImages.size();
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
        Picasso.get().load(lstImages.get(position).getImageAlbum()).into(imageView, new Callback() {
            @Override
            public void onSuccess() {
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(Exception e) {

            }
        });
        title.setText(lstImages.get(position).getTitleAlbum());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ListBookToChoice.class);
                Pair<View, String> p1 = Pair.create((View)imageView, "shareImage");
                Pair<View, String> p2 = Pair.create((View)title, "shareTitle");
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation((Activity)context, p1, p2);
                intent.putExtra("image",lstImages.get(position).getImageAlbum());
                intent.putExtra("title",lstImages.get(position).getTitleAlbum());
                context.startActivity(intent, options.toBundle());
            }
        });
        container.addView(view);
        return view;
    }


}
