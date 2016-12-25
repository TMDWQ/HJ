package com.micro.android316.housekeeping.activity;

import android.app.Activity;
import android.os.*;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.micro.android316.housekeeping.R;
import com.micro.android316.housekeeping.adapter.OnlineDate;
import com.micro.android316.housekeeping.model.ConsultationMessage;
import com.micro.android316.housekeeping.utils.Client;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/20.
 */

public class Online extends Activity {

    private TextView send;
    private ListView listview;
    private Client client;
    private EditText content;
    private List<ConsultationMessage> list;
    private OnlineDate adpter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.consultation);
        init();
        sendMessage();
        ConsultationMessage con=new ConsultationMessage();
        con.setContent("咨询:尊敬的用户，请问我有什么可以帮助你？");
        con.setHeadImage(R.mipmap.my_head);
        con.setUser(false);
        list.add(con);
        listview.setAdapter(adpter);

    }

    public void sendMessage(){
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                client.send(content.getText().toString());
                ConsultationMessage con=new ConsultationMessage();
                con.setContent(content.getText().toString());
                con.setHeadImage(R.mipmap.my_head);
                con.setUser(true);
                list.add(con);
                adpter.notifyDataSetChanged();
                content.setText("");
                listview.smoothScrollByOffset(list.size()-1);
            }
        });

        content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                String ss=s.toString();
                if(ss.length()!=0)
                    if(ss.charAt(s.length()-1)=='\n'){
                        client.send(content.getText().toString());
                        ConsultationMessage con=new ConsultationMessage();
                        con.setContent(content.getText().toString());
                        con.setHeadImage(R.mipmap.my_head);
                        con.setUser(true);
                        list.add(con);
                        adpter.notifyDataSetChanged();
                        content.setText("");
                        listview.smoothScrollByOffset(list.size()-1);
                    }
            }
        });
    }

    public void init(){
        send= (TextView) findViewById(R.id.send);
        listview= (ListView) findViewById(R.id.consultation);
        client=new Client(handler);
        content= (EditText) findViewById(R.id.edit_online_words);
        list=new ArrayList<>();
        adpter=new OnlineDate(this,list);
    }

    private Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            ConsultationMessage con=new ConsultationMessage();
            con.setContent((String) msg.obj);
            con.setHeadImage(R.mipmap.server);
            con.setUser(false);
            list.add(con);
            adpter.notifyDataSetChanged();
            listview.smoothScrollByOffset(list.size()-1);
            return true;
        }
    });

    @Override
    protected void onDestroy() {
        super.onDestroy();
        client.destory();
    }


}
