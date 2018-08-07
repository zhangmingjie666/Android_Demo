package com.example.nrbzms17.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import com.example.nrbzms17.R;

public class EchartsActivity extends AppCompatActivity implements View.OnClickListener{

    WebView webView;
    Button charts;
    private boolean isShowOrNot = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_echarts);

        charts = findViewById(R.id.charts);

        webView = findViewById(R.id.echarts);

        charts.setOnClickListener(this);
    }
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.charts:

                if (isShowOrNot == false) {

                    webView.setVisibility(View.VISIBLE); // 设置显示

                    webView.getSettings().setAllowFileAccess(true);

                    webView.getSettings().setJavaScriptEnabled(true);

                    webView.loadUrl("file:///android_asset/echart/index.html");

                    isShowOrNot = true;

                } else {

                    webView.setVisibility(View.GONE); // 设置隐藏

                    isShowOrNot = false;

                }

                break;
            default:
                break;
        }
    }
}
