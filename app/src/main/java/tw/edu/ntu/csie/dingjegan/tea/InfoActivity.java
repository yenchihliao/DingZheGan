package tw.edu.ntu.csie.dingjegan.tea;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle recv =this.getIntent().getExtras();
        Integer item = recv.getInt("ItemNum");
        setContentView(R.layout.viewitem);
        ImageView ItemImage = (ImageView)findViewById(R.id.ViewImage);
        TextView ItemTitle = (TextView)findViewById(R.id.ViewItemName);
        TextView ItemPrice = (TextView)findViewById(R.id.ViewPrice);
        TextView ItemInfo = (TextView)findViewById(R.id.ViewInfo);
        ImageButton ItemBuy = (ImageButton)findViewById(R.id.TeaInfoBuyButton);

        

        switch (item){
            case 1:
                ItemImage.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.tea1));
                //ItemBuy.setOnClickListener("GoBuyTea1");
                ItemBuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GoBuyTea1(v);
                    }
                });
                ItemTitle.setText(getString(R.string.Tea1));
                ItemPrice.setText(getString(R.string.Tea1Price));
                ItemInfo.setText(getString(R.string.Tea1Info));
                break;
            case 2:
                ItemImage.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.tea2));
                //ItemBuy.setOnClickListener("GoBuyTea2");
                ItemBuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GoBuyTea2(v);
                    }
                });
                ItemTitle.setText(getString(R.string.Tea2));
                ItemPrice.setText(getString(R.string.Tea2Price));
                ItemInfo.setText(getString(R.string.Tea2Info));
                break;
            case 3:
                ItemImage.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.tea3));
                //ItemBuy.setOnClickListener("GoBuyTea3");
                ItemBuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GoBuyTea3(v);
                    }
                });
                ItemTitle.setText(getString(R.string.Tea3));
                ItemPrice.setText(getString(R.string.Tea3Price));
                ItemInfo.setText(getString(R.string.Tea3Info));
                break;
            case 4:
                ItemImage.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.tea4));
                //ItemBuy.setOnClickListener("GoBuyTea4");
                ItemBuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GoBuyTea4(v);
                    }
                });
                ItemTitle.setText(getString(R.string.Tea4));
                ItemPrice.setText(getString(R.string.Tea4Price));
                ItemInfo.setText(getString(R.string.Tea4Info));
                break;
            default:
                ItemImage.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.tea1));
                break;
        }


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
}
