package activities;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import com.i3center.android.shohada.App;
import com.i3center.android.shohada.R;

import javax.xml.transform.ErrorListener;

import activities.ActivityParent;
import custom.LinearSeekBar;

public class ActivityVideoDetails extends ActivityParent {
    private VideoView videoPlayer;
    private boolean canChange;
    private boolean showSeekBar;
    private int duration;

    int position = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_details);
        showSeekBar = true;
        canChange = true;

        videoPlayer = (VideoView)findViewById(R.id.videoPlayer);
        videoPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                finish();
            }
        });

        Intent intent = getIntent();
        Bundle info = intent.getExtras();

        if (intent.hasExtra("POSITION")) {
            position = info.getInt("POSITION");
        }
        reset(position);

    }

    private void reset(int p) {
        canChange = false;
        Uri uri = Uri.parse("android.resource://"+ App.getAppPackageName()+"/"+App.videos.get(p).videoResourceId);
        videoPlayer.setVideoURI(uri);

        App.getUIHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                MediaController mediaController = new MediaController(App.getContext());
                mediaController.setPrevNextListeners(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        position++;
                        if(position>=App.videos.size()){
                            position = 0;
                        }
                        reset(position);
                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        videoPlayer.pause();
                        videoPlayer.stopPlayback();
                        position++;
                        if(position<0){
                            position = App.videos.size()-1;
                        }
                        reset(position);
                    }
                });
                videoPlayer.showContextMenu();
                videoPlayer.setMediaController(mediaController);
                videoPlayer.setKeepScreenOn(true);
                videoPlayer.requestFocus();
                videoPlayer.start();
                videoPlayer.setVisibility(View.VISIBLE);
                canChange = true;
            }
        },1000);
    }
    private String convertToTimeFormat(int time) {
        time = time / 1000;
        String stringTime = "";
        int minutes = time / 60;
        int seconds = time % 60;
        if (minutes < 10) {
            stringTime += "0";
        }
        stringTime += minutes;
        stringTime += ":";

        if (seconds < 10) {
            stringTime += "0";
        }
        stringTime += seconds;
        return stringTime;
    }
}
