package com.micro.android316.housekeeping.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.micro.android316.housekeeping.R;
import com.micro.android316.housekeeping.fragment.HomePageFragment;
import com.micro.android316.housekeeping.fragment.ToheaderOneFragment;
import com.micro.android316.housekeeping.fragment.ToheaderThreeFragment;
import com.micro.android316.housekeeping.fragment.ToheaderTwoFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/13.
 */
public class ToheaderActivity extends FragmentActivity{
    ViewPager toheaderViewpager;
    List<Fragment> fragmentList = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.toheader_all);
        toheaderViewpager = (ViewPager) findViewById(R.id.toheader_viewpager);
        getFragmentList();
        MineViewPagerFragment mineViewPagerFragment = new MineViewPagerFragment(getSupportFragmentManager());
        toheaderViewpager.setAdapter(mineViewPagerFragment);

//        toheaderViewpager.addOnPageChangeListener();
    }
    public void getFragmentList() {
        ToheaderOneFragment toheaderOneFragment = new ToheaderOneFragment();
        ToheaderTwoFragment toheaderTwoFragment = new ToheaderTwoFragment();
        ToheaderThreeFragment toheaderThreeFragment = new ToheaderThreeFragment();
        fragmentList.add(toheaderOneFragment);
        fragmentList.add(toheaderTwoFragment);
        fragmentList.add(toheaderThreeFragment);
    }
    public class MineViewPagerFragment extends FragmentStatePagerAdapter {

        public MineViewPagerFragment(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }
}
