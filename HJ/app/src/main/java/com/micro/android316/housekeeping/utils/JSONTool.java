package com.micro.android316.housekeeping.utils;

import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by Administrator on 2016/12/14.
 * 作者:张文
 *   作用：将数据加载到list中。
 *    参数说明：c,model类对象。
 *              s,model里面的set,方法的名字
 *              ss,值
 *    例子： s={"Name","Age"},ss就必须为ss={"zhangsan","24"}
 *          做的操作为 setName("zhangsan"),setAge("24")
 *
 *
 */



public class JSONTool {//                    set字段        value字段
    public static void load(List list,Class c,String [] s,String []ss){
        if(s.length!=ss.length)
            return;
        Method[] methods=c.getMethods();
        try {
            Object object=c.newInstance();
            for(int i=0;i<methods.length;i++){
                Method method=methods[i];
                String name=method.getName();
                Log.i("tool","method="+name);
                for(int j=0;j<s.length;j++){
                    if(("set"+s[j]).equals(name)){
                        method.invoke(object,ss[j]);
                        j=s.length;
                    }
                }

            }
            Log.i("tool","object="+object.toString());
            list.add(object);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


    }

}
