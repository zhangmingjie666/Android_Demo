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
import com.example.nrbzms17.Utils.UIHelper;
import com.example.nrbzms17.data.Api;
import com.example.nrbzms17.Utils.JSONUtils;
import com.example.nrbzms17.data.listener.OnNetRequest;

//import com.example.fragmentdemo.adapter.OrderListAdapter.java;
import com.example.nrbzms17.data.model.OrderBean;
import com.example.nrbzms17.data.model.OrderBeanResponse;
import com.example.nrbzms17.data.model.OrderListBean;
import com.example.nrbzms17.data.model.StatusBean;
import com.example.nrbzms17.data.model.StatusBeanResponse;
import com.example.nrbzms17.ui.adapter.OrderDetailAdapter;
import com.example.nrbzms17.ui.adapter.OrderListAdapter;
import com.example.nrbzms17.ui.adapter.SpinnerStatusAdapter;
import com.example.nrbzms17.ui.widget.ClearEditText;
import com.google.gson.JsonObject;
import com.jingchen.pulltorefresh.PullToRefreshLayout;
//import com.example.fragmentdemo.widget.ClearEditText;
//import com.jingchen.pulltorefresh.PullToRefreshLayout;

import java.util.ArrayList;
import java.util.List;

public class OrderListActivity extends AppCompatActivity {

    OrderListAdapter adapter = new OrderListAdapter();

    private List<OrderBean.Data> OrderBeanList = new ArrayList<>();


    private OrderBean order;
    Spinner spinner;
    TextView textView;
    OrderDetailAdapter orderadapter;
    ClearEditText etCode;
    PullToRefreshLayout pullToRefreshLayout;
    String[] Items = {"待审核", "已审核"};
    SpinnerStatusAdapter statusAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);

        spinner = (Spinner) findViewById(R.id.spinner_status);
        textView = (TextView) findViewById(R.id.txtv_status);

        etCode = (ClearEditText) findViewById(R.id.etCode);
        spinner = findViewById(R.id.spinner_status);

        order = new OrderBean();


        getOrderList("status");

        //下拉列表
        ArrayAdapter<String> statusAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Items);

        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        spinner.setAdapter(statusAdapter);

        spinner.setSelection(0, true);

        spinner.setOnItemSelectedListener(new com.example.nrbzms17.ui.activity.OrderListActivity.SpinnerSelectedListener());

        initViews();
//        getStatus();


//        setClickListeners();

        Button button = (Button) findViewById(R.id.back_menu);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


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


//


        etSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOrderList("status");
                spinner.setSelection(0, true);
            }
        });
//        // 下拉刷新事件
        pullToRefreshLayout.setOnPullListener(new PullToRefreshLayout.OnPullListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                getOrderList("status");
                spinner.setSelection(0, true);
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
    }

    //获取当前状态
    public void getStatus() {

        Api api = new Api(this, new OnNetRequest(this) {

            @Override
            public void onSuccess(String msg) {

                StatusBeanResponse response = JSONUtils.fromJson(msg, StatusBeanResponse.class);

                if (response != null && response.result != null) {

                    statusAdapter.refresh(response.result);

                    for (int i = 0; i < statusAdapter.getCount(); i++) {

                        StatusBean bean = (StatusBean) statusAdapter.getItem(i);

                        spinner.setSelection(i);

                        break;

                    }
                }
            }

            @Override
            public void onFail() {

            }

        });

    }

    //获取订单列表
    public void getOrderList(String status) {
//        List<OrderBean> list = new ArrayList<>();
//        adapter.refresh(list);
//
//        String barcode = etCode.getText().toString().trim();
//
//        if(barcode.equals("")){
//            adapter.refresh(list);
//        } else {

//        adapter = new OrderListAdapter.java();

//        listView.setAdapter(adapter);


        Api api = new Api(this, new OnNetRequest(this, true, "正在加载.....") {
            @Override
            public void onSuccess(String msg) {

                OrderBeanResponse response = JSONUtils.fromJson(msg, OrderBeanResponse.class);

                if (response != null && response.result != null) {

                    if (response.result.size() > 0) {

                        OrderBeanList = response.result;

                        adapter.refresh(OrderBeanList);


//                        order = OrderBeanList.get(0);


                    } else {

                        OrderBeanList = new ArrayList<>();

                        order = null;

                    }
                    adapter.refresh(OrderBeanList);

                    pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                }
            }

            @Override
            public void onFail() {

                adapter.refresh(OrderBeanList);

                pullToRefreshLayout.refreshFinish(PullToRefreshLayout.FAIL);
            }
        });

        api.getIndentInfoList(status, etCode.getText().toString().trim());
//        }

    }

    //状态选择事件
    class SpinnerSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> arg0, View view, int position, long id) {

//            String data = (String) spinner.getItemAtPosition(position);


//            Toast.makeText(OrderListActivity.this,str,Toast.LENGTH_SHORT).show();


            String str;

            str = position + "";

            getOrderList(str);


        }

        public void onNothingSelected(AdapterView<?> arg0) {

        }
    }


    // 删除
//    public void deleteProcessPack(String id) {
//        Api api = new Api(this, new OnNetRequest(this, true, "请稍等") {
//            @Override
//            public void onSuccess(String msg) {
//                ProcessOrderPackListBean responseBean = JSONUtils.fromJson(msg, ProcessOrderPackListBean.class);
//                if (responseBean != null && responseBean.result != null) {
//                    if (responseBean.result.size() == 0) {
//                    }
//                    UIHelper.showShortToast(ProcessOrderPackActivity.this, "删除成功");
//                    adapter.refresh(responseBean.result);
//                } else {
//                }
//
//                listView.turnNormal();
//                calcQuantity();
//            }
//
//            @Override
//            public void onFail() {
//
//                listView.turnNormal();
//            }
//        });
//
//        api.deleteProcessPack(id);
//    }


//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        getOrderList("status");
//    }

    @Override
    protected void onResume() {

        super.onResume();

        getOrderList("status");

        spinner.setSelection(0, true);
    }


}
