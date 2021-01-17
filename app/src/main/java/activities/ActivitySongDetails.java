package activities;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.i3center.android.shohada.App;
import com.i3center.android.shohada.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import custom.WaterProgressBar;
import de.hdodenhof.circleimageview.CircleImageView;
import structures.StructSong;

public class ActivitySongDetails extends ActivityParent {
    private MediaPlayer player;
    private boolean progressCanUpdate;
    private WaterProgressBar customProgress;
    private int duration;
    private int now;
    private boolean isMusicPlaying;
    int position = -1;
    TextView txtName;
    TextView txtCover;
    TextView txtNow;
    TextView txtDuration;
    ImageView imgPlay;
    CircleImageView imgCover;
    StructSong song;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_details);

        progressCanUpdate = true;

        imgCover = (CircleImageView) findViewById(R.id.imgCover);
        customProgress = (WaterProgressBar) findViewById(R.id.customProgress);
        txtName = (TextView) findViewById(R.id.txtName);
        txtNow = (TextView) findViewById(R.id.txtNow);
        txtDuration = (TextView) findViewById(R.id.txtDuration);
        txtCover = (TextView) findViewById(R.id.txtCover);
        imgPlay = (ImageView) findViewById(R.id.imgPlay);
        ImageView imgNext = (ImageView) findViewById(R.id.imgNext);
        ImageView imgPrevious = (ImageView) findViewById(R.id.imgPrevious);
        txtCover.setVisibility(View.GONE);
        customProgress.setProgress(0);


        imgPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isMusicPlaying) {
                    imgPlay.setImageResource(R.drawable.play);
                    player.pause();
                    isMusicPlaying = false;
                } else {
                    imgPlay.setImageResource(R.drawable.pause);
                    player.start();
                    isMusicPlaying = true;
                }
            }
        });

        imgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player.stop();
                position = position + 1;
                if (position >= App.songs.size()) {
                    position = 0;
                }
                loadFile();
                reset();

            }
        });

        imgPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player.stop();
                position = position - 1;
                if (position <0) {
                    position = App.songs.size()-1;
                }
                loadFile();
                reset();

            }
        });

        customProgress.setOnProgressChangeListener(new WaterProgressBar.OnProgressChangeListener() {
            @Override
            public void onTouch(boolean isTouch) {
                progressCanUpdate = !isTouch;
            }

            @Override
            public void onProgressChange(float progress, boolean isTouch) {
                if (!isTouch) {
                    float percent = progress / 100;
                    player.seekTo((int) (duration * percent));
                }
                if (progress == 100) {
                    reset();
                    startThread();
                }
                progressCanUpdate = !isTouch;
                txtNow.setText(convertToTimeFormat(player.getCurrentPosition()));
            }
        });


        Intent intent = getIntent();
        Bundle info = intent.getExtras();

        if (intent.hasExtra("POSITION")) {
            position = info.getInt("POSITION");
        }
        reset();


    }

    private void startThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    duration = player.getDuration();
                    int now = 0;
                    while (now < duration) {
                        if (progressCanUpdate) {
                            now = player.getCurrentPosition();
                            float progress = (float) (100.0f * (float) now / (float) duration);
                            customProgress.setProgress(progress);
                            final int min = Math.min(duration, now);
                            App.getUIHandler().post(new Runnable() {
                                @Override
                                public void run() {
                                    txtDuration.setText(convertToTimeFormat(duration));
                                    txtNow.setText(convertToTimeFormat(min));
                                }
                            });
                        }
                        Thread.sleep(30);

                    }
                    App.getUIHandler().post(new Runnable() {
                        @Override
                        public void run() {
                            player.stop();
                            reset();
                            startThread();
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        player.stop();
    }

    private void reset() {
        song = App.songs.get(position);
        int coverId = song.coverResourceId;
        int songId = song.songResourceId;
        String name = song.persianName;
        customProgress.setProgress(0);
        imgPlay.setImageResource(R.drawable.pause);
        txtNow.setText("00:00");

        txtName.setTypeface(App.persianFont);
        txtDuration.setTypeface(App.persianFont);
        txtNow.setTypeface(App.persianFont);
        txtName.setText(name);
        imgCover.setImageResource(coverId);

        if (coverId == 0) {
            txtCover.setVisibility(View.VISIBLE);
            imgCover.setVisibility(View.GONE);
        } else {
            txtCover.setVisibility(View.GONE);
            imgCover.setVisibility(View.VISIBLE);
        }

        player = MediaPlayer.create(App.getContext(), songId);
        player.setLooping(true);
        startThread();
        player.start();
        isMusicPlaying = true;
        duration = player.getDuration();
        txtDuration.setText(convertToTimeFormat(duration) + "");
    }

    private void loadFile() {
        final File file = new File(App.DIR_MEDIA + "/" + song.fileName);
        Log.i("LOG", "1");
        if (!file.exists()) {
            Log.i("LOG", "2");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    int permissionCheck = ContextCompat.checkSelfPermission(App.getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                        App.requestPermission();
                        return;
                    }
                    try {
                        InputStream inputStream = App.getCurrentActivity().getResources().openRawResource(song.songResourceId);
                        OutputStream outputStream = new FileOutputStream(file);
                        byte buffer[] = new byte[1024];
                        int len;
                        while ((len = inputStream.read(buffer)) > 0) {
                            outputStream.write(buffer, 0, len);
                        }
                        outputStream.flush();
                        outputStream.close();
                        inputStream.close();

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ActivityNotFoundException e) {
                        // Instruct the user to install a PDF reader here, or something
                    } catch (Exception e) {
                    }
                }
            }).start();

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == App.WES_PermissionCode) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                new File(App.DIR_PDF).mkdirs();
                new File(App.DIR_MEDIA).mkdirs();
            }
        }
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
