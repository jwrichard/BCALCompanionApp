package ca.justinrichard.bcal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.firebase.iid.FirebaseInstanceId;

import static ca.justinrichard.bcal.R.id.mWebView;

public class MainActivity extends AppCompatActivity {

    public final static String TAG = "MainActivity";
    //public final static String homeUrl = "https://54.187.207.160/";
    public final static String homeUrl = "http://google.ca";
    private WebView wv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup the toolbar
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        // Ensure we have a firebase id
        Log.i(TAG, "Getting firebase token...");
        String firebaseToken = FirebaseInstanceId.getInstance().getToken();

        wv = (WebView) findViewById(mWebView);
        if(wv != null){
            wv.getSettings().setJavaScriptEnabled(true);
            wv.getSettings().setDomStorageEnabled(true);
            wv.getSettings().setBuiltInZoomControls(true);
            wv.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
            wv.postDelayed(new Runnable() {
                @Override
                public void run() {
                    wv.loadUrl(homeUrl);
                }
            }, 500);

            //wv.loadUrl("https://54.187.207.160/");

        } else {
            Log.e(TAG, "Failed to get WebView object");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_refresh:
                // Refresh the current page the user has open
                Log.i(TAG, "User clicked refresh");
                return true;
            case R.id.action_home:
                // Bring user back to starting page
                wv.loadUrl("https://54.187.207.160/");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
