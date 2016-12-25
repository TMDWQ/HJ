package com.micro.android316.housekeeping.CustomControls;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import com.micro.android316.housekeeping.R;

/**
 * Created by Administrator on 2016/12/19.
 */

public class RatingBar extends View{

    private float outR;
    private float inR;
    private Paint paint;
    private int width;
    private int r;
    private Path path;
    private int starNum;
    private int color=Color.YELLOW;
    private  float start=4.5f;
    private boolean isfrist=true;




    public void init(){
        width= (int) getResources().getDimension(R.dimen.star);
        starNum=getResources().getInteger(R.integer.star_num);
        color=getResources().getColor(R.color.yellow_rating);
        r=width/2;
        paint=new Paint();
        paint.setAntiAlias(true);
        paint.setColor(color);
        paint.setStrokeWidth(1);
        outR=width/2 /starNum;
        inR=outR*sin(18)/sin(180-36-18);
    }



    public RatingBar(Context context) {
        super(context);
    }

    public RatingBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RatingBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        init();
        canvas.translate(0, (float) ((r*1.5)/starNum));
        canvas.rotate(-18);

        int i=0;
        for(;i<start-1;i++){
            drawAll(canvas);
        }
        if(start!=i){
            drawHaff(canvas);
            i++;
        }
        for(;i<starNum;i++){
            drawEmpty(canvas);
        }
        

    }

    public void drawHaff(Canvas canvas){
        canvas.rotate(18);
        if(isfrist){
            isfrist=false;
            canvas.translate(r/starNum+3, 0);
        }else {
            canvas.translate(r/starNum * 2 , 0);
        }
        canvas.rotate(-18);
        path = getCompletePath(outR, inR);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(path, paint);
        path = getHalfPath(outR, inR);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawPath(path, paint);
    }
    public void drawAll(Canvas canvas){
        canvas.rotate(18);
        if(isfrist){
            isfrist=false;
            canvas.translate(r / starNum+3, 0);
        }else {
            canvas.translate(r / starNum * 2, 0);
        }
        canvas.rotate(-18);
        path = getCompletePath(outR, inR);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(path, paint);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawPath(path, paint);
    }
    public void drawEmpty(Canvas canvas){
        canvas.rotate(18);
        if(isfrist){
            isfrist=false;
            canvas.translate(r / starNum+3, 0);
        }else {
            canvas.translate(r / starNum * 2, 0);
        }
        canvas.rotate(-18);
        path = getCompletePath(outR, inR);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(path, paint);
    }

    private Path getHalfPath(float outR, float inR) {
        Path path;
        path=new Path();

        path.moveTo(outR*cos(72*4), outR*sin(72*4));

        path.lineTo(inR*cos(72*1+36), inR*sin(72*1+36));
        path.lineTo(outR*cos(72*2), outR*sin(72*2));
        path.lineTo(inR*cos(72*2+36), inR*sin(72*2+36));
        path.lineTo(outR*cos(72*3), outR*sin(72*3));
        path.lineTo(inR*cos(72*3+36), inR*sin(72*3+36));

        path.close();
        return path;
    }

    private Path getCompletePath(float outR, float inR) {
        Path path=new Path();

        path.moveTo(outR*cos(72*0), outR*sin(72*0));

        path.moveTo(outR*cos(72*0), outR*sin(72*0));
        path.lineTo(inR*cos(72*0+36), inR*sin(72*0+36));
        path.lineTo(outR*cos(72*1), outR*sin(72*1));
        path.lineTo(inR*cos(72*1+36), inR*sin(72*1+36));
        path.lineTo(outR*cos(72*2), outR*sin(72*2));
        path.lineTo(inR*cos(72*2+36), inR*sin(72*2+36));
        path.lineTo(outR*cos(72*3), outR*sin(72*3));
        path.lineTo(inR*cos(72*3+36), inR*sin(72*3+36));
        path.lineTo(outR*cos(72*4), outR*sin(72*4));
        path.lineTo(inR*cos(72*4+36), inR*sin(72*4+36));
        path.close();
        return path;
    }



    float cos(int num){
        return (float) Math.cos(num*Math.PI/180);
    }
    float sin(int num){
        return (float) Math.sin(num*Math.PI/180);
    }

    public void setRating(float f){
        this.start=f;
        invalidate();
    }


}

