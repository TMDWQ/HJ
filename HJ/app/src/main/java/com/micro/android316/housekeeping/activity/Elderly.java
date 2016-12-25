package com.micro.android316.housekeeping.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.micro.android316.housekeeping.R;
import com.micro.android316.housekeeping.adapter.ElderAdapter;
import com.micro.android316.housekeeping.model.ElderlyDate;
import com.micro.android316.housekeeping.utils.HttpTools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/12/10.
 */

public class Elderly extends Activity{

    private  ListView listView;
    private ElderAdapter adapter;
    private List list;
    private LayoutInflater inflater;
    private TextView title;
    private Intent intent;
    private ImageView back;
    private HttpTools tools;
    private static final String URL="http://139.199.196.199/android/index.php/home/index/getnannysinfomation?type=";
    private static final int DATA=1234;
    private String string;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.elderly);
        init();
        title.setText(string);
        getDate();
        adapter=new ElderAdapter(this,list);

        View view=inflater.inflate(R.layout.elderly_item_head,null);
        listView.addHeaderView(view);
        listView.setAdapter(adapter);
        back.setOnClickListener(listener);
        requestHttp(string);



    }

    private void init(){
        listView= (ListView) findViewById(R.id.list_view);
        inflater=LayoutInflater.from(this);
        title= (TextView) findViewById(R.id.title);
        intent=getIntent();
        back= (ImageView) findViewById(R.id.back);
        string=intent.getStringExtra("name");

    }

    public void getDate()
    {
        list=new ArrayList();
    }

    public void listViewSetOnclick(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Log.i("hahaha",position+"------"+d);
                if(position==0){
                    return;
                }
                Intent intent=new Intent(Elderly.this, NannyInformation.class);
                ElderlyDate date= (ElderlyDate) list.get(position-1);
                intent.putExtra("id",date.getId());
                intent.putExtra("type",date.getWorkSpace());
                intent.putExtra("stars",date.getStars());
                startActivity(intent);
            }
        });

    }

    View.OnClickListener listener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.back:
                    finish();
                    break;
            }
        }
    };

    public void getDatas(int type){
        tools=new HttpTools(URL+type) {
            @Override
            public void post(String line) {
                try {
                    JSONObject object=new JSONObject(line);
                    if(object.getString("code").equals("1")){
                        JSONArray array=object.getJSONArray("info");
                        list.clear();
                        for(int i=0;i<array.length();i++){
                            //String headURL, String evaluateURL, String name, int age, String experience, String workSpace, String briefIntroduction, int guanZhu_count, int pingLun_count, int zhan_count
                            object=array.getJSONObject(i);
                            ElderlyDate date=new ElderlyDate();

                                date.setAge(object.getString("age"));
                                date.setHeadURL(object.getString("head"));
                                date.setEvaluateURL("");
                                date.setName(object.getString("name"));
                                date.setWorkSpace(object.getString("worktime"));
                                date.setGuanZhu_count(object.getString("fabulous"));
                                date.setZhan_count(object.getInt("follow_count"));
                                date.setBriefIntroduction(object.getString("introduction"));
                                date.setPingLun_count(10);
                                date.setWorkSpace(string);
                                date.setId(object.getString("id"));
                                date.setStars((float) object.optDouble("stars",0));

                            list.add(date);

                        }

                        handler.sendEmptyMessage(DATA);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }




            }
        };

        tools.runForGet();
    }

    Handler handler=new Handler(){
      public void handleMessage(Message message){
          switch (message.what){
              case DATA:
                  adapter.notifyDataSetChanged();
                  listViewSetOnclick();
                  break;


          }
      }
    };

    public void requestHttp(String s){
        if(s.equals("老人护理")){
            getDatas(1);
        }else if(s.equals("婴幼儿护理")){
            getDatas(2);
        }else if(s.equals("家庭保洁")){
            getDatas(3);
        }else if(s.equals("家具保洁")){
            getDatas(4);
        }else if(s.equals("烹饪料理")){
            getDatas(5);
        }
    }
}
