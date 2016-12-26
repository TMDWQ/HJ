package com.micro.android316.housekeeping.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.micro.android316.housekeeping.R;
import com.micro.android316.housekeeping.utils.AppFinal;
import com.micro.android316.housekeeping.utils.HttpTools;
import com.micro.android316.housekeeping.utils.LoginMessgae;
import com.micro.android316.housekeeping.utils.ShareToWeiXin;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

/**
 * Created by Administrator on 2016/12/16.
 */

public class Set extends Activity {
    private LinearLayout linearLayout;
    private LinearLayout shareToQZong;
    private LinearLayout clear;
    private final static String URL="http://139.199.196.199/android/index.php/home/index/signout?tel=";
    private final static int EXIT_SUCCESS=1;
    private final static int EXIT_ERROR=-1;
    private  File file;
    private long size=0;
    private ShareToWeiXin shareToWeiXin;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        init();
        exit();
        share();
        Log.i("zhzh",file.toString());
        getCatchSize(file);
        textView.setText(getMemery(size));
        clearMemery();


    }
    public void init(){
        linearLayout= (LinearLayout) findViewById(R.id.exit);
        shareToQZong= (LinearLayout) findViewById(R.id.share_to_qzong);
        shareToWeiXin=new ShareToWeiXin(Set.this);
        textView= (TextView) findViewById(R.id.mine_message_img);
        clear= (LinearLayout) findViewById(R.id.clear);
        file=getCacheDir();
    }

    public void exit(){
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exitRequest();
            }
        });
    }
    public void share(){
       shareToQZong.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               //QQShare.shar(Set.this);
               Log.i("hhh","分享");
               shareToWeiXin.shareToSession("hello");
           }
       });
    }

    public void exitRequest(){
        HttpTools tools=new HttpTools(URL+ LoginMessgae.getTel(this)) {
            @Override
            public void post(String line) {
                try {
                    JSONObject object=new JSONObject(line);
                    if(object.getString("state").equals("1")){
                        handler.sendEmptyMessage(EXIT_SUCCESS);
                    }else {
                        handler.sendEmptyMessage(EXIT_ERROR);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        tools.runForGet();
    }
    Handler handler=new Handler(){
        public void handleMessage(android.os.Message message){
            switch (message.what){
                case EXIT_SUCCESS:
                    AppFinal.activity.finish();
                    Set.this.finish();
                    break;
                case EXIT_ERROR:
                    Toast.makeText(Set.this,"退出失败",Toast.LENGTH_SHORT).show();
                    break;

            }
        }
    };



    public void clearCache(File file){
        File [] files=file.listFiles();
        if(files!=null && files.length!=0){
            for(File i:files){
                if(i.isDirectory()){
                    clearCache(i);
                }else {
                    i.delete();
                }
            }
        }

    }

    public void getCatchSize(File file){
        File[] files=file.listFiles();
        //Log.i("zhzh",file.getName()+files.length);
        if(files!=null && files.length!=0){
            for(File i:files){
               if(i.isDirectory()){
                   getCatchSize(i);
               } else {
                  size+=i.length();
                   //Log.i("zhzh","aaaaaaaaaaaaaaaaaaaaaa");
               }
            }
        }
    }

    public String getMemery(Long b){
        long l;
        if((l=b/(1024*1024))!=0){
            double db=(double)b/(1024*1024);
            return getStringToSaveTwo(db)+" M";
        }
        if((l=b/1024)!=0){
            double db=(double)b/1024;
            return getStringToSaveTwo(db)+" KB";
        }
        return l+" b";
    }

    public void clearMemery(){
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearCache(file);
                Toast.makeText(Set.this,"清理成功",Toast.LENGTH_SHORT).show();
                getCatchSize(file);
                textView.setText("0 KB");
            }
        });

    }

    public String getStringToSaveTwo(double db){
        String s=db+"";
        String re="";
        for(int i=0;i<s.length();i++){
            char c=s.charAt(i);
            if(c=='.'){
                re+='.';
                re+=s.charAt(i+1);
                re+=s.charAt(i+2);
                return re;
            }
            re+=c;
        }
        return  s;
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        shareToWeiXin.unregister();
    }
}
