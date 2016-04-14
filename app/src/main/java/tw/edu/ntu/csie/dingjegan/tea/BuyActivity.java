package tw.edu.ntu.csie.dingjegan.tea;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
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

public class BuyActivity extends AppCompatActivity {

    int itemnum;
    int teaitems[];
    int remaining_items = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle recv =this.getIntent().getExtras();
        itemnum = recv.getInt("ItemNum");
        teaitems = recv.getIntArray("ItemNumsArray");
        setContentView(R.layout.buyitem);
        ImageView ItemImage = (ImageView)findViewById(R.id.BuyImage);
        TextView ItemTitle = (TextView)findViewById(R.id.BuyItemName);
        TextView ItemPrice = (TextView)findViewById(R.id.BuyPrice);

        //GET JSON
        AsyncHttpRequest task = new AsyncHttpRequest(this,new DownloadImageTask(ItemImage), ItemTitle, ItemPrice);
        task.execute("http://user.paga.moe/Rikiu-test/products/" + teaitems[itemnum]);

        EditText number = (EditText)findViewById(R.id.number);
        number.setKeyListener(null);

    }
    public void add (View view){
        EditText number = (EditText)findViewById(R.id.number);
        int val = Integer.parseInt( number.getText().toString() );
        if(val < remaining_items) { val = val + 1; }
        else {
            Context context = getApplicationContext();
            CharSequence text = "已达商品剩余库存量、欲购从速";
            if (remaining_items == 0){
                text = "商品已无库存";
            }
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        number.setText(String.valueOf(val));
    }
    public void subtract (View view){
        EditText number = (EditText)findViewById(R.id.number);
        int val = Integer.parseInt( number.getText().toString() );
        if(val > 0) { val = val - 1; }
        number.setText(String.valueOf(val));
    }

    public void GoPay (View view){
        Intent intent = new Intent (this, PayActivity.class);
        Bundle bundle = new Bundle();

        bundle.putInt("ProductSN",teaitems[itemnum]);

        EditText number = (EditText)findViewById(R.id.number);
        if(Integer.parseInt(number.getText().toString()) == 0) {
            return;}
        bundle.putInt("Quantity", Integer.parseInt(number.getText().toString()));

        TextView price = (TextView)findViewById(R.id.BuyPrice);
        bundle.putInt("Price", Integer.parseInt(price.getText().toString()));

        TextView title = (TextView)findViewById(R.id.BuyItemName);
        bundle.putString("ItemTitle", title.getText().toString());

        intent.putExtras(bundle);
        startActivity(intent);
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            if(urldisplay == null){
                return null;
            }
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            //pDlg.dismiss();
            if (result == null){
                Context context = getApplicationContext();
                Bitmap nopicBitmap = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.nopic);
                bmImage.setImageBitmap(nopicBitmap);
                return;
            }
            bmImage.setImageBitmap(result);
        }
    }

    public class AsyncHttpRequest extends AsyncTask<String, Void, String> {

        private Activity mainActivity;
        private DownloadImageTask task;
        private TextView Title;
        private TextView Price;
        public AsyncHttpRequest(Activity activity,DownloadImageTask task, TextView Title, TextView Price) {

            this.mainActivity = activity;
            this.task = task;
            this.Title = Title;
            this.Price = Price;
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
                task.execute(null);
                Title.setText("暂无商品名称");
                Price.setText("暂无商品售价");
                remaining_items = 0;
                return;
            }
            JsonObject myObject = new JsonParser().parse(result).getAsJsonObject();
            int status = myObject.get("status").getAsInt();
            //System.out.println(status);
            // TODO:if (status == 1)
            JsonObject data = myObject.getAsJsonObject("data");
            String imageURL = data.get("LargeIcon").getAsString();
            task.execute(imageURL);
            Title.setText(data.get("ProductTitle").getAsString());
            Price.setText(data.get("SellPriceCNY").getAsString());
            remaining_items = data.get("ProductQuantity").getAsInt();
        }

    }
}
