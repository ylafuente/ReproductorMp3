package com.example.reproductormusical;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    List<String> list;
    ArrayAdapter<String> adapter;
    MediaPlayer mediaPlayer;
    AudioManager audioManager;
    boolean isActivePlaying = true;
    ImageView button_p_p,next_butt,prev_butt;
    SeekBar volumeSeekBar,advanceSeekbar;
    TextView tv;
    int song;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button_p_p = findViewById(R.id.play_pause);
        next_butt = findViewById(R.id.nextButt);
        prev_butt = findViewById(R.id.prevButt);
        tv = findViewById(R.id.NombrePrincipal);

        audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);

        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        volumeSeekBar = findViewById(R.id.volumeSeekBar);
        advanceSeekbar = findViewById(R.id.advanceSeekBar);

        advanceSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mediaPlayer.seekTo(i);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


       /* new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                advanceSeekbar.setProgress(mediaPlayer.getCurrentPosition());

            }
            }, 0, 1000
        );*/

        volumeSeekBar.setMax(maxVolume);
        volumeSeekBar.setProgress(currentVolume);
        volumeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                Log.d("volume:", Integer.toString(progress));
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        next_butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(song == list.size()-1){
                    song = -1;
                }
                song++;
                if(mediaPlayer != null){

                    mediaPlayer.release();
                }
                int resId = getResources().getIdentifier(list.get(song),"raw",getPackageName());
                mediaPlayer = MediaPlayer.create(MainActivity.this,resId);
                tv.setText(list.get(song));
                isActivePlaying = true;
                button_p_p.setImageResource(R.drawable.play_button);
                mediaPlayer.start();
                int duration = mediaPlayer.getDuration();
                int progress = mediaPlayer.getCurrentPosition();
                advanceSeekbar.setMax(duration);
                advanceSeekbar.setProgress(progress);
            }
        });

         prev_butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(song == 0){
                    song = list.size();
                }
                song--;
                if(mediaPlayer != null){

                    mediaPlayer.release();
                }
                int resId = getResources().getIdentifier(list.get(song),"raw",getPackageName());
                mediaPlayer = MediaPlayer.create(MainActivity.this,resId);
                tv.setText(list.get(song));
                isActivePlaying = true;
                button_p_p.setImageResource(R.drawable.play_button);
                mediaPlayer.start();
                int duration = mediaPlayer.getDuration();
                int progress = mediaPlayer.getCurrentPosition();
                advanceSeekbar.setMax(duration);
                advanceSeekbar.setProgress(progress);
            }
        });

        button_p_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer != null){
                    if(isActivePlaying ) {
                        mediaPlayer.pause();
                        isActivePlaying = false;
                        button_p_p.setImageResource(R.drawable.pause);
                    }
                    else{
                        mediaPlayer.start();
                        isActivePlaying = true;
                        button_p_p.setImageResource(R.drawable.play_button);
                    }
                }
            }
        });

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
                song = i;
                mediaPlayer = MediaPlayer.create(MainActivity.this,resId);
                isActivePlaying = true;
                button_p_p.setImageResource(R.drawable.play_button);
                mediaPlayer.start();
                tv.setText(list.get(song));
                int duration = mediaPlayer.getDuration();
                int progress = mediaPlayer.getCurrentPosition();
                advanceSeekbar.setMax(duration);
                advanceSeekbar.setProgress(progress);
            }

        });

    }

}
