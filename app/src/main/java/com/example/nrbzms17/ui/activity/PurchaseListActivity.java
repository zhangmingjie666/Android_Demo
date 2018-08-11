package com.example.nrbzms17.ui.activity;


import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.nrbzms17.R;

import com.example.nrbzms17.Utils.JSONUtils;
import com.example.nrbzms17.data.Api;
import com.example.nrbzms17.data.SharedPreference;
import com.example.nrbzms17.data.listener.OnNetRequest;
import com.example.nrbzms17.data.model.DateBean;
import com.example.nrbzms17.data.model.DateBeanResponse;
import com.example.nrbzms17.data.model.PurchaseBean;
import com.example.nrbzms17.data.model.PurchaseBeanResponse;
import com.example.nrbzms17.data.model.StatusBean;
import com.example.nrbzms17.data.model.StatusBeanResponse;
import com.example.nrbzms17.ui.adapter.PurchaseListAdapter;
import com.example.nrbzms17.ui.adapter.SpinnerDateAdapter;
import com.example.nrbzms17.ui.adapter.SpinnerStatusAdapter;
import com.example.nrbzms17.ui.widget.ClearEditText;
import com.jingchen.pulltorefresh.PullToRefreshLayout;

import java.util.ArrayList;



import java.util.List;

public class PurchaseListActivity extends AppCompatActivity {

    Button purchase_menu;

    PurchaseBean purchaseBean;

    ListView purchaseView;

    PurchaseListAdapter purchaseListAdapter;

    private List<PurchaseBean.Data> PurchaseBean = new ArrayList<>();

    PurchaseBean purchase = new PurchaseBean();
    ClearEditText purCode;

    Spinner purchase_status;



    Button purSearch;

    SpinnerStatusAdapter statusAdapter;

    private StatusBean statusBean;
    private DateBean dateBean;


    TextView txtv_Name;

    Spinner choose_date;

    SpinnerDateAdapter dateAdapter;

    PullToRefreshLayout pullToRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_list);

        initview();

        setClickListeners();


        getStatusInfo();

        getDateInfo();

    }

    public void initview() {

        pullToRefreshLayout = findViewById(R.id.pullToRefreshLayout);
        purchaseListAdapter = new PurchaseListAdapter();

        //日期查询
        choose_date = findViewById(R.id.choose_date);
        dateAdapter = new SpinnerDateAdapter();
        choose_date.setAdapter(dateAdapter);


        // 设置列表适配器
        ListView listView = (ListView) pullToRefreshLayout.getPullableView();
        listView.setAdapter(purchaseListAdapter);
        pullToRefreshLayout.setPullUpEnable(false);

        //返回
        purchase_menu = findViewById(R.id.purchase_menu);
        purchase_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        purCode = (ClearEditText) findViewById(R.id.purCode);

        purchase_status = (Spinner) findViewById(R.id.purchase_status);

        purSearch = (Button) findViewById(R.id.purSearch);

        txtv_Name = (TextView) findViewById(R.id.txtv_Name);

        pullToRefreshLayout.setOnPullListener(new PullToRefreshLayout.OnPullListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                getPurchaseList();
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {

            }

        });

        //查询
        purSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPurchaseList();
            }
        });

        //栏目点击
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PurchaseBean.Data processOrder = (PurchaseBean.Data) purchaseListAdapter.getItem(position);

                Intent intent = new Intent(PurchaseListActivity.this, PurchaseDetailActivity.class);
                Bundle bundle = new Bundle();

                bundle.putSerializable(PurchaseBean.Data.class.getSimpleName(), processOrder);
                intent.putExtras(bundle);

                startActivity(intent);
            }
        });


        //状态
        statusAdapter = new SpinnerStatusAdapter();

        purchase_status.setAdapter(statusAdapter);

    }

    //状态下拉
    private void setClickListeners() {

        purchase_status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                statusBean = (StatusBean) statusAdapter.getItem(position);
                getPurchaseList();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        choose_date.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                dateBean = (DateBean) dateAdapter.getItem(position);
                getPurchaseList();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    //获取采购列表
    public void getPurchaseList() {

        String status = "";

        if (statusBean != null) {
            status = statusBean.id;

            if (status == "-1") {
                status = "";
            }
        }

        String date ="";
        if(dateBean !=null){
            date= dateBean.id;
        }

        Api api = new Api(this, new OnNetRequest(this, true, "正在加载.....") {
            @Override
            public void onSuccess(String msg) {

                PurchaseBeanResponse response = JSONUtils.fromJson(msg, PurchaseBeanResponse.class);

                if (response != null && response.result != null) {
                    PurchaseBean = response.result;
                } else {
                    PurchaseBean = new ArrayList<>();
                }

                purchaseListAdapter.refresh(PurchaseBean);
                pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
            }

            @Override
            public void onFail() {

                purchaseListAdapter.refresh(PurchaseBean);
                pullToRefreshLayout.refreshFinish(PullToRefreshLayout.FAIL);

            }
        });

        api.getPurchaseList(status, purCode.getText().toString().trim(), date);
    }


    // 获取状态信息
    public void getStatusInfo() {
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

        api.getStatusInfo();
    }

    //获取查询日期
    public void getDateInfo(){
        Api api = new Api(this, new OnNetRequest(this) {
            @Override
            public void onSuccess(String msg) {
                DateBeanResponse dateBeanResponse = JSONUtils.fromJson(msg, DateBeanResponse.class);
                if (dateBeanResponse != null && dateBeanResponse.result != null) {

                    dateAdapter.refresh(dateBeanResponse.result);

                    for (int i = 0; i < statusAdapter.getCount(); i++) {
                        DateBean bean = (DateBean) dateAdapter.getItem(i);
                    }
                }
            }
            @Override
            public void onFail() {

            }
        });

        api.getDateInfo();
    }

    @Override
    protected void onResume() {

        super.onResume();

        getPurchaseList();

        purCode.setText("");
    }
}