package activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.i3center.android.shohada.App;
import com.i3center.android.shohada.R;

import java.util.ArrayList;

import activities.ActivityParent;
import adapters.AdapterArmyTable;
import adapters.AdapterOperationTable;
import helper.TextFile;
import structures.StructArmyTable;
import structures.StructOperationTable;

public class ActivityOperationTable extends ActivityParent {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operation_table);

        ConstraintLayout root = (ConstraintLayout)findViewById(R.id.root);

        TextView txtDesc = (TextView)findViewById(R.id.txtDesc);
        txtDesc.setTypeface(App.persianFont);
        txtDesc.setText(TextFile.readTextFile(App.getContext(),R.raw.operation_table_desc));

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.background_2);
        bitmap = rotateBitmap(bitmap,90);
        BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), bitmap);
        root.setBackground(bitmapDrawable);

        ListView lst = (ListView)findViewById(R.id.lst);


        ArrayList<String> texts = new ArrayList<>();
        texts.add("ردیف");
        texts.add("نام عملیات");
        texts.add("رمز عملیات");
        texts.add("منطقه عملیات");
        texts.add("گردان های عمل کننده");
        texts.add("تحت یگان");

        texts.add("1");
        texts.add("طریق القدس");
        texts.add("یا حسین(ع)");
        texts.add("منطقه عمومی");
        texts.add("گردان امام علی (ع)");
        texts.add("تیپ عاشورا");

        texts.add("2");
        texts.add("مطلع الفجر");
        texts.add("یا مهدی ادرکنی (عج)");
        texts.add("گیلان غرب و سرپل ذهاب");
        texts.add("یک گردان (بفرماندهی آواره) گروهان بی سیم");
        texts.add("تیپ ذوالفقار\n" +
                "تیپ ذوالفقار");

        texts.add("3");
        texts.add("فتح المبین");
        texts.add("یا زهرا (س)");
        texts.add("غرب دزفول و شوش و منطقه غرب رودخانه کرخه");
        texts.add("یک گردان از شهر میبد\n" +
                "گروهان پاسداران");
        texts.add("تیپ 17 علی ابن ابیطالب تیپ کربلا");


        texts.add("4");
        texts.add("بیت المقدس");
        texts.add("یا علی بن ابیطالب (ع)");
        texts.add("غرب کارون، جنوب غربی اهواز شمال خرمشهر");
        texts.add("گردان امام علی (ع)،گردان زرهی،گردان کربلا،گردان امام رضا (ع)،یک گروهان");
        texts.add("تیپ عاشورا \n" +
                "تیپ عاشورا\n" +
                "تیپ کربلا\n" +
                "تیپ 17 قم \n" +
                "تیپ امام حسین (ع)");



        texts.add("5");
        texts.add("رمضان");
        texts.add("یا صاحب الزمان ادرکنی");
        texts.add("شوق بصره");
        texts.add("گردان امام حسین (ع)\n" +
                "گردان امام علی (ع)");
        texts.add("تیپ کربلا\n" +
                "تیپ نجف");

        texts.add("6");
        texts.add("محرم");
        texts.add("یا زینب (س)");
        texts.add("جنوب شرق دهلران");
        texts.add("گردان امام محمد باقر (ع)\n" +
                "گردان 2 شهید صدوقی \n" +
                "گردان 11\n" +
                "گردان مستقل شهید صدوقی");
        texts.add("گردان امام محمد باقر (ع)\n" +
                "گردان 2 شهید صدوقی \n" +
                "گردان 11\n" +
                "گردان مستقل شهید صدوقی");

        texts.add("7");
        texts.add("والفجر مقدماتی");
        texts.add("یا الله یاالله یا الله");
        texts.add("فکه");
        texts.add("گردان امام علی (ع)\n" +
                "گردان مسلم بن عقیل\n" +
                "گردان قمر بنی هاشم (ع)\n" +
                "گردان امام رضا (ع)\n" +
                "گروهان شهید صدوقی");
        texts.add("لشکر 8 نجف\n" +
                "لشکر 8 نجف\n" +
                "لشکر 8 نجف\n" +
                "لشکر 8 نجف\n" +
                "لشکر 8 نجف");

        texts.add("8");
        texts.add("والفجر 2");
        texts.add("یا الله یا الله یا الله");
        texts.add("حاج عمران");
        texts.add("گردان امام علی (ع)\n" +
                "گردان امام حسن (ع)\n" +
                "گردان امام حسین (ع)");
        texts.add("لشکر 8 نجف\n" +
                "لشکر 8 نجف\n" +
                "لشکر 8 نجف");

        texts.add("9");
        texts.add("والفجر 4");
        texts.add("یا الله یا الله یا الله");
        texts.add("دره شیلر در شمال مریوان و پنجوین");
        texts.add("گردان امام علی (ع)\n" +
                "گردان خاتم الانبیاء (ص)\n" +
                "1 گروهان");
        texts.add("لشکر 8 نجف\n" +
                "لشکر 8 نجف\n" +
                "لشکر 8 نجف");

        for(int i=0;i<texts.size();i+=6){
            StructOperationTable operation = new StructOperationTable(
                   texts.get(i),
                   texts.get(i+1),
                   texts.get(i+2),
                   texts.get(i+3),
                   texts.get(i+4),
                   texts.get(i+5)
            );
            App.operations.add(operation);
        }




        AdapterOperationTable adapter = new AdapterOperationTable(App.getContext(),App.operations);
        lst.setAdapter(adapter);
    }

    private Bitmap rotateBitmap(Bitmap source, float angle)
    {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }
}
