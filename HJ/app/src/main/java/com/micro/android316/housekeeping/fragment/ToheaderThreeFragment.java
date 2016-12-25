package com.micro.android316.housekeeping.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.micro.android316.housekeeping.R;
import com.micro.android316.housekeeping.activity.GuideActivity;

/**
 * Created by Administrator on 2016/12/13.
 */
public class ToheaderThreeFragment extends Fragment{
    TextView tasteNow;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.toheader_three,null);
        tasteNow = (TextView) view.findViewById(R.id.taste_now);
        tasteNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GuideActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        return view;
    }

}
