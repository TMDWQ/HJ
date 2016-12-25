package com.micro.android316.housekeeping.utils;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2016/11/28.
 */

public class GpsUtil {
    private Context context;
    private Location location;

    private Handler handler;

    public GpsUtil(Context context, Handler handler) {
        this.context = context;
        this.handler = handler;
    }

    private double jing, wei;
    private LocationManager locationManager;

    public void getAddressInfo() {
        if(!isOpen(context)){openGPS(context);}
        getGPSConfi();
        if(location==null){
            return;
        }
        new Thread() {
            public void run() {
                getHttp();
            }
        }.start();

    }


    public void getHttp() {
        HttpURLConnection connection = null;
        String path = "http://apis.baidu.com/wxlink/here/here?lat=" + location.getLatitude() + "&lng=" + location.getLongitude() + "&cst=1";
        BufferedReader reader = null;

        try {
            connection = (HttpURLConnection) new URL(path).openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setRequestProperty("apikey", "b40c5fd6cf88ca538cd360c42d75c89d");
            connection.connect();
            if (connection.getResponseCode() == 200) {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = "";
                StringBuffer buffer = new StringBuffer();
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                Log.i("hhh", buffer.toString());
                //Toast.makeText(this,buffer.toString(),Toast.LENGTH_SHORT).show();
                Message message = Message.obtain();
                //handler.sendMessage()
                message.obj = buffer.toString();
                handler.sendMessage(message);


            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                connection.disconnect();
            }
        }

    }

    /**
     * 判断手机GPS是否开启
     * @param
     * @return
     */
    public boolean isOpen(Context context) {
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        //通过GPS卫星定位,定位级别到街
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        //通过WLAN或者移动网络确定位置
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (gps || network) {
            return true;
        }
        return false;
    }

    public void openGPS(Context context) {
        Intent GPSIntent = new Intent();
        GPSIntent.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvide");
        GPSIntent.addCategory("android.intent.category.ALTERNATIVE");
        GPSIntent.setData(Uri.parse("custom:3"));

        try {
            //使用PendingIntent发送广播告诉手机去开启GPS功能
            PendingIntent.getBroadcast(context, 0, GPSIntent, 0).send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
    }

    /**
     * GPS功能已经打开-->根据GPS去获取经纬度
     */
    public void getGPSConfi() {
        Location location;
        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, locationListener);
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locationListener);
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }
        this.location=location;

        if (location != null) {
            this.location=location;

        } else {
            for(int i=0;i<100;i++){
                location=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if(location==null){
                    location=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    Log.i("hhh",location+"");
                    if(location!=null){
                        this.location=location;
                        i=101;
                    }
                }else {
                    i=101;
                }
            }
            Log.i("hhh","未获取到经纬度数据");
        }
    }

    LocationListener locationListener=new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };






}
