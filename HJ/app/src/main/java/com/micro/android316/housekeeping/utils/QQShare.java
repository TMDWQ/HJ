package com.micro.android316.housekeeping.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.tencent.connect.share.QzoneShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

/**
 * Created by Administrator on 2016/12/20.
 */

public class QQShare{

    public static void shar(Context context){
        Tencent tencent=Tencent.createInstance("798503604",context);
        Bundle params = new Bundle();

        params.putString(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, String.valueOf(QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT));
        params.putString(QzoneShare.SHARE_TO_QQ_TITLE, "标题");//必填
        params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, "摘要");//选填
        params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, "http://www.baidu.com");//必填
        // params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, "图片链接ArrayList");
        tencent.shareToQzone((Activity) context, params, new IUiListener() {
            @Override
            public void onComplete(Object o) {

            }

            @Override
            public void onError(UiError uiError) {
                Log.e("hhh",uiError.errorMessage);
            }

            @Override
            public void onCancel() {

            }
        });

    }


}
