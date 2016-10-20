package sy.com.high_shoppingdemo.myactivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import sy.com.high_shoppingdemo.R;

public class Third_WebViewActivity extends AppCompatActivity {

    @BindView(R.id.web_toolBar)
    Toolbar webToolbar;
    @BindView(R.id.web_webView)
    WebView webView;
    @BindView(R.id.web_progressBar)
    ProgressBar web_progressBar;

    String path = "http://m.hichao.com/lib/interface.php?m=goodsdetail&sid=";
    private String sourceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third__web_view);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        sourceId = intent.getStringExtra("sourceId");
//初始化  设置ToolBar
        initToolBar();
        //WebView 加载网址
        initWebView();


    }

    private void initWebView() {

        web_progressBar.setMax(100);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.setWebChromeClient(new WebViewClient() );
        webView.loadUrl(path + sourceId);

     /*   webView.loadUrl(path + sourceId);
        webView.setWebViewClient(new android.webkit.WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });*/
    }

    private void initToolBar() {
        webToolbar.setTitleTextColor(Color.WHITE);
        webToolbar.setTitle("详情");
        webToolbar.setNavigationIcon(android.R.drawable.star_off);
        webToolbar.setBackgroundColor(Color.DKGRAY);
        setSupportActionBar(webToolbar);


    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.web_shoucang:

                break;
            case R.id.web_shopping:

                break;
            case R.id.web_buy:

                break;
        }

    }

    private class WebViewClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {

            web_progressBar.setProgress(newProgress);
            if (newProgress == 100){
                web_progressBar.setVisibility(View.GONE);
            }
            super.onProgressChanged(view, newProgress);

        }
    }
}
