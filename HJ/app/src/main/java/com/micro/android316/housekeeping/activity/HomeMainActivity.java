package com.micro.android316.housekeeping.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.micro.android316.housekeeping.R;
import com.micro.android316.housekeeping.fragment.HomePageFragment;
import com.micro.android316.housekeeping.fragment.MainIndentFragment;
import com.micro.android316.housekeeping.fragment.MineFragment;
import com.micro.android316.housekeeping.utils.AppFinal;
import com.micro.android316.housekeeping.utils.LoadImage;
import com.micro.android316.housekeeping.utils.LoginMessgae;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/12.
 */
public class HomeMainActivity extends FragmentActivity{
    public RadioGroup radioGroup;
    RadioButton homePage,homeOrder,homeMine;
    private List<Fragment> fragmentList = new ArrayList<>();
    private ViewPager viewPager;
    public static List<View> views=new ArrayList<>();
    private HomePageFragment homePageFragment;
    private MineFragment mineFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_main);

        radioGroup = (RadioGroup) findViewById(R.id.homepage_radiogroup);
        viewPager = (ViewPager) findViewById(R.id.homepage_viewpager);
        homePage = (RadioButton) findViewById(R.id.home_page);
        homeOrder = (RadioButton) findViewById(R.id.home_order);
        homeMine = (RadioButton) findViewById(R.id.home_mine);

        getFragmentList();
        MyViewPagerFragment myViewPagerFragment = new MyViewPagerFragment(getSupportFragmentManager());
        viewPager.setAdapter(myViewPagerFragment);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //左右滑动的监听
                switch (position){
                    case 0:
                        homePage.setChecked(true);
                        break;
                    case 1:
                        homeOrder.setChecked(true);
                        break;
                    case 2:
                        homeMine.setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                /**
                 * 页面滚动的回调
                 * ViewPager.SCROLL_STATE_IDLE   0  此时viewPager处于闲置状态
                 * ViewPager.SCROLL_STATE_DRAGGING  1  此时viewPager处于拖动状态
                 * ViewPager.SCROLL_STATE_SETTLING  2  此时viewPager处于切换状态
                 */
            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.home_page:
                        viewPager.setCurrentItem(0);
                        homePage.setTextColor(getResources().getColor(R.color.yellow));
                        homeOrder.setTextColor(getResources().getColor(R.color.white));
                        homeMine.setTextColor(getResources().getColor(R.color.white));
                        break;
                    case R.id.home_order:
                        viewPager.setCurrentItem(1);
                        homePage.setTextColor(getResources().getColor(R.color.white));
                        homeOrder.setTextColor(getResources().getColor(R.color.yellow));
                        homeMine.setTextColor(getResources().getColor(R.color.white));
                        break;
                    case R.id.home_mine:
                        viewPager.setCurrentItem(2);
                        homePage.setTextColor(getResources().getColor(R.color.white));
                        homeOrder.setTextColor(getResources().getColor(R.color.white));
                        homeMine.setTextColor(getResources().getColor(R.color.yellow));
                        break;
                }
            }
        });
        AppFinal.activity=this;

    }
    MainIndentFragment mainIndentFragment;
    public void getFragmentList() {
         homePageFragment = new HomePageFragment();
         mainIndentFragment = new MainIndentFragment();
         mineFragment = new MineFragment();
        fragmentList.add(homePageFragment);
        fragmentList.add(mainIndentFragment);
        fragmentList.add(mineFragment);
    }
    public class MyViewPagerFragment extends FragmentStatePagerAdapter{

        public MyViewPagerFragment(FragmentManager fm) {
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

    long time1,time2;
    //重写手机外部返回键的监听
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            time2 = System.currentTimeMillis();
            if(time2 - time1 > 1500)
            {
                Toast.makeText(HomeMainActivity.this, "再次点击退出", Toast.LENGTH_SHORT).show();
                time1 = time2;
                return true;
            }else{
                //此处添加退出整个应用的代码
                finish();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        mainIndentFragment.isNotComment=mainIndentFragment.isFirst=mainIndentFragment.isFirstTree=mainIndentFragment.isFirstTwo=true;
        homePageFragment.setAddress(this);
        mainIndentFragment.reAdd();
        if(mineFragment!=null) {
            if(mineFragment.getImage()!=null)
            LoadImage.Load(mineFragment.getImage(), LoginMessgae.getHead(this), this);
        }
    }
}
