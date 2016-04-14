package tw.edu.ntu.csie.dingjegan.tea;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PayActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle recv =this.getIntent().getExtras();
        int ProductSN = recv.getInt("ProductSN");
        int Quantity = recv.getInt("Quantity");
        int Price = recv.getInt("Price");
        int Amount = Quantity * Price;
        String Title = recv.getString("ItemTitle");

        setContentView(R.layout.payitem);
        TextView ItemTitle = (TextView)findViewById(R.id.PayItemName);
        ItemTitle.setText(Title);
        TextView PayText = (TextView)findViewById(R.id.PayText);
        PayText.setText("感谢您购买:"+Title+Quantity+"件，单价 ¥"+Price+"共 ¥"+Amount);


    }

    public void GoUnionPay (View view){
        
    }



}
