package adapters;


import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import activities.ActivityVideoDetails;
import com.i3center.android.shohada.App;
import com.i3center.android.shohada.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import structures.StructVideo;

public class AdapterVideo extends ArrayAdapter<StructVideo> {

    private LayoutInflater inflater;

    public AdapterVideo(Context context, ArrayList<StructVideo> list) {
        super(context, 0, list);
        inflater = LayoutInflater.from(context);
    }

    public static class ViewHolder {
        CardView root;
        LinearLayout layoutDesc;
        TextView txtName;
        TextView txtTime;
        ImageView imgCover;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final StructVideo item = getItem(position);
        final ViewHolder viewHolder;
        if (convertView == null) { // there is empty space
            convertView = inflater.inflate(R.layout.struct_video, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.root = (CardView) convertView.findViewById(R.id.root);
            viewHolder.layoutDesc = (LinearLayout) convertView.findViewById(R.id.layoutDesc);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.txtName);
            viewHolder.txtTime = (TextView) convertView.findViewById(R.id.txtTime);
            viewHolder.imgCover = (ImageView) convertView.findViewById(R.id.imgCover);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            viewHolder.root.setBackgroundResource(R.drawable.struct_video_ripple);
        } else {
            viewHolder.root.setBackgroundResource(R.drawable.struct_video_selection);
        }


        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int a = 2;
                if(a==2){
                    Intent intent = new Intent(App.getCurrentActivity(), ActivityVideoDetails.class);
                    intent.putExtra("POSITION",position);
                    App.getCurrentActivity().startActivity(intent);
                    return;
                }
                final File file = new File(App.DIR_MEDIA + "/" + item.fileName);
                if (!file.exists()) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            int permissionCheck = ContextCompat.checkSelfPermission(App.getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
                            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                                App.requestPermission();
                                return;
                            }
                            try {
                                InputStream inputStream = App.getCurrentActivity().getResources().openRawResource(item.videoResourceId);
                                OutputStream outputStream = new FileOutputStream(file);
                                byte buffer[] = new byte[1024];
                                int len;
                                while ((len = inputStream.read(buffer)) > 0) {
                                    outputStream.write(buffer, 0, len);
                                }
                                outputStream.flush();
                                outputStream.close();
                                inputStream.close();

                                Intent target = new Intent(Intent.ACTION_VIEW);
                                target.setDataAndType(Uri.fromFile(file), "video/*");
                                Intent intent = Intent.createChooser(target, "Open Media");
                                App.getCurrentActivity().startActivity(intent);

                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                                Log.i("LOG", "A");
                            } catch (IOException e) {
                                e.printStackTrace();
                                Log.i("LOG", "B");
                            } catch (ActivityNotFoundException e) {
                                // Instruct the user to install a PDF reader here, or something
                                Log.i("LOG", "C");
                            } catch (Exception e) {
                                Log.i("LOG", "D");
                            }
                        }
                    }).start();

                }else{
                    Intent target = new Intent(Intent.ACTION_VIEW);
                    target.setDataAndType(Uri.fromFile(file), "video/*");
                    Intent intent = Intent.createChooser(target, "Open Media");
                    App.getCurrentActivity().startActivity(intent);
                }

            }
//            }
        };

        viewHolder.txtName.setTypeface(App.persianFont);
        viewHolder.txtTime.setTypeface(App.persianFont);
        viewHolder.root.setOnClickListener(clickListener);
        viewHolder.txtName.setText(item.persianName);
        viewHolder.txtTime.setText(convertToTimeFormat(item.time));
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
