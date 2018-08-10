package com.example.nrbzms17.ui.activity;

import android.support.v7.app.AppCompatActivity;
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

import com.example.nrbzms17.data.model.SaleBean;
import com.example.nrbzms17.data.model.SaleBeanResponse;

import com.example.nrbzms17.data.model.StatusBean;
import com.example.nrbzms17.data.model.StatusBeanResponse;
import com.example.nrbzms17.ui.adapter.SaleListAdapter;
import com.example.nrbzms17.ui.adapter.SpinnerStatusAdapter;
import com.jingchen.pulltorefresh.PullToRefreshLayout;

import java.util.ArrayList;
import java.util.List;

public class SaleListActivity extends AppCompatActivity {

    private List<SaleBean.Data> SaleBeanList = new ArrayList<>();

    ListView listView;

    PullToRefreshLayout pullToRefreshLayout;

    SaleListAdapter adapter = new SaleListAdapter();

    SpinnerStatusAdapter statusAdapter;

    Spinner sale_status;

    private StatusBean statusBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_list);
        initview();
        getSaleList();
        setClickListeners();
        getCommenStatus();
    }

    public void initview() {
        pullToRefreshLayout = findViewById(R.id.pullToRefreshLayout);
        sale_status = findViewById(R.id.sale_status);
        //状态
        statusAdapter = new SpinnerStatusAdapter();
        sale_status.setAdapter(statusAdapter);

        // 设置列表适配器
        listView = (ListView) pullToRefreshLayout.getPullableView();

        listView.setAdapter(adapter);

        pullToRefreshLayout.setPullUpEnable(false);

        pullToRefreshLayout.setOnPullListener(new PullToRefreshLayout.OnPullListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {

                getSaleList();
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {

            }

        });
    }

    //状态下拉
    private void setClickListeners() {

        sale_status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                statusBean = (StatusBean) statusAdapter.getItem(position);

                getSaleList();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    //获取发货列表
    public void getSaleList() {

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

                SaleBeanResponse response = JSONUtils.fromJson(msg, SaleBeanResponse.class);

                if (response != null && response.result != null) {

                    SaleBeanList = response.result;
//                    adapter.refresh(OrderBeanList);

                } else {
                    SaleBeanList = new ArrayList<>();
                }
                adapter.refresh(SaleBeanList);
                pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
            }

            @Override
            public void onFail() {

                adapter.refresh(SaleBeanList);

                pullToRefreshLayout.refreshFinish(PullToRefreshLayout.FAIL);
            }
        });

        api.getSaleInfoList(status);

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
