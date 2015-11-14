package org.wiyi.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.wiyi.ninegridview.NineGridView;

import java.util.ArrayList;
import java.util.Arrays;

public class ImageActivity extends AppCompatActivity {

    private FrameLayout flContent ;
    private ListView listview ;
    private ArrayList<Moment> moments = new ArrayList<>(9) ;
    private Integer[] images = new Integer[]{
            R.drawable.sky1,
            R.drawable.sky2,
            R.drawable.sky3,
            R.drawable.sky4,
            R.drawable.sky5,
            R.drawable.sky6,
            R.drawable.sky7,
            R.drawable.sky8,
            R.drawable.sky9,
    } ;

    private String[] urls = new String[]{
            "http://img.blog.csdn.net/20151115004147583",
            "http://img.blog.csdn.net/20151115004214075",
            "http://img.blog.csdn.net/20151115004236274",
            "http://img.blog.csdn.net/20151115004258230",
            "http://img.blog.csdn.net/20151115004316107",
            "http://img.blog.csdn.net/20151115004348219",
            "http://img.blog.csdn.net/20151115004411358",
            "http://img.blog.csdn.net/20151115004442588",
            "http://img.blog.csdn.net/20151115004502212"
    } ;
    private int mode ; //0 local 1remote

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
    }

    private void initData() {
        mode = getIntent().getIntExtra("mode",0) ;

        for (int i=0;i<9;i++) {
            Moment moment = new Moment() ;
            moment.content = "content:" + (i+1) ;

            Integer[] resource = new Integer[(i+1)] ;
            String[] address = new String[(i+1)] ;
            for (int j=0;j<resource.length;j++) {
                resource[j] = images[j] ;
                address[j] = urls[j] ;
            }

            moment.resource = resource ;
            moment.address = address ;
            moments.add(moment) ;
        }

        listview = (ListView) findViewById(R.id.listview);
        listview.setAdapter(new MyAdapter());
    }


    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return moments.size();
        }

        @Override
        public Object getItem(int position) {
            return moments.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder ;
            Moment moment = moments.get(position) ;
            if (convertView == null) {
                convertView = LayoutInflater.from(ImageActivity.this).inflate(R.layout.view_list_item, null) ;
                holder = new ViewHolder() ;
                holder.content = (TextView) convertView.findViewById(R.id.tvContent);
                holder.gallery = (NineGridView) convertView.findViewById(R.id.gallery);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            if (mode == 0) {
                ImageAdapter adapter = new ImageAdapter(ImageActivity.this, Arrays.asList(moment.resource)) ;
                holder.gallery.setAdapter(adapter);
            } else {
                NetworkImageAdapter adapter = new NetworkImageAdapter(ImageActivity.this,Arrays.asList(moment.address));
                holder.gallery.setAdapter(adapter);
            }

            holder.content.setText(moment.content);

            return convertView;
        }
    }

    static class ViewHolder {
        public NineGridView gallery ;
        public TextView content ;
    }

    static class Moment {
        public String content ;
        public Integer[] resource ; //
        public String[] address ;
    }
}
