package com.micro.android316.housekeeping.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.micro.android316.housekeeping.R;
import com.micro.android316.housekeeping.model.ConsultationMessage;

import java.util.List;

/**
 * Created by Administrator on 2016/12/20.
 */

public class OnlineDate extends BaseAdapter{
    private LayoutInflater inflater;
    private Context context;
    private List<ConsultationMessage> list;


    public OnlineDate(Context context, List<ConsultationMessage> list) {

        this.context = context;
        this.list = list;
        inflater=LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //convertView=inflater.inflate(R.layout)
        if(list.get(position).isUser()){
            convertView=inflater.inflate(R.layout.consultation_client_item,null);
            TextView textView= (TextView) convertView.findViewById(R.id.user);
            textView.setText(list.get(position).getContent());
        }else {
            convertView=inflater.inflate(R.layout.consultation_comstomer_item,null);
            TextView textView= (TextView) convertView.findViewById(R.id.service);
            textView.setText("咨询："+list.get(position).getContent());
        }
        return convertView;
    }
}
