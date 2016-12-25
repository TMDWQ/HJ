package com.micro.android316.housekeeping.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

/**
 * Created by Administrator on 2016/12/15.
 */

public class PermissionTools {

    /**
     * 检查权限的工具类
     * <p/>
     * Created by wangchenlong on 16/1/26.
     */

        private final Context mContext;

        public PermissionTools(Context context) {
            mContext = context.getApplicationContext();
        }

        // 判断权限集合
        public boolean lacksPermissions(String... permissions) {
            for (String permission : permissions) {
                if (lacksPermission(permission)) {
                    return true;
                }
            }
            return false;
        }

        // 判断是否缺少权限
        private boolean lacksPermission(String permission) {
            return ContextCompat.checkSelfPermission(mContext, permission) ==
                    PackageManager.PERMISSION_DENIED;
        }

}
