package tw.edu.ntu.csie.dingjegan.tea;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ListActivity extends AppCompatActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    Integer pagenum;

    int[] teaitems = {2,15,17,28,29,30,77,78,79,80,81,345,346,347,348,349,350,351,352,353,82,83,255};
    int maxpage = (teaitems.length-1)/5 + 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);

        Bundle recv =this.getIntent().getExtras();
        pagenum = recv.getInt("PageNum");
        if(pagenum == 1){
            ImageButton prevpage = (ImageButton) findViewById(R.id.PreviousPageButton);
            prevpage.setVisibility(4);//invisible
        }
        if(pagenum == maxpage){
            ImageButton nextpage = (ImageButton) findViewById(R.id.NextPageButton);
            nextpage.setVisibility(4);//invisible
            int remainder = teaitems.length%5;
            LinearLayout LLs[]={
                    (LinearLayout) findViewById(R.id.listItem1),
                    (LinearLayout) findViewById(R.id.listItem2),
                    (LinearLayout) findViewById(R.id.listItem3),
                    (LinearLayout) findViewById(R.id.listItem4),
                    (LinearLayout) findViewById(R.id.listItem5),
            };
            for(int i = remainder; 0 < i && i < 5; i++){
                LLs[i].setVisibility(8);//gone
            }
        }
        TextView pagetext = (TextView) findViewById(R.id.ListPage);
        String page = "第" + pagenum + "页";
        pagetext.setText(page);

        //Set images
        ImageView IVs[]={
            (ImageView) findViewById(R.id.TeaListImage1),
            (ImageView) findViewById(R.id.TeaListImage2),
            (ImageView) findViewById(R.id.TeaListImage3),
            (ImageView) findViewById(R.id.TeaListImage4),
            (ImageView) findViewById(R.id.TeaListImage5),
        };
        //Set title texts
        TextView TVs[]={
                (TextView) findViewById(R.id.TeaTitle1),
                (TextView) findViewById(R.id.TeaTitle2),
                (TextView) findViewById(R.id.TeaTitle3),
                (TextView) findViewById(R.id.TeaTitle4),
                (TextView) findViewById(R.id.TeaTitle5),
        };
        //GET JSON
        //usage: new DownloadImageTask(img1).execute("http://www.csie.ntu.edu.tw/~b03902051/fc2/station_convert.png");
        int j = 0;
        for(int k = 5*(pagenum-1); k < 5*(pagenum-1)+5 && k < teaitems.length; k++){
            AsyncHttpRequest task = new AsyncHttpRequest(this,new DownloadImageTask(IVs[j]), TVs[j]);
            task.execute("http://user.paga.moe/Rikiu-test/products/"+teaitems[k]);
            j++;
        }

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void GoBuyTea1(View view) {
        Intent intent = new Intent(this, BuyActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("ItemNum", 5*(pagenum-1));
        bundle.putIntArray("ItemNumsArray",teaitems);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void GoBuyTea2(View view) {
        Intent intent = new Intent(this, BuyActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("ItemNum", 5*(pagenum-1)+1);
        bundle.putIntArray("ItemNumsArray", teaitems);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void GoBuyTea3(View view) {
        Intent intent = new Intent(this, BuyActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("ItemNum", 5*(pagenum-1)+2);
        bundle.putIntArray("ItemNumsArray", teaitems);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void GoBuyTea4(View view) {
        Intent intent = new Intent(this, BuyActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("ItemNum", 5*(pagenum-1)+3);
        bundle.putIntArray("ItemNumsArray", teaitems);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void GoBuyTea5(View view) {
        Intent intent = new Intent(this, BuyActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("ItemNum", 5*(pagenum-1)+4);
        bundle.putIntArray("ItemNumsArray", teaitems);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void GoInfoTea1(View view) {
        Intent intent = new Intent(this, InfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("ItemNum", 5*(pagenum-1));
        bundle.putIntArray("ItemNumsArray", teaitems);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void GoInfoTea2(View view) {
        Intent intent = new Intent(this, InfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("ItemNum", 5*(pagenum-1)+1);
        bundle.putIntArray("ItemNumsArray", teaitems);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void GoInfoTea3(View view) {
        Intent intent = new Intent(this, InfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("ItemNum", 5*(pagenum-1)+2);
        bundle.putIntArray("ItemNumsArray", teaitems);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void GoInfoTea4(View view) {
        Intent intent = new Intent(this, InfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("ItemNum", 5*(pagenum-1)+3);
        bundle.putIntArray("ItemNumsArray", teaitems);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void GoInfoTea5(View view) {
        Intent intent = new Intent(this, InfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("ItemNum", 5*(pagenum-1)+4);
        bundle.putIntArray("ItemNumsArray", teaitems);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void GoPrevPage(View view) {
        if(pagenum > 1){
            finish();
        }
    }

    public void GoNextPage(View view) {
        if(pagenum < maxpage){
            Intent intent = new Intent(this, ListActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("PageNum", pagenum+1);
            intent.putExtras(bundle);
            startActivity(intent);
        }
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
        private TextView TV;
        public AsyncHttpRequest(Activity activity,DownloadImageTask task, TextView TV) {

            this.mainActivity = activity;
            this.task = task;
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
                task.execute(null);
                TV.setText("暂无商品名称");
                return;
            }
            JsonObject myObject = new JsonParser().parse(result).getAsJsonObject();
            int status = myObject.get("status").getAsInt();
            //System.out.println(status);
            // TODO:if (status == 1)
            JsonObject data = myObject.getAsJsonObject("data");
            String imageURL = data.get("LargeIcon").getAsString();
            String title = data.get("ProductTitle").getAsString();

            task.execute(imageURL);
            TV.setText(title);
        }

    }



    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "List Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://tw.edu.ntu.csie.dingjegan.tea/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "List Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://tw.edu.ntu.csie.dingjegan.tea/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }



}


