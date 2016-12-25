package com.micro.android316.housekeeping.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.micro.android316.housekeeping.CustomControls.RatingBar;
import com.micro.android316.housekeeping.R;
import com.micro.android316.housekeeping.model.ElderlyDate;
import com.micro.android316.housekeeping.utils.LoadImage;

import java.util.List;



/**
 * Created by Administrator on 2016/12/10.
 */

public  class ElderAdapter extends BaseAdapter{
    private Context context;
    private List list;
    private LayoutInflater inflater;
    public ElderAdapter(Context context,List list){
        inflater=LayoutInflater.from(context);
        this.list=list;
        this.context=context;
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
    public  View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            convertView=inflater.inflate(R.layout.elderly_item,null);
            holder=new ViewHolder();
            holder.name= (TextView) convertView.findViewById(R.id.name);
            holder.age= (TextView) convertView.findViewById(R.id.age);
            holder.wk_time= (TextView) convertView.findViewById(R.id.wk_time);
            holder.wk_space= (TextView) convertView.findViewById(R.id.wk_space);
            holder.js= (TextView) convertView.findViewById(R.id.js);
            holder.gz= (TextView) convertView.findViewById(R.id.gz);
            holder.pl= (TextView) convertView.findViewById(R.id.pl);
            holder.head= (ImageView) convertView.findViewById(R.id.head);
            holder.ratingBar= (RatingBar) convertView.findViewById(R.id.rating_bar);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        ElderlyDate date= (ElderlyDate) list.get(position);
        if(date.getName()!=null)
        holder.name.setText(date.getName());
        if(date.getAge()!=null)
        holder.age.setText(date.getAge()+"岁");
        if(date.getExperience()!=null)
        holder.wk_time.setText("从业时间:"+date.getExperience());
        if(date.getWorkSpace()!=null)
        holder.wk_space.setText("工作范围: "+date.getWorkSpace());
        if(date.getBriefIntroduction()!=null)
        holder.js.setText(date.getBriefIntroduction());
        if(date.getGuanZhu_count()!=null)
        holder.gz.setText(date.getGuanZhu_count());
        //holder.pl.setText(date.getPingLun_count());
        if(date.getHeadURL()!=null)
            LoadImage.Load(holder.head,date.getHeadURL(),context);
        holder.ratingBar.setRating(date.getStars());




        return convertView;
    }

    class ViewHolder{
        ImageView head;
        TextView name ,age,wk_time,wk_space,js,gz,pl;
        RatingBar ratingBar;
    }
}
