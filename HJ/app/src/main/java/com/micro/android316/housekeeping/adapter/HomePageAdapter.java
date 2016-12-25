package com.micro.android316.housekeeping.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.micro.android316.housekeeping.R;
import com.micro.android316.housekeeping.model.HomePageModel;
import com.micro.android316.housekeeping.utils.LoadImage;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by Administrator on 2016/12/12.
 */
public class HomePageAdapter extends BaseAdapter{
    private List<HomePageModel> modelList = new ArrayList<>();
    private Context context;
    private LayoutInflater inflater;
    ClickListener clickListener;

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public interface ClickListener{
        public void convertClick(int id,String type,float stars);
    }


    public HomePageAdapter(Context context,List<HomePageModel> modelList){
        this.modelList = modelList;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return modelList.size();
    }

    @Override
    public Object getItem(int position) {
        return modelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView==null){
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.home_layout_item,null);
            viewHolder.picture = (ImageView) convertView.findViewById(R.id.home_recommend_pc);
            viewHolder.name = (TextView) convertView.findViewById(R.id.worker_name);
            viewHolder.workRanges = (TextView) convertView.findViewById(R.id.working_range);
            viewHolder.briefInduction = (TextView) convertView.findViewById(R.id.brief_introduction);
            viewHolder.age = (TextView) convertView.findViewById(R.id.worker_age);
            viewHolder.workYears = (TextView) convertView.findViewById(R.id.wk_ys);
            viewHolder.browseTimes = (TextView) convertView.findViewById(R.id.browse_times);
            viewHolder.commentTimes = (TextView) convertView.findViewById(R.id.comment_times);
            viewHolder.ratingBar = (com.micro.android316.housekeeping.CustomControls.RatingBar) convertView.findViewById(R.id.appraise_ratingbar);
            convertView.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) convertView.getTag();
        final HomePageModel homePageModel = modelList.get(position);
        LoadImage.Load(viewHolder.picture,homePageModel.getPicture(),context);
        viewHolder.name.setText(homePageModel.getName());
        viewHolder.workRanges.setText(homePageModel.getWorkRanges());
       // Log.i("hhh",viewHolder.workYears+"--->"+homePageModel.getWorkYears());
        viewHolder.workYears.setText(homePageModel.getWorkYears()+"");
        viewHolder.age.setText(homePageModel.getAge()+"");
        viewHolder.briefInduction.setText(homePageModel.getBriefInduction());
        viewHolder.browseTimes.setText(homePageModel.getBrowseTimes()+"");
        viewHolder.commentTimes.setText(homePageModel.getCommentTimes()+"");
        viewHolder.ratingBar.setRating((float)homePageModel.getNumStars());
        final int id = homePageModel.getId();
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.convertClick(id,homePageModel.getWorkRanges(), (float) homePageModel.getNumStars());
            }
        });
        return convertView;
    }
    private class ViewHolder{
        ImageView picture;
        TextView name,workRanges,briefInduction,age,workYears,browseTimes,commentTimes;
        com.micro.android316.housekeeping.CustomControls.RatingBar ratingBar;
    }
}
