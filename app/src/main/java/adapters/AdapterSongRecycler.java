package adapters;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import activities.ActivitySongDetails;
import com.i3center.android.shohada.App;
import com.i3center.android.shohada.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import structures.StructSong;

public class AdapterSongRecycler extends RecyclerView.Adapter<AdapterSongRecycler.ViewHolder> {
    private ViewHolder previousHolder = null;
    private ArrayList<StructSong> list;

    public AdapterSongRecycler(ArrayList<StructSong> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(App.getContext()).inflate(R.layout.struct_song, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final StructSong item = list.get(position);
        if (item.duration == 0) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    MediaPlayer player = MediaPlayer.create(App.getContext(), item.songResourceId);
                    if (player != null) {
                        item.duration = player.getDuration();
                        App.getUIHandler().post(new Runnable() {
                            @Override
                            public void run() {
                                App.songs.get(position).duration = item.duration;
                                holder.txtDuration.setText(convertToTimeFormat(item.duration / 1000));
                            }
                        });


                    }
                }
            }).start();

        }else{
            holder.txtDuration.setText(convertToTimeFormat(item.duration / 1000));
        }

        holder.txtFileName.setText(item.persianName);
        holder.txtCoverName.setTypeface(App.persianFont);
        holder.txtFileName.setTypeface(App.persianFont);
        holder.txtDuration.setTypeface(App.persianFont);
        holder.txtCoverName.setText(item.persianName.charAt(0) + "");
        if (item.coverResourceId == 0) {
            holder.imgCover.setVisibility(View.GONE);
            holder.txtCoverName.setVisibility(View.VISIBLE);

        } else {
            holder.txtCoverName.setVisibility(View.GONE);
            holder.imgCover.setVisibility(View.VISIBLE);
            holder.imgCover.setImageResource(item.coverResourceId);
        }

        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int a = 2;
                if(a==2){
                    Intent intent = new Intent(App.getCurrentActivity(), ActivitySongDetails.class);
                    intent.putExtra("POSITION",position);
                    App.getCurrentActivity().startActivity(intent);
                    return;
                }
                final File file = new File(App.DIR_MEDIA + "/" + item.fileName);
                Log.i("LOG", "1");
                if (!file.exists()) {
                    Log.i("LOG", "2");
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            int permissionCheck = ContextCompat.checkSelfPermission(App.getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
                            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                                App.requestPermission();
                                return;
                            }
                            try {
                                InputStream inputStream = App.getCurrentActivity().getResources().openRawResource(item.songResourceId);
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
                                target.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                //target.setDataAndType(Uri.fromFile(file), "audio/*");
                                Uri uri = FileProvider.getUriForFile(App.getContext(), App.getContext().getApplicationContext().getPackageName() + ".provider", file);
                                target.setDataAndType(uri, "audio/*");
                                Intent intent = Intent.createChooser(target, "Open Media");
                                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
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

                } else {
                    Intent target = new Intent(Intent.ACTION_VIEW);
                    target.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    //target.setDataAndType(Uri.fromFile(file), "audio/*");
                    Uri uri = FileProvider.getUriForFile(App.getContext(), App.getContext().getApplicationContext().getPackageName() + ".provider", file);
                    target.setDataAndType(uri, "audio/*");
                    Intent intent = Intent.createChooser(target, "Open Media");
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    App.getCurrentActivity().startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgCover;
        TextView txtFileName;
        TextView txtDuration;
        TextView txtCoverName;
        ViewGroup root;
        SeekBar seekBar;

        public ViewHolder(View view) {
            super(view);
            root = (ViewGroup) view.findViewById(R.id.root);
            imgCover = (ImageView) view.findViewById(R.id.imgCover);
            txtFileName = (TextView) view.findViewById(R.id.txtFileName);
            txtCoverName = (TextView) view.findViewById(R.id.txtCoverName);
            txtDuration = (TextView) view.findViewById(R.id.txtDuration);
        }
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
