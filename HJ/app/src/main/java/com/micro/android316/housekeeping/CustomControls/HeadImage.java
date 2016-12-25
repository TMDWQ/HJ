package com.micro.android316.housekeeping.CustomControls;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2016/12/24.
 */

public class HeadImage extends View {
    private Paint paint;
    private Bitmap bitmap;

    public HeadImage(Context context) {
        super(context);
        init();
    }

    public HeadImage(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HeadImage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(bitmap==null){
            canvas.drawCircle(60,60,60,paint);
            return ;
        }

        canvas.drawCircle(60,60,60,paint);
        //canvas.drawARGB(0, 0, 0, 0); // 填充整个Canvas
        setLayerType(LAYER_TYPE_SOFTWARE, null);//注意，一定要禁用硬件加速器
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap,0,0,paint);
        paint.setXfermode(null);
//        paint.setStyle(Paint.Style.STROKE);
//        paint.setStrokeWidth(5);
//        canvas.drawCircle(60,60,60,paint);



    }
    public void init(){
        paint=new Paint();
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(1);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(125,125);
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
        invalidate();
    }
}
