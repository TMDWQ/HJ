package com.micro.android316.housekeeping.CustomControls;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.micro.android316.housekeeping.R;
import com.micro.android316.housekeeping.utils.SystemInfo;


/**
 * Created by Administrator on 2016/12/9.
 */

public class SwitchView extends View{

    private Paint paint;
    private float height;
    private float width=84*2;
    private MidPoint point1,point2,point3;
    private int point=1;
    private float r1=60,r2=36,r3=36;
    private int white,yellow,green,txt1,txt2;
    private float onclickX,onclickY;
    private OnChoiceListener choiceListener;
    private int last=1;
    private float sizeA=34,sizeB=18,sizeC=18;


    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public SwitchView(Context context) {
        super(context);
        init(context);

    }

    public SwitchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SwitchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(getResources().getColor(R.color.zw_deep_green));

        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(5);
        Log.i("hhh",height+"");
        canvas.drawRect(0,0,84*2,height,paint);
        switch (point){
            case 1:
                drawOval(canvas,point1,r1,white,yellow);
                drawOval(canvas,point2,r2,green,white);
                drawOval(canvas,point3,r3,green,white);
                MidPoint midPoint=new MidPoint(point1.x-sizeA,point1.y+sizeA/2);
                drawText(midPoint,canvas,sizeA,txt1,"护理");
                midPoint=new MidPoint(point2.x-sizeB,point2.y);
                drawText(midPoint,canvas,sizeB,txt2,"家居");
                midPoint=new MidPoint(point2.x-sizeB,point2.y+sizeB);
                drawText(midPoint,canvas,sizeB,txt2,"清洁");
                midPoint=new MidPoint(point3.x-sizeC,point3.y);
                drawText(midPoint,canvas,sizeC,txt2,"日常");
                midPoint=new MidPoint(point3.x-sizeC,point3.y+sizeC);
                drawText(midPoint,canvas,sizeC,txt2,"烹饪");
                break;
            case 2:
                drawOval(canvas,point2,r2,white,yellow);
                drawOval(canvas,point1,r1,green,white);
                drawOval(canvas,point3,r3,green,white);

                midPoint=new MidPoint(point1.x-sizeA,point1.y+sizeA/2);
                drawText(midPoint,canvas,sizeA,txt2,"护理");
                midPoint=new MidPoint(point2.x-sizeB,point2.y);
                drawText(midPoint,canvas,sizeB,txt1,"家居");
                midPoint=new MidPoint(point2.x-sizeB,point2.y+sizeB);
                drawText(midPoint,canvas,sizeB,txt1,"清洁");
                midPoint=new MidPoint(point3.x-sizeC,point3.y);
                drawText(midPoint,canvas,sizeC,txt2,"日常");
                midPoint=new MidPoint(point3.x-sizeC,point3.y+sizeC);
                drawText(midPoint,canvas,sizeC,txt2,"烹饪");
                break;
            case 3:
                drawOval(canvas,point3,r3,white,yellow);
                drawOval(canvas,point1,r1,green,white);
                drawOval(canvas,point2,r2,green,white);

                midPoint=new MidPoint(point1.x-sizeA,point1.y+sizeA/2);
                drawText(midPoint,canvas,sizeA,txt2,"护理");
                midPoint=new MidPoint(point2.x-sizeB,point2.y);
                drawText(midPoint,canvas,sizeB,txt1,"家居");
                midPoint=new MidPoint(point2.x-sizeB,point2.y+sizeB);
                drawText(midPoint,canvas,sizeB,txt1,"清洁");
                midPoint=new MidPoint(point3.x-sizeC,point3.y);
                drawText(midPoint,canvas,sizeC,txt2,"日常");
                midPoint=new MidPoint(point3.x-sizeC,point3.y+sizeC);
                drawText(midPoint,canvas,sizeC,txt2,"烹饪");

                break;

        }
    }

    private void init(Context context){
        paint=new Paint();
        height= SystemInfo.getHeight(context);
        width= SystemInfo.getWidth(context)/4;
        point1=new MidPoint(width/2,height/7+r1);
        point2=new MidPoint(width/2,(height*2.5f)/7+r2);
        point3=new MidPoint(width/2,(height*4f)/7+r3);
        white=getResources().getColor(R.color.zw_white);
        yellow=getResources().getColor(R.color.zw_yellow);
        green=getResources().getColor(R.color.zw_green);
        txt1=getResources().getColor(R.color.txt_1);
        txt2=getResources().getColor(R.color.txt_2);
    }
  //float left, float top, float right, float bottom, @NonNull Paint paint

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension((int) width,(int) height);
    }
   private class  MidPoint{
        float x;
        float y;
        MidPoint(float x,float y){
            this.x=x;
            this.y=y;
        }

       @Override
       public String toString() {
           return "x="+x+";y="+y;
       }
   }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        MidPoint begin,end;
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                 Log.i("hhh","p1("+point1+");p2=("+point2+");p3=("+point3+")");
                end=new MidPoint(event.getX(),event.getY());
                switch (ischoice(end)){
                    case 1:
                        point=1;
                        choiceListener.onClick(point,last);
                        last=point;
                        invalidate();
                        break;
                    case 2:
                        point=2;
                        choiceListener.onClick(point,last);
                        last=point;
                        invalidate();
                        break;
                    case 3:
                        point=3;
                        choiceListener.onClick(point,last);
                        last=point;
                        invalidate();
                        break;
                }
                break;
        }

        return true;
    }


    private void drawOval(Canvas canvas, MidPoint midPoint, float r, int w, int n){
        paint.setColor(w);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(5);
        float left=midPoint.x-r;
        float top=midPoint.y-r;
        float right=midPoint.x+r;
        float bottom=midPoint.y+r;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            canvas.drawOval(left,top,right,bottom,paint);
//        }
        canvas.drawCircle(midPoint.x,midPoint.y,r,paint);
        //canvas.drawCircle();
        paint.setColor(n);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            canvas.drawOval(left+10,top+10,right-10,bottom-10,paint);
//        }
        canvas.drawCircle(midPoint.x,midPoint.y,r-10,paint);
    }

    private void drawText(MidPoint midPoint,Canvas canvas,float size,int color,String conten){
        paint.setColor(color);
        paint.setTextSize(size);
        canvas.drawText(conten,midPoint.x,midPoint.y,paint);
    }

    private int ischoice(MidPoint po){
        if(Math.abs(point1.x-po.x)<r1&&Math.abs(point1.y-po.y)<r1){
            return 1;
        }else if(Math.abs(point2.x-po.x)<r2&&Math.abs(point2.y-po.y)<r2){
            return 2;
        }else if (Math.abs(point3.x-po.x)<r3&&Math.abs(point3.y-po.y)<r3){
            return 3;
        }
        return 0;
    }


    public interface OnChoiceListener{
        public void onClick(int state, int last);
    }

    public void setOnChoiceListener(OnChoiceListener listener){
        choiceListener=listener;
    }


    public float getR1() {
        return r1;
    }

    public void setR1(float r1) {
        this.r1 = r1;
    }

    public float getR2() {
        return r2;
    }

    public void setR2(float r2) {
        this.r2 = r2;
    }

    public float getR3() {
        return r3;
    }

    public void setR3(float r3) {
        this.r3 = r3;
    }

    public void setSizeA(float sizeA) {
        this.sizeA = sizeA;
    }

    public void setSizeB(float sizeB) {
        this.sizeB = sizeB;
    }

    public void setSizeC(float sizeC) {
        this.sizeC = sizeC;
    }

    public int getLast() {
        return last;
    }

    public void setLast(int last) {
        this.last = last;
    }
}
