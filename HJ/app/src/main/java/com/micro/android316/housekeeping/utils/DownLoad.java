package com.micro.android316.housekeeping.utils;

import android.os.Environment;
import android.os.Handler;
import android.os.Message;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2016/12/9.
 */
public class DownLoad {
    //创建一个线程池
    private Executor threadPool= Executors.newFixedThreadPool(3);
    private Handler handler;

    public DownLoad(Handler handler){
        this.handler=handler;
    }

    static class DownLoadRunnable implements Runnable{
        private String url;

        private String fileName;

        private long start;

        private long end;
        private  Handler handler;
        public DownLoadRunnable(String url,String fileName,long start,long end,Handler handler){
            this.url=url;
            this.fileName=fileName;
            this.start=start;
            this.end=end;
            this.handler=handler;
        }

        @Override
        public void run() {
            try {
                URL httpUrl=new URL(url);
                HttpURLConnection connection= (HttpURLConnection) httpUrl.openConnection();
                connection.setRequestMethod("GET");
                connection.setReadTimeout(5000);
                connection.setRequestProperty("Range","bytes="+start+"-"+end);
                RandomAccessFile accessFile=new RandomAccessFile(new File(fileName),"rwd");
                accessFile.seek(start);
                InputStream is=connection.getInputStream();
                byte[] b=new byte[1024*4];
                int len=0;
                while ((len=is.read(b))!=-1){
                    accessFile.write(b,0,len);
                }
                if(accessFile!=null){
                    accessFile.close();
                }
                if(is!=null){
                    is.close();
                }
                Message message=new Message();
                message.what=1;
                handler.sendMessage(message);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        }
    public void downLoadFile(String url){

        try {
            URL httpUrl=new URL(url);
            HttpURLConnection connection= (HttpURLConnection) httpUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(5000);
            int count=connection.getContentLength();
            int block=count/3;

            //获得下载的文件名字
            String fileName=getFileName(url);
            //获得SD卡路径
            File parent= Environment.getExternalStorageDirectory();
            File fileDownLoad=new File(parent,fileName);

            for(int i=0;i<3;i++){
                long start=i*block;
                long end=(i+1)*block-1;
                if(i==2){
                    end=count;
                }
                DownLoadRunnable runnable=new DownLoadRunnable(url,fileDownLoad.getAbsolutePath(),start,end,handler);
                threadPool.execute(runnable);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
   public String getFileName(String url){
       return url.substring(url.lastIndexOf("/")+1);
   }
}
