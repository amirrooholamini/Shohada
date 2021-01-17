package activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bluejamesbond.text.DocumentView;
import com.i3center.android.shohada.App;
import com.i3center.android.shohada.R;

import helper.TextFile;

public class ActivityMartyrDetails extends ActivityParent {
    ImageView imgCover;
    TextView txtDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int paperResourceId = -1;
        int imageResourceId = -1;
        String name = "";

        if (intent.hasExtra("COVER_ID")) {
            imageResourceId = bundle.getInt("COVER_ID");
        }
        if (intent.hasExtra("PAPER_ID")) {
            paperResourceId = bundle.getInt("PAPER_ID");
        }
        if (intent.hasExtra("NAME")) {
            name = bundle.getString("NAME");
        }
        Log.i("LOG",""+imageResourceId);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setContentView(R.layout.activity_martyr_details_v5);
            Log.i("LOG","1");
            imgCover = (ImageView) findViewById(R.id.imgCover);
            txtDesc = (TextView) findViewById(R.id.txtDesc);

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbar);
            setSupportActionBar(toolbar);
            toolbar.setTitle(name);
            collapsingToolbar.setCollapsedTitleTypeface(App.persianFont);
            collapsingToolbar.setExpandedTitleTypeface(App.persianFont);

        } else {
            Log.i("LOG","2");
            setContentView(R.layout.activity_martyr_details);
            imgCover = (ImageView) findViewById(R.id.imgCover);
            txtDesc = (TextView) findViewById(R.id.txtDesc);
            TextView txtName = (TextView) findViewById(R.id.txtName);
            txtName.setText(name);
            txtName.setTypeface(App.persianFont);
        }
        imgCover.setImageResource(imageResourceId);
        txtDesc.setText(TextFile.readTextFile(App.getContext(), paperResourceId));
        txtDesc.setTypeface(App.persianFont);
    }
}
