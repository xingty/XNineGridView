package org.wiyi.sample;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import org.wiyi.ninegridview.adapter.DefaultAdapter;

import java.util.List;

/**
 * Created by xing on 11/15/15.
 */
public class ImageAdapter extends DefaultAdapter<Integer> {

    public ImageAdapter(Context context, List<Integer> t) {
        super(context, t);
    }

    @Override
    public View getView(int positon, View recycleView) {
        ImageView imageView ;

        if (recycleView == null) {
            imageView = generialDefaultImageView() ;
        } else {
            imageView = (ImageView) recycleView;
        }

        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(getItem(positon));

        return imageView;
    }
}
