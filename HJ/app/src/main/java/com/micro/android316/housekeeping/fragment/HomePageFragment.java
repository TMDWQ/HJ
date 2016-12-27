package com.micro.android316.housekeeping.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.micro.android316.housekeeping.R;
import com.micro.android316.housekeeping.activity.Category;
import com.micro.android316.housekeeping.activity.MainActivity;
import com.micro.android316.housekeeping.activity.NannyInformation;
import com.micro.android316.housekeeping.activity.Search;
import com.micro.android316.housekeeping.adapter.HomePageAdapter;
import com.micro.android316.housekeeping.model.HomePageModel;
import com.micro.android316.housekeeping.utils.LoginMessgae;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/12/12.
 */
public class HomePageFragment extends Fragment{
    List<HomePageModel> lists = new ArrayList<>();
    ListView listView;
    HomePageAdapter homePageAdapter;
    RelativeLayout goNurse;
    TextView clean,cooking;
    TextView addressLocation;
    ImageView clearImage,cookingImgae;
    ImageView search;




    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.homepage_listview,null);
        listView = (ListView) view.findViewById(R.id.home_listview);
        View view1 = LayoutInflater.from(getActivity()).inflate(R.layout.homepage_header,null);
        goNurse = (RelativeLayout) view1.findViewById(R.id.go_nurse);
        clean = (TextView) view1.findViewById(R.id.clean_text1);
        cooking = (TextView) view1.findViewById(R.id.cooking_text1);
        addressLocation= (TextView) view1.findViewById(R.id.address_location);
        clearImage= (ImageView) view1.findViewById(R.id.clean);
        cookingImgae= (ImageView) view1.findViewById(R.id.cooking);
        search= (ImageView) view1.findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), Search.class);
                startActivity(intent);
            }
        });
        String ads= LoginMessgae.getAddress(getContext());
        if(ads!=null && !ads.equals("")){
            addressLocation.setText(div(ads));
        }
        goNurse.setOnClickListener(clickListener);
        clean.setOnClickListener(clickListener);
        cooking.setOnClickListener(clickListener);
        clearImage.setOnClickListener(clickListener);
        cookingImgae.setOnClickListener(clickListener);
        listView.addHeaderView(view1);
//        homePageAdapter = new HomePageAdapter(getActivity(),lists);
//        homePageAdapter.setClickListener(new HomePageAdapter.ClickListener() {
//            @Override
//            public void convertClick(int id,String type,float stars) {
//                intent.setClass(getActivity(),NannyInformation.class);
//                intent.putExtra("id",id+"");
//                intent.putExtra("type",type);
//                intent.putExtra("stars",stars);
//                startActivity(intent);
//            }
//        });
        //listView.setAdapter(homePageAdapter);
        addressLocation.setOnClickListener(clickListener);

        new Thread(){
            @Override
            public void run() {
                getData();
            }
        }.start();
        return view;
    }
    Intent intent = new Intent();
    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.go_nurse:
                    intent.setClass(getActivity(), Category.class);
                    intent.putExtra("location",1);
                    startActivity(intent);
                    break;
                case R.id.clean_text1:
                    intent.setClass(getActivity(), Category.class);
                    intent.putExtra("location",2);
                    startActivity(intent);
                    break;
                case R.id.cooking_text1:
                    intent.setClass(getActivity(), Category.class);
                    intent.putExtra("location",3);
                    startActivity(intent);
                    break;
                case R.id.address_location:
                    intent.setClass(getActivity(), MainActivity.class);
                    startActivity(intent);
                    break;
                case R.id.clean:
                    intent.setClass(getActivity(), Category.class);
                    intent.putExtra("location",2);
                    startActivity(intent);
                    break;
                case R.id.cooking:
                    intent.setClass(getActivity(), Category.class);
                    intent.putExtra("location",3);
                    startActivity(intent);
                    break;
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();

    }



    String[] types = {"老人护理","婴幼儿护理","家居保洁","家具保洁","烹饪"};
    public void getData(){
        String string = "http://139.199.196.199/android/index.php/home/index/getalltype";
        try {
            URL url = new URL(string);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("GET");
            http.setConnectTimeout(5000);
            http.connect();
            if(http.getResponseCode() == HttpURLConnection.HTTP_OK){
                lists.clear();
                BufferedReader reader = new BufferedReader(new InputStreamReader(http.getInputStream(),"utf-8"));
                String s;
                StringBuilder stringBuilder = new StringBuilder();
                while((s=reader.readLine())!=null){
                    stringBuilder.append(s);
                }
//                Log.i("data==========>",stringBuilder.toString());
                JSONObject jsonObject = new JSONObject(stringBuilder.toString());
                JSONArray jsonArray = jsonObject.optJSONArray("info");
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject object = jsonArray.optJSONObject(i);
                    HomePageModel homePageModel = new HomePageModel();
                    homePageModel.setId(object.optInt("id"));
                    homePageModel.setName(object.optString("name"));
                    homePageModel.setPicture(object.optString("head"));
                    homePageModel.setAge(object.optInt("age"));
                    homePageModel.setBriefInduction(object.optString("introduction"));
                    homePageModel.setWorkRanges(types[object.optInt("type")-1]);
                    homePageModel.setWorkYears(object.optInt("worktime"));
                    homePageModel.setNumStars(object.optDouble("stars"));
                    homePageModel.setBrowseTimes(object.optInt("follow_count"));
                    homePageModel.setCommentTimes(object.optInt("fabulous"));
//                    Log.i("homePageModel----->",homePageModel.toString());
                    lists.add(homePageModel);
                }
                handler.sendEmptyMessage(0);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            //homePageAdapter.notifyDataSetChanged();
            homePageAdapter = new HomePageAdapter(getActivity(),lists);
            homePageAdapter.setClickListener(new HomePageAdapter.ClickListener() {
                @Override
                public void convertClick(int id,String type,float stars) {
                    intent.setClass(getActivity(),NannyInformation.class);
                    intent.putExtra("id",id+"");
                    intent.putExtra("type",type);
                    intent.putExtra("stars",stars);
                    startActivity(intent);
                }
            });
            listView.setAdapter(homePageAdapter);
            return true;
        }
    });

    public String div(String s){
        String ss="";
        if(s.charAt(0)=='中'&& s.charAt(1)=='国'){
            ss+=s.charAt(2);
            ss+=s.charAt(3);
        }
        return ss;

    }

    public void setAddress(Context context){
        String ads= LoginMessgae.getAddress(context);
        if(ads!=null && !ads.equals("")){
            addressLocation.setText(div(ads));
        }
    }
}
