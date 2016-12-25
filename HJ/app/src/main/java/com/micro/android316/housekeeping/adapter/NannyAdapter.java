package com.micro.android316.housekeeping.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.micro.android316.housekeeping.R;
import com.micro.android316.housekeeping.model.Coment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;



/**
 * Created by Administrator on 2016/12/12.
 */

public class NannyAdapter extends BaseAdapter {
    private List<Coment> list;
    private LayoutInflater inflater;
    private Context context;

    public NannyAdapter(List<Coment> list, Context context) {
        this.list = list;
        inflater=LayoutInflater.from(context);
        this.context = context;
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
    public   View getView(int position, View convertView, ViewGroup parent) {

       ViewHolder holder;
       if(convertView==null){
           convertView=inflater.inflate(R.layout.nanny_information_item,null);
           holder=new ViewHolder();
           holder.name= (TextView) convertView.findViewById(R.id.name);
           holder.content= (TextView) convertView.findViewById(R.id.content);
           holder.time= (TextView) convertView.findViewById(R.id.time);
           convertView.setTag(holder);
       }else{
           holder= (ViewHolder) convertView.getTag();
       }
        //2016/10/18

        String n=list.get(position).getName();
        if(n!=null)
        holder.name.setText(n);

        n=list.get(position).getContent();
        if(n!=null)
        holder.content.setText(n);

        n=list.get(position).getTime();
        if(n!=null) {
            String s = n;
            Long l=new Long(s);
            SimpleDateFormat format=new SimpleDateFormat("yyyy/MM/dd");
            s=format.format(new Date(l));
            holder.time.setText(s);
        }
        return convertView;
    }

    class ViewHolder{
        ImageView head;
        TextView time;
        TextView name,content;
    }
}
