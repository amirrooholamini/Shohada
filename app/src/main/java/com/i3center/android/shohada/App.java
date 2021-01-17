package com.i3center.android.shohada;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.res.ResourcesCompat;

import java.io.File;
import java.util.ArrayList;

import structures.StructArmyTable;
import structures.StructOperationTable;
import structures.StructSong;
import structures.StructVideo;

public class App extends Application {
    private static Context context;
    private static Handler handler;
    public static Typeface persianFont;
    public static Typeface nastaliq;
    private static String packageName;
    private static Activity currentActivity;
    public static final String DIR_SDCARD = Environment.getExternalStorageDirectory().getAbsolutePath();
    public static final String DIR_APP = DIR_SDCARD+"/Shohada/";
    public static final String DIR_PDF = DIR_APP+"/PDF/";
    public static final String DIR_MEDIA = DIR_APP+"/Media/";
    public static final int WES_PermissionCode = 1; //Write External Storage Permission Code
    public static ArrayList<StructSong> songs = new ArrayList<StructSong>();
    public static ArrayList<StructVideo> videos = new ArrayList<StructVideo>();
    public static ArrayList<StructOperationTable> operations = new ArrayList<>();
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        packageName = getPackageName();
        handler = new Handler();
        persianFont = ResourcesCompat.getFont(context, R.font.persian);
        nastaliq = ResourcesCompat.getFont(context, R.font.nastaliq);
        new File(DIR_PDF).mkdirs();
        new File(DIR_MEDIA).mkdirs();
    }

    public static Context getContext(){
        if(currentActivity !=null){
            return currentActivity;
        }
        return context;
    }

    public static String getAppPackageName(){
        return packageName;
    }

    public static Handler getUIHandler(){
        return handler;
    }


    public static Activity getCurrentActivity(){
        return currentActivity;
    }

    public static void setCurrentActivity(Activity activity){
        currentActivity = activity;
    }

    public static void requestPermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(
                App.currentActivity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE))
        {
            new AlertDialog.Builder(App.currentActivity)
                    .setTitle(R.string.permission_title)
                    .setMessage(R.string.permission_message)
                    .setPositiveButton(R.string.permission_ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(
                                        App.currentActivity,
                                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                        WES_PermissionCode
                                );
                        }
                    })
                    .setNegativeButton(R.string.permission_cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).show();
        }else{
            ActivityCompat.requestPermissions(
                    App.currentActivity,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    WES_PermissionCode
            );
        }
    }

}
