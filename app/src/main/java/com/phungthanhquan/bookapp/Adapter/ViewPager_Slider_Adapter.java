package com.phungthanhquan.bookapp.Adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.phungthanhquan.bookapp.Object.Slider;
import com.phungthanhquan.bookapp.R;
import com.phungthanhquan.bookapp.View.Activity.BookDetail;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ViewPager_Slider_Adapter extends PagerAdapter {

    private Context context;
    private List<Slider> listslider;

    public ViewPager_Slider_Adapter(Context context, List<Slider> listslider) {
        this.context = context;
        this.listslider = listslider;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View sliderLayout = inflater.inflate(R.layout.viewpager_slider,null);
        final ProgressBar progressBar = sliderLayout.findViewById(R.id.progress);
        final ImageView slider = sliderLayout.findViewById(R.id.pager_slider_image);
        Picasso.get().load(listslider.get(position).getUrl()).into(slider, new Callback() {
            @Override
            public void onSuccess() {
            progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(Exception e) {

            }
        });
        slider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BookDetail.class);
                intent.putExtra("image",listslider.get(position).getImage());
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
