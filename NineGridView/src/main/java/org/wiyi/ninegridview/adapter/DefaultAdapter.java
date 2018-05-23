package org.wiyi.ninegridview.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.wiyi.ninegridview.NineGridView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xing on 11/13/15.
 */
public abstract class DefaultAdapter<T> implements NineGridView.NineGridAdapter{
    protected List<T> data = new ArrayList<>();

    @Override
    public int getCount() {
        return data == null ? 0 : data.size() ;
    }

    @Override
    public T getItem(int position) {
        return data.get(position);
    }

    public ImageView getDefaultImageView(Context context) {
        ImageView imageView = new ImageView(context) ;
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        ViewGroup.LayoutParams params = getDefaultLayoutParams() ;
        imageView.setLayoutParams(params);

        return imageView ;
    }

    private ViewGroup.LayoutParams getDefaultLayoutParams() {
        return new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT) ;
    }

    public List<T> getDataList() {
        return data;
    }
}
