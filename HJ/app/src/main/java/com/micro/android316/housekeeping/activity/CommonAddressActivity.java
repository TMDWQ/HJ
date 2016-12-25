package com.micro.android316.housekeeping.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.micro.android316.housekeeping.R;
import com.micro.android316.housekeeping.adapter.AddressAdapter;
import com.micro.android316.housekeeping.model.AddressCommon;
import com.micro.android316.housekeeping.utils.HttpTools;
import com.micro.android316.housekeeping.utils.LoginMessgae;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/13.
 */
public class CommonAddressActivity extends Activity{
    private ListView listView;
    private final String URL_GET_ADDRESS="http://139.199.196.199/android/index.php/home/index/getaddressfortel?tel=";
    private final String URL_DELETE_ADDRESS="http://139.199.196.199/android/index.php/home/index/deleteaddress?id=";
    private final int GET_SUCCESS=1;
    private final int GET_ERROR=-1;
    private final int DETELE_SUCCESS=2;
    private final int DELETE_EORROR=-2;
    private List<AddressCommon> list=new ArrayList<>();
    private AddressAdapter adapter;
    private TextView textView;
    private int position=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailed_address);
        listView= (ListView) findViewById(R.id.listView_address);
        textView= (TextView) findViewById(R.id.is_null);
        textView.setVisibility(View.GONE);
        getAddress();

    }



    private void getAddress(){
        Log.i("hhhaaa",URL_GET_ADDRESS+ LoginMessgae.getTel(this));
        HttpTools tools=new HttpTools(URL_GET_ADDRESS+ LoginMessgae.getTel(this)) {

            @Override
            public void post(String line) {
                try {
                    Log.i("hhhaaa",line);
                    JSONObject object=new JSONObject(line);
                    if(object.optInt("code",0)==1){
                        JSONArray array=object.getJSONArray("info");
                        for(int i=0;i<array.length();i++){
                            object=array.getJSONObject(i);
                            AddressCommon common=new AddressCommon();
                            common.setAddress(div(object.optString("address","")));
                            common.setId(object.optString("id",""));
                            common.setTel(gonePhone(object.optString("tel","")));
                            list.add(common);
                        }
                        handler.sendEmptyMessage(GET_SUCCESS);
                    }else {
                        handler.sendEmptyMessage(GET_ERROR);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        tools.runForGet();
    }

    private Handler handler=new Handler(){

        public void handleMessage(android.os.Message message){
            switch (message.what){
                case GET_SUCCESS:
                    adapter= new AddressAdapter(CommonAddressActivity.this, list) {
                        @Override
                        public void setOnclick(String id,int position) {
                            delete(id);
                            CommonAddressActivity.this.position=position;
                        }
                    };
                    listView.setAdapter(adapter);
                    break;
                case GET_ERROR:
                    textView.setVisibility(View.VISIBLE);
                    break;
                case DETELE_SUCCESS:
                    if(position!=-1){
                        list.remove(position);
                    }
                    adapter.notifyDataSetChanged();
                    Toast.makeText(CommonAddressActivity.this,"删除成功",Toast.LENGTH_SHORT).show();
                    if (list.size()==0){
                        textView.setVisibility(View.VISIBLE);
                    }
                    break;
                case DELETE_EORROR:
                    Toast.makeText(CommonAddressActivity.this,"删除失败",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    public String div(String str){
        String s="";
        boolean b=false;
        for(int i=0;i<str.length();i++){
            char c=str.charAt(i);
            if(b){
                s+=c;
            }
            if(c==':'){
                b=true;
            }
        }
        if(b){
            return s;
        }else {
            return str;
        }

    }

    public String gonePhone(String str){
        if(str.length()!=11){
            return str;
        }
        String s="";
        for(int i=0;i<str.length();i++){
            if(i<3 || i>7){
                s+=str.charAt(i);
            }else {
                s+="*";
            }

        }
        return s;
    }

    public void delete(String id){
        HttpTools tools=new HttpTools(URL_DELETE_ADDRESS+id) {
            @Override
            public void post(String line) {
                try {
                    JSONObject object=new JSONObject(line);
                    if(object.getString("code").equals("1")){
                        handler.sendEmptyMessage(DETELE_SUCCESS);
                    }else {
                        handler.sendEmptyMessage(DELETE_EORROR);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        tools.runForGet();
    }


}
