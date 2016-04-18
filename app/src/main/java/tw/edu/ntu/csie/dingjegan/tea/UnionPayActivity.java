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
import com.unionpaysdk.main.*;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UnionPayActivity extends AppCompatActivity {
    private UnionPaySDK unionPaySDK = null;

    private UnionPayActivity upa = this;

    private Context ctx = null;

    //this will be provided UnionSDK
    private static String scode="EID006501";

    //this should be applied by CP and will be provided UnionSDK
    private static String key="mE5en39229";

    //CP should provide this to UnionSDK
    private static String payCallBackUrl="http://52.69.107.107:3000/";

    //this should be on the CP' own server, which has nothing to do with SDK, and should be handle by CP self
    private String orderid ="";

    private double amount;
    private String memo ="This is a memo";

    private IPaymentCallback paymentCallback = new IPaymentCallback() {

        @Override
        public void onOrderFinished() {
            show("完成订购程序");
            // after the order is finished, the client should immediately request the response
            //from CP's own server to get to know the result of the order

            //GET JSON
            AsyncHttpRequest task = new AsyncHttpRequest(upa);
            task.execute(getResources().getString(R.string.ServerUnionpay)+orderid);



        }

        @Override
        public void onOrderNotFinished() {
            show("订购程序未完成");
            finish();


        }


    };


    private ICheckOrderCallback checkOrderCallBack = new ICheckOrderCallback() {

        @Override
        public void onSuccess(String json) {

            show("json is "+ json);

        }

        @Override
        public void onFailed() {


        }

    };



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Bundle recv =this.getIntent().getExtras();
        String resptime = recv.getString("resptime");
        orderid = recv.getString("ExternalOrderNo");
        amount = recv.getFloat("amount");

        setContentView(R.layout.activity_unionpay_webview);
        ctx=this;

        unionPaySDK = UnionPaySDK.getInstance();
        //must Initialize first
        unionPaySDK.Initialize(ctx, scode, key, true);


        // this is random for demo purpose only
        //orderid = getRandomString(10);
        unionPaySDK.payOrderRequest(this, orderid, amount, memo, payCallBackUrl, paymentCallback);
        Log.e("UPaySDK payOrderRequest",orderid);


    }


    private void show(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText (upa,message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public class AsyncHttpRequest extends AsyncTask<String, Void, String> {

        private Activity mainActivity;
        public AsyncHttpRequest(Activity activity) {

            this.mainActivity = activity;
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

            JsonObject myObject = new JsonParser().parse(result).getAsJsonObject();
            int status = myObject.get("status").getAsInt();
            if(status == 1){
                show("订单尚未建立，请稍后查询");
                return;
            }
            JsonObject data = myObject.getAsJsonObject("data");
            int payment_status = data.get("status").getAsInt();
            // 1 = success -1 = fail 0 = user_abort
            if(payment_status == 1){
                show("交易成功");

            }else if (payment_status == 0){
                show("中途终止交易");
            }else{
                show("交易失败");
            }


        }

    }

}
