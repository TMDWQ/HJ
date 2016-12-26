package com.micro.android316.housekeeping.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.micro.android316.housekeeping.CustomControls.HeadImage;
import com.micro.android316.housekeeping.R;
import com.micro.android316.housekeeping.activity.About;
import com.micro.android316.housekeeping.activity.CommonAddressActivity;
import com.micro.android316.housekeeping.activity.FeedBack;
import com.micro.android316.housekeeping.activity.Online;
import com.micro.android316.housekeeping.activity.PersonalInformationActivity;
import com.micro.android316.housekeeping.activity.Set;
import com.micro.android316.housekeeping.utils.LoadImage;
import com.micro.android316.housekeeping.utils.LoginMessgae;

/**
 * Created by Administrator on 2016/12/12.
 */
public class MineFragment extends Fragment{
    private Context context;
    private HeadImage image;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.mine_fragment,null);
        image= (HeadImage) view.findViewById(R.id.mine_head_img);



        LinearLayout linearLayout= (LinearLayout) view.findViewById(R.id.person_info);
        context=getActivity();
        LoadImage.Load(image, LoginMessgae.getHead(context),context);
        linearLayout.setOnClickListener(listener);
        linearLayout= (LinearLayout) view.findViewById(R.id.message);
        linearLayout.setOnClickListener(listener);
        linearLayout= (LinearLayout) view.findViewById(R.id.set);
        linearLayout.setOnClickListener(listener);
        linearLayout= (LinearLayout) view.findViewById(R.id.guan_yu);
        linearLayout.setOnClickListener(listener);
        linearLayout= (LinearLayout) view.findViewById(R.id.zi_xun);
        linearLayout.setOnClickListener(listener);
        linearLayout= (LinearLayout) view.findViewById(R.id.com_back);
        linearLayout.setOnClickListener(listener);
        linearLayout= (LinearLayout) view.findViewById(R.id.address);
        linearLayout.setOnClickListener(listener);


        return view;
    }

    private View.OnClickListener listener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent;
            switch (v.getId()){
                case R.id.person_info:
                    intent=new Intent(context, PersonalInformationActivity.class);
                    startActivity(intent);
                    break;
                case R.id.message:
                    intent=new Intent(context, com.micro.android316.housekeeping.activity.Message.class);
                    startActivity(intent);
                    break;
                case R.id.set:
                    intent=new Intent(context, Set.class);
                    startActivity(intent);
                    break;
                case R.id.guan_yu:
                    intent=new Intent(context, About.class);
                    startActivity(intent);
                    break;

                case R.id.zi_xun:
                    intent=new Intent(context,Online.class);
                    startActivity(intent);
                    break;

                case R.id.com_back:
                    intent=new Intent(context,FeedBack.class);
                    startActivity(intent);
                    break;
                case R.id.address:
                    intent=new Intent(context,CommonAddressActivity.class);
                    startActivity(intent);
                    break;


            }
        }
    };

    public HeadImage getImage() {
        return image;
    }

    public void setImage(HeadImage image) {
        this.image = image;
    }
}
