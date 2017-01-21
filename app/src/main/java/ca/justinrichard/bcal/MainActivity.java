package ca.justinrichard.bcal;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.firebase.iid.FirebaseInstanceId;

import static ca.justinrichard.bcal.R.id.mWebView;

public class MainActivity extends AppCompatActivity {

    public final static String TAG = "MainActivity";
    public final static String homeUrl = "https://bcal.ca/";

    private WebView wv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Prepare swipe to refresh
        SwipeRefreshLayout swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipeToRefresh);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Insert your code here
                wv.reload(); // refreshes the WebView
            }
        });

        // Check to see if extra data sent
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        Log.i(TAG, "Intent extra url: "+url);

        // Ensure we have a firebase id
        Log.i(TAG, "Getting firebase token...");
        String firebaseToken = FirebaseInstanceId.getInstance().getToken();

        // Get the webView object and load the page
        wv = (WebView) findViewById(mWebView);
        // Set cookie
        CookieManager.getInstance().setCookie("https://bcal.ca/", "firebaseToken="+firebaseToken);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.getSettings().setDomStorageEnabled(true);
        wv.setWebViewClient(new WebViewClient());
        if(url != null){
            wv.loadUrl("https://bcal.ca"+url);
        } else {
            wv.loadUrl(homeUrl);
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
                wv.reload();
                return true;
            case R.id.action_home:
                // Bring user back to starting page
                wv.loadUrl(homeUrl);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
