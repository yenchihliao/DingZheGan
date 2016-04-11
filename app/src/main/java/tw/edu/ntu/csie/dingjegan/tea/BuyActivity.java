package tw.edu.ntu.csie.dingjegan.tea;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class BuyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle recv =this.getIntent().getExtras();
        Integer item = recv.getInt("ItemNum");
        setContentView(R.layout.buyitem);
        ImageView ItemImage = (ImageView)findViewById(R.id.BuyImage);
        TextView ItemTitle = (TextView)findViewById(R.id.BuyTitle);
        TextView ItemPrice = (TextView)findViewById(R.id.BuyPrice);
        switch (item){
            case 1:
                ItemImage.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.tea1));
                ItemTitle.setText("购买:" + getString(R.string.Tea1));
                ItemPrice.setText(getString(R.string.Tea1Price));
                break;
            case 2:
                ItemImage.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.tea2));
                ItemTitle.setText("购买:" + getString(R.string.Tea2));
                ItemPrice.setText(getString(R.string.Tea2Price));
                break;
            case 3:
                ItemImage.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.tea3));
                ItemTitle.setText("购买:" + getString(R.string.Tea3));
                ItemPrice.setText(getString(R.string.Tea3Price));
                break;
            case 4:
                ItemImage.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.tea4));
                ItemTitle.setText("购买:" + getString(R.string.Tea4));
                ItemPrice.setText(getString(R.string.Tea4Price));
                break;
            default:
                ItemImage.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.tea1));
                break;
        }
        EditText number = (EditText)findViewById(R.id.number);
        number.setKeyListener(null);

    }
    public void add (View view){
        EditText number = (EditText)findViewById(R.id.number);
        int val = Integer.parseInt( number.getText().toString() );
        val = val + 1;
        number.setText(String.valueOf(val));
    }
    public void subtract (View view){
        EditText number = (EditText)findViewById(R.id.number);
        int val = Integer.parseInt( number.getText().toString() );
        if(val > 0) { val = val - 1; }
        number.setText(String.valueOf(val));
    }
}
