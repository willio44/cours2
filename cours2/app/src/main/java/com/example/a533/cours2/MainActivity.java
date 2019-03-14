package com.example.a533.cours2;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    MediaPlayer mediaplayer;
    int Volume=0;
    int maxVolume = 50;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaplayer = MediaPlayer.create(this,R.raw.farm);
        mediaplayer.setLooping(false);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SeekBar seekBarMedia = findViewById(R.id.seekBar_media);
                seekBarMedia.setProgress(mediaplayer.getCurrentPosition()*100/mediaplayer.getDuration());
                handler.postDelayed(this,1000);
            }
        }, 1000);
        setListener();
        setEndListener();
    }

    public void setListener(){
        findViewById(R.id.btn_play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playMediaPlayer();
            }
        });
        findViewById(R.id.btn_pause).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseMediaPlayer();
            }
        });
        setSeekBarListener();
        setSeekBarListenerSound();
    }

    private void playMediaPlayer(){
       if(!mediaplayer.isPlaying()){
           mediaplayer.start();;
       }
    }

    private void pauseMediaPlayer(){
        if(mediaplayer.isPlaying()){
            mediaplayer.pause();;
        }
    }

    private void setSeekBarListener(){
        SeekBar seekBarMedia = findViewById(R.id.seekBar_media);
        seekBarMedia.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    seekInMedia(progress);

                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void setSeekBarListenerSound(){
        SeekBar seekBarMediaSound = findViewById(R.id.seekBar_sound);

        seekBarMediaSound.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    setVolume(progress);
                    Volume = progress;
                    mediaplayer.setVolume(Volume,Volume);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(getApplicationContext(), "Volume: " + Integer.toString(Volume), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void seekInMedia(int progress){
        mediaplayer.seekTo(progress*mediaplayer.getDuration()/100);
    }

    private void setVolume(int volume ){
        float log1=(float)(Math.log(maxVolume-volume)/Math.log(maxVolume));
        mediaplayer.setVolume(1-log1, 1-log1);
    }

    private void setEndListener(){
        mediaplayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Toast.makeText(getApplicationContext(),"END",Toast.LENGTH_SHORT).show();
            }
        });
    }



}
