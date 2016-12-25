package com.micro.android316.housekeeping.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.micro.android316.housekeeping.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.micro.android316.housekeeping.R;
import com.micro.android316.housekeeping.adapter.ChatContentAdapter;
import com.micro.android316.housekeeping.model.ChatContent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/12/12.
 */
public class OnlineOnsultant extends Activity {
    private ImageView back,sounds;
    private EditText chat;
    private TextView send;
    private ListView listView;
    private String strName="红豆",strContent,strHead="www.baidu.com";
    private List<ChatContent>list=new ArrayList<>();
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ChatContentAdapter chatContentAdapter=new ChatContentAdapter(OnlineOnsultant.this,list);
            listView.setAdapter(chatContentAdapter);

        }
    };
    private View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
                    switch(v.getId()){
                        case R.id.textview_online_send:
                            strContent=chat.getText().toString();
                            new Thread(){
                                @Override
                                public void run() {
                                    super.run();
                                    saveDate();
                                }
                            }.start();
                            break;
                        case R.id.img_online_back:
                            finish();
                            break;
                    }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_onsultant);
        init();
    }
    public void init(){
        back=(ImageView)findViewById(R.id.img_online_back);
        sounds=(ImageView)findViewById(R.id.img_online_sounds);
        chat=(EditText)findViewById(R.id.edit_online_words);
        send=(TextView)findViewById(R.id.textview_online_send);
        listView=(ListView)findViewById(R.id.listview);
        back.setOnClickListener(onClickListener);
        sounds.setOnClickListener(onClickListener);
        send.setOnClickListener(onClickListener);
    }
    //http://192.168.7.2/Picture/mall01.png
    //http://192.168.7.2//index.php/Home/Index/chat?name=
    public void saveDate(){
        String httpURL="http://192.168.7.3//index.php/Home/Index/chat?name="+strName+"&content="+strContent+"&head="+strHead;

        try {
            URL url=new URL(httpURL);
            StringBuilder stringBuilder=new StringBuilder();
            HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.connect();
            Log.i("getResponseCode=====",""+httpURLConnection.getResponseCode());
            if(httpURLConnection.getResponseCode()==200){
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(),"utf-8"));
                String str;
                if((str=bufferedReader.readLine().toString())!=null){
                   stringBuilder.append(str);
                }
                Log.i("LENGTH=",""+stringBuilder.append(str));
                JSONObject jsonObject=new JSONObject(stringBuilder.toString());
                if (jsonObject.getString("status").equals("1")){
                    JSONArray jsonArray=jsonObject.getJSONArray("result");
                    Log.i("LENGTH=",""+jsonArray.length());
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject object=jsonArray.getJSONObject(i);
                        ChatContent chatContent=new ChatContent();
                        chatContent.setChat(object.getString("content"));
                        Log.i("content=",""+object.getString("content"));
                        chatContent.setHead(R.mipmap.my_head);
                        chatContent.setName("红豆");
                        list.add(chatContent);
                    }
                    Log.i("size=",""+list.size());
                }
                handler.sendEmptyMessage(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}
