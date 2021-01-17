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

public class AdapterLibraryRecycler extends RecyclerView.Adapter<AdapterLibraryRecycler.ViewHolder> {

    private ArrayList<StructBook> list;

    public AdapterLibraryRecycler(ArrayList<StructBook> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(App.getContext()).inflate(R.layout.struct_book, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final StructBook item = list.get(position);
        holder.txtBookName.setTypeface(App.persianFont);
        holder.txtPageNumber.setTypeface(App.persianFont);
        holder.txtBookName.setText(item.bookName);
        holder.txtPageNumber.setText("تعداد صفحات : " + item.pageNumber);
        holder.imgBookCover.setImageResource(item.coverResourceId);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //   holder.root.setBackgroundResource(R.drawable.search_button_ripple);
        } else {
            //   holder.root.setBackgroundResource(R.drawable.search_button_selection);
        }

        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final File file = new File(App.DIR_PDF + "/"+item.bookFileName);
                if(!file.exists()){
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            int permissionCheck = ContextCompat.checkSelfPermission(App.getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
                            if(permissionCheck != PackageManager.PERMISSION_GRANTED){
                                App.requestPermission();
                                return;
                            }
                            try {
                                InputStream inputStream = App.getCurrentActivity().getResources().openRawResource(item.bookResourceId);
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
                                target.setDataAndType(uri, "application/pdf");
                                Intent intent = Intent.createChooser(target, "Open File");
                                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                App.getCurrentActivity().startActivity(intent);

//                                Intent target = new Intent(Intent.ACTION_VIEW);
//                                target.setDataAndType(Uri.fromFile(file), "application/pdf");
//                                Intent intent = Intent.createChooser(target, "Open File");
//                                App.getCurrentActivity().startActivity(intent);

                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                                Log.i("LOG", "A");
                            } catch (IOException e) {
                                e.printStackTrace();
                                Log.i("LOG", "B");
                            } catch (ActivityNotFoundException e) {
                                // Instruct the user to install a PDF reader here, or something
                                Log.i("LOG", "C");
                            }catch (Exception e){
                                Log.i("LOG", "D");
                            }
                        }
                    }).start();
                }else{
                    Intent target = new Intent(Intent.ACTION_VIEW);
                    target.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    //target.setDataAndType(Uri.fromFile(file), "audio/*");
                    Uri uri = FileProvider.getUriForFile(App.getContext(), App.getContext().getApplicationContext().getPackageName() + ".provider", file);
                    target.setDataAndType(uri, "application/pdf");
                    Intent intent = Intent.createChooser(target, "Open File");
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
        ImageView imgBookCover;
        TextView txtBookName;
        TextView txtPageNumber;
        ViewGroup root;

        public ViewHolder(View view) {
            super(view);
            root = (ViewGroup) view.findViewById(R.id.root);
            imgBookCover = (ImageView) view.findViewById(R.id.imgCover);
            txtBookName = (TextView) view.findViewById(R.id.txtBookName);
            txtPageNumber = (TextView) view.findViewById(R.id.txtPageNumber);
        }
    }
}
