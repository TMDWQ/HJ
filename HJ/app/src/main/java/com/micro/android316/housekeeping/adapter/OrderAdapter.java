package com.micro.android316.housekeeping.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.micro.android316.housekeeping.R;
import com.micro.android316.housekeeping.model.OrderDate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/12/21.
 */

public class OrderAdapter extends BaseAdapter{
    private Context context;
    private List<OrderDate> list;
    private LayoutInflater inflater;
    private int position;

    public OrderAdapter(Context context, List<OrderDate> list) {
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
        final OrderDate date=list.get(position);

        if(list.get(position).isPayed()){
            convertView=inflater.inflate(R.layout.waitservice_indent_item,null);
            TextView type,time,oldPrice,price;
            type= (TextView) convertView.findViewById(R.id.waitservice_range);
            time= (TextView) convertView.findViewById(R.id.waitservice_time);
            price= (TextView) convertView.findViewById(R.id.waitservice_current_price);
            oldPrice= (TextView) convertView.findViewById(R.id.waitservice_orginal_price);

            int len=list.get(position).getLen().charAt(0)-48;
            type.setText(list.get(position).getType());
            SimpleDateFormat format=new SimpleDateFormat("yyyy年MM月dd日\nHH:mm");
            SimpleDateFormat format1=new SimpleDateFormat("HH:mm");
            time.setText(format.format(new Date(list.get(position).getTime()))+"-"+
                    format1.format(new Date(list.get(position).getTime()+len*3600*1000)));
            price.setText(list.get(position).getPrice()+"元/小时");
            oldPrice.setText((toInt(list.get(position).getPrice())+10)+"元/小时");

        }else{
            convertView=inflater.inflate(R.layout.waitpay_indent_item,null);
            int len=list.get(position).getLen().charAt(0)-48;
            TextView whatService,whatTime,whatPlace,ziFu;
            whatPlace= (TextView) convertView.findViewById(R.id.what_place);
            whatTime= (TextView) convertView.findViewById(R.id.what_time);
            whatService= (TextView) convertView.findViewById(R.id.what_service);
            whatService.setText(list.get(position).getType());
            SimpleDateFormat format=new SimpleDateFormat("yyyy年MM月dd日HH:mm");
            whatPlace.setText(list.get(position).getAddress());
            SimpleDateFormat format1=new SimpleDateFormat("HH:mm");
            whatTime.setText(format.format(new Date(list.get(position).getTime()))+"-"+
                    format1.format(new Date(list.get(position).getTime()+len*3600*1000)));
            ziFu= (TextView) convertView.findViewById(R.id.zi_fu);
            ziFu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    List<OrderDate> li=new ArrayList<>();
                    copy(list,li);
                    listener2.pay(deepCopy(date));
                }
            });


        }


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener1.onClick(v,deepCopy(date));
            }
        });

        return convertView;
    }
    private OnItemClickListener listener1;
    private OnPayListener listener2;
    public void setOnItemClickListener(OnItemClickListener listener1){
        this.listener1=listener1;
    }
    public void setOnPayListener(OnPayListener listener2){
        this.listener2=listener2;
    }

    public interface OnItemClickListener{
        public void onClick(View view,OrderDate date);
    }

    public interface OnPayListener{
        public void pay(OrderDate date);
    }

    private void copy(List<OrderDate> list1,List<OrderDate> list2){
        list2.clear();
        for(OrderDate i:list1){
            list2.add(deepCopy(i));
        }
    }

    private OrderDate deepCopy(OrderDate orderDate){
        OrderDate o=new OrderDate();
        o.setLen(orderDate.getLen());
        o.setType(orderDate.getType());
        o.setPayed(orderDate.isPayed());
        o.setPrice(orderDate.getPrice());
        o.setAddress(orderDate.getAddress());
        o.setId(orderDate.getId());
        o.setTime(orderDate.getTime());


        return o;

    }

    public int toInt(String str){
        int n=0;
        if(str==null && str.length()==0){
            return 0;
        }
        for(int i=0;i<str.length();i++){
            char c=str.charAt(1);
            if(c>'9'||c<'0'){
                return n;
            }

            int num=c-48;
            n=n*10+num;
        }

        return n;
    }
}
