package activities;

import android.content.Intent;
import android.os.Build;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.i3center.android.shohada.App;
import com.i3center.android.shohada.R;

import helper.TextFile;

public class ActivityOperationDetails extends ActivityParent {
    ImageView imgCover;
    TextView txtDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int textResourceId = -1;
        int imageResourceId = -1;
        String name = "";

        if (intent.hasExtra("TEXT_RESOURCE_ID")) {
            textResourceId = bundle.getInt("TEXT_RESOURCE_ID");
        }
        if (intent.hasExtra("IMAGE_RESOURCE_ID")) {
            imageResourceId = bundle.getInt("IMAGE_RESOURCE_ID");
        }
        if (intent.hasExtra("NAME")) {
            name = bundle.getString("NAME");
        }
        Log.i("LOG",""+imageResourceId);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setContentView(R.layout.activity_operation_details_v5);
            imgCover = (ImageView) findViewById(R.id.imgCover);
            txtDesc = (TextView) findViewById(R.id.txtDesc);

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbar);
            setSupportActionBar(toolbar);
            toolbar.setTitle(name);
            collapsingToolbar.setCollapsedTitleTypeface(App.persianFont);
            collapsingToolbar.setExpandedTitleTypeface(App.persianFont);
            collapsingToolbar.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
            collapsingToolbar.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);

        } else {
            setContentView(R.layout.activity_operation_details);
            imgCover = (ImageView) findViewById(R.id.imgCover);
            txtDesc = (TextView) findViewById(R.id.txtDesc);
            TextView txtName = (TextView) findViewById(R.id.txtName);
            txtName.setText(name);
            txtName.setTypeface(App.persianFont);
        }
        imgCover.setImageResource(imageResourceId);
        txtDesc.setText(TextFile.readTextFile(App.getContext(), textResourceId));
        txtDesc.setTypeface(App.persianFont);
    }
}
