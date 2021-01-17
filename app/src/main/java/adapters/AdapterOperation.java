package adapters;


import android.content.Context;
import android.content.Intent;
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

import activities.ActivityArmyTable;
import activities.ActivityOperationDetails;
import activities.ActivityOperationTable;
import structures.StructOperation;

public class AdapterOperation extends ArrayAdapter<StructOperation> {

    private LayoutInflater inflater;

    public AdapterOperation(Context context, ArrayList<StructOperation> list) {
        super(context, 0, list);
        inflater = LayoutInflater.from(context);
    }

    public static class ViewHolder {
        CardView root;
        TextView txtName;
        ImageView imgCover;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final StructOperation item = getItem(position);
        final ViewHolder viewHolder;
        if (convertView == null) { // there is empty space
            convertView = inflater.inflate(R.layout.struct_operation, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.root = (CardView) convertView.findViewById(R.id.root);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.txtName);
            viewHolder.imgCover = (ImageView) convertView.findViewById(R.id.imgCover);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position == 5) {
                    Intent intent = new Intent(App.getCurrentActivity(), ActivityArmyTable.class);
                    App.getCurrentActivity().startActivity(intent);
                } else if(position == 6) {
                    Intent intent = new Intent(App.getCurrentActivity(), ActivityOperationTable.class);
                    App.getCurrentActivity().startActivity(intent);
                }else{
                    Intent intent = new Intent(App.getCurrentActivity(), ActivityOperationDetails.class);
                    intent.putExtra("TEXT_RESOURCE_ID", item.textResourceId);
                    intent.putExtra("IMAGE_RESOURCE_ID", item.coverResourceId);
                    intent.putExtra("NAME", item.operationName);
                    App.getCurrentActivity().startActivity(intent);
                }
            }
        };
        viewHolder.root.setOnClickListener(clickListener);


        viewHolder.txtName.setTypeface(App.persianFont);
        viewHolder.txtName.setText(item.operationName);
        viewHolder.imgCover.setImageResource(item.coverResourceId);
        viewHolder.imgCover.setAlpha(0.5f);


        return convertView;
    }

    private String convertToTimeFormat(int time) {
        String stringTime = "";
        int minutes = time / 60;
        int seconds = time % 60;
        if (minutes < 10) {
            stringTime += "0";
        }
        stringTime += minutes;
        stringTime += ":";

        if (seconds < 10) {
            stringTime += "0";
        }
        stringTime += seconds;
        return stringTime;
    }
}
