package com.micro.android316.housekeeping.utils;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.GetMessageFromWX;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXTextObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * Created by Administrator on 2016/12/23.
 */

public class ShareToWeiXin{

    private static final String APP_ID="wxeb75f44c14e4d931";
    private IWXAPI api;
    private Context context;

    public ShareToWeiXin(Context context){
        this.context=context;
        register();
    }

    private void register(){
        api= WXAPIFactory.createWXAPI(context,APP_ID,true);
        api.registerApp(APP_ID);
    }

    public void unregister(){
        api.unregisterApp();
    }

    //BaseReq req,
    private void sendRep( String text){//是第三方app主动发送消息给微信，发送完成之后会切回到第三方app界面
        WXTextObject textObject=new WXTextObject();
        textObject.text=text;
        WXMediaMessage msg=new WXMediaMessage();
        msg.mediaObject=textObject;
        msg.description=text;

        SendMessageToWX.Req req=new SendMessageToWX.Req();
        req.transaction=String.valueOf(System.currentTimeMillis());
        req.message=msg;

        api.sendReq(req);
    }
    //BaseResp resp,
    private void sendResp(String text){//sendResp是微信向第三方app请求数据，第三方app回应数据之后会切回到微信界面。
        WXTextObject textObject=new WXTextObject();
        textObject.text=text;
        WXMediaMessage msg=new WXMediaMessage();
        msg.mediaObject=textObject;
        msg.description=text;

        SendMessageToWX.Resp resp=new SendMessageToWX.Resp();
        resp.transaction= new GetMessageFromWX.Req().transaction;
        api.sendResp(resp);
    }

    public void shareToSession(String text){
        if (!api.isWXAppInstalled()) {
            Toast.makeText(context, "您还未安装微信客户端",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        WXTextObject object=new WXTextObject();
        object.text=text;
        WXMediaMessage msg=new WXMediaMessage();
        msg.mediaObject=object;
        msg.description=text;
        SendMessageToWX.Req req=new SendMessageToWX.Req();
        req.transaction=System.currentTimeMillis()+"";
        req.message=msg;
        req.scene=SendMessageToWX.Req.WXSceneSession;
        api.sendReq(req);
        api.handleIntent(((Activity)context).getIntent(),handler);
        //Log.i("hhh",api+""+api.openWXApp());
    }
    public void shareToTimeline(String text){
        WXTextObject object=new WXTextObject();
        object.text=text;

        WXMediaMessage msg=new WXMediaMessage();
        msg.mediaObject=object;
        msg.description=text;
        SendMessageToWX.Req req=new SendMessageToWX.Req();
        req.transaction=System.currentTimeMillis()+"";
        req.message=msg;
        req.scene=SendMessageToWX.Req.WXSceneTimeline;
        api.sendReq(req);
        api.handleIntent(((Activity)context).getIntent(),handler);
    }

    IWXAPIEventHandler handler=new IWXAPIEventHandler() {
        @Override
        public void onReq(BaseReq baseReq) {
            Log.i("zwzw",baseReq.transaction);
        }

        @Override
        public void onResp(BaseResp baseResp) {
            //Log.i("zwzw",baseResp.toString());
            Log.v("caodongquan", "hello world");
            String result = "";

            switch (baseResp.errCode) {
                case BaseResp.ErrCode.ERR_OK:
                    result = "errcode_success";
                    Toast.makeText(context, "分享成功",
                            Toast.LENGTH_SHORT).show();
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL:
                    result = "errcode_cancel";
                    break;
                case BaseResp.ErrCode.ERR_AUTH_DENIED:
                    result = "errcode_deny";
                    break;
                default:
                    result = "errcode_unknown";
                    break;
            }


        }
    };




}
