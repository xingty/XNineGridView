package org.wiyi.sample;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.wiyi.ninegridview.adapter.DefaultAdapter;

/**
 * Created by Bigbyto on 11/15/15.
 */
public class ImageAdapter extends DefaultAdapter<Integer> {
    @Override
    public View getView(ViewGroup parent,int positon, View recycleView) {
        ImageView imageView ;

        if (recycleView == null) {
            imageView = getDefaultImageView(parent.getContext());
        } else {
            imageView = (ImageView) recycleView;
        }

        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(getItem(positon));

        return imageView;
    }
}
