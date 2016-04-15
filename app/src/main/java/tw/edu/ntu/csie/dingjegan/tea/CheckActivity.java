package tw.edu.ntu.csie.dingjegan.tea;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CheckActivity extends AppCompatActivity {

    ArrayList<String> ExternalOrderNos = new ArrayList<String>();
    private CheckActivity ca = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check);
        try {
            readOrderFromFile();
        } catch (FileNotFoundException e) {
            Context context = getApplicationContext();
            CharSequence text = "未找到历史订单，跳转至订购页面";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            GoListActivity();
            finish();
        }

        //Set spinner
        ArrayAdapter<String> OrderList;
        final Context mContext = this.getApplicationContext();
        Spinner spinner = (Spinner)findViewById(R.id.OrderSpinner);
        OrderList = new ArrayAdapter<String>(this, R.layout.order_spinner, ExternalOrderNos);
        OrderList.setDropDownViewResource(R.layout.order_spinner_dropdown);
        spinner.setAdapter(OrderList);

        final TextView OrderText = (TextView)findViewById(R.id.OrderText);
        final TextView OrderText2 = (TextView)findViewById(R.id.OrderText2);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,int position, long arg3) {
                AsyncHttpRequest task = new AsyncHttpRequest(ca, OrderText);
                task.execute(getResources().getString(R.string.ServerUnionpay)
                        +/*ExternalOrderNos.get(position)*/112112) ;
                AsyncHttpRequest2 task2 = new AsyncHttpRequest2(ca, OrderText2);
                task2.execute(getResources().getString(R.string.ServerOrders)
                                +ExternalOrderNos.get(position)) ;
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

    }

    public void GoListActivity (){
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


    private void readOrderFromFile() throws FileNotFoundException {

        try {
            InputStream inputStream = openFileInput("order_numbers.txt");
            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";

                int k = 0;
                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    ExternalOrderNos.add(receiveString);
                    k++;
                }

                inputStream.close();
            }
        }
        catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
            throw new FileNotFoundException("No such file or directory");
        }
    }

    public class AsyncHttpRequest extends AsyncTask<String, Void, String> {

        private Activity mainActivity;
        private TextView TV;
        public AsyncHttpRequest(Activity activity, TextView TV) {

            this.mainActivity = activity;
            this.TV = TV;
        }

        @Override
        protected String doInBackground(String... url) {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(url[0])
                    .build();
            String s = null;
            try{
                Response response = client.newCall(request).execute();
                s = response.body().string();
            }catch (IOException e){

            }
            return s;
        }

        @Override
        protected void onPostExecute(String result) {
            if (result == null){
                TV.setText("服务器错误");
                return;
            }
            JsonObject myObject = new JsonParser().parse(result).getAsJsonObject();
            int status = myObject.get("status").getAsInt();
            Log.e("Status", "Call order feedback status: " + status);
            if (status == 0){
                JsonObject data = myObject.getAsJsonObject("data");
                String status_text;
                if((data.get("status") != JsonNull.INSTANCE) && (data.get("status").getAsString().length() != 0)){
                    if(data.get("status").getAsString().equals("1")){
                        status_text = "交易成功";

                    }else if(data.get("status").getAsString().equals("0")){
                        status_text = "用户中途终止交易";
                    }else{
                        status_text = "交易失败";
                    }
                }else{
                    status_text = "查无结果";
                }
                String orderid_text;
                if((data.get("orderid") != JsonNull.INSTANCE) && (data.get("orderid").getAsString().length() != 0)){
                    orderid_text = data.get("orderid").getAsString();
                }else{
                    orderid_text = "查无序号";
                }
                String amount_text;
                if((data.get("amount") != JsonNull.INSTANCE) && (data.get("amount").getAsString().length() != 0)){
                    amount_text = data.get("amount").getAsString();
                }else{
                    amount_text = "查无金额";
                }
                String resptime_text;
                if((data.get("resptime") != JsonNull.INSTANCE) && (data.get("resptime").getAsString().length() != 0)){
                    resptime_text = data.get("resptime").getAsString();
                }else{
                    resptime_text = "查无完成时间";
                }
                String text = "交易序号 : "+orderid_text
                        +"\n订单总金额 : "+amount_text
                        +"\n交易完成时间 : "+resptime_text
                        +"\n交易结果 : "+status_text;
                TV.setText(text);
            }else{
                TV.setText("查询错误");
            }
        }

    }

    public class AsyncHttpRequest2 extends AsyncTask<String, Void, String> {

        private Activity mainActivity;
        private TextView TV;
        public AsyncHttpRequest2(Activity activity, TextView TV) {

            this.mainActivity = activity;
            this.TV = TV;
        }

        @Override
        protected String doInBackground(String... url) {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(url[0])
                    .build();
            String s = null;
            try{
                Response response = client.newCall(request).execute();
                s = response.body().string();
            }catch (IOException e){

            }
            return s;
        }

        @Override
        protected void onPostExecute(String result) {
            if (result == null){
                TV.setText("服务器错误");
                return;
            }
            JsonObject myObject = new JsonParser().parse(result).getAsJsonObject();
            int status = myObject.get("status").getAsInt();
            Log.e("Status", "Call order feedback status: " + status);
            if (status == 0){
                JsonObject data = myObject.getAsJsonObject("data");

                String ItemName;
                if((data.get("ItemName") != JsonNull.INSTANCE) && (data.get("ItemName").getAsString().length() != 0)){
                    ItemName = data.get("ItemName").getAsString();
                }else{
                    ItemName = "查无商品名称";
                }
                String OrderName;
                if((data.get("OrderName") != JsonNull.INSTANCE) && (data.get("OrderName").getAsString().length() != 0)){
                    OrderName = data.get("OrderName").getAsString();
                }else{
                    OrderName = "查无订购人姓名";
                }
                String ConsigneeName;
                if((data.get("ConsigneeName") != JsonNull.INSTANCE) && (data.get("ConsigneeName").getAsString().length() != 0)){
                    ConsigneeName = data.get("ConsigneeName").getAsString();
                }else{
                    ConsigneeName = "查无收件人姓名";
                }
                String PaymentResult;
                if((data.get("PaymentResult") != JsonNull.INSTANCE) && (data.get("PaymentResult").getAsString().length() != 0)){
                    PaymentResult = data.get("PaymentResult").getAsString();
                    if(PaymentResult.equals("1")){
                        PaymentResult = "已付款";
                    }else{
                        PaymentResult = "未付款";
                    }
                }else{
                    PaymentResult = "查无付款状态";
                }
                String DeliverTime;
                if((data.get("DeliverTime") != JsonNull.INSTANCE) && (data.get("DeliverTime").getAsString().length() != 0)){
                    DeliverTime = data.get("DeliverTime").getAsString();
                }else{
                    DeliverTime = "查无配送时间";
                }
                String text = "商品名称 : "+ItemName
                        +"\n订购人姓名 : "+OrderName
                        +"\n收件人姓名 : "+ConsigneeName
                        +"\n付款状态 : "+PaymentResult
                        +"\n配送时间 : "+DeliverTime;
                TV.setText(text);
            }else{
                TV.setText("查询错误");
            }
        }

    }
}