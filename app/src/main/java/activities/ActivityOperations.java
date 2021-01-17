package activities;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
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

import java.util.ArrayList;

import adapters.AdapterOperation;
import structures.StructOperation;

public class ActivityOperations extends ActivityParent {
    public static Toolbar toolbar;

    private boolean searchTime;
    GridView lstOperations;
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

    ArrayList<StructOperation> operations;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operations);
        searchTime = false;

        imgSearch = (ImageButton) findViewById(R.id.imgSearch);
        imgCancel = (ImageButton) findViewById(R.id.imgCancel);
        layoutSearch = (RelativeLayout) findViewById(R.id.layoutSearch);
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        edtSearch = (EditText) findViewById(R.id.edtSearch);

        edtSearch.setTypeface(App.persianFont);
        txtTitle.setTypeface(App.persianFont);

        operations = new ArrayList<StructOperation>();

        StructOperation operation = new StructOperation();
        operation.coverResourceId = R.drawable.operation1;
        operation.operationName = getResources().getString(R.string.operationTitle_1);
        operation.textResourceId = R.raw.operation_1;
        operations.add(operation);

        operation = new StructOperation();
        operation.coverResourceId = R.drawable.operation2;
        operation.operationName = getResources().getString(R.string.operationTitle_2);
        operation.textResourceId = R.raw.operation_2;
        operations.add(operation);

        operation = new StructOperation();
        operation.coverResourceId = R.drawable.operation3;
        operation.operationName = getResources().getString(R.string.operationTitle_3);
        operation.textResourceId = R.raw.operation_3;
        operations.add(operation);

        operation = new StructOperation();
        operation.coverResourceId = R.drawable.operation4;
        operation.operationName = getResources().getString(R.string.operationTitle_4);
        operation.textResourceId = R.raw.operation_4;
        operations.add(operation);

        operation = new StructOperation();
        operation.coverResourceId = R.drawable.operation5;
        operation.operationName = getResources().getString(R.string.operationTitle_5);
        operation.textResourceId = R.raw.operation_5;
        operations.add(operation);

        operation = new StructOperation();
        operation.coverResourceId = R.drawable.operation6;
        operation.operationName = getResources().getString(R.string.operationTitle_6);
        operation.textResourceId = 0;
        operations.add(operation);

        operation = new StructOperation();
        operation.coverResourceId = R.drawable.operation7;
        operation.operationName = getResources().getString(R.string.operationTitle_7);
        operation.textResourceId = 0;
        operations.add(operation);


        lstOperations = (GridView) findViewById(R.id.lstOperations);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            lstOperations.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
        AdapterOperation adapter = new AdapterOperation(App.getContext(), operations);
        lstOperations.setAdapter(adapter);

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
                AdapterOperation adapter = new AdapterOperation(App.getContext(), operations);
                lstOperations.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                edtSearch.setText("");
            }
        });

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ArrayList<StructOperation> searchedOperations = new ArrayList<StructOperation>();
                for (int index = 0; index < operations.size(); index++) {
                    StructOperation operation = operations.get(index);
                    if (operation.operationName.contains(charSequence)) {
                        searchedOperations.add(operation);
                    }
                }
                AdapterOperation adapter = new AdapterOperation(App.getContext(), searchedOperations);
                lstOperations.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
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
            AdapterOperation adapter = new AdapterOperation(App.getContext(), operations);
            lstOperations.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            edtSearch.setText("");
        }
    }
}

