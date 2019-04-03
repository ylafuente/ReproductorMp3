package com.example.reproductormusical;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    List<String> list;
    ArrayAdapter<String> adapter;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.PhoneMusicList);
        list = new ArrayList<>();

        Field[] fields = R.raw.class.getFields();

        for(int i = 0; i < fields.length;i++){

            list.add(fields[i].getName());
        }

        adapter = new ArrayAdapter<>(MainActivity.this,android.R.layout.simple_list_item_1,list);
        listView.setAdapter( adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(mediaPlayer != null){

                    mediaPlayer.release();
                }
                int resId = getResources().getIdentifier(list.get(i),"raw",getPackageName());
                mediaPlayer = MediaPlayer.create(MainActivity.this,resId);
                mediaPlayer.start();
            }

        });
    }

}
