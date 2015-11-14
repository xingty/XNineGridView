package org.wiyi.sample;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.wiyi.ninegridview.adapter.DefaultAdapter;

import java.util.List;

/**
 * Created by xing on 11/15/15.
 */
public class NetworkImageAdapter extends DefaultAdapter<String> {

    public NetworkImageAdapter(Context context, List<String> t) {
        super(context, t);
    }

    @Override
    public View getView(int positon, View recycleView) {
        String url = getItem(positon) ;
        ImageView imageView ;

        if (recycleView == null) {
            imageView = generialDefaultImageView() ;
        } else {
            imageView = (ImageView) recycleView;
        }

        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Picasso.with(context).load(url).placeholder(R.drawable.ic_placeholder).into(imageView);

        return imageView;
    }
}
