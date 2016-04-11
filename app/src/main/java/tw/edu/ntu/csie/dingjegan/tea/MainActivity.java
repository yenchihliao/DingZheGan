package tw.edu.ntu.csie.dingjegan.tea;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void GoListActivity (View view){
        Intent intent = new Intent (this, ListActivity.class);
        startActivity(intent);
    }
    public void GoKnowledgeActivity (View view){
        Intent intent = new Intent (this, KnowledgeActivity.class);
        startActivity(intent);
    }

}
