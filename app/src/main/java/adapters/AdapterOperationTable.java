package adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.i3center.android.shohada.App;
import com.i3center.android.shohada.R;

import java.util.ArrayList;

import structures.StructArmyTable;
import structures.StructOperation;
import structures.StructOperationTable;

public class AdapterOperationTable extends ArrayAdapter<StructOperationTable> {
    private LayoutInflater inflater;

    public AdapterOperationTable(Context context, ArrayList<StructOperationTable> list) {
        super(context, 0, list);
        inflater = LayoutInflater.from(context);
    }

    public static class ViewHolder {
        TextView txt1;
        TextView txt2;
        TextView txt3;
        TextView txt4;
        TextView txt5;
        TextView txt6;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final StructOperationTable item = getItem(position);
        final ViewHolder viewHolder;
        if (convertView == null) { // there is empty space
            convertView = inflater.inflate(R.layout.struct_operation_table, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.txt1 = (TextView) convertView.findViewById(R.id.txt1);
            viewHolder.txt2 = (TextView) convertView.findViewById(R.id.txt2);
            viewHolder.txt3 = (TextView) convertView.findViewById(R.id.txt3);
            viewHolder.txt4 = (TextView) convertView.findViewById(R.id.txt4);
            viewHolder.txt5 = (TextView) convertView.findViewById(R.id.txt5);
            viewHolder.txt6 = (TextView) convertView.findViewById(R.id.txt6);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.txt1.setTypeface(App.persianFont);
        viewHolder.txt2.setTypeface(App.persianFont);
        viewHolder.txt3.setTypeface(App.persianFont);
        viewHolder.txt4.setTypeface(App.persianFont);
        viewHolder.txt5.setTypeface(App.persianFont);
        viewHolder.txt6.setTypeface(App.persianFont);

        viewHolder.txt1.setText(item.text1);
        viewHolder.txt2.setText(item.text2);
        viewHolder.txt3.setText(item.text3);
        viewHolder.txt4.setText(item.text4);
        viewHolder.txt5.setText(item.text5);
        viewHolder.txt6.setText(item.text6);

        return convertView;
    }

}
