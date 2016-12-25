package com.micro.android316.housekeeping.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.micro.android316.housekeeping.R;
import com.micro.android316.housekeeping.model.Message;

import java.util.List;

/**
 * Created by Administrator on 2016/12/14.
 */
public class MessageAdapter extends BaseAdapter{
    public List<Message> messageList;
    public Context context;
    public LayoutInflater layoutInflater;


    public MessageAdapter(Context context,List<Message> messageList){
        this.context=context;
        this.messageList=messageList;
        this.layoutInflater=LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return messageList.size();
    }

    @Override
    public Object getItem(int position) {
        return messageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView=layoutInflater.inflate(R.layout.listview_item_message,null);
        }

        return convertView;
    }



    private class ViewHolder{
        private TextView titleTv;
        private ImageView Img;
        private TextView contentTv;
        private TextView timeTv;

        public ViewHolder(View view){
            titleTv= (TextView) view.findViewById(R.id.item_message_title);
            Img= (ImageView) view.findViewById(R.id.item_message_img);
            contentTv= (TextView) view.findViewById(R.id.item_message_content);
            timeTv= (TextView) view.findViewById(R.id.item_message_time);
        }
    }
}
