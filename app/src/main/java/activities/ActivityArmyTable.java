package activities;

import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.i3center.android.shohada.App;
import com.i3center.android.shohada.R;

import java.util.ArrayList;

import adapters.AdapterArmyTable;
import helper.TextFile;
import structures.StructArmyTable;

public class ActivityArmyTable extends ActivityParent {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_army_table);

        ConstraintLayout root = (ConstraintLayout)findViewById(R.id.root);

        TextView txtDesc = (TextView)findViewById(R.id.txtDesc);
        txtDesc.setTypeface(App.persianFont);
        txtDesc.setText(TextFile.readTextFile(App.getContext(),R.raw.army_desc));

        root.setBackgroundResource(R.drawable.background_2);

        ListView lst = (ListView)findViewById(R.id.lst);
        ArrayList<StructArmyTable> armyes = new ArrayList<>();
        ArrayList<String> texts = new ArrayList<>();

        texts.add("ردیف");
        texts.add("یگان عمل کننده");
        texts.add("استان");
        texts.add("تعداد گردان");

        texts.add("1");
        texts.add("تیپ 17 علی ابن ابیطالب (ع)");
        texts.add("قم");
        texts.add("2");

        texts.add("2");
        texts.add("تیپ 14 امام حسین (ع)");
        texts.add("اصفهان");
        texts.add("1");

        texts.add("3");
        texts.add("تیپ 31 عاشورا");
        texts.add("آذربایجان شرقی");
        texts.add("3");

        texts.add("4");
        texts.add("تیپ 25 کربلا");
        texts.add("مازندران");
        texts.add("2");

        texts.add("5");
        texts.add("تیپ نجف اشرف");
        texts.add("نجف آباد اصفهان");
        texts.add("16");

        texts.add("6");
        texts.add("استان کردستان");
        texts.add("کردستان");
        texts.add("2");

        texts.add("7");
        texts.add("تیپ 2 ذوالفقار");
        texts.add("ارتش");
        texts.add("2");

        for(int i=0;i<texts.size();i+=4){
            StructArmyTable army = new StructArmyTable(texts.get(i),texts.get(i+1),texts.get(i+2),texts.get(i+3));
            armyes.add(army);
        }


        AdapterArmyTable adapter = new AdapterArmyTable(App.getContext(),armyes);
        lst.setAdapter(adapter);
    }
}
