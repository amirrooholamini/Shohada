package activities;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.GridLayoutAnimationController;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.i3center.android.shohada.App;
import com.i3center.android.shohada.R;

import java.io.File;
import java.util.ArrayList;

import adapters.AdapterVideo;
import structures.StructVideo;

public class ActivityVideo extends ActivityParent {

    private boolean searchTime;
    GridView lstVideo ;
    ImageButton imgSearch;
    ImageButton imgCancel;
    RelativeLayout layoutSearch;
    TextView txtTitle;
    EditText edtSearch;

    Animation search_in;
    Animation search_out;
    Animation title_in;
    Animation title_out;
    Animation search_icon_in;
    Animation search_icon_out;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_video);
        searchTime = false;
        imgSearch = (ImageButton) findViewById(R.id.imgSearch);
        imgCancel = (ImageButton) findViewById(R.id.imgCancel);
        layoutSearch = (RelativeLayout) findViewById(R.id.layoutSearch);
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        edtSearch = (EditText)findViewById(R.id.edtSearch);

        edtSearch.setTypeface(App.persianFont);
        txtTitle.setTypeface(App.persianFont);

        App.videos = new ArrayList<StructVideo>();
            StructVideo video = new StructVideo();
            video.coverResourceId = R.drawable.search;
            video.persianName = "محل شهادت شهید محراب";
            video.fileName = "shahid mehrab.mp4";
            video.time = (int) (Math.random()*100);
            video.coverResourceId = R.drawable.shahidmehrab;
            video.videoResourceId = R.raw.shahidmehrab;
            App.videos.add(video);

        Animation fadeIn = AnimationUtils.loadAnimation(App.getContext(),R.anim.fade_in);
        lstVideo = (GridView)findViewById(R.id.lstVideo);
        AdapterVideo adapter = new AdapterVideo(App.getContext(), App.videos);
        lstVideo.setAdapter(adapter);
        lstVideo.setLayoutAnimation(new GridLayoutAnimationController(fadeIn));

        search_in = AnimationUtils.loadAnimation(App.getContext(), R.anim.search_in);
        search_out = AnimationUtils.loadAnimation(App.getContext(), R.anim.search_out);
        title_in = AnimationUtils.loadAnimation(App.getContext(), R.anim.title_in);
        title_out = AnimationUtils.loadAnimation(App.getContext(), R.anim.title_out);
        search_icon_in = AnimationUtils.loadAnimation(App.getContext(), R.anim.search_icon_in);
        search_icon_out = AnimationUtils.loadAnimation(App.getContext(), R.anim.search_icon_out);

        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchTime = true;
                layoutSearch.startAnimation(search_in);
                imgSearch.startAnimation(search_icon_out);
                txtTitle.startAnimation(title_out);
                imgSearch.setVisibility(View.GONE);
                txtTitle.setVisibility(View.GONE);
                layoutSearch.setVisibility(View.VISIBLE);
            }
        });

        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchTime = false;
                layoutSearch.startAnimation(search_out);
                imgSearch.startAnimation(search_icon_in);
                txtTitle.startAnimation(title_in);
                imgSearch.setVisibility(View.VISIBLE);
                txtTitle.setVisibility(View.VISIBLE);
                layoutSearch.setVisibility(View.GONE);
                AdapterVideo adapter = new AdapterVideo(App.getContext(), App.videos);
                lstVideo.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                edtSearch.setText("");
            }
        });

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ArrayList<StructVideo> searchedVideos = new ArrayList<StructVideo>();
                for (int index = 0; index < App.videos.size(); index++) {
                    StructVideo video = App.videos.get(index);
                    if(video.persianName.contains(charSequence)){
                        searchedVideos.add(video);
                    }
                }
                AdapterVideo adapter = new AdapterVideo(App.getContext(),searchedVideos);
                lstVideo.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });


    }

    @Override
    public void onBackPressed() {
        if (!searchTime) {
            super.onBackPressed();
        } else {
            //super.onBackPressed();
            searchTime = false;
            layoutSearch.startAnimation(search_out);
            imgSearch.startAnimation(search_icon_in);
            txtTitle.startAnimation(title_in);
            imgSearch.setVisibility(View.VISIBLE);
            txtTitle.setVisibility(View.VISIBLE);
            layoutSearch.setVisibility(View.GONE);
            AdapterVideo adapter = new AdapterVideo(App.getContext(),App.videos);
            lstVideo.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            edtSearch.setText("");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == App.WES_PermissionCode){
            if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                new File(App.DIR_PDF).mkdirs();
                new File(App.DIR_MEDIA).mkdirs();
            }
        }
    }
}
