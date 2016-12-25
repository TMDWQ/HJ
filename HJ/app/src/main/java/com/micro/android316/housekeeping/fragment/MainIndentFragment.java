package com.micro.android316.housekeeping.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.micro.android316.housekeeping.R;
import com.micro.android316.housekeeping.activity.HomeMainActivity;
import com.micro.android316.housekeeping.activity.Orderdetail;
import com.micro.android316.housekeeping.activity.Payment;
import com.micro.android316.housekeeping.adapter.OrderAdapter;
import com.micro.android316.housekeeping.model.OrderDate;
import com.micro.android316.housekeeping.utils.HttpTools;
import com.micro.android316.housekeeping.utils.LoginMessgae;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/13.
 */
public class MainIndentFragment extends Fragment {

    private RadioButton all[]=new RadioButton[4];
    private final int radios[]={R.id.all,R.id.wait_pay,R.id.wait_service,R.id.wait_appraise};
    private RadioGroup indentRadaioGroup;
    private ScrollView waitAppraiseView;
    private String[] types={"老人护理","婴幼儿护理","家居保洁","家具保洁","烹饪调理"};
    private ListView orderInfo;
    private Context context;
    private HomeMainActivity activity;
    private List<OrderDate> list=new ArrayList();
    private List<OrderDate> list1=new ArrayList<>(),list2=new ArrayList<>(),list3=new ArrayList<>();
    private OrderAdapter adapter;
    private String tel;
    private RadioGroup group;
    public boolean isFirst=false,isFirstTwo=false,isFirstTree=false,isNotComment=false;
    private boolean canNotSubmit=false;
    private String typeComment;
    private Button submitApprasise;
    private TextView comment;
    private RatingBar bar;
    private EditText content;
    private String ordID;             //订单ID
    private final  static int ALL=1;   //handle消息的唯一标识，获取所有订单
    private final static int WAIT_PAY=2; //handle消息的唯一标识，获取没有支付的订单
    private final static int WAIY_SERVICE=3;//handle消息的唯一标识，获取已经支付的订单
    private final static int GET_COMMENT=4; //handle消息的唯一标识，获取评论
    private final static int SUBMIT_COMMENT=5; //handle消息的唯一标识，评论上传成功
    private final static int GET_COMMENT_HAS_NOT=-1;//handle消息的唯一标识，评论上传失败
    private final static String URL_ALL="http://139.199.196.199/android/index.php/home/index/getallord?tel=";
    private final static String URL_WAIT_PAY="http://139.199.196.199/android/index.php/home/index/nopayment?tel=";
    private final static String URL_WAIT_SERVICE="http://139.199.196.199/android/index.php/home/index/payment?tel=";
    private final static String URL_GET_COMMENT="http://139.199.196.199/android/index.php/home/index/getlist?tel=";
    private final static String URL_SUBMIT_COMMENT="http://139.199.196.199/android/index.php/home/index/addordforanny?";



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.indent_main_layout,null);
        init(v);
        context=getContext();
        activity= (HomeMainActivity) getActivity();
        tel=LoginMessgae.getTel(context);
        adapter=new OrderAdapter(context,list);
        setOnclick();
        pay();
        orderInfo.setAdapter(adapter);
        group=activity.radioGroup;
        chooces();//设置选择监听
        getDate(URL_ALL+tel,ALL);
        getDate(URL_WAIT_PAY+tel,list2);
        getDate(URL_WAIT_SERVICE+tel,list3);
        submitComment();
        return v;
    }

    public void init(View view)
    {
        for(int i=0;i<4;i++){
            all[i]= (RadioButton) view.findViewById(radios[i]);
        }
        indentRadaioGroup= (RadioGroup) view.findViewById(R.id.indent_radiogroup);
        waitAppraiseView= (ScrollView) view.findViewById(R.id.wait_appraise_view);
        orderInfo= (ListView) view.findViewById(R.id.order_info);
        comment= (TextView) view.findViewById(R.id.comment);
        submitApprasise= (Button) view.findViewById(R.id.submit_apprasise);
        bar= (RatingBar) view.findViewById(R.id.service_appraise_rating);
        content= (EditText) view.findViewById(R.id.edit_appraise);
    }

    public void chooces(){
        indentRadaioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.all:
                        for(int i=0;i<4;i++){
                            if(i==0){
                                all[i].setTextColor(getResources().getColor(R.color.txt_1));
                            } else{
                                all[i].setTextColor(getResources().getColor(R.color.dark_black));
                            }
                        }
                        MainIndentFragment.this.group.setVisibility(View.VISIBLE);
                        waitAppraiseView.setVisibility(View.GONE);
                        orderInfo.setVisibility(View.VISIBLE);
                        if (isFirst){
                            isFirst=false;
                            getDate(URL_ALL+tel,ALL);
                            return;
                        }else {
                            copy(list1,list);
                            adapter.notifyDataSetChanged();
                        }



                        break;
                    case R.id.wait_pay:

                        for(int i=0;i<4;i++){
                            if(i==1){
                                all[i].setTextColor(getResources().getColor(R.color.txt_1));
                            } else{
                                all[i].setTextColor(getResources().getColor(R.color.dark_black));
                            }
                        }
                        MainIndentFragment.this.group.setVisibility(View.VISIBLE);
                        waitAppraiseView.setVisibility(View.GONE);
                        orderInfo.setVisibility(View.VISIBLE);
                        if (isFirstTwo){
                            getDate(URL_WAIT_PAY+tel,WAIT_PAY);
                            isFirstTwo=false;
                            return;
                        }else {
                            copy(list2,list);
                            adapter.notifyDataSetChanged();
                        }


                        break;
                    case R.id.wait_service:
                        for(int i=0;i<4;i++){
                            if(i==2){
                                all[i].setTextColor(getResources().getColor(R.color.txt_1));
                            } else{
                                all[i].setTextColor(getResources().getColor(R.color.dark_black));
                            }
                        }
                        MainIndentFragment.this.group.setVisibility(View.VISIBLE);
                        waitAppraiseView.setVisibility(View.GONE);
                        orderInfo.setVisibility(View.VISIBLE);
                        Log.i("heheheh",URL_WAIT_SERVICE+tel+WAIY_SERVICE);
                        if (isFirstTree){
                            getDate(URL_WAIT_SERVICE+tel,WAIY_SERVICE);
                            isFirstTree=false;
                            return;
                        }else {
                            copy(list3,list);
                            adapter.notifyDataSetChanged();
                        }



                        break;
                    case R.id.wait_appraise:
                        for(int i=0;i<4;i++){
                            if(i==3){
                                all[i].setTextColor(getResources().getColor(R.color.txt_1));
                            } else{
                                all[i].setTextColor(getResources().getColor(R.color.dark_black));
                            }
                        }
                        MainIndentFragment.this.group.setVisibility(View.GONE);
                        waitAppraiseView.setVisibility(View.VISIBLE);
                        orderInfo.setVisibility(View.GONE);

                        if(isNotComment){
                            getComment();
                            isNotComment=false;
                        }

                        break;
                }
            }
        });
    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case ALL:
                    adapter.notifyDataSetChanged();
                    copy(list,list1);
                    break;
                case WAIT_PAY:
                    adapter.notifyDataSetChanged();
                    copy(list,list2);
                    break;
                case WAIY_SERVICE:
                    adapter.notifyDataSetChanged();
                    copy(list,list3);
                    break;
                case GET_COMMENT:
                    comment.setText(typeComment);
                    break;
                case SUBMIT_COMMENT:
                    Toast.makeText(context,"评论成功，正在切换到下一个评论",Toast.LENGTH_SHORT).show();
                    content.setText("");
                    getComment();
                    break;
                case GET_COMMENT_HAS_NOT:
                    comment.setText("无评论对象");
                    canNotSubmit=true;
                    content.setText("");
                    Toast.makeText(context,"你没有可评论的订单",Toast.LENGTH_SHORT).show();
                    break;

            }

        }
    };

    public void getDate(String url, final int code){//获取数据，code(设置handle的what)
        HttpTools tools=new HttpTools(url) {
            @Override
            public void post(String line) {
                try {
                    JSONObject object=new JSONObject(line);
                    list.clear();
                    if(object.optString("code","-1").equals("1")){
                        JSONArray array=object.getJSONArray("info");
                        for(int i=0;i<array.length();i++){
                            object=array.getJSONObject(i);
                            OrderDate date=new OrderDate();
                            date.setAddress(addressSplit(object.optString("address","")));
                            date.setId(object.optString("id",""));
                            date.setPrice(object.optString("price",""));
                            date.setType(types[object.optInt("type")-1]);
                            date.setTime(object.optLong("time",0));
                            date.setLen(object.optString("length","0"));
                            if(object.optInt("state")==1) {
                                date.setPayed(true);
                            }else if(object.optInt("state")==-1){
                                date.setPayed(false);
                            }
                            list.add(date);
                        }

                        handler.sendEmptyMessage(code);


                    }else {
                        handler.sendEmptyMessage(code);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        tools.runForGet();

    }


    public void getDate(String url, final List<OrderDate> list){//是上面方法的重载，不会发送消息更改UI，为的是第一次加载不卡顿
        HttpTools tools=new HttpTools(url) {
            @Override
            public void post(String line) {
                // Log.i("hhhaaa",line);
                try {
                    JSONObject object=new JSONObject(line);
                    list.clear();
                    if(object.optString("code","-1").equals("1")){
                        JSONArray array=object.getJSONArray("info");
                        for(int i=0;i<array.length();i++){
                            object=array.getJSONObject(i);
                            OrderDate date=new OrderDate();
                            date.setAddress(addressSplit(object.optString("address","")));
                            date.setId(object.optString("id",""));
                            date.setPrice(object.optString("price",""));
                            date.setType(types[object.optInt("type")-1]);
                            date.setTime(object.optLong("time",0));
                            date.setLen(object.optString("length","0"));
                            if(object.optInt("state")==1) {
                                date.setPayed(true);
                            }else if(object.optInt("state")==-1){
                                date.setPayed(false);
                            }
                            list.add(date);
                        }



                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        tools.runForGet();

    }

    public void copy(List<OrderDate> list1,List<OrderDate> list2){
        list2.clear();
        if(list1.size()==0){
            return;
        }
        for(OrderDate i:list1){
            list2.add(i);
        }
    }

    public void setOnclick(){
        adapter.setOnItemClickListener(new OrderAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view,OrderDate date) {
                        /**code=intent.getStringExtra("state");
                         type=intent.getStringExtra("type");
                         id=intent.getStringExtra("id");
                         price=intent.getStringExtra("price");
                         address=intent.getStringExtra("address");
                         time=intent.getStringExtra("time");
                         * */
                Intent intent=new Intent(context, Orderdetail.class);
                if(date.isPayed())
                    intent.putExtra("state","1");
                else
                    intent.putExtra("state","0");
                intent.putExtra("id",date.getId());
                intent.putExtra("price",date.getPrice());
                intent.putExtra("address",date.getAddress());
                intent.putExtra("time",date.getTime());
                intent.putExtra("type",date.getType());
                startActivity(intent);
             //   Log.i("twlz",date.toString());
            }
        });
    }

    public void pay(){
        adapter.setOnPayListener(new OrderAdapter.OnPayListener() {
            @Override
            public void pay(OrderDate date) {
                /**
                 *  id=getIntent().getStringExtra("id");
                 price=getIntent().getStringExtra("price");
                 type=getIntent().getStringExtra("type");
                 * */
                Intent intent=new Intent(context, Payment.class);
                intent.putExtra("id",date.getId());
                intent.putExtra("price",date.getPrice());
                intent.putExtra("type",date.getType());
                startActivity(intent);
            }
        });
    }

    public String addressSplit(String str){

        if(str==null){
            return str;
        }
        String[] s=str.split("：");
        if(s.length==2){
            return s[1];
        }
        return str;


    }

    public void getComment(){
        HttpTools tools=new HttpTools(URL_GET_COMMENT+tel) {
            @Override
            public void post(String line) {
                try {
                    JSONObject object=new JSONObject(line);
                    if(object.optInt("code",0)==1){
                        object=object.getJSONObject("info");
                        typeComment=types[object.optInt("type")-1];
                        ordID=object.getString("id");
                        handler.sendEmptyMessage(GET_COMMENT);
                    }else {
                        handler.sendEmptyMessage(GET_COMMENT_HAS_NOT);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };
        tools.runForGet();
    }

    public void Submit(){
        if(canNotSubmit){
            Toast.makeText(context,"你没有可评论的订单",Toast.LENGTH_SHORT).show();
            return;
        }

        String c=content.getText().toString();
        try {
            c= URLEncoder.encode(c,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if(c==null && c.length()==0){
            Toast.makeText(context,"你的评论内容不能为空",Toast.LENGTH_SHORT).show();
            return;
        }

        String value="content=" +c+ "&tel="+tel+"&ord=" +ordID+ "&type="+getStartsForType();
        Log.i("yoyoyo",URL_SUBMIT_COMMENT+value);
        HttpTools tools=new HttpTools(URL_SUBMIT_COMMENT+value) {
            @Override
            public void post(String line) {
                try {
                    JSONObject object=new JSONObject(line);
                    if(object.getInt("code")==1){
                        handler.sendEmptyMessage(SUBMIT_COMMENT);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        tools.runForGet();

    }

    public void submitComment(){
        submitApprasise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Submit();
            }
        });
    }

    private int getStartsForType(){
        float f=bar.getRating();
        if(f<2.0f){
            return 3;
        }

        if(f<4f){
            return 2;
        }

        return 1;

    }

    public void reAdd(){
        if(all[0].isChecked()){
            getDate(URL_ALL+tel,ALL);
            isFirst=false;
        }else if(all[1].isChecked()){

            getDate(URL_WAIT_PAY+tel,WAIT_PAY);
            isFirstTwo=false;
        }else if(all[2].isChecked()){

            getDate(URL_WAIT_SERVICE+tel,WAIY_SERVICE);
            isFirstTree=false;
        }
    }





}
