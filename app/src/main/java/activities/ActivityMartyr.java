package activities;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.i3center.android.shohada.App;
import com.i3center.android.shohada.R;

import java.io.File;
import java.util.ArrayList;

import adapters.AdapterMartyr;
import adapters.AdapterMartyr;
import structures.StructMartyr;
import structures.StructMartyr;

public class ActivityMartyr extends ActivityParent {
    
    private boolean searchTime;

    GridView lstMartyr;
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

    ArrayList<StructMartyr> martyrs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_martyr);
        searchTime = false;
        imgSearch = (ImageButton) findViewById(R.id.imgSearch);
        imgCancel = (ImageButton) findViewById(R.id.imgCancel);
        layoutSearch = (RelativeLayout) findViewById(R.id.layoutSearch);
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        edtSearch = (EditText) findViewById(R.id.edtSearch);

        edtSearch.setTypeface(App.persianFont);
        txtTitle.setTypeface(App.persianFont);

        martyrs = new ArrayList<StructMartyr>();

        StructMartyr martyr = new StructMartyr();
        martyr.name = getResources().getString(R.string.seyyed_rasool_ashraf);
        martyr.imageResourceId = R.drawable.rasoul_ashraf;
        martyr.paperId = R.raw.rasool_ashraf;
        martyr.birthDate = "1343/08/20";
        martyr.testimonyDate = "1363/08/20";
        martyrs.add(martyr);

        martyr = new StructMartyr();
        martyr.name = getResources().getString(R.string.seyyed_mohammad_ebrahimi);
        martyr.imageResourceId = R.drawable.seyed_mohammad_ebrahimi;
        martyr.paperId = R.raw.mohammad_ebrahimi;
        martyr.birthDate = "1341/06/15";
        martyr.testimonyDate = "1365/11/08";
        martyrs.add(martyr);

        martyr = new StructMartyr();
        martyr.name = getResources().getString(R.string.mohammadali_amini);
        martyr.imageResourceId = R.drawable.mohammadali_amini;
        martyr.paperId = R.raw.mohammad_amini;
        martyr.birthDate = "1342/02/01";
        martyr.testimonyDate = "1363/03/15";
        martyrs.add(martyr);

        martyr = new StructMartyr();
        martyr.name = getResources().getString(R.string.mohammadali_rahnamoon);
        martyr.imageResourceId = R.drawable.mohammadali_rahnamoon;
        martyr.paperId = R.raw.mohammad_rahnamoon;
        martyr.birthDate = "1333/12/23";
        martyr.testimonyDate = "1362/12/16";
        martyrs.add(martyr);

        martyr = new StructMartyr();
        martyr.name = getResources().getString(R.string.mahmood_pagardkar);
        martyr.imageResourceId = R.drawable.mahmood_pagardkar;
        martyr.paperId = R.raw.mahmood_pagardkar;
        martyr.birthDate = "1344/11/09";
        martyr.testimonyDate = "1363/07/25";
        martyrs.add(martyr);




        lstMartyr = (GridView) findViewById(R.id.lstMartyr);
        AdapterMartyr adapter = new AdapterMartyr(App.getContext(), martyrs);
        lstMartyr.setAdapter(adapter);
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
                AdapterMartyr adapter = new AdapterMartyr(App.getContext(),martyrs);
                lstMartyr.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                edtSearch.setText("");
            }
        });

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ArrayList<StructMartyr> searchedMartyrs = new ArrayList<StructMartyr>();
                for (int index = 0; index < martyrs.size(); index++) {
                    StructMartyr martyr = martyrs.get(index);
                    if(martyr.name.contains(charSequence)){
                        searchedMartyrs.add(martyr);
                    }
                }
                AdapterMartyr adapter = new AdapterMartyr(App.getContext(),searchedMartyrs);
                lstMartyr.setAdapter(adapter);
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
            AdapterMartyr adapter = new AdapterMartyr(App.getContext(),martyrs);
            lstMartyr.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            edtSearch.setText("");
        }
    }

}
