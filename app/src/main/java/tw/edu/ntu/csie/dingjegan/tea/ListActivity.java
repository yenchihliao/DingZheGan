package tw.edu.ntu.csie.dingjegan.tea;

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

import java.io.InputStream;

public class ListActivity extends AppCompatActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    Integer pagenum;

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
        if(pagenum == 10/*MAXPAGE*/){
            ImageButton nextpage = (ImageButton) findViewById(R.id.NextPageButton);
            nextpage.setVisibility(4);//invisible
        }
        TextView pagetext = (TextView) findViewById(R.id.ListPage);
        pagetext.setText("第"+pagenum+"页");

        ImageView img1 = (ImageView) findViewById(R.id.TeaListImage1);
        new DownloadImageTask(img1).execute("http://www.csie.ntu.edu.tw/~b03902051/fc2/station_convert.png");

        /* For dynamic layout.
        LinearLayout ll = (LinearLayout) findViewById(R.id.ScrollListLayout);
        int items = 1;
        for (int k = 1; k <= items; k++) {
            LinearLayout linear1 = new LinearLayout(this);
            ll.addView(linear1);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) linear1.getLayoutParams();
            float scale = getResources().getDisplayMetrics().density;
            int px = (int) (300 * scale + 0.5f);
            params.height = px;
            px = (int) (20 * scale + 0.5f);
            params.setMargins(px, 0, px, 0);
            px = (int) (50 * scale + 0.5f);
            linear1.setPadding(0, px, 0, px);
            linear1.setLayoutParams(params);
            linear1.setOrientation(LinearLayout.HORIZONTAL);


            LinearLayout linear2 = new LinearLayout(this);
            linear1.addView(linear2);
            linear2.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams params2 = (LinearLayout.LayoutParams)linear2.getLayoutParams();
            params2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
            linear2.setLayoutParams(params2);

            FrameLayout frame1 = new FrameLayout(this);
            linear2.addView(frame1);
            LinearLayout.LayoutParams params3 = (LinearLayout.LayoutParams)frame1.getLayoutParams();
            params3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
            frame1.setLayoutParams(params3);

            ImageView img2 = new ImageView(this);
            frame1.addView(img2);
            img2.setContentDescription(getResources().getString(R.string.Tea1TextFrameDescription));
            img2.setId(R.id.Tea1TextFrame);
            img2.setScaleType(ImageView.ScaleType.FIT_XY);
            img2.setImageResource(R.drawable.textframe);

            TextView text1 = new TextView(this);
            frame1.addView(text1);
            FrameLayout.LayoutParams params4 = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
            params4.gravity = Gravity.CENTER;
            text1.setLayoutParams(params4);
            text1.setId(R.id.TeaTitle1);
            px = (int) (15 * scale + 0.5f);
            text1.setPadding(px, px, px, px);
            text1.setText(R.string.Tea1);
            text1.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            text1.setTextColor(getResources().getColor(R.color.colorText));
            //px = (int) (30 * scale + 0.5f);
            text1.setTextSize(30);


            LinearLayout linear3 = new LinearLayout(this);
            linear2.addView(linear3);
            linear3.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams params5 = (LinearLayout.LayoutParams)linear3.getLayoutParams();
            params5 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
            linear3.setLayoutParams(params5);

            ImageButton imgbtn1 = new ImageButton(this);
            linear3.addView(imgbtn1);
            LinearLayout.LayoutParams params6 = (LinearLayout.LayoutParams)imgbtn1.getLayoutParams();
            params6 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
            imgbtn1.setLayoutParams(params6);

            int[] attrs = new int[]{R.attr.selectableItemBackground};
            TypedArray typedArray = this.obtainStyledAttributes(attrs);
            int backgroundResource = typedArray.getResourceId(0, 0);
            imgbtn1.setBackgroundResource(backgroundResource);
            typedArray.recycle();

            imgbtn1.setContentDescription(getResources().getString(R.string.InfoButtonDescription));
            imgbtn1.setId(R.id.Tea1InfoButton);
            imgbtn1.setOnClickListener(new Button.OnClickListener() {
                public void onClick(View v) {
                    GoInfoTea1(v);
                }
            });
            imgbtn1.setImageResource(R.drawable.morebtn);
            imgbtn1.setScaleType(ImageButton.ScaleType.FIT_XY);


            ImageButton imgbtn2 = new ImageButton(this);
            linear3.addView(imgbtn2);
            LinearLayout.LayoutParams params7 = (LinearLayout.LayoutParams)imgbtn1.getLayoutParams();
            params7 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
            imgbtn2.setLayoutParams(params7);

            attrs = new int[]{R.attr.selectableItemBackground};
            typedArray = this.obtainStyledAttributes(attrs);
            backgroundResource = typedArray.getResourceId(0, 0);
            imgbtn2.setBackgroundResource(backgroundResource);
            typedArray.recycle();

            imgbtn2.setContentDescription(getResources().getString(R.string.BuyButtonDescription));
            imgbtn2.setId(R.id.Tea1BuyButton);
            imgbtn2.setOnClickListener(new Button.OnClickListener() {
                public void onClick(View v) {
                    GoBuyTea1(v);
                }
            });
            imgbtn2.setImageResource(R.drawable.cartbtn);
            imgbtn2.setScaleType(ImageButton.ScaleType.FIT_XY);


            ImageView img1 = new ImageView(this);
            linear1.addView(img1);
            img1.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT, 1.0f));
            img1.setContentDescription(getResources().getString(R.string.TeaImage1Description));
            img1.setId(R.id.TeaImage1);
            img1.setScaleType(ImageView.ScaleType.CENTER);
            img1.setImageResource(R.drawable.tea1);


        }*/
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void GoBuyTea1(View view) {
        Intent intent = new Intent(this, BuyActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("ItemNum", 1);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void GoBuyTea2(View view) {
        Intent intent = new Intent(this, BuyActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("ItemNum", 2);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void GoBuyTea3(View view) {
        Intent intent = new Intent(this, BuyActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("ItemNum", 3);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void GoBuyTea4(View view) {
        Intent intent = new Intent(this, BuyActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("ItemNum", 4);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void GoInfoTea1(View view) {
        Intent intent = new Intent(this, InfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("ItemNum", 1);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void GoInfoTea2(View view) {
        Intent intent = new Intent(this, InfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("ItemNum", 2);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void GoInfoTea3(View view) {
        Intent intent = new Intent(this, InfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("ItemNum", 3);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void GoInfoTea4(View view) {
        Intent intent = new Intent(this, InfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("ItemNum", 4);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void GoPrevPage(View view) {
        if(pagenum > 1){
            finish();
        }
    }

    public void GoNextPage(View view) {
        if(pagenum < 10/*MAXPAGE*/){
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
            bmImage.setImageBitmap(result);
        }}

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
