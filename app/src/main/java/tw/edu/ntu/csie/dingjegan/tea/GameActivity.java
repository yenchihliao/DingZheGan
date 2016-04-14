package tw.edu.ntu.csie.dingjegan.tea;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class GameActivity  extends AppCompatActivity {
    //TextView testNumber;
    int lastClick;
    Button[] testBtn;
    Float[] speed;
    ImageView flashB1;
    ImageView flashB2;
    TextView score;
    Animation fadein;
    int nTime;
    String scoreStr;
    Handler mHandlerTime = new Handler();
    Handler TransBtn1 = new Handler();
    Handler DieHandle = new Handler();
    Random randomer = new Random();
//    Animation translate = new TranslateAnimation( -200, 0, 0, 0);
    private final Runnable timerRun = new Runnable() {
        public void run()
        {
            ++nTime;
            scoreStr = ""+((nTime/60>9)? nTime/60:"0"+nTime/60)+":"+((nTime%60>9)? (nTime%60):"0"+(nTime%60));
            score.setText(scoreStr);
            mHandlerTime.postDelayed(this, 1000);
        }
    };
    private final Runnable testBtn1R = new Runnable() {
        public void run() {
            for (int i = 0; i < 6; i++){
                testBtn[i].animate().setInterpolator(new LinearInterpolator());
                testBtn[i].animate().setDuration(2000);
                testBtn[i].animate().translationYBy(speed[i]);
                testBtn[i].animate().start();
            }
            TransBtn1.postDelayed(this, 2000);
        }
    };
    private final Runnable AfterClick = new Runnable() {
        public void run() {
                    speed[lastClick] = randomer.nextFloat()*50+20;
                    testBtn[lastClick].animate().setInterpolator(new LinearInterpolator());
                    testBtn[lastClick].animate().setDuration(2000);
                    testBtn[lastClick].animate().translationYBy(speed[lastClick]);
                    testBtn[lastClick].animate().start();
        }
    };
    private final Runnable getYTranslate = new Runnable() {
        public void run() {
            for (int i = 0; i < 6; i++) {
                Float btn1 = testBtn[i].getTranslationY();
                testBtn[i].setText(btn1.toString());
            }
            DieHandle.postDelayed(this, 50);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        testBtn = new Button[6];
        speed = new Float[6];
        testBtn[0] = (Button) findViewById(R.id.TestBtn1);
        testBtn[1] = (Button) findViewById(R.id.TestBtn2);
        testBtn[2] = (Button) findViewById(R.id.TestBtn3);
        testBtn[3] = (Button) findViewById(R.id.TestBtn4);
        testBtn[4] = (Button) findViewById(R.id.TestBtn5);
        testBtn[5] = (Button) findViewById(R.id.TestBtn6);
        for(int i = 0; i < 6; i++){
            speed[i] = randomer.nextFloat()*50+20;
        }
        flashB1 = (ImageView) findViewById(R.id.flashBar1);
        flashB2 = (ImageView) findViewById(R.id.flashBar2);
        fadein = new AlphaAnimation(0.1f,1.0f);
        fadein.setDuration(800);
        fadein.setRepeatCount(Animation.INFINITE);
        fadein.setRepeatMode(Animation.REVERSE);
        flashB1.setAnimation(fadein);
        flashB2.setAnimation(fadein);
        fadein.startNow();
        score = (TextView) findViewById(R.id.yourScore);
        scoreStr = "00:00";
        score.setText(scoreStr);
        nTime = 0;
        mHandlerTime.postDelayed(timerRun, 1000);
        TransBtn1.postDelayed(testBtn1R, 200);
        DieHandle.postDelayed(getYTranslate,50);
        testBtn[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastClick = 0;
                testBtn[0].animate().cancel();
                testBtn[0].animate().setDuration(100);
                testBtn[0].animate().translationYBy(-40);
                testBtn[0].animate().start();
                TransBtn1.postDelayed(AfterClick, 120);
            }
        });
        testBtn[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastClick = 1;
                testBtn[1].animate().cancel();
                testBtn[1].animate().setDuration(100);
                testBtn[1].animate().translationYBy(-40);
                testBtn[1].animate().start();
                TransBtn1.postDelayed(AfterClick, 120);
            }
        });
        testBtn[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastClick = 2;
                testBtn[2].animate().cancel();
                testBtn[2].animate().setDuration(100);
                testBtn[2].animate().translationYBy(-40);
                testBtn[2].animate().start();
                TransBtn1.postDelayed(AfterClick, 120);
            }
        });
        testBtn[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastClick = 3;
                testBtn[3].animate().cancel();
                testBtn[3].animate().setDuration(100);
                testBtn[3].animate().translationYBy(-40);
                testBtn[3].animate().start();
                TransBtn1.postDelayed(AfterClick, 120);
            }
        });
        testBtn[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastClick = 4;
                testBtn[4].animate().cancel();
                testBtn[4].animate().setDuration(100);
                testBtn[4].animate().translationYBy(-40);
                testBtn[4].animate().start();
                TransBtn1.postDelayed(AfterClick, 120);
            }
        });
        testBtn[5].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastClick = 5;
                testBtn[5].animate().cancel();
                testBtn[5].animate().setDuration(100);
                testBtn[5].animate().translationYBy(-40);
                testBtn[5].animate().start();
                TransBtn1.postDelayed(AfterClick, 120);
            }
        });
    }
}
