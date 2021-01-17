package activities;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

import java.io.File;
import java.util.ArrayList;

import adapters.AdapterLibraryRecycler;
import structures.StructBook;

public class ActivityLibrary extends ActivityParent {

    private boolean searchTime;
    RecyclerView lstBook;
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

    ArrayList<StructBook> books;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);

        searchTime = false;

        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        lstBook = (RecyclerView) findViewById(R.id.lstBooks);
        imgSearch = (ImageButton) findViewById(R.id.imgSearch);
        imgCancel = (ImageButton) findViewById(R.id.imgCancel);
        layoutSearch = (RelativeLayout) findViewById(R.id.layoutSearch);
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        edtSearch = (EditText)findViewById(R.id.edtSearch);

        edtSearch.setTypeface(App.persianFont);
        txtTitle.setTypeface(App.persianFont);

        books = new ArrayList<StructBook>();

        StructBook book = new StructBook();
        book.bookName = "همیشه تنها";
        book.bookFileName = "For Ever Alon.pdf";
        book.bookResourceId = R.raw.for_ever_alon;
        book.coverResourceId = R.drawable.for_ever_alon;
        book.pageNumber = 194;
        books.add(book);


        Animation slideIn = AnimationUtils.loadAnimation(App.getContext(),android.R.anim.slide_in_left);
        lstBook.setLayoutManager(new LinearLayoutManager(App.getContext()));
        AdapterLibraryRecycler adapter = new AdapterLibraryRecycler(books);
        lstBook.setAdapter(adapter);
        lstBook.setLayoutAnimation(new LayoutAnimationController(slideIn));

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
                lstBook.setLayoutManager(new LinearLayoutManager(App.getContext()));
                AdapterLibraryRecycler adapter = new AdapterLibraryRecycler(books);
                lstBook.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                edtSearch.setText("");
            }
        });

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ArrayList<StructBook> searchedBooks = new ArrayList<StructBook>();
                for (int index = 0; index < books.size(); index++) {
                    StructBook book = books.get(index);
                    if(book.bookName.contains(charSequence)){
                        searchedBooks.add(book);
                    }
                }
                lstBook.setLayoutManager(new LinearLayoutManager(App.getContext()));
                AdapterLibraryRecycler adapter = new AdapterLibraryRecycler(searchedBooks);
                lstBook.setAdapter(adapter);
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
            lstBook.setLayoutManager(new LinearLayoutManager(App.getContext()));
            AdapterLibraryRecycler adapter = new AdapterLibraryRecycler(books);
            lstBook.setAdapter(adapter);
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
