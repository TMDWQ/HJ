package com.micro.android316.housekeeping.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.micro.android316.housekeeping.R;
import com.micro.android316.housekeeping.model.Waitpay;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/13.
 */
public class WaitpayAdapter extends BaseAdapter {
    Context context;
    List<Waitpay> indentList = new ArrayList<>();
    LayoutInflater inflater;
    public WaitpayAdapter(Context context,List<Waitpay> indentList){
        this.context = context;
        this.indentList = indentList;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return indentList.size();
    }

    @Override
    public Object getItem(int position) {
        return indentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if(convertView==null){
            holder=new ViewHolder();
            convertView = inflater.inflate(R.layout.waitpay_indent_item,null);
            holder.whatPlace= (TextView) convertView.findViewById(R.id.what_place);
            holder.whatService= (TextView) convertView.findViewById(R.id.what_service);
            holder.whatTime= (TextView) convertView.findViewById(R.id.what_time);
            convertView.setTag(holder);
        }
        holder= (ViewHolder) convertView.getTag();
        holder.whatTime.setText(indentList.get(position).getTime());
        holder.whatService.setText(indentList.get(position).getRange());
        holder.whatPlace.setText(indentList.get(position).getPlace());

        return convertView;
    }

    public static class ViewHolder{
        public TextView whatTime,whatPlace,whatService;
    }
}
