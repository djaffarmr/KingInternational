package id.dgd.kinginternational;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;

/**
 * Created by Admin on 8/18/2017.
 */

public class WebPagesActivity extends AppCompatActivity {
    // variables
    private WebView webView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Intent intent;
    private String extraString;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_webview);

        // custom toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // swipe refresh method
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadWeb();
            }
        });

        // unpack intent's extra from MainActivity
        intent = getIntent();
        if (intent.hasExtra(Intent.EXTRA_TEXT)) {
            extraString = intent.getStringExtra(Intent.EXTRA_TEXT);
        }

        // load the web
        loadWeb();

        // home button function
        homeBtn();
    }

    public void loadWeb() {
        // state the webview
        webView = (WebView) findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);

        // set webview to counter unresponsive website
        if (extraString.equals("pesawat") | extraString.equals("kereta") |
                extraString.equals("hotel") | extraString.equals("transport") |
                extraString.equals("agen")) {

            // zoom in the page to better view
            webView.setInitialScale(150);
        } else if (extraString.equals("ppob") | extraString.equals("pulsa")) {

            webView.setInitialScale(120);

        } else {
            // zoom out to fit the web content
            webView.setInitialScale(1);
        }

        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);

        // allow webview to be refreshed
        swipeRefreshLayout.setRefreshing(true);

        // enable built-in zoom function
        webView.getSettings().setBuiltInZoomControls(true);

        // TODO (1) change client's urls here!
        // decide which url to use. based on intent's extra value
        switch (extraString) {
            case "pesawat":
                url = "http://booking.klikmbc.co.id/booking/flights/page/formagen.php?s=kinginternationaltravel.com&d=hotel-kereta-antarjemput-registeragen-umroh-bustravel";
                break;
            case "kereta":
                url = "http://booking.klikmbc.co.id/booking/flights/page/formagen.php?s=kinginternationaltravel.com&d=pesawat-hotel-antarjemput-registeragen-umroh-bustravel";
                break;
            case "hotel":
                url = "http://booking.klikmbc.co.id/booking/flights/page/formagen.php?s=kinginternationaltravel.com&d=pesawat-kereta-antarjemput-registeragen-umroh-bustravel";
                break;
            case "umroh":
                url = "http://kinginternationaltravel.com/umroh";
                break;
            case "bus":
                url = "http://transaksi.klikmbc.co.id/bustravel/index.php?s=kinginternationaltravel.com";
                break;
            case "transport":
                url = "http://booking.klikmbc.co.id/booking/flights/page/formagen.php?s=kinginternationaltravel.com&d=pesawat-hotel-kereta-registeragen-umroh-bustravel";
                break;
            case "ppob":
                url = "http://transaksi.klikmbc.co.id/ppob";
                break;
            case "pulsa":
                url = "https://transaksi.klikmbc.co.id/pulsa";
                break;
            case "tur":
                url = "http://tour.klikmbc.co.id/?s=tour.klikmbc.co.id?s=kinginternationaltravel.com";
                break;
            case "agen":
                url = "http://booking.klikmbc.co.id/booking/flights/page/formagen.php?s=kinginternationaltravel.com&d=pesawat-kereta-antarjemput-umroh-bustravel-hotel";
                break;
            case "kontak":
                url = "http://kinginternationaltravel.com/contact-us";
                break;
        }

        webView.loadUrl(url);

        // set to error page if loading failed
        webView.setWebViewClient(new WebViewClient() {

            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {

                webView.loadUrl("file:///android_asset/error.html");
            }

            public void onPageFinished(WebView view, String url) {

                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    public void homeBtn() {
        ImageButton btnHome = (ImageButton) findViewById(R.id.btn_home);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WebPagesActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    // set device back button act like browser
    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

}
