package activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.i3center.android.shohada.App;
import com.i3center.android.shohada.R;

public class ActivityMain extends ActivityParent {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final LinearLayout root = (LinearLayout) findViewById(R.id.root);
        final RelativeLayout layoutLibrary = (RelativeLayout) findViewById(R.id.layoutLibrary);
        final RelativeLayout layoutActions = (RelativeLayout) findViewById(R.id.layoutActions);
        final RelativeLayout layoutSong = (RelativeLayout) findViewById(R.id.layoutSong);
        final RelativeLayout layoutVideo = (RelativeLayout) findViewById(R.id.layoutVideo);
        final RelativeLayout layout1 = (RelativeLayout) findViewById(R.id.layout1);
        final RelativeLayout layoutAbout = (RelativeLayout) findViewById(R.id.layoutAbout);
        final RelativeLayout layoutMemorial = (RelativeLayout) findViewById(R.id.layoutMemorial);

        ImageButton imgLibrary = (ImageButton) findViewById(R.id.imgLibrary);
        ImageButton imgSong = (ImageButton) findViewById(R.id.imgSong);
        ImageButton imgVideo = (ImageButton) findViewById(R.id.imgVideo);
        ImageButton imgActions = (ImageButton) findViewById(R.id.imgActions);
        ImageButton imgMartyr = (ImageButton) findViewById(R.id.imgMartyr);
        ImageButton imgAbout = (ImageButton) findViewById(R.id.imgAbout);
        ImageButton imgMemorial = (ImageButton) findViewById(R.id.imgMemorial);

        TextView txtLibrary = (TextView) findViewById(R.id.txtLibrary);
        TextView txtSong = (TextView) findViewById(R.id.txtSong);
        TextView txtVideo = (TextView) findViewById(R.id.txtVideo);
        TextView txtAbout = (TextView) findViewById(R.id.txtAbout);
        TextView txt1 = (TextView) findViewById(R.id.txt1);
        TextView txtActions = (TextView) findViewById(R.id.txtActions);
        TextView txtMemorial = (TextView) findViewById(R.id.txtMemorial);

        final Animation scale = AnimationUtils.loadAnimation(App.getContext(), R.anim.scale_animation);

        txtLibrary.setTypeface(App.persianFont);
        txtSong.setTypeface(App.persianFont);
        txtVideo.setTypeface(App.persianFont);
        txtAbout.setTypeface(App.persianFont);
        txt1.setTypeface(App.persianFont);
        txtActions.setTypeface(App.persianFont);

        new Thread(new Runnable() {
            @Override
            public void run() {
                root.setVisibility(View.VISIBLE);
                root.startAnimation(scale);
            }
        }).start();


        imgLibrary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(App.getCurrentActivity(), ActivityLibrary.class);
                App.getCurrentActivity().startActivity(intent);
            }
        });

        imgSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(App.getCurrentActivity(), ActivitySong.class);
                App.getCurrentActivity().startActivity(intent);
            }
        });

        imgVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(App.getCurrentActivity(), ActivityVideo.class);
                App.getCurrentActivity().startActivity(intent);
            }
        });

        imgActions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(App.getCurrentActivity(), ActivityOperations.class);
                App.getCurrentActivity().startActivity(intent);
            }
        });

        imgAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(App.getCurrentActivity(), ActivityAbout.class);
                App.getCurrentActivity().startActivity(intent);
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tg://resolve?domain=partsilicon"));
//                startActivity(intent);
            }
        });

        imgMartyr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(App.getCurrentActivity(), ActivityMartyr.class);
                App.getCurrentActivity().startActivity(intent);
            }
        });

        imgMemorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(App.getCurrentActivity(), ActivityMemorial.class);
                App.getCurrentActivity().startActivity(intent);
            }
        });

        View.OnTouchListener touchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                ImageButton img = (ImageButton)view;
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        img.setImageResource(R.drawable.btn_press);
                        return false;
                    }
                    case MotionEvent.ACTION_UP: {
                        img.setImageResource(R.drawable.btn);
                        return false;
                    }
                }
                return false;
            }
        };

        imgLibrary.setOnTouchListener(touchListener);
        imgSong.setOnTouchListener(touchListener);
        imgVideo.setOnTouchListener(touchListener);
        imgActions.setOnTouchListener(touchListener);
        imgMartyr.setOnTouchListener(touchListener);
        imgAbout.setOnTouchListener(touchListener);
        imgMemorial.setOnTouchListener(touchListener);


    }
}
