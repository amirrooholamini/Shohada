package adapters;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.i3center.android.shohada.App;
import com.i3center.android.shohada.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import structures.StructBook;
import structures.StructMemorial;

public class AdapterMemorialRecycler extends RecyclerView.Adapter<AdapterMemorialRecycler.ViewHolder> {

    private ArrayList<StructMemorial> list;

    public AdapterMemorialRecycler(ArrayList<StructMemorial> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(App.getContext()).inflate(R.layout.struct_memorial, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final StructMemorial item = list.get(position);
        holder.txtCity.setTypeface(App.persianFont);
        holder.txtburialDate.setTypeface(App.persianFont);
        holder.txtBurialPlace.setTypeface(App.persianFont);
        holder.txtNumber.setTypeface(App.persianFont);

        holder.txtCity.setText(App.getContext().getResources().getString(R.string.city)+" : "+item.city);
        holder.txtNumber.setText("تعداد شهداء : " + item.number);
        holder.txtburialDate.setText("تاریخ خاکسپاری : " + item.burialDate);
        holder.txtBurialPlace.setText("محل خاکسپاری : " + item.burialPlace);
        holder.imgCover.setImageResource(item.coverId);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgCover;
        TextView txtCity;
        TextView txtNumber;
        TextView txtburialDate;
        TextView txtBurialPlace;
        ViewGroup root;

        public ViewHolder(View view) {
            super(view);
            root = (ViewGroup) view.findViewById(R.id.root);
            imgCover = (ImageView) view.findViewById(R.id.imgCover);
            txtCity = (TextView) view.findViewById(R.id.txtCity);
            txtburialDate = (TextView) view.findViewById(R.id.txtburialDate);
            txtBurialPlace = (TextView) view.findViewById(R.id.txtBurialPlace);
            txtNumber = (TextView) view.findViewById(R.id.txtNumber);
        }
    }
}
