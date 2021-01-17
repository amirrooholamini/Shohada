package adapters;


import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.i3center.android.shohada.App;
import com.i3center.android.shohada.R;

import java.util.ArrayList;

import activities.ActivityMartyrDetails;
import structures.StructMartyr;

public class AdapterMartyr extends ArrayAdapter<StructMartyr> {

    private LayoutInflater inflater;

    public AdapterMartyr(Context context, ArrayList<StructMartyr> list) {
        super(context, 0, list);
        inflater = LayoutInflater.from(context);
    }

    public static class ViewHolder {
        CardView root;
        TextView txtName;
        TextView txt1;
        TextView txt2;
        TextView txtBirthDate;
        TextView txtTestimonyDate;
        ImageView imgCover;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final StructMartyr item = getItem(position);
        final ViewHolder viewHolder;
        if (convertView == null) { // there is empty space
            convertView = inflater.inflate(R.layout.struct_martyr, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.root = (CardView) convertView.findViewById(R.id.root);
            viewHolder.imgCover = (ImageView) convertView.findViewById(R.id.imgCover);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.txtName);
            viewHolder.txt1 = (TextView) convertView.findViewById(R.id.txt1);
            viewHolder.txt2 = (TextView) convertView.findViewById(R.id.txt2);
            viewHolder.txtBirthDate = (TextView) convertView.findViewById(R.id.txtBirthDate);
            viewHolder.txtTestimonyDate = (TextView) convertView.findViewById(R.id.txtTestimonyDate);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            viewHolder.root.setBackgroundResource(R.drawable.struct_video_ripple);
        } else {
            viewHolder.root.setBackgroundResource(R.drawable.struct_video_selection);
        }


        viewHolder.txtName.setTypeface(App.persianFont);
        viewHolder.txt1.setTypeface(App.persianFont);
        viewHolder.txt2.setTypeface(App.persianFont);
        viewHolder.txtBirthDate.setTypeface(App.persianFont);
        viewHolder.txtTestimonyDate.setTypeface(App.persianFont);

        viewHolder.imgCover.setImageResource(item.imageResourceId);
        viewHolder.txtName.setText(item.name);
        viewHolder.txt1.setText(App.getContext().getResources().getString(R.string.birthDate));
        viewHolder.txt2.setText(App.getContext().getResources().getString(R.string.testimonyDate));
        viewHolder.txtBirthDate.setText(item.birthDate);
        viewHolder.txtTestimonyDate.setText(item.testimonyDate);
        viewHolder.imgCover.setAlpha(0.5f);

        viewHolder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(App.getCurrentActivity(), ActivityMartyrDetails.class);
                intent.putExtra("NAME",item.name);
                intent.putExtra("COVER_ID",item.imageResourceId);
                intent.putExtra("PAPER_ID",item.paperId);
                App.getCurrentActivity().startActivity(intent);
            }
        });


        return convertView;
    }

}
