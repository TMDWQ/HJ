package com.micro.android316.housekeeping.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.micro.android316.housekeeping.CustomControls.RatingBar;
import com.micro.android316.housekeeping.R;
import com.micro.android316.housekeeping.adapter.NannyAdapter;
import com.micro.android316.housekeeping.model.Coment;
import com.micro.android316.housekeeping.model.NannyInfo;
import com.micro.android316.housekeeping.utils.HttpTools;
import com.micro.android316.housekeeping.utils.JSONTool;
import com.micro.android316.housekeeping.utils.LoadImage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by Administrator on 2016/12/12.
 */

public class NannyInformation extends Activity {

    private ListView pingLun;
    private List<Coment> list;
    private List<Coment> goodList,badList,normalList;
    private NannyAdapter nannyAdapter;
    private LayoutInflater inflater;
    private TextView yuYue;
    private ImageView back;
    private String ID;
    private static final String URL="http://139.199.196.199/android/index.php/home/index/getpinglun?id=";//获取评论的url
    private static final String url="http://139.199.196.199/android/index.php/home/index/getnanny?tel=188189846&token=188189846&id=";
    private static final int NANNY=1;
    private static final int EVALUATE=0;
    private ImageView head;
    private TextView name;
    private TextView content;
    private NannyInfo nannyInfo;
    private CheckBox[] boxes=new CheckBox[8];
    private int checkBoxId[]={R.id.b1,R.id.b2,R.id.b3,R.id.b4,R.id.b5,R.id.b6,R.id.b7,R.id.b8};
    boolean[] bs={false,false,false,false,false,false,false,false};
    private int state=1;
    private TextView good,normal,bad;
    private String type;
    private RatingBar ratingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nanny_information);
        createData();
        init();
        yuYue.setOnClickListener(listener);
        back.setOnClickListener(listener);
        requestHttp();
        getComment();



    }

    private void init(){
        inflater=LayoutInflater.from(this);
        pingLun= (ListView) findViewById(R.id.pinglun_list);
        nannyAdapter=new NannyAdapter(list,this);

        View view=inflater.inflate(R.layout.nanny_information_head,null);
        head= (ImageView) view.findViewById(R.id.head);
        name= (TextView) view.findViewById(R.id.name);
        content= (TextView) view.findViewById(R.id.text);
        yuYue= (TextView) view.findViewById(R.id.li_ji_yu_yue);
        for(int i=0;i<checkBoxId.length;i++){
            boxes[i]= (CheckBox) view.findViewById(checkBoxId[i]);
            boxes[i].setChecked(bs[i]);
            boxes[i].setEnabled(false);
        }
        good= (TextView) view.findViewById(R.id.good);
        bad= (TextView) view.findViewById(R.id.bad);
        normal= (TextView) view.findViewById(R.id.normal);
        ratingBar= (RatingBar) view.findViewById(R.id.rating_bar);
        pingLun.addHeaderView(view);
        pingLun.setAdapter(nannyAdapter);
        back= (ImageView) findViewById(R.id.back);
        Intent intent=getIntent();
        ID=intent.getStringExtra("id");
        type=intent.getStringExtra("type");
        float stars=intent.getFloatExtra("stars",0);
        ratingBar.setRating(stars);
        Log.i("hahaha",ID);
        nannyInfo=new NannyInfo();
        goodList=new ArrayList<>();
        badList=new ArrayList<>();
        normalList=new ArrayList<>();

    }

    private void createData(){
        list=new ArrayList<>();
    }

    View.OnClickListener listener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case  R.id.li_ji_yu_yue:
                    Intent intent=new Intent(NannyInformation.this,Appointment.class);
                    intent.putExtra("id",ID);
                    intent.putExtra("type",type);
                    startActivity(intent);
                    break;
                case R.id.back:
                    finish();
                    break;
                case R.id.good:
                    good.setTextColor(getResources().getColor(R.color.good));
                    bad.setTextColor(getResources().getColor(R.color.bad));
                    normal.setTextColor(getResources().getColor(R.color.bad));
                    state=1;
                    copy(goodList);
                    nannyAdapter.notifyDataSetChanged();

                    break;
                case R.id.bad:
                    good.setTextColor(getResources().getColor(R.color.bad));
                    bad.setTextColor(getResources().getColor(R.color.good));
                    normal.setTextColor(getResources().getColor(R.color.bad));
                    state=3;
                    copy(badList);
                    nannyAdapter.notifyDataSetChanged();
                    break;
                case R.id.normal:
                    good.setTextColor(getResources().getColor(R.color.bad));
                    bad.setTextColor(getResources().getColor(R.color.bad));
                    normal.setTextColor(getResources().getColor(R.color.good));
                    state=2;
                    copy(normalList);
                    nannyAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };





    public void requestHttp(){
        Log.i("hahaha",url+ID);
        HttpTools tools=new HttpTools(url+ID) {
            @Override
            public void post(String line){
                try {
                    JSONObject object=new JSONObject(line);
                    //String image, String title, String content
                    Log.i("hahaha",line);
                    if(object.getString("state").equals("1")) {
                        object=object.getJSONObject("info");
                        //JSONTool.load(list, NannyInfo.class, new String[]{"Image","Title","Content"}, new String[]{object.getString("head"),object.getString("name"),object.getString("introduction")});
                        nannyInfo.setContent(object.getString("introduction"));
                        nannyInfo.setImage(object.getString("head"));
                        nannyInfo.setTitle(object.getString("name"));
                        object=object.getJSONObject("certificates_info");
                        Log.i("hahaha",nannyInfo.toString());
                        for(int i=0;i<8;i++){
                            int s=object.getInt("z"+(i+1));
                            if(s==1){
                                bs[i]=true;
                            }else {
                                bs[i]=false;
                            }

                        }
                        handler.sendEmptyMessage(NANNY);
                        Log.i("hahaha","adsdas"+nannyInfo.toString());
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };
        tools.runForGet();
    }


    public void getComment(){
        HttpTools tools=new HttpTools(URL+ID) {
            @Override
            public void post(String line) {
                try {
                    JSONObject object=new JSONObject(line);
                    if(object.getString("code").equals("1")){
                        JSONArray array=object.getJSONArray("info");
                        for(int i=0;i<array.length();i++){
                            object=array.getJSONObject(i);
                            Log.i("hhh",line);
                            String type=object.getString("type");
                            //String head, String content, long time, String name
                            String []s=new String[]{"Content","Time","Name"};
                            String []ss=new String[]{object.getString("content"),object.getString("time"),object.getString("user_id")};
                            if(type.equals("1")){
                                JSONTool.load(goodList,Coment.class,s,ss);
                            }else if(type.equals("2")){
                                JSONTool.load(normalList,Coment.class,s,ss);
                            }else if(type.equals("3")){
                                JSONTool.load(badList,Coment.class,s,ss);
                            }
                        }
                        handler.sendEmptyMessage(EVALUATE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        tools.runForGet();
    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message message){
            switch (message.what){
                case NANNY:
                    LoadImage.Load(head,nannyInfo.getImage(),NannyInformation.this);
                    name.setText(nannyInfo.getTitle());
                    content.setText(nannyInfo.getContent());
                    for(int i=0;i<bs.length;i++){
                        boxes[i].setChecked(bs[i]);
                    }
                    break;
                case EVALUATE:
                    good.setOnClickListener(listener);
                    bad.setOnClickListener(listener);
                    normal.setOnClickListener(listener);
                    good.setText("满意  "+goodList.size());
                    bad.setText("不满意  "+badList.size());
                    normal.setText("一般 "+normalList.size());
                    switch (state){
                        case 1:
                            copy(goodList);
                            nannyAdapter.notifyDataSetChanged();
                            break;
                        case 2:
                            copy(normalList);
                            nannyAdapter.notifyDataSetChanged();
                            break;
                        case 3:
                            copy(badList);
                            nannyAdapter.notifyDataSetChanged();
                            break;
                    }

                    break;

            }
        }
    };
    public void copy(List<Coment> lis){
        list.clear();
        for(Coment i:lis){
            list.add(i);
        }
    }


}
