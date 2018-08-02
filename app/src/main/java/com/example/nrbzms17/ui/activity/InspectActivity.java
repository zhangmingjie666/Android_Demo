package com.example.nrbzms17.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

import com.example.nrbzms17.R;

public class InspectActivity extends Activity {
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_echarts);
//        webView = findViewById(R.id.echarts);
//        webView.getSettings().setAllowFileAccess(true);
//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.loadUrl("file:///android_asset/echart/index.html");
//        webView.loadUrl("http://www.baidu.com");



    }
}
