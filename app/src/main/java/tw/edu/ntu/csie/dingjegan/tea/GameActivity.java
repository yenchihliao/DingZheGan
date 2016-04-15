package tw.edu.ntu.csie.dingjegan.tea;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Random;

public class GameActivity  extends AppCompatActivity {
    public static float dp2Px(float dp) {
        return (dp * Resources.getSystem().getDisplayMetrics().density);
    }
    public static float px2Dp(float px) {
        return (px / Resources.getSystem().getDisplayMetrics().density);
    }
    //TextView testNumber;
    int lastClick;
    ImageButton[] testBtn;
    Float[] speed;
    Float speedAdd;
    ImageView flashB1;
    ImageView flashB2;
    ImageView transition;
    TextView score, endScore, highScore;
    Animation fadein;
    float upDie, downDie;
    boolean dead;
    int nTime;
    String scoreStr;
    Handler mHandlerTime = new Handler();
    Handler TransBtn1 = new Handler();
    Handler DieHandle = new Handler();
    Random randomer = new Random();
    AnimationDrawable frameAnimation, frameAnimation2;
    View[] potGroup;
    ImageView[] mid, top;
    ImageView[] dying;
    private final Runnable timerRun = new Runnable() {
        public void run()
        {
            ++nTime;
            speedAdd += 0.2f;
            scoreStr = ""+((nTime/60>9)? nTime/60:"0"+nTime/60)+":"+((nTime%60>9)? (nTime%60):"0"+(nTime%60));

            if(!dead){
                score.setText(scoreStr);
                mHandlerTime.postDelayed(this, 1000);
            }else{
                --nTime;

                for(int i = 0; i<6;i++) {
                    freeAnimationDrawable((AnimationDrawable)mid[i].getBackground());
                    freeAnimationDrawable((AnimationDrawable)top[i].getBackground());
                    mid[i].setBackgroundResource(R.drawable.mid);
                    top[i].setBackgroundResource(R.drawable.top);
                }
                transition = (ImageView) findViewById(R.id.transition);
                transition.setBackgroundResource(R.drawable.trans_ani);
                AnimationDrawable endTrans = (AnimationDrawable) transition.getBackground();
                endTrans.start();
                mHandlerTime.postDelayed(ending, 1400);
            }
        }
    };
    private final Runnable ending = new Runnable() {
        public void run()
        {
            boolean newHigh = false;
            freeAnimationDrawable((AnimationDrawable)transition.getBackground());
            transition.setBackgroundColor(0xFFFFFF);
            setContentView(R.layout.game_over);
            endScore = (TextView)findViewById(R.id.EndScore);
            scoreStr = ""+((nTime/60>9)? nTime/60:"0"+nTime/60)+":"+((nTime%60>9)? (nTime%60):"0"+(nTime%60));
            endScore.setText(scoreStr);
            String HighScore;
            try{
                HighScore = readFromFile();
                if(Integer.valueOf(HighScore) < nTime){
                    newHigh = true;
                    writeToFile(new Integer(nTime).toString());
                }
            }catch(Exception e){
                HighScore = new Integer(nTime).toString();
                newHigh = true;
                writeToFile(HighScore);
            }
            Integer HighScoreInt = Integer.valueOf(HighScore);
            if(newHigh) HighScoreInt = nTime;
            highScore = (TextView)findViewById(R.id.HighScore);
            String hiScoreString = ""+((HighScoreInt/60>9)? HighScoreInt/60:"0"+HighScoreInt/60)+":"+((HighScoreInt%60>9)? (HighScoreInt%60):"0"+(HighScoreInt%60));
            highScore.setText(hiScoreString);
        }
    };
    private final Runnable testBtn1R = new Runnable() {
        public void run() {
            if(!dead) {
                for (int i = 0; i < 6; i++) {
                    potGroup[i].animate().setInterpolator(new LinearInterpolator());
                    potGroup[i].animate().setDuration(2000);
                    potGroup[i].animate().translationYBy(speed[i]);
                    potGroup[i].animate().start();
                }
            }
            if(!dead) TransBtn1.postDelayed(this, 2000);
        }
    };
    private final Runnable AfterClick = new Runnable() {
        public void run() {
            if(!dead) {
                speed[lastClick] = randomer.nextFloat() * (30 + speedAdd) + 5 + speedAdd;
                potGroup[lastClick].animate().setInterpolator(new LinearInterpolator());
                potGroup[lastClick].animate().setDuration(2000);
                potGroup[lastClick].animate().translationYBy(speed[lastClick]);
                potGroup[lastClick].animate().start();
            }
        }
    };
    private final Runnable getYTranslate = new Runnable() {
        public void run() {
            Float temp;
            Float upBig = dp2Px(-35), downBig = dp2Px(-35.0f), upSmall = dp2Px(-35), downSmall = dp2Px(-35.0f);
            for (int i = 0; i < 6; i++) {
                temp = potGroup[i].getTranslationY();
                //testBtn[i].setText(temp.toString());
                if(temp < upDie || temp > downDie){
                    //testBtn[i].setText("DEAD");
                    if(!dead) {
                        for (int j = 0; j < 6; j++) {
                            potGroup[j].animate().cancel();
                            potGroup[j].animate().setDuration(10);
                            potGroup[j].animate().translationYBy(0);
                            potGroup[j].animate().start();
                        }
                    }
                    dead = true;
                }
                if(i<3){
                    if(temp < upSmall) upSmall = temp;
                    if(temp > upBig) upBig = temp;
                }else{
                    if(temp < downSmall) downSmall = temp;
                    if(temp > downBig) downBig = temp;
                }
            }
            dying[0].setAlpha((px2Dp(upSmall)<-55.0f)? -(px2Dp(upSmall)+55.0f)/20:0);
            dying[1].setAlpha((px2Dp(upBig)>-15.0f)? (px2Dp(upBig)+15.0f)/20:0);
            dying[2].setAlpha((px2Dp(downSmall)<-55.0f)? -(px2Dp(downSmall)+55.0f)/20:0);
            dying[3].setAlpha((px2Dp(downBig)>-15.0f)? (px2Dp(downBig)+15.0f)/20:0);
            if(!dead) DieHandle.postDelayed(this, 80);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        testBtn = new ImageButton[6];
        speed = new Float[6];
        mid = new ImageView[6];
        top = new ImageView[6];
        potGroup = new View[6];
        dead = false;
        speedAdd = 0.1f;
        testBtn[0] = (ImageButton) findViewById(R.id.PotBtn0);
        testBtn[1] = (ImageButton) findViewById(R.id.PotBtn1);
        testBtn[2] = (ImageButton) findViewById(R.id.PotBtn2);
        testBtn[3] = (ImageButton) findViewById(R.id.PotBtn3);
        testBtn[4] = (ImageButton) findViewById(R.id.PotBtn4);
        testBtn[5] = (ImageButton) findViewById(R.id.PotBtn5);
        mid[0] = (ImageView)findViewById(R.id.PotMid0);
        top[0] = (ImageView)findViewById(R.id.PotTop0);
        mid[1] = (ImageView)findViewById(R.id.PotMid1);
        top[1] = (ImageView)findViewById(R.id.PotTop1);
        mid[2] = (ImageView)findViewById(R.id.PotMid2);
        top[2] = (ImageView)findViewById(R.id.PotTop2);
        mid[3] = (ImageView)findViewById(R.id.PotMid3);
        top[3] = (ImageView)findViewById(R.id.PotTop3);
        mid[4] = (ImageView)findViewById(R.id.PotMid4);
        top[4] = (ImageView)findViewById(R.id.PotTop4);
        mid[5] = (ImageView)findViewById(R.id.PotMid5);
        top[5] = (ImageView)findViewById(R.id.PotTop5);
        potGroup[0] = findViewById(R.id.potGroup0);
        potGroup[1] = findViewById(R.id.potGroup1);
        potGroup[2] = findViewById(R.id.potGroup2);
        potGroup[3] = findViewById(R.id.potGroup3);
        potGroup[4] = findViewById(R.id.potGroup4);
        potGroup[5] = findViewById(R.id.potGroup5);
        dying = new ImageView[4];
        dying[0] = (ImageView)findViewById(R.id.up_dying_up);
        dying[1] = (ImageView)findViewById(R.id.down_dying_up);
        dying[2] = (ImageView)findViewById(R.id.up_dying_down);
        dying[3] = (ImageView)findViewById(R.id.down_dying_down);
        dying[0].setAlpha(0.0f);
        dying[1].setAlpha(0.0f);
        dying[2].setAlpha(0.0f);
        dying[3].setAlpha(0.0f);
        for(int i = 0; i<6;i++) {
            mid[i].setBackgroundResource(R.drawable.mid_ani);
            top[i].setBackgroundResource(R.drawable.top_ani);
            frameAnimation2 = (AnimationDrawable) top[i].getBackground();
            frameAnimation2.start();
            potGroup[i].setTranslationY(dp2Px(-55));
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
        upDie = dp2Px(-75);
        downDie = dp2Px(5);
        nTime = 0;
        mHandlerTime.postDelayed(timerRun, 1000);
        TransBtn1.postDelayed(testBtn1R, 200);
        DieHandle.postDelayed(getYTranslate,80);
        testBtn[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!dead) {
                    lastClick = 0;
                    potGroup[0].animate().cancel();
                    potGroup[0].animate().setDuration(100);
                    potGroup[0].animate().translationYBy(-40);
                    potGroup[0].animate().start();
                    frameAnimation = (AnimationDrawable) mid[0].getBackground();
                    frameAnimation.stop();
                    frameAnimation.selectDrawable(0);
                    frameAnimation.start();
                    TransBtn1.postDelayed(AfterClick, 120);
                }
            }
        });
        testBtn[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!dead) {
                    lastClick = 1;
                    potGroup[1].animate().cancel();
                    potGroup[1].animate().setDuration(100);
                    potGroup[1].animate().translationYBy(-40);
                    potGroup[1].animate().start();
                    frameAnimation = (AnimationDrawable) mid[1].getBackground();
                    frameAnimation.stop();
                    frameAnimation.selectDrawable(0);
                    frameAnimation.start();
                    TransBtn1.postDelayed(AfterClick, 120);
                }
            }
        });
        testBtn[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!dead) {
                    lastClick = 2;
                    potGroup[2].animate().cancel();
                    potGroup[2].animate().setDuration(100);
                    potGroup[2].animate().translationYBy(-40);
                    potGroup[2].animate().start();
                    frameAnimation = (AnimationDrawable) mid[2].getBackground();
                    frameAnimation.stop();
                    frameAnimation.selectDrawable(0);
                    frameAnimation.start();
                    TransBtn1.postDelayed(AfterClick, 120);
                }
            }
        });
        testBtn[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!dead) {
                    lastClick = 3;
                    potGroup[3].animate().cancel();
                    potGroup[3].animate().setDuration(100);
                    potGroup[3].animate().translationYBy(-40);
                    potGroup[3].animate().start();
                    frameAnimation = (AnimationDrawable) mid[3].getBackground();
                    frameAnimation.stop();
                    frameAnimation.selectDrawable(0);
                    frameAnimation.start();
                    TransBtn1.postDelayed(AfterClick, 120);
                }
            }
        });
        testBtn[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!dead) {
                    lastClick = 4;
                    potGroup[4].animate().cancel();
                    potGroup[4].animate().setDuration(100);
                    potGroup[4].animate().translationYBy(-40);
                    potGroup[4].animate().start();
                    frameAnimation = (AnimationDrawable) mid[4].getBackground();
                    frameAnimation.stop();
                    frameAnimation.selectDrawable(0);
                    frameAnimation.start();
                    TransBtn1.postDelayed(AfterClick, 120);
                }
            }
        });
        testBtn[5].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!dead) {
                    lastClick = 5;
                    potGroup[5].animate().cancel();
                    potGroup[5].animate().setDuration(100);
                    potGroup[5].animate().translationYBy(dp2Px(-16));
                    potGroup[5].animate().start();
                    frameAnimation = (AnimationDrawable) mid[5].getBackground();
                    frameAnimation.stop();
                    frameAnimation.selectDrawable(0);
                    frameAnimation.start();
                    TransBtn1.postDelayed(AfterClick, 120);
                }
            }
        });
    }
    public void BackMainActivity (View view){
        this.finish();
        Intent intent = new Intent (this, MainActivity.class);
        intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY); // Adds the FLAG_ACTIVITY_NO_HISTORY flag
        startActivity(intent);
    }
    public void RestartGameActivity (View view){
        this.finish();
        Intent intent = new Intent (this, GameActivity.class);
        intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY); // Adds the FLAG_ACTIVITY_NO_HISTORY flag
        startActivity(intent);
    }
    public void GameOverListActivity (View view){

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            Intent intent = new Intent (this, ListActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("PageNum", 1);
            intent.putExtras(bundle);
            this.finish();
            startActivity(intent);
        } else {
            Context context = getApplicationContext();
            CharSequence text = "请连接网络";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            Log.d("CDA", "onKeyDown Called");
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onBackPressed() {
        Log.d("CDA", "onBackPressed Called");
        this.finish();
        Intent intent = new Intent (this, MainActivity.class);
        intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY); // Adds the FLAG_ACTIVITY_NO_HISTORY flag
        startActivity(intent);
    }
    private void freeAnimationDrawable(AnimationDrawable animationDrawable) {
        animationDrawable.stop();
        for (int i = 1; i < animationDrawable.getNumberOfFrames(); ++i){
            Drawable frame = animationDrawable.getFrame(i);
            if (frame instanceof BitmapDrawable) {
                ((BitmapDrawable)frame).getBitmap().recycle();
            }
            frame.setCallback(null);
        }

        animationDrawable.setCallback(null);
    }
    private void writeToFile(String data) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput("high_score.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
    private String readFromFile() throws FileNotFoundException{

        String ret = "";

        try {
            InputStream inputStream = openFileInput("high_score.txt");
            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }
        return ret;
    }
}
