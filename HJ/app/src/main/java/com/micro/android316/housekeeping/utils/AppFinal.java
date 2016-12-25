package com.micro.android316.housekeeping.utils;

import android.app.Activity;
import android.app.Application;

/**
 * Created by Administrator on 2016/12/10.
 */

public class AppFinal extends Application {
    public static SaveInMemery memery=SaveInMemery.getMemoryCache(50);
    private static AppFinal appFinal;
    public static Activity activity;

    private AppFinal(){};
    public static AppFinal getAppfinal(){
        if(appFinal==null){
            appFinal=new AppFinal();
        }
        return appFinal;
    }


}
