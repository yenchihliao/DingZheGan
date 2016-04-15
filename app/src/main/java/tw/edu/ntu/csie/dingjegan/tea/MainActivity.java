package tw.edu.ntu.csie.dingjegan.tea;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    boolean played;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        played = false;
        Animation rotateTea;
        ImageView tea = (ImageView) findViewById(R.id.Tea);
        tea.setImageResource(R.drawable.tea);
        rotateTea = new RotateAnimation(-3.0f,3.0f,Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        rotateTea.setDuration(800);
        rotateTea.setRepeatCount(Animation.INFINITE);
        rotateTea.setRepeatMode(Animation.REVERSE);
        tea.setAnimation(rotateTea);
        rotateTea.startNow();
    }
    public void GoListActivity (View view){
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            Intent intent = new Intent (this, ListActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("PageNum", 1);
            intent.putExtras(bundle);
            startActivity(intent);
        } else {
            Context context = getApplicationContext();
            CharSequence text = "请连接网络";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }

    }
    public void GoKnowledgeActivity (View view){
        Intent intent = new Intent (this, KnowledgeActivity.class);
        startActivity(intent);
    }
    public void GoGameActivity (View view){
        if(played == true) {
            this.finish();
            Intent intent = new Intent(this, GameActivity.class);
            intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY); // Adds the FLAG_ACTIVITY_NO_HISTORY flag
            startActivity(intent);
        }else{
            played = true;
            Intent intent = new Intent(this, TutActivity.class);
            startActivity(intent);
        }
    }
    public void GoAchieveActivity (View view){
        //Intent intent = new Intent (this, AchieveActivity.class);
        //startActivity(intent);
    }

}
