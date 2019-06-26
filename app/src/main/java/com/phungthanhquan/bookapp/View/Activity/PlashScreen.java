package com.phungthanhquan.bookapp.View.Activity;

import android.animation.ValueAnimator;
import android.app.ActivityOptions;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.phungthanhquan.bookapp.R;

import java.io.File;

public class PlashScreen extends AppCompatActivity {

    private ImageView logo;
    private LottieAnimationView lottieAnimationView;
    Toast toast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plash_screen);
        lottieAnimationView = findViewById(R.id.animation);
        logo = findViewById(R.id.logo);
        function();

        Animation animation = AnimationUtils.loadAnimation(this,R.anim.plashscreen);
        Animation animation2 = AnimationUtils.loadAnimation(this,R.anim.plashscreen2);
        logo.startAnimation(animation);
        lottieAnimationView.setAnimation(animation2);
        Thread timer = new Thread(){
            public void run(){
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    PlashScreen.this.runOnUiThread(new Runnable() {
                        Intent intent;
                        String packetName = getPackageName();
                        File f = new File(
                                "/data/data/" + packetName + "/shared_prefs/User_Info.xml");
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        @Override
                        public void run() {
                            if(MainActivity.isNetworkConnected(PlashScreen.this))/*có internet*/{
                                if (f.exists() && user!=null){
                                    intent = new Intent(PlashScreen.this,MainActivity.class);
                                    startActivity(intent);
                                }
                                else{
                                    intent = new Intent(PlashScreen.this,Login.class);
                                    ActivityOptions options = null;
                                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                                        options = (ActivityOptions) ActivityOptions.makeSceneTransitionAnimation(PlashScreen.this,
                                                logo,"vanchuyenlogo");
                                    }
                                    startActivity(intent, options.toBundle());
                                }
                            }else /*không có internet*/ {
                                if(f.exists() && user!=null){
                                    intent = new Intent(PlashScreen.this,MainActivity.class);
                                    startActivity(intent);
                                }else {
                                    intent = new Intent(PlashScreen.this,Login.class);
                                    ActivityOptions options = null;
                                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                                        options = (ActivityOptions) ActivityOptions.makeSceneTransitionAnimation(PlashScreen.this,
                                                logo,"vanchuyenlogo");
                                    }
                                    startActivity(intent, options.toBundle());
                                    showAToast(getResources().getString(R.string.openinternet));
                                }
                            }
                        }
                    });

                }
            }
       };
        timer.start();

    }
    private void function(){
        ValueAnimator animator = ValueAnimator.ofFloat(0f,1f).setDuration(2500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
            lottieAnimationView.setProgress((Float) animation.getAnimatedValue());
            }
        });
        if(lottieAnimationView.getProgress()==0f){

            animator.start();
        }else {
            lottieAnimationView.setProgress(0f);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
    public void showAToast (String st){ //"Toast toast" is declared in the class
        try{ toast.getView().isShown();     // true if visible
            toast.setText(st);
        } catch (Exception e) {         // invisible if exception
            toast = Toast.makeText(this, st,  Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
        }
        toast.show();  //finally display it
    }
}
