package com.micro.android316.housekeeping.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.*;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.micro.android316.housekeeping.R;
import com.micro.android316.housekeeping.utils.SimpleSearchTools;

import java.util.List;

/**
 * Created by Administrator on 2016/12/27.
 */

public class Search extends Activity {


    private EditText editText;
    private ListView listView;
    private List<com.micro.android316.housekeeping.model.Search> list;
    private final int REQUEST_FIRST=1;
    private final int REQUEST=-1;
    private boolean isFirst=true;
    private SimpleSearchTools searchTools;
    private LayoutInflater inflater;
    private String types[]={"老人护理","婴幼儿护理","家居保洁","家具保洁","烹饪料理"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        init();
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.i("search",s.toString());
                if(isFirst) {
                    searchTools.setWordKey(s.toString());
                    boolean bool = searchTools.search(handler, REQUEST_FIRST);
                    isFirst=!bool;
                    Log.i("search",bool+"");
                    return;
                }
                searchTools.setWordKey(s.toString());
                searchTools.search(handler,REQUEST);

            }
        });
    }

    private void init(){
        editText= (EditText) findViewById(R.id.edit_search);
        listView= (ListView) findViewById(R.id.list_search);
        searchTools=new SimpleSearchTools();
        inflater=LayoutInflater.from(this);
    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case REQUEST_FIRST:
                    list=searchTools.getList();
                    listView.setAdapter(adapter);
                    break;
                case REQUEST:
                    list=searchTools.getList();
                    adapter.notifyDataSetChanged();
                    break;
            }

        }
    };

    private BaseAdapter adapter=new BaseAdapter() {
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            convertView=inflater.inflate(R.layout.search_item,null);
            TextView name,type;
            name= (TextView) convertView.findViewById(R.id.name);
            type= (TextView) convertView.findViewById(R.id.type);
            name.setText(list.get(position).getName());
            char c=list.get(position).getType().charAt(0);
            final int i=c-48;
            type.setText(types[i-1]);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(Search.this,NannyInformation.class);
                    /**
                     *   ID=intent.getStringExtra("id");
                     type=intent.getStringExtra("type");
                     float stars=intent.getFloatExtra("stars",0);*/
                    intent.putExtra("id",list.get(position).getId());
                    intent.putExtra("type",types[i-1]);
                    intent.putExtra("stars",list.get(position).getStars());
                    startActivity(intent);
                }
            });
            return convertView;
        }
    };

}
