package com.example.nrbzms17.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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
import com.example.nrbzms17.data.model.OrderBean;
import com.example.nrbzms17.data.model.OrderBeanResponse;
import com.example.nrbzms17.data.model.OrderListBean;
import com.example.nrbzms17.data.model.StatusBean;
import com.example.nrbzms17.data.model.StatusBeanResponse;
import com.example.nrbzms17.ui.adapter.OrderDetailAdapter;
import com.example.nrbzms17.ui.adapter.OrderListAdapter;
import com.example.nrbzms17.ui.adapter.SpinnerDateAdapter;
import com.example.nrbzms17.ui.adapter.SpinnerStatusAdapter;
import com.example.nrbzms17.ui.widget.ClearEditText;
import com.jingchen.pulltorefresh.PullToRefreshLayout;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderListActivity extends AppCompatActivity {

    OrderListAdapter adapter = new OrderListAdapter();

    private List<OrderBean.Data> OrderBeanList = new ArrayList<>();

    private OrderBean order;

    Spinner order_status;

    TextView textView;

    OrderDetailAdapter orderadapter;

    ClearEditText etCode;

    PullToRefreshLayout pullToRefreshLayout;

    SpinnerStatusAdapter statusAdapter;

    private StatusBean statusBean;

    Spinner choose_date;

    SpinnerDateAdapter dateAdapter;

    private DateBean dateBean;

    @BindView(R.id.txtvActionbarTitle)
    TextView txtvActionbarTitle;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_order_list);
        ButterKnife.bind(this);

        order_status = (Spinner) findViewById(R.id.order_status);

        textView = (TextView) findViewById(R.id.txtv_status);

        etCode = (ClearEditText) findViewById(R.id.etCode);

        statusAdapter = new SpinnerStatusAdapter();

        order_status.setAdapter(statusAdapter);

        order = new OrderBean();

        initViews();

        getOrderList();


        setClickListeners();

        Button button = (Button) findViewById(R.id.back_menu);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        getStatusInfo();

        getDateInfo();
        txtvActionbarTitle.setText("订单列表");
    }


    /**
     * 初始化试图
     */
    private void initViews() {

        Button etSearch = (Button) findViewById(R.id.etSearch);

        pullToRefreshLayout = findViewById(R.id.pullToRefreshLayout);

        // 设置列表适配器
        ListView listView = (ListView) pullToRefreshLayout.getPullableView();

        listView.setAdapter(adapter);

        pullToRefreshLayout.setPullUpEnable(false);


        etSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOrderList();

            }
        });
        // 下拉刷新事件
        pullToRefreshLayout.setOnPullListener(new PullToRefreshLayout.OnPullListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {

                getOrderList();
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {

            }

        });

        // 栏目点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                OrderBean.Data processOrder = (OrderBean.Data) adapter.getItem(position);


                Intent intent = new Intent(OrderListActivity.this, OrderDetailActivity.class);
                Bundle bundle = new Bundle();

                bundle.putSerializable(OrderListBean.Data.class.getSimpleName(), processOrder);
                intent.putExtras(bundle);

                startActivity(intent);
            }
        });
        //日期查询
        choose_date = findViewById(R.id.order_date);
        dateAdapter = new SpinnerDateAdapter();
        choose_date.setAdapter(dateAdapter);
    }


    //状态下拉
    private void setClickListeners() {

        order_status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                statusBean = (StatusBean) statusAdapter.getItem(position);
                getOrderList();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        choose_date.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                dateBean = (DateBean) dateAdapter.getItem(position);
                getOrderList();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    //获取订单列表
    public void getOrderList() {

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

                OrderBeanResponse response = JSONUtils.fromJson(msg, OrderBeanResponse.class);

                if (response != null && response.result != null) {

                    OrderBeanList = response.result;
//                    adapter.refresh(OrderBeanList);

                } else {
                    OrderBeanList = new ArrayList<>();
                }
                adapter.refresh(OrderBeanList);
                pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
            }

            @Override
            public void onFail() {

                adapter.refresh(OrderBeanList);

                pullToRefreshLayout.refreshFinish(PullToRefreshLayout.FAIL);
            }
        });

        api.getIndentInfoList(status, etCode.getText().toString().trim(),date);
//        }

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

        getOrderList();
    }


}
