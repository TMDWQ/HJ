package com.micro.android316.housekeeping.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by Administrator on 2016/12/16.
 */

public class LoginMessgae {

    public static void save(Context context,String tel,String passwd){
        SharedPreferences preferences=context.getSharedPreferences("login",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("password",passwd);
        editor.putString("tel",tel);
        editor.commit();
    }

    public static String getTel(Context context){
        Log.i("hhh",context+"");
        SharedPreferences preferences=context.getSharedPreferences("login",Context.MODE_PRIVATE);
        return preferences.getString("tel",null);
    }

    public static String getpassWord(Context context){
        SharedPreferences preferences=context.getSharedPreferences("login",Context.MODE_PRIVATE);
        return preferences.getString("password",null);
    }

    public static void saveStateForRemenberPassWord(Context context,Boolean b){
        SharedPreferences preferences=context.getSharedPreferences("login",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putBoolean("is",b);
        editor.commit();
    }

    public static boolean getSateForRemenberPassWord(Context context){
        SharedPreferences preferences=context.getSharedPreferences("login",Context.MODE_PRIVATE);
        return  preferences.getBoolean("is",false);
    }

    public static void saveToken(Context context,String token){
        SharedPreferences preferences=context.getSharedPreferences("login",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("token",token);
        editor.commit();
    }

    public static String getToken(Context context){
        SharedPreferences preferences=context.getSharedPreferences("login",Context.MODE_PRIVATE);
        return preferences.getString("token",null);
    }

    public static void saveAddress(Context context,String address){
        SharedPreferences preferences=context.getSharedPreferences("login",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("address",address);
        editor.commit();
    }

    public static String getAddress(Context context){
        SharedPreferences preferences=context.getSharedPreferences("login",Context.MODE_PRIVATE);
        return preferences.getString("address","");
    }


    public static void saveHead(Context context,String address){
        SharedPreferences preferences=context.getSharedPreferences("login",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("head",address);
        editor.commit();
    }

    public static String getHead(Context context){
        SharedPreferences preferences=context.getSharedPreferences("login",Context.MODE_PRIVATE);
        return preferences.getString("head","");
    }

}
