package com.micro.android316.housekeeping;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.micro.android316.housekeeping.utils.AlbumTools;
import com.micro.android316.housekeeping.utils.ImageCat;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        AlbumTools.openAlbum(this,100);
        imageView= (ImageView) findViewById(R.id.image);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 100:
                ImageCat.cat(data.getData(),100,100,this,101);
                break;
            case 101:
                Bitmap bitmap=ImageCat.getBitmap(data);
                if(bitmap!=null)
                imageView.setImageBitmap(bitmap);
                break;
        }



    }
}
