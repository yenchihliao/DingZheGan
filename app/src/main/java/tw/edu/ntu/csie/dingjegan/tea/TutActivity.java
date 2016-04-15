package tw.edu.ntu.csie.dingjegan.tea;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ViewFlipper;

public class TutActivity extends AppCompatActivity{


    private ViewFlipper TruitonFlipper;
    private float initialX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutorial);
        TruitonFlipper = (ViewFlipper) findViewById(R.id.flipper);
        ImageButton start = (ImageButton) findViewById(R.id.startButton);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame();
            }
        });
    }
    @Override
    public boolean onTouchEvent(MotionEvent touchevent) {
        switch (touchevent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                initialX = touchevent.getX();
                break;
            case MotionEvent.ACTION_UP:
                float finalX = touchevent.getX();
                if (initialX > finalX) {
                    if (TruitonFlipper.getDisplayedChild() == 3)
                        break;

                    TruitonFlipper.setInAnimation(this, android.R.anim.fade_in);
                    TruitonFlipper.setOutAnimation(this, android.R.anim.fade_out);

                    TruitonFlipper.showNext();
                } else {
                    if (TruitonFlipper.getDisplayedChild() == 0)
                        break;

                    TruitonFlipper.setInAnimation(this, android.R.anim.slide_in_left);
                    TruitonFlipper.setOutAnimation(this, android.R.anim.slide_out_right);

                    TruitonFlipper.showPrevious();
                }
                break;
        }
        return false;
    }
        private void startGame(){
            this.finish();
            Intent intent = new Intent(this, GameActivity.class);
            intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY); // Adds the FLAG_ACTIVITY_NO_HISTORY flag
            startActivity(intent);
        }

}
