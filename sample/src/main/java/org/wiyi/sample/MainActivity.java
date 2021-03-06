package org.wiyi.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView listview ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initData();
    }

    private void initViews() {
        listview = findViewById(R.id.listview);
    }

    private void initData() {
        ArrayList<String> titles = new ArrayList<>(2) ;
        titles.add("load image from drawable") ;
        titles.add("load image from network") ;

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,titles) ;
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0 : {
                        toImageActivity(0);
                        break ;
                    }
                    case 1 :
                        toImageActivity(1);
                        break ;
                }
            }
        });
    }

    private void toImageActivity(int mode) {
        Intent intent = new Intent(this,ImageActivity.class) ;
        intent.putExtra("mode", mode) ;
        startActivity(intent);
    }
}
