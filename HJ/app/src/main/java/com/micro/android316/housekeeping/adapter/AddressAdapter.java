package com.micro.android316.housekeeping.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.micro.android316.housekeeping.R;
import com.micro.android316.housekeeping.model.AddressCommon;

import java.util.List;
/**
 * Created by Administrator on 2016/12/13.
 */
public abstract class AddressAdapter extends BaseAdapter{
    private  List<AddressCommon> addressList;
    private  Context context;
    private  LayoutInflater layoutInflater;
    private String id;


    public AddressAdapter(Context context,List<AddressCommon> addressList){
        this.context=context;
        this.addressList=addressList;
        this.layoutInflater=LayoutInflater.from(context);
    }
    @Override
    public int getCount() {

        return addressList.size();
    }

    @Override
    public Object getItem(int position) {

        return addressList.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if(convertView==null){
            viewHolder=new ViewHolder();
            convertView=layoutInflater.inflate(R.layout.listview_item_address,null);
            viewHolder.addressTv= (TextView) convertView.findViewById(R.id.item_address);
            viewHolder.phoneTv= (TextView) convertView.findViewById(R.id.item_address_phone);
            viewHolder.deleteTv= (TextView) convertView.findViewById(R.id.item_address_delete);
            convertView.setTag(viewHolder);
        }else {
           viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.addressTv.setText(addressList.get(position).getAddress());
        viewHolder.phoneTv.setText(addressList.get(position).getTel());
        id=addressList.get(position).getId();
        viewHolder.deleteTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOnclick(id,position);
            }
        });
        return convertView;
    }

    private class ViewHolder{
        TextView phoneTv;
        TextView addressTv;
        TextView deleteTv;

    }

    public abstract void setOnclick(String id,int position);



}
