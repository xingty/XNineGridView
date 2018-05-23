package org.wiyi.sample;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.wiyi.ninegridview.adapter.DefaultAdapter;

/**
 * Created by xing on 11/15/15.
 */
public class NetworkImageAdapter extends DefaultAdapter<String> {

    @Override
    public View getView(@NonNull ViewGroup parent, int position, View recycleView) {
        String url = getItem(position) ;
        ImageView imageView ;

        if (recycleView == null) {
            imageView = getDefaultImageView(parent.getContext());
        } else {
            imageView = (ImageView) recycleView;
        }

        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Picasso.with(parent.getContext()).load(url).placeholder(R.drawable.ic_placeholder).into(imageView);

        return imageView;
    }
}
