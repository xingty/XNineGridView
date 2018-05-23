package org.wiyi.sample;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

/**
 * Created by xing on 12/1/15.
 */
public class CustomImageView extends ImageView{
    private static final String TAG = "CustomImageView" ;

    public CustomImageView(Context context) {
        super(context);
    }

    public CustomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.e(TAG,"custom imageview measured:" + this) ;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
