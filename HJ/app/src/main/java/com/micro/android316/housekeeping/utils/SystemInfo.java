package com.micro.android316.housekeeping.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2016/12/9.
 */

public class SystemInfo {

    public static float getWidth(Context context){
        SharedPreferences sharedPreferences=context.getSharedPreferences("SystemInfo", Context.MODE_PRIVATE);
        return sharedPreferences.getFloat("width",0);
    }
    public static float getHeight(Context context){
        SharedPreferences sharedPreferences=context.getSharedPreferences("SystemInfo", Context.MODE_PRIVATE);
        return sharedPreferences.getFloat("height",0);
    }
    public static void save(Activity activity){
        SharedPreferences sharedPreferences=activity.getSharedPreferences("SystemInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putFloat("width",activity.getWindowManager().getDefaultDisplay().getWidth());
        editor.putFloat("height",activity.getWindowManager().getDefaultDisplay().getHeight());
        editor.commit();
    }

}
