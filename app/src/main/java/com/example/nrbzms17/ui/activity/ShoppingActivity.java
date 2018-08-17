package com.example.nrbzms17.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.nrbzms17.R;
import com.example.nrbzms17.Utils.JSONUtils;
import com.example.nrbzms17.data.Api;
import com.example.nrbzms17.data.listener.OnNetRequest;
import com.example.nrbzms17.data.model.OrderBean;
import com.example.nrbzms17.data.model.OrderBeanResponse;
import com.example.nrbzms17.data.model.PurchasingBean;
import com.example.nrbzms17.data.model.PurchasingBeanResponse;
import com.example.nrbzms17.data.model.SaleBean;
import com.example.nrbzms17.ui.adapter.PurchasingAdapter;
import com.jingchen.pulltorefresh.PullToRefreshLayout;
import com.squareup.okhttp.Response;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShoppingActivity extends AppCompatActivity {

    @BindView(R.id.txtvActionbarTitle)
    TextView txtvActionbarTitle;

    PullToRefreshLayout pullToRefreshLayout;
    PurchasingAdapter purchasingAdapter;

    private List<PurchasingBean.Data> PurchasingBeanList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);
        ButterKnife.bind(this);
        txtvActionbarTitle.setText("收货列表");

        initview();

        getPurchasing();
    }

    public void initview() {
        purchasingAdapter = new PurchasingAdapter();

        pullToRefreshLayout = findViewById(R.id.pullToRefreshLayout);

        // 设置列表适配器
        ListView listView = (ListView) pullToRefreshLayout.getPullableView();

        listView.setAdapter(purchasingAdapter);

        pullToRefreshLayout.setPullUpEnable(false);


        // 下拉刷新事件
        pullToRefreshLayout.setOnPullListener(new PullToRefreshLayout.OnPullListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {

                getPurchasing();
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {

            }

        });

        //栏目点击
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PurchasingBean.Data processOrder = (PurchasingBean.Data) purchasingAdapter.getItem(position);

                Intent intent = new Intent(ShoppingActivity.this, ShoppingDetailActivity.class);
                Bundle bundle = new Bundle();

                bundle.putSerializable(SaleBean.Data.class.getSimpleName(), processOrder);
                intent.putExtras(bundle);

                startActivity(intent);
            }
        });
    }

    //获取订单列表
    public void getPurchasing() {

//        String date ="";
//        if(dateBean !=null){
//            date= dateBean.id;
//        }

        Api api = new Api(this, new OnNetRequest(this, true, "正在加载.....") {
            @Override
            public void onSuccess(String msg) {

                PurchasingBeanResponse response = JSONUtils.fromJson(msg, PurchasingBeanResponse.class);

                if (response != null && response.result != null) {

                    PurchasingBeanList = response.result;
//                    adapter.refresh(OrderBeanList);

                } else {
                    PurchasingBeanList = new ArrayList<>();
                }
                purchasingAdapter.refresh(PurchasingBeanList);
                pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
            }

            @Override
            public void onFail() {

                purchasingAdapter.refresh(PurchasingBeanList);

                pullToRefreshLayout.refreshFinish(PullToRefreshLayout.FAIL);
            }
        });

        api.getPurchasing();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
