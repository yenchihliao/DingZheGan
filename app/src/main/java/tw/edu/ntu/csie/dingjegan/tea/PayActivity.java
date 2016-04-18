package tw.edu.ntu.csie.dingjegan.tea;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import com.unionpaysdk.main.*;

public class PayActivity extends AppCompatActivity {

    Integer Quantity = 0;
    Float Price = 0f;
    Float Amount = 0f;
    Integer ProductSN = 0;
    String ExternalOrderNo = null;
    String date = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle recv =this.getIntent().getExtras();
        ProductSN = recv.getInt("ProductSN");
        Quantity = recv.getInt("Quantity");
        Price = recv.getFloat("Price");
        Amount = Quantity * Price;
        String Title = recv.getString("ItemTitle");

        setContentView(R.layout.payitem);
        TextView ItemTitle = (TextView)findViewById(R.id.PayItemName);
        ItemTitle.setText(Title);
        TextView PayText = (TextView)findViewById(R.id.PayText);
        PayText.setText("感谢您购买:" + Title + Quantity + "件，单价 ¥" + Price + " 共 ¥" + Amount);


    }
    public void GoCopy (View view){
        EditText OrderName = (EditText)findViewById(R.id.OrderName);
        EditText OrderAddress = (EditText)findViewById(R.id.OrderAddress);
        EditText OrderEmail = (EditText)findViewById(R.id.OrderEmail);
        EditText OrderPhone = (EditText)findViewById(R.id.OrderPhone);
        EditText ConsigneeName = (EditText)findViewById(R.id.ConsigneeName);
        EditText ConsigneeAddress = (EditText)findViewById(R.id.ConsigneeAddress);
        EditText ConsigneeEmail = (EditText)findViewById(R.id.ConsigneeEmail);
        EditText ConsigneePhone = (EditText)findViewById(R.id.ConsigneePhone);
        ConsigneeName.setText(OrderName.getText());
        ConsigneeAddress.setText(OrderAddress.getText());
        ConsigneeEmail.setText(OrderEmail.getText());
        ConsigneePhone.setText(OrderPhone.getText());
    }
    public void GoUnionPay (View view){
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            Intent intent = new Intent (this, UnionPayActivity.class);

            Bundle bundle = new Bundle();
            bundle.putString("ExternalOrderNo", ExternalOrderNo);
            bundle.putString("resptime", date);
            bundle.putFloat("amount", Amount);
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
    public void PostServer (View view){


        EditText OrderName = (EditText)findViewById(R.id.OrderName);
        EditText OrderAddress = (EditText)findViewById(R.id.OrderAddress);
        EditText OrderEmail = (EditText)findViewById(R.id.OrderEmail);
        EditText OrderPhone = (EditText)findViewById(R.id.OrderPhone);
        EditText ConsigneeName = (EditText)findViewById(R.id.ConsigneeName);
        EditText ConsigneeAddress = (EditText)findViewById(R.id.ConsigneeAddress);
        EditText ConsigneeEmail = (EditText)findViewById(R.id.ConsigneeEmail);
        EditText ConsigneePhone = (EditText)findViewById(R.id.ConsigneePhone);
        EditText DeliverTime = (EditText)findViewById(R.id.DeliverTime);


        JsonObject json = new JsonObject();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        //JsonElement jelem = gson.fromJson(OrderName.getText().toString(), JsonElement.class);
        json.addProperty("OrderName", OrderName.getText().toString());
        //jelem = gson.fromJson(OrderAddress.getText().toString(), JsonElement.class);
        json.addProperty("OrderAddress", OrderAddress.getText().toString());
        //jelem = gson.fromJson(OrderEmail.getText().toString(), JsonElement.class);
        json.addProperty("OrderEmail", OrderEmail.getText().toString());
        //jelem = gson.fromJson(OrderPhone.getText().toString(), JsonElement.class);
        json.addProperty("OrderPhone", OrderPhone.getText().toString());
        //jelem = gson.fromJson(ConsigneeName.getText().toString(), JsonElement.class);
        json.addProperty("ConsigneeName", ConsigneeName.getText().toString());
        //jelem = gson.fromJson(ConsigneeAddress.getText().toString(), JsonElement.class);
        json.addProperty("ConsigneeAddress", ConsigneeAddress.getText().toString());
        //jelem = gson.fromJson(ConsigneeEmail.getText().toString(), JsonElement.class);
        json.addProperty("ConsigneeEmail", ConsigneeEmail.getText().toString());
        //jelem = gson.fromJson(ConsigneePhone.getText().toString(), JsonElement.class);
        json.addProperty("ConsigneePhone", ConsigneePhone.getText().toString());
        //jelem = gson.fromJson(DeliverTime.getText().toString(), JsonElement.class);
        json.addProperty("DeliverTime", DeliverTime.getText().toString());

        String rand = getRandomString(30);
        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date current = new Date();
        date = sdFormat.format(current);
        //Log.e("D1",date);

        sdFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        current = new Date();
        ExternalOrderNo = sdFormat.format(current)+rand;
        //Log.e("D2",ExternalOrderNo);

        //jelem = gson.fromJson(ExternalOrderNo, JsonElement.class);
        json.addProperty("ExternalOrderNo", ExternalOrderNo);
        //jelem = gson.fromJson(ProductSN.toString(), JsonElement.class);
        json.addProperty("ProductSN", ProductSN.toString());
        //jelem = gson.fromJson(Quantity.toString(), JsonElement.class);
        json.addProperty("Quantity", Quantity.toString());
        //jelem = gson.fromJson(Price.toString(), JsonElement.class);
        json.addProperty("Price", Price.toString());
        //jelem = gson.fromJson(Amount.toString(), JsonElement.class);
        json.addProperty("Amount", Amount.toString());

        //jelem = gson.fromJson("1", JsonElement.class);
        json.addProperty("Result", "1");
        //jelem = gson.fromJson("1", JsonElement.class);
        json.addProperty("PaymentResult", "2");
        //jelem = gson.fromJson("TEXT", JsonElement.class);
        json.addProperty("Param", "TEXT");


        //System.out.println("json ready");

        GoUnionPay(view);


        //POST Order Form
        AsyncHttpRequest taskPOST = new AsyncHttpRequest(this);
        taskPOST.execute(getResources().getString(R.string.ServerOrders), json.toString());

    }

    private static String getRandomString(int len)
    {


        String str = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < len; i++)
        {
            int idx = (int)(Math.random() * str.length());
            sb.append(str.charAt(idx));
        }
        String result = sb.toString();


        return result;

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
            JsonObject myObject = new JsonParser().parse(result).getAsJsonObject();
            int status = -1;
            if((myObject.get("status") != JsonNull.INSTANCE) && (myObject.get("status").getAsString().length() != 0)){
                status = myObject.get("status").getAsInt();
            }
            if (status == 0){
                writeToFile(ExternalOrderNo);
                Context context = getApplicationContext();
                CharSequence text = "订单传送成功，请继续完成付款，再至'查询订单'查询状态\n订单编号:"+ExternalOrderNo;
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

    private void writeToFile(String data) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput("order_numbers.txt", Context.MODE_APPEND));
            outputStreamWriter.write(data);
            outputStreamWriter.write("\n");
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}