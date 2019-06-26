package com.phungthanhquan.bookapp.CustomView;

import android.content.Context;
import android.graphics.drawable.Drawable;
import androidx.core.content.ContextCompat;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.phungthanhquan.bookapp.R;

public class CustomEditText extends androidx.appcompat.widget.AppCompatEditText {
    Drawable crossx,nonecrossx,drawable;
    Boolean visible = false;
    public CustomEditText(Context context) {
        super(context);
        khoitao();
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        khoitao();
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        khoitao();
    }

    private void khoitao(){
        crossx = ContextCompat.getDrawable(getContext(), R.drawable.icon_clear_black_24dp).mutate();
        nonecrossx = ContextCompat.getDrawable(getContext(),android.R.drawable.screen_background_light_transparent).mutate();

        cauhinh();
    }

    private void cauhinh(){
        setInputType(InputType.TYPE_CLASS_TEXT);
        Drawable[] drawables = getCompoundDrawables();
        drawable = visible? crossx:nonecrossx;
        setCompoundDrawablesWithIntrinsicBounds(drawables[0],drawables[1],drawable,drawables[3]);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(MotionEvent.ACTION_DOWN == event.getAction() && event.getX() >= (getRight() - drawable.getBounds().width())){
            setText("");
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        if(lengthAfter == 0 && start == 0){
            visible = false;
            cauhinh();
        }else{
            visible = true;
            cauhinh();
        }

    }
}
