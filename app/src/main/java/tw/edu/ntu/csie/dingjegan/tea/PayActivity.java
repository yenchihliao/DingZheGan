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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

//import com.unionpaysdk.main.*;

public class PayActivity extends AppCompatActivity {

    Integer Quantity = 0;
    Integer Price = 0;
    Integer Amount = 0;
    Integer ProductSN = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle recv =this.getIntent().getExtras();
        ProductSN = recv.getInt("ProductSN");
        Quantity = recv.getInt("Quantity");
        Price = recv.getInt("Price");
        Amount = Quantity * Price;
        String Title = recv.getString("ItemTitle");

        setContentView(R.layout.payitem);
        TextView ItemTitle = (TextView)findViewById(R.id.PayItemName);
        ItemTitle.setText(Title);
        TextView PayText = (TextView)findViewById(R.id.PayText);
        PayText.setText("感谢您购买:" + Title + Quantity + "件，单价 ¥" + Price + " 共 ¥" + Amount);


    }
    public void GoUnionPay (View view){


    }
    public void PostServer (View view){
        GoUnionPay(view);
        EditText OrderName = (EditText)findViewById(R.id.OrderName);
        EditText OrderAddress = (EditText)findViewById(R.id.OrderAddress);
        EditText OrderEmail = (EditText)findViewById(R.id.OrderEmail);
        EditText OrderPhone = (EditText)findViewById(R.id.OrderPhone);
        EditText ConsigneeName = (EditText)findViewById(R.id.ConsigneeName);
        EditText ConsigneeAddress = (EditText)findViewById(R.id.ConsigneeAddress);
        EditText ConsigneeEmail = (EditText)findViewById(R.id.ConsigneeEmail);
        EditText ConsigneePhone = (EditText)findViewById(R.id.ConsigneePhone);


        JsonObject json = new JsonObject();
        //TODO:Eliminate illegal characters ,;:{}[]...
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonElement jelem = gson.fromJson(OrderName.getText().toString(), JsonElement.class);
        json.add("OrderName", jelem);
        jelem = gson.fromJson(OrderAddress.getText().toString(), JsonElement.class);
        json.add("OrderAddress", jelem);
        jelem = gson.fromJson(OrderEmail.getText().toString(), JsonElement.class);
        json.add("OrderEmail", jelem);
        jelem = gson.fromJson(OrderPhone.getText().toString(), JsonElement.class);
        json.add("OrderPhone", jelem);
        jelem = gson.fromJson(ConsigneeName.getText().toString(), JsonElement.class);
        json.add("ConsigneeName", jelem);
        jelem = gson.fromJson(ConsigneeAddress.getText().toString(), JsonElement.class);
        json.add("ConsigneeAddress", jelem);
        jelem = gson.fromJson(ConsigneeEmail.getText().toString(), JsonElement.class);
        json.add("ConsigneeEmail", jelem);
        jelem = gson.fromJson(ConsigneePhone.getText().toString(), JsonElement.class);
        json.add("ConsigneePhone", jelem);
        String ExternalOrderNo = "~ExternalOrderNo~";
        jelem = gson.fromJson(ExternalOrderNo, JsonElement.class);
        json.add("ExternalOrderNo", jelem);
        jelem = gson.fromJson(ProductSN.toString(), JsonElement.class);
        json.add("ProductSN", jelem);
        jelem = gson.fromJson(Quantity.toString(), JsonElement.class);
        json.add("Quantity", jelem);
        jelem = gson.fromJson(Price.toString(), JsonElement.class);
        json.add("Price", jelem);
        jelem = gson.fromJson(Amount.toString(), JsonElement.class);
        json.add("Amount", jelem);
        jelem = gson.fromJson("~DeliverTime~", JsonElement.class);
        json.add("DeliverTime", jelem);
        jelem = gson.fromJson("1", JsonElement.class);
        json.add("Result", jelem);
        jelem = gson.fromJson("1", JsonElement.class);
        json.add("PaymentResult", jelem);
        jelem = gson.fromJson("TEXT", JsonElement.class);
        json.add("Param", jelem);

        //System.out.println("json ready");


        //POST Order Form
        AsyncHttpRequest taskPOST = new AsyncHttpRequest(this);
        taskPOST.execute(getResources().getString(R.string.ServerOrders), json.toString());

    }

    public class AsyncHttpRequest extends AsyncTask<String, Void, String> {

        private Activity mainActivity;

        public AsyncHttpRequest(Activity activity) {

            this.mainActivity = activity;

        }

        @Override
        protected String doInBackground(String... url) {
            final MediaType JSON
                    = MediaType.parse("application/json; charset=utf-8");

            OkHttpClient client = new OkHttpClient();
            String s = null;
            try {
                RequestBody body = RequestBody.create(JSON, url[1]);
                Request request = new Request.Builder()
                        .url(url[0])
                        .post(body)
                        .build();
                Response response = client.newCall(request).execute();
                s = response.body().string();
            }catch(IOException e) {
                Context context = getApplicationContext();
                CharSequence text = "服务器维护中，请见谅";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                finish();
            }
            return s;
        }

        @Override
        protected void onPostExecute(String result) {
            System.out.println(result);
            if (result.equals("{\"status\":0,\"data\":\"success\"}")){
                Context context = getApplicationContext();
                CharSequence text = "订单传送成功";
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                finish();
            }else{
                Context context = getApplicationContext();
                CharSequence text = "订单传送失败，请检查填写栏位及网络设置";
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        }
    }
}