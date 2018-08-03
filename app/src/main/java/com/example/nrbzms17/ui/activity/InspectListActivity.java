package com.example.nrbzms17.ui.activity;

import android.app.Activity;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;


import com.example.nrbzms17.R;
import com.example.nrbzms17.Utils.JSONUtils;
import com.example.nrbzms17.data.Api;
import com.example.nrbzms17.data.SharedPreference;
import com.example.nrbzms17.data.listener.OnNetRequest;
import com.example.nrbzms17.data.model.InspectBean;
import com.example.nrbzms17.data.model.InspectBeanResponse;

import com.example.nrbzms17.data.model.StatusBean;
import com.example.nrbzms17.data.model.StatusBeanResponse;
import com.example.nrbzms17.ui.adapter.InspectListAdapter;
import com.example.nrbzms17.ui.adapter.SpinnerStatusAdapter;
import com.jingchen.pulltorefresh.PullToRefreshLayout;

import java.util.ArrayList;
import java.util.List;

public class InspectListActivity extends Activity {


    PullToRefreshLayout pullToRefreshLayout;
    InspectListAdapter inspectListAdapter;

    Spinner inspect_status;

    SpinnerStatusAdapter statusAdapter;

    private List<InspectBean.Data> InspectBeanList = new ArrayList<>();


    private StatusBean statusBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspect);
//        webView = findViewById(R.id.echarts);
//        webView.getSettings().setAllowFileAccess(true);
//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.loadUrl("file:///android_asset/echart/index.html");
//        webView.loadUrl("http://www.baidu.com");
        getInspectList();
        initview();
        setClickListeners();
        getCommenStatus();

    }

    public void initview() {
        pullToRefreshLayout = findViewById(R.id.pullToRefreshLayout);
        inspectListAdapter = new InspectListAdapter();
        inspect_status = (Spinner) findViewById(R.id.inspect_status);

        //状态
        statusAdapter = new SpinnerStatusAdapter();
        inspect_status.setAdapter(statusAdapter);


        ListView listView = (ListView) pullToRefreshLayout.getPullableView();
        listView.setAdapter(inspectListAdapter);
        pullToRefreshLayout.setPullUpEnable(false);
        pullToRefreshLayout.setOnPullListener(new PullToRefreshLayout.OnPullListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {

                getInspectList();
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {

            }

        });
    }
    //状态下拉
    private void setClickListeners() {

        inspect_status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                statusBean = (StatusBean) statusAdapter.getItem(position);
                getInspectList();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    //获取检验
    public void getInspectList() {
        String status = "";

        if (statusBean != null) {
            status = statusBean.id;

            if (status == "-1") {
                status = "";
            }
        }
        Api api = new Api(this, new OnNetRequest(this, true, "正在加载.....") {
            @Override
            public void onSuccess(String msg) {

                InspectBeanResponse response = JSONUtils.fromJson(msg, InspectBeanResponse.class);

                if (response != null && response.result != null) {

                    InspectBeanList = response.result;

                } else {
                    InspectBeanList = new ArrayList<>();
                }
                inspectListAdapter.refresh(InspectBeanList);
                pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
            }

            @Override
            public void onFail() {

                inspectListAdapter.refresh(InspectBeanList);

                pullToRefreshLayout.refreshFinish(PullToRefreshLayout.FAIL);
            }
        });

        api.getInspectList(status);
    }

    // 获取状态信息
    public void getCommenStatus() {
        Api api = new Api(this, new OnNetRequest(this) {
            @Override
            public void onSuccess(String msg) {
                StatusBeanResponse statusBeanResponse = JSONUtils.fromJson(msg, StatusBeanResponse.class);
                if (statusBeanResponse != null && statusBeanResponse.result != null) {

                    statusAdapter.refresh(statusBeanResponse.result);

                    String departmentId = SharedPreference.getDepartmentId();
                    if (TextUtils.isEmpty(departmentId) || departmentId.equals("-1")) {
                        return;
                    }
                    for (int i = 0; i < statusAdapter.getCount(); i++) {
                        StatusBean bean = (StatusBean) statusAdapter.getItem(i);

                    }
                }
            }

            @Override
            public void onFail() {

            }
        });

        api.getCommenStatus();
    }
}
