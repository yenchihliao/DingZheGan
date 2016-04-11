package tw.edu.ntu.csie.dingjegan.tea;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
    }
    public void GoBuyTea1 (View view){
        Intent intent = new Intent (this, BuyActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("ItemNum",1);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    public void GoBuyTea2 (View view){
        Intent intent = new Intent (this, BuyActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("ItemNum",2);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    public void GoBuyTea3 (View view){
        Intent intent = new Intent (this, BuyActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("ItemNum",3);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    public void GoBuyTea4 (View view){
        Intent intent = new Intent (this, BuyActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("ItemNum",4);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    public void GoInfoTea1 (View view){
        Intent intent = new Intent (this, InfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("ItemNum",1);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    public void GoInfoTea2 (View view){
        Intent intent = new Intent (this, InfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("ItemNum",2);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    public void GoInfoTea3 (View view){
        Intent intent = new Intent (this, InfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("ItemNum",3);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    public void GoInfoTea4 (View view){
        Intent intent = new Intent (this, InfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("ItemNum",4);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
