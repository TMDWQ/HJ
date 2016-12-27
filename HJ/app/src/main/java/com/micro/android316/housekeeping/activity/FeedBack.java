package com.micro.android316.housekeeping.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.micro.android316.housekeeping.R;
import com.micro.android316.housekeeping.utils.AlbumTools;
import com.micro.android316.housekeeping.utils.LoginMessgae;

/**
 * Created by Administrator on 2016/12/12.
 */
public class FeedBack extends Activity {
    private TextView commit;
    private ImageView add,back;
    private EditText idea,phone,editText;
    private String strIdea,strPhone;
    private final int REQUEST_CODE=100;
    private View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.img_feedback_back:
                    finish();
                    break;
                case R.id.img_feedback_add:
                    AlbumTools.openAlbum(FeedBack.this,REQUEST_CODE);

                    break;
                case R.id.textview_feedback_commit:
                    strIdea=idea.getText().toString();
                    strPhone=phone.getText().toString();
                    if(strIdea.length()==0 || strPhone.length()==0){
                        Toast.makeText(FeedBack.this,"你的评论内容不能为空",Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Toast.makeText(FeedBack.this,"发送成功,正在返回请稍候...",Toast.LENGTH_LONG).show();
                    new Thread(){
                       public void run(){
                           try {
                               Thread.sleep(3000);
                               finish();
                           } catch (InterruptedException e) {
                               e.printStackTrace();
                           }
                       }
                    }.start();



                    break;
            }
        }
    };
//    private Handler handler=new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//           switch (msg.arg1){
//               case 1:
//                   Toast.makeText(FeedBack.this,msg.obj.toString(),Toast.LENGTH_LONG).show();
//                   break;
//               case -1:
//                   Toast.makeText(FeedBack.this,msg.obj.toString(),Toast.LENGTH_SHORT).show();
//                   break;
//           }
//        }
//    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        init();
        phone.setText(LoginMessgae.getTel(this));
    }
    public void init(){
        commit=(TextView)findViewById(R.id.textview_feedback_commit);
        back=(ImageView)findViewById(R.id.img_feedback_back);
        add=(ImageView)findViewById(R.id.img_feedback_add);
        idea=(EditText)findViewById(R.id.edit_feedback_idea);
        phone=(EditText)findViewById(R.id.edit_feedback_phone);
        editText= (EditText) findViewById(R.id.edit);
        //img_feedback_add

        add.setOnClickListener(onClickListener);
        back.setOnClickListener(onClickListener);
        commit.setOnClickListener(onClickListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data==null){
            return;
        }
        String path=AlbumTools.choose(data,this);
        add.setPadding(0,0,0,0);
        //add.setImageBitmap(BitmapFactory.decodeFile(path));
        AlbumTools.narrowPhoto(add,path,100);
        editText.setEnabled(true);
    }
}
