package com.micro.android316.housekeeping.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2016/12/14.
 *
 */

public abstract class HttpTools {

    private String url;
    public HttpTools(String url){
        this.url=url;
    }

    public void runForGet(){
        new Thread(){
            @Override
            public void run(){
                http();
            }
        }.start();
    }

    public void http(){
        HttpURLConnection connection=null;
        BufferedReader reader=null;
        try {
            connection= (HttpURLConnection) new URL(url).openConnection();
            connection.setConnectTimeout(3000);
            connection.setRequestMethod("GET");
            connection.connect();

            if(connection.getResponseCode()==200){
                reader=new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line="";
                StringBuffer buffer=new StringBuffer();
                while ((line=reader.readLine())!=null){
                    buffer.append(line);
                }
                post(buffer.toString());
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public abstract void post(String line);










}
