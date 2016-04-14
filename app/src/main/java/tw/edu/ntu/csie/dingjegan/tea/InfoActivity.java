package tw.edu.ntu.csie.dingjegan.tea;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class InfoActivity extends AppCompatActivity {

    int itemnum;
    int[] teaitems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle recv =this.getIntent().getExtras();
        itemnum = recv.getInt("ItemNum");
        teaitems = recv.getIntArray("ItemNumsArray");
        setContentView(R.layout.viewitem);
        ImageView ItemImage = (ImageView)findViewById(R.id.ViewImage);
        TextView ItemTitle = (TextView)findViewById(R.id.ViewItemName);
        TextView ItemPrice = (TextView)findViewById(R.id.ViewPrice);
        TextView ItemInfo = (TextView)findViewById(R.id.ViewInfo);
        ImageButton ItemBuy = (ImageButton)findViewById(R.id.TeaInfoBuyButton);

        //GET JSON
        AsyncHttpRequest task = new AsyncHttpRequest(this,new DownloadImageTask(ItemImage), ItemTitle, ItemPrice, ItemInfo);
        task.execute(getResources().getString(R.string.Server) + teaitems[itemnum]);

    }

    public void GoBuyTea (View view){
        Intent intent = new Intent (this, BuyActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("ItemNum",itemnum);
        bundle.putIntArray("ItemNumsArray", teaitems);
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
        private TextView Info;
        public AsyncHttpRequest(Activity activity,DownloadImageTask task, TextView Title, TextView Price, TextView Info) {

            this.mainActivity = activity;
            this.task = task;
            this.Title = Title;
            this.Price = Price;
            this.Info = Info;
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
                Info.setText("暂无商品资料");
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
            Price.setText("售价:¥"+data.get("SellPriceCNY").getAsString());
            Info.setText(data.get("ProductIntroduction").getAsString());
        }

    }
}
