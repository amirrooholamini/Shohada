package activities;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.i3center.android.shohada.App;
import com.i3center.android.shohada.R;

import java.util.ArrayList;

import adapters.AdapterLibraryRecycler;
import adapters.AdapterMemorialRecycler;
import structures.StructBook;
import structures.StructMemorial;

public class ActivityMemorial extends ActivityParent {
    private boolean searchTime;
    RecyclerView lstMemorials;
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

    ArrayList<StructMemorial> memorials;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memorial);

        searchTime = false;

        lstMemorials = (RecyclerView) findViewById(R.id.lstMemorials);
        imgSearch = (ImageButton) findViewById(R.id.imgSearch);
        imgCancel = (ImageButton) findViewById(R.id.imgCancel);
        layoutSearch = (RelativeLayout) findViewById(R.id.layoutSearch);
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        edtSearch = (EditText)findViewById(R.id.edtSearch);

        edtSearch.setTypeface(App.persianFont);
        txtTitle.setTypeface(App.persianFont);

        memorials = new ArrayList<StructMemorial>();

        StructMemorial memorial = new StructMemorial();

        memorial.city = "یزد";
        memorial.coverId = R.drawable.meyboduniversity;
        memorial.burialPlace = "میبد - دانشگاه میبد";
        memorial.burialDate = "1383/04/21";
        memorial.number = 3;
        memorials.add(memorial);

        memorial = new StructMemorial();
        memorial.coverId = R.drawable.bafghuniversity;
        memorial.city = "یزد";
        memorial.burialPlace = "بافق - جاده ارتباطي به سنگ آهن - دانشگاه آزاد اسلامي بافق";
        memorial.burialDate = "1384/01/19";
        memorial.number = 3;
        memorials.add(memorial);

        memorial = new StructMemorial();
        memorial.coverId = R.drawable.mehriz;
        memorial.city = "یزد";
        memorial.burialPlace = "مهریز - بلوار اصلي ورودي شهر";
        memorial.burialDate = "1380/06/08";
        memorial.number = 3;
        memorials.add(memorial);

        Animation slideIn = AnimationUtils.loadAnimation(App.getContext(),android.R.anim.slide_in_left);
        lstMemorials.setLayoutManager(new LinearLayoutManager(App.getContext()));
        AdapterMemorialRecycler adapter = new AdapterMemorialRecycler(memorials);
        lstMemorials.setAdapter(adapter);
        lstMemorials.setLayoutAnimation(new LayoutAnimationController(slideIn));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            imgSearch.setBackgroundResource(R.drawable.search_button_ripple);
        } else {
            imgSearch.setBackgroundResource(R.drawable.search_button_selection);
        }

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
                lstMemorials.setLayoutManager(new LinearLayoutManager(App.getContext()));
                AdapterMemorialRecycler adapter = new AdapterMemorialRecycler(memorials);
                lstMemorials.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                edtSearch.setText("");
            }
        });

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ArrayList<StructMemorial> searchedMemorials = new ArrayList<StructMemorial>();
                for (int index = 0; index < memorials.size(); index++) {
                    StructMemorial memorial = memorials.get(index);
                    if(memorial.city.contains(charSequence)||memorial.burialPlace.contains(charSequence)){
                        searchedMemorials.add(memorial);
                    }
                }
                lstMemorials.setLayoutManager(new LinearLayoutManager(App.getContext()));
                AdapterMemorialRecycler adapter = new AdapterMemorialRecycler(searchedMemorials);
                lstMemorials.setAdapter(adapter);
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
            lstMemorials.setLayoutManager(new LinearLayoutManager(App.getContext()));
            AdapterMemorialRecycler adapter = new AdapterMemorialRecycler(memorials);
            lstMemorials.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            edtSearch.setText("");
        }
    }
}
