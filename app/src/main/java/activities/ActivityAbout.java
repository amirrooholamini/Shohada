package activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.i3center.android.shohada.App;
import com.i3center.android.shohada.R;

import helper.TextFile;

public class ActivityAbout extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        final String phoneNumber = "03538273991";
        final String smsNumber = "30001318";
        final String web1 = "http://www.defapress.ir";
        final String web2 = "http://www.khatshekanan.ir";
        final String web3 = "http://www.shohada-yazd.ir";
        final String web4 = "http://www.shohada-yazd.ir";

        TextView txtDesc = (TextView)findViewById(R.id.txtDesc);
        TextView txtCall = (TextView)findViewById(R.id.txtCall);
        TextView txtSms = (TextView)findViewById(R.id.txtSms);
        TextView txtFax = (TextView)findViewById(R.id.txtFax);
        TextView txtPostalCode = (TextView)findViewById(R.id.txtPostalCode);
        TextView txtTelegram = (TextView)findViewById(R.id.txtTelegram);
        TextView txtSoroush = (TextView)findViewById(R.id.txtSoroush);
        TextView txtWeb1 = (TextView)findViewById(R.id.txtWeb1);
        TextView txtWeb2 = (TextView)findViewById(R.id.txtWeb2);
        TextView txtWeb3 = (TextView)findViewById(R.id.txtWeb3);
        TextView txtWeb4 = (TextView)findViewById(R.id.txtWeb4);

        String enter = "\n";
        txtWeb1.setText(getString(R.string.web1)+enter+"www.defapress.ir");
        txtWeb2.setText(getString(R.string.web2)+enter+"www.khatshekanan.ir");
        txtWeb3.setText(getString(R.string.web3)+enter+"www.shohada-yazd.ir");
        txtWeb4.setText(getString(R.string.web4)+enter+"www.yazd.bonyaddefa.ir");


        txtDesc.setTextSize(TypedValue.COMPLEX_UNIT_SP, ((getResources().getDimension(R.dimen.textSizeSmall)+2) / getResources().getDisplayMetrics().density));

       // int mediumTextSize = (int) ((getResources().getDimension(R.dimen.textSize)+getResources().getDimension(R.dimen.textSizeSmall))/2);


        txtCall.setTypeface(App.persianFont);
        txtSms.setTypeface(App.persianFont);
        txtFax.setTypeface(App.persianFont);
        txtPostalCode.setTypeface(App.persianFont);
        txtTelegram.setTypeface(App.persianFont);
        txtSoroush.setTypeface(App.persianFont);
        txtWeb1.setTypeface(App.persianFont);
        txtWeb2.setTypeface(App.persianFont);
        txtWeb3.setTypeface(App.persianFont);
        txtWeb4.setTypeface(App.persianFont);
        txtDesc.setTypeface(App.persianFont);

        String about = TextFile.readTextFile(App.getContext(),R.raw.about);
        String address = TextFile.readTextFile(App.getContext(),R.raw.address);
       //String conncetions = getString(R.string.connections);
        String description = about+address;
        txtDesc.setText(description);

        txtCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+phoneNumber));
                startActivity(intent);
            }
        });

        txtSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", smsNumber, null));
                //intent.putExtra("sms_body", "Here you can set the SMS text to be sent");
                startActivity(intent);
            }
        });

        txtWeb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(web1));
                startActivity(browserIntent);
            }
        });
        txtWeb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(web2));
                startActivity(browserIntent);
            }
        });
        txtWeb3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(web3));
                startActivity(browserIntent);
            }
        });
        txtWeb4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(web4));
                startActivity(browserIntent);
            }
        });

    }
}
