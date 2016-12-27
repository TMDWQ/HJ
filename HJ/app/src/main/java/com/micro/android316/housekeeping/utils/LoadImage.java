package com.micro.android316.housekeeping.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.micro.android316.housekeeping.CustomControls.HeadImage;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by Administrator on 2016/12/10.
 */

public class LoadImage extends AsyncTask<String,Void,Bitmap>{

    private ImageView imageView;
    private String url;
    private Context context;
    private HeadImage headImage;



    private LoadImage(){}

    public static void Load(ImageView imageView, String url, Context context) {
        LoadImage loadImage=new LoadImage();
        loadImage.imageView = imageView;
        loadImage.url = url;
        loadImage.context = context;
        loadImage.imageView.setTag(url);
        loadImage.execute(url);
    }
    public static void Load(HeadImage imageView, String url, Context context) {
        LoadImage loadImage=new LoadImage();
        loadImage.headImage = imageView;
        loadImage.url = url;
        loadImage.context = context;
        loadImage.headImage.setTag(url);
        loadImage.execute(url);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        if(bitmap!=null)
        {
            if (imageView!=null && imageView.getTag().equals(url)) {
                imageView.setImageBitmap(bitmap);
            }
            if(headImage!=null){
                headImage.setBitmap(bitmap);
            }
        }
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        Bitmap bitmap=null;
        InputStream is=null;
        try {
            is=new URL(url).openConnection().getInputStream();
            InputStream iss=SaveInFile.get(context,url);
            if(AppFinal.memery.get(url)!=null){
                bitmap=AppFinal.memery.get(url);
                Log.i("hhh","bitmap1="+bitmap+"");
            }else if(iss!=null){
                bitmap= BitmapFactory.decodeStream(iss);
                Log.i("hhh","bitmap2="+bitmap+"");
            }else {
                SaveInFile.save(context,is,url);
                iss=SaveInFile.get(context,url);
                bitmap=BitmapFactory.decodeStream(iss);
                Log.i("hhh","bitmap3="+bitmap+"");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return bitmap;
    }
}
