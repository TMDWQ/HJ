package com.micro.android316.housekeeping.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.micro.android316.housekeeping.CustomControls.SwitchView;
import com.micro.android316.housekeeping.R;
import com.micro.android316.housekeeping.utils.SystemInfo;


/**
 * Created by Administrator on 2016/12/10.
 */

public class Category extends Activity{

    private SwitchView choice;
    private TextView old;
    private TextView child;
    private TextView title;
    private TextView text;
    private AnimatorSet animatorSet;
    private LinearLayout pager;
    private View.OnClickListener listener;
    private int point=1;
    private  int COLOR1;
    private  int COLOR2;
    private Intent intent;
    private ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SystemInfo.save(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category);
        init();
        setChoiceOnclick();//为自定义的控件设置变化监听
        old.setOnClickListener(listener);
        child.setOnClickListener(listener);
        back.setOnClickListener(listener);
        Intent intent=getIntent();
        int s=intent.getIntExtra("location",0);
        if(s==1){
            forState(1,1);
            choice.setPoint(1);
            choice.setLast(1);
        }else if(s==2){
            forState(1,2);
            choice.setPoint(2);
            choice.setLast(2);
        }else if(s==3){
            forState(1,3);
            choice.setPoint(3);
            choice.setLast(3);
        }


    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void init(){//初始化变量
        COLOR1=getResources().getColor(R.color.txt_1);
        COLOR2=getResources().getColor(R.color.txt_3);
        choice= (SwitchView) findViewById(R.id.choice);
        old= (TextView) findViewById(R.id.old);
        child= (TextView) findViewById(R.id.xh);
        title= (TextView) findViewById(R.id.title);
        text= (TextView) findViewById(R.id.text);
        pager= (LinearLayout) findViewById(R.id.pager);
        back= (ImageView) findViewById(R.id.back);
        listener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.old://左边
                        old.setTextColor(COLOR1);
                        child.setTextColor(COLOR2);
                        switch (point){
                            case 1:
                                intent=new Intent(Category.this,Elderly.class);
                                intent.putExtra("name","老人护理");
                                startActivity(intent);
                                title.setText("关注老年人身体和心理的健康");
                                text.setText("\t\t时下，老年人保健主要是药物、健身，很少有人考虑心理健康问题。但现实" +
                                        "生活中很多老年人由于长期缺乏与人沟通，易产生孤独、自尊感不强和老 而无用的感觉，牢骚越" +
                                        "多越影响心理健康，也不懂得如何调整自己的心态。 " +"\r\t\t"+
                                        "良好的心理素质有益于增强体质，提高抗病能力。对于老人，晚辈不仅要在生活上给予无微不至的关照，" +
                                        "在心理和情感上也要给予必要的抚慰。要陪老人多拉家常，多散心，并要注意多尊敬老人，千万不可随意批评和" +
                                        "顶撞，为了养育儿女，父母的一生经历了数不清的艰辛，当他们人至暮年时，别说所表现出来的“古怪”是有" +
                                        "情可原的，就是确有无理取闹、耍小孩子脾气之处，做晚辈的也应多加忍让，而不可在老人面前造次");
                                break;
                            case 2:
                                intent=new Intent(Category.this,Elderly.class);
                                intent.putExtra("name","家庭保洁");
                                startActivity(intent);
                                title.setText("一切法的自性是不垢不净");
                                text.setText("\t\t打扫卫生是最好的修持方法。一切法的自性是不垢不净的，你感觉脏乱，那些众生也感觉脏乱，这都是你的业障和烦恼，也是众生的业障和烦恼。这些业障和烦恼正好显现在你眼前，你把它们打扫干净了，就消除了自己与众生的烦恼和业障，这才是真正的消业积福，这才是真正的还债。这里有弘扬佛法，有救度众生。你打扫得干干净净了，别人看了也有舒服的感觉，也有欢喜心，这是佛性。");
                                break;
                            case 3:
                                intent=new Intent(Category.this,Elderly.class);
                                intent.putExtra("name","烹饪料理");
                                startActivity(intent);
                                break;

                        }
                        break;
                    case R.id.xh://右边
                        old.setTextColor(COLOR2);
                        child.setTextColor(COLOR1);
                        switch (point){
                            case 1:
                                intent=new Intent(Category.this,Elderly.class);
                                intent.putExtra("name","婴幼儿护理");
                                startActivity(intent);
                                text.setText("\t\t确定宝宝的气质是活泼型、安静型，还是一般型，对于宝宝的养育非常必要；奶水不足的母亲不要急于加代乳品，多吃些能下奶的食物，如鲫鱼、鲢鱼、猪蹄、黄花菜、丝瓜、芝麻、酒糟；给宝宝拍照时，不用闪光灯。");
                                title.setText("新生儿的护理关键");
                                break;
                            case 2:
                                intent=new Intent(Category.this,Elderly.class);
                                intent.putExtra("name","家具保洁");
                                startActivity(intent);
                                title.setText("家电清洁的必要性");
                                text.setText("\t\t家用电器在使用了一段时间之后就容易聚集灰尘，并且这些电器的功能也会下降等，所以必须经常对这些电器进行清洗和消毒。但是如果清洁方法不当的话，则会造成隐患，从而危害家人的健康。例如，家居使用的空调、冰箱和洗衣机。");
                                break;
                            case 3:
                                break;

                        }
                        break;

                    case R.id.back:
                        finish();
                        break;


                }
            }
        };

    }

    private void function(movePoint a, movePoint b, movePoint c){//为自定义的控件SwitchView设置点击时间
        ValueAnimator animatorA=ValueAnimator.ofFloat(a.begin,a.end);
        ValueAnimator animatorB=ValueAnimator.ofFloat(b.begin,b.end);
        ValueAnimator animatorC=ValueAnimator.ofFloat(c.begin,c.end);
        ValueAnimator t1,t2,t3;
        if(a.end>a.begin){
            t1=ValueAnimator.ofFloat(18,34);
        }else if(a.begin==a.end && a.begin==36){
            t1=ValueAnimator.ofFloat(18,18);
        }else if(a.begin>a.end){
            t1=ValueAnimator.ofFloat(34,18);
        }else {
            t1=ValueAnimator.ofFloat(34,34);
        }

        if(b.end>b.begin){
            t2=ValueAnimator.ofFloat(18,34);
        }else if(b.begin==b.end && b.begin==36){
            t2=ValueAnimator.ofFloat(18,18);
        }else if(b.begin>b.end){
            t2=ValueAnimator.ofFloat(34,18);
        }else {
            t2=ValueAnimator.ofFloat(34,34);
        }

        if(c.end>c.begin){
            t3=ValueAnimator.ofFloat(18,34);
        }else if(c.begin==c.end && c.begin==36){
            t3=ValueAnimator.ofFloat(18,18);
        }else if(c.begin>c.end){
            t3=ValueAnimator.ofFloat(34,18);
        }else {
            t3=ValueAnimator.ofFloat(34,34);
        }

        t1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                choice.setSizeA((Float) animation.getAnimatedValue());
            }
        });
        t2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                choice.setSizeB((Float) animation.getAnimatedValue());
            }
        });
        t3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                choice.setSizeC((Float) animation.getAnimatedValue());
            }
        });

        animatorSet=new AnimatorSet();
        animatorA.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                choice.setR1((Float) animation.getAnimatedValue());
                choice.invalidate();
            }
        });
        animatorB.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                choice.setR2((Float) animation.getAnimatedValue());
                choice.invalidate();
            }
        });
        animatorC.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                choice.setR3((Float) animation.getAnimatedValue());
                choice.invalidate();
            }
        });

        animatorSet.play(animatorA).with(animatorB).with(animatorC).with(t1).with(t2).with(t3);
        animatorSet.setDuration(500);
        animatorSet.start();

    }

    private class movePoint{
        float begin,end;

        public void set(float begin, float end) {
            this.begin = begin;
            this.end = end;
        }

    }

    public void setChoiceOnclick(){
        choice.setOnChoiceListener(new SwitchView.OnChoiceListener() {
            @Override
            public void onClick(int state, int last) {
                point=state;
                movePoint a,b,c;
                a=new movePoint();
                b=new movePoint();
                c=new movePoint();
                switch (last){
                    case 1:
                        switch (state){
                            case 1:
                                break;
                            case 2:
                                a.set(60,36);
                                b.set(36,60);
                                c.set(36,36);
                                function(a,b,c);
                                replace(2);
                                break;
                            case 3:
                                a.set(60,36);
                                b.set(36,36);
                                c.set(36,60);
                                function(a,b,c);
                                replace(3);
                                break;
                        }
                        break;
                    case 2:
                        switch (state){
                            case 2:
                                break;
                            case 1:
                                a.set(36,60);
                                b.set(60,36);
                                c.set(36,36);
                                function(a,b,c);
                                replace(1);
                                break;
                            case 3:
                                a.set(36,36);
                                b.set(60,36);
                                c.set(36,60);
                                function(a,b,c);
                                replace(3);
                                break;
                        }
                        break;
                    case 3:
                        switch (state){
                            case 3:
                                break;
                            case 1:
                                a.set(36,60);
                                b.set(36,36);
                                c.set(60,36);
                                function(a,b,c);
                                replace(1);
                                break;
                            case 2:
                                a.set(36,36);
                                b.set(36,60);
                                c.set(60,36);
                                function(a,b,c);
                                replace(2);
                                break;
                        }
                        break;
                }

            }

        });
    }


    public void set(int s){
        switch (s){
            case 1:
                old.setText("老人护理");
                child.setText("婴幼儿护理");
                title.setText("关注老年人身体和心理的健康");
                text.setText("\t\t时下，老年人保健主要是药物、健身，很少有人考虑心理健康问题。但现实生活中很多老年人由于长期缺乏与人沟通，易产生孤独、自尊感不强和老 而无用的感觉，牢骚越多越影响心理健康，也不懂得如何调整自己的心态。 " +"\r\t\t"+
                        "良好的心理素质有益于增强体质，提高抗病能力。对于老人，晚辈不仅要在生活上给予无微不至的关照，在心理和情感上也要给予必要的抚慰。要陪老人多拉家常，多散心，并要注意多尊敬老人，千万不可随意批评和顶撞，为了养育儿女，父母的一生经历了数不清的艰辛，当他们人至暮年时，别说所表现出来的“古怪”是有情可原的，就是确有无理取闹、耍小孩子脾气之处，做晚辈的也应多加忍让，而不可在老人面前造次");
                break;
            case 2:
                old.setText("家庭保洁");
                child.setText("家具保洁");
                title.setText("一切法的自性是不垢不净");
                text.setText("\t\t打扫卫生是最好的修持方法。一切法的自性是不垢不净的，你感觉脏乱，那些众生也感觉脏乱，这都是你的业障和烦恼，也是众生的业障和烦恼。这些业障和烦恼正好显现在你眼前，你把它们打扫干净了，就消除了自己与众生的烦恼和业障，这才是真正的消业积福，这才是真正的还债。这里有弘扬佛法，有救度众生。你打扫得干干净净了，别人看了也有舒服的感觉，也有欢喜心，这是佛性。");
                break;
            case 3:
                old.setText("烹饪料理");
                child.setText("");
                title.setText("合理的膳食搭配有助于身体健康");
                text.setText("\t\t文化代代相传，文明的温润血液在人文传承中得以延续，饮食文化心口相传，由家庭长辈教会，师承相授，厨艺也在其间渲染，通过看、问、学、做，最后人人都爱美食，人人都爱烹饪。\n" +
                        "\t\t美食的诱惑来源于人类的本能，当为温饱而挣扎的时候，似乎毫无味觉可言，只求果腹。当生活品质得以提升时，人们开始不满足于果腹之食，食物触及味蕾，一家人可以惬意的体验美食，舒心、自然。");
                break;
        }
    }


    public void replace(final int i){
        ObjectAnimator animator= ObjectAnimator.ofFloat(pager,"Alpha",1f,0.5f,0f,0f,0.5f,1f);
        animator.setDuration(2000);
        animator.start();

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if((float)(animation.getAnimatedValue())==0f){
                    set(i);
                }
            }
        });
    }

    public void forState(int last,int state){
        point=state;
        movePoint a,b,c;
        a=new movePoint();
        b=new movePoint();
        c=new movePoint();
        switch (last){
            case 1:
                switch (state){
                    case 1:
                        break;
                    case 2:
                        a.set(60,36);
                        b.set(36,60);
                        c.set(36,36);
                        function(a,b,c);
                        replace(2);
                        break;
                    case 3:
                        a.set(60,36);
                        b.set(36,36);
                        c.set(36,60);
                        function(a,b,c);
                        replace(3);
                        break;
                }
                break;
            case 2:
                switch (state){
                    case 2:
                        break;
                    case 1:
                        a.set(36,60);
                        b.set(60,36);
                        c.set(36,36);
                        function(a,b,c);
                        replace(1);
                        break;
                    case 3:
                        a.set(36,36);
                        b.set(60,36);
                        c.set(36,60);
                        function(a,b,c);
                        replace(3);
                        break;
                }
                break;
            case 3:
                switch (state){
                    case 3:
                        break;
                    case 1:
                        a.set(36,60);
                        b.set(36,36);
                        c.set(60,36);
                        function(a,b,c);
                        replace(1);
                        break;
                    case 2:
                        a.set(36,36);
                        b.set(36,60);
                        c.set(60,36);
                        function(a,b,c);
                        replace(2);
                        break;
                }
                break;
        }

    }





}
