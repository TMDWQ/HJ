package com.micro.android316.housekeeping.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.micro.android316.housekeeping.R;
import com.micro.android316.housekeeping.model.ChatContent;

import java.util.List;

/**
 * Created by Administrator on 2016/12/13.
 */
public class ChatContentAdapter extends BaseAdapter {
    List<ChatContent> list;
    Context context;
    LayoutInflater layoutInflater;
    public ChatContentAdapter(Context context, List<ChatContent> list){
        this.list=list;
        this.context=context;
        layoutInflater=LayoutInflater.from(context);
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
        if(convertView==null){
            convertView=layoutInflater.inflate(R.layout.listview_item_online_myself,null);
        }
        ChatContent chatContent=list.get(position);
        ImageView head=(ImageView)convertView.findViewById(R.id.img_myhead);
        TextView name=(TextView)convertView.findViewById(R.id.textview_name);
        TextView content=(TextView)convertView.findViewById(R.id.textview_content);
        head.setImageResource(chatContent.getHead());
        name.setText(chatContent.getName());
        content.setText(chatContent.getChat());
        return convertView;
    }
}
