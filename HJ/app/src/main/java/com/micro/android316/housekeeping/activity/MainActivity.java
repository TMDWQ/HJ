package com.micro.android316.housekeeping.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.micro.android316.housekeeping.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private String permissionInfo;
    private final int SDK_PERMISSION_REQUEST = 127;
    private BitmapDescriptor mCurrentMarker;
    private Location location;
    private Button gaoQing,normal,weiXing;
    private boolean isOFF=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        getPermission();
        mMapView = (MapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        //mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
        init();
        go();
        chooice();
    }

    public void init(){
        gaoQing= (Button) findViewById(R.id.good);
        weiXing= (Button) findViewById(R.id.wei_xing);
        normal= (Button) findViewById(R.id.normal);
    }

    public void chooice(){
        View.OnClickListener onClickListener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.good:
                        //普通地图
                        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                        gaoQing.setTextColor(Color.RED);
                        weiXing.setTextColor(Color.WHITE);
                       // normal.setTextColor(Color.WHITE);
                        gaoQing.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.map),null,null,null);
                        weiXing.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.wei_xing_no),null,null,null);

                        break;
                    case R.id.wei_xing:
                        //卫星地图
                        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
                        gaoQing.setTextColor(Color.WHITE);
                        weiXing.setTextColor(Color.RED);
                        gaoQing.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.map_no),null,null,null);
                        weiXing.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.wei_xing),null,null,null);
                      //  normal.setTextColor(Color.WHITE);
                        break;
                    case  R.id.normal:
                        //显示路况
                        if(isOFF){
                            mBaiduMap.setTrafficEnabled(true);
                            mBaiduMap.setBaiduHeatMapEnabled(true);
                            normal.setTextColor(Color.RED);
                            normal.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.dao_lu),null,null,null);
                            isOFF=false;
                            return;
                        }
                        normal.setTextColor(Color.WHITE);
                        mBaiduMap.setTrafficEnabled(false);
                        mBaiduMap.setBaiduHeatMapEnabled(false);
                        normal.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.dao_lu_no),null,null,null);
                        isOFF=true;
                        break;
                }
            }
        };
        gaoQing.setOnClickListener(onClickListener);
        weiXing.setOnClickListener(onClickListener);
        normal.setOnClickListener(onClickListener);
    }

    @TargetApi(23)
    private void getPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ArrayList<String> permissions = new ArrayList<String>();
            /***
             * 定位权限为必须权限，用户如果禁止，则每次进入都会申请
             */
            // 定位精确位置
            if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }
            if(checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            }
			/*
			 * 读写权限和电话状态权限非必要权限(建议授予)只会申请一次，用户同意或者禁止，只会弹一次
			 */
            // 读写权限
            if (addPermission(permissions, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                permissionInfo += "Manifest.permission.WRITE_EXTERNAL_STORAGE Deny \n";
            }
            // 读取电话状态权限
            if (addPermission(permissions, Manifest.permission.READ_PHONE_STATE)) {
                permissionInfo += "Manifest.permission.READ_PHONE_STATE Deny \n";
            }


            if (permissions.size() > 0) {
                requestPermissions(permissions.toArray(new String[permissions.size()]), SDK_PERMISSION_REQUEST);
            }
        }
    }

    @TargetApi(23)
    private boolean addPermission(ArrayList<String> permissionsList, String permission) {
        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) { // 如果应用没有获得对应权限,则添加到列表中,准备批量申请
            if (shouldShowRequestPermissionRationale(permission)){
                return true;
            }else{
                permissionsList.add(permission);
                return false;
            }

        }else{
            return true;
        }
    }

    @TargetApi(23)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // TODO Auto-generated method stub
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
        location.unRegister();

    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

    public void LocationForNow(BDLocation location){
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
// 构造定位数据
        MyLocationData locData = new MyLocationData.Builder()
                .accuracy(location.getRadius())
                // 此处设置开发者获取到的方向信息，顺时针0-360
                .direction(100).latitude(location.getLatitude())
                .longitude(location.getLongitude()).build();
// 设置定位数据
// 设置定位数据
// 设置定位数据

        mBaiduMap.setMyLocationData(locData);
// 设置定位图层的配置（定位模式，是否允许方向信息，用户自定义定位图标）
        mCurrentMarker = BitmapDescriptorFactory
                .fromResource(R.mipmap.ic_launcher);
        MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL, true, mCurrentMarker);
        //mBaiduMap.setMyLocationConfiguration();
        mBaiduMap.setMyLocationConfigeration(config);
// 当不需要定位图层时关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
    }

    public void go(){
        location=new Location() {
            @Override
            public void success(BDLocation l) {
                //LocationForNow(l);
                move(l);

            }
        };
        location.onCreate(this);
        location.start();

    }

    public void move(BDLocation location){
        //设定中心点坐标
        //Toast.makeText(this,location.getLatitude()+","+location.getLongitude(),Toast.LENGTH_LONG).show();
        LatLng cenpt = new LatLng(location.getLatitude(),location.getLongitude());
        //定义地图状态
        MapStatus mMapStatus = new MapStatus.Builder()
                .target(cenpt)
                .zoom(18)
                .build();
        //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化


        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        //改变地图状态
        mBaiduMap.setMapStatus(mMapStatusUpdate);

    }

}
