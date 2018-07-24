package com.example.nrbzms17.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.nrbzms17.R;
import com.example.nrbzms17.Utils.JSONUtils;
import com.example.nrbzms17.data.Api;
import com.example.nrbzms17.data.listener.OnNetRequest;
import com.example.nrbzms17.data.model.OrderBean;
import com.example.nrbzms17.data.model.OrderBeanResponse;
import com.example.nrbzms17.data.model.PurchaseBean;
import com.example.nrbzms17.data.model.PurchaseBeanResponse;
import com.example.nrbzms17.ui.adapter.PurchaseListAdapter;
import com.example.nrbzms17.ui.widget.ClearEditText;
import com.jingchen.pulltorefresh.PullToRefreshLayout;

import java.util.ArrayList;
import java.util.List;

public class PurchaseActivity extends AppCompatActivity {

    Button purchase_menu;

    PurchaseBean purchaseBean;

    ListView purchaseView;

    PurchaseListAdapter purchaseListAdapter;

    private List<PurchaseBean.Data> PurchaseBean = new ArrayList<>();

    PurchaseBean purchase = new PurchaseBean();
    ClearEditText purCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_list);

        initview();

        getPurchaseList();
    }

    public void initview() {
        //返回
        purchase_menu = findViewById(R.id.purchase_menu);
        purchase_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        purchaseView = findViewById(R.id.purchaseView);

        purchaseListAdapter = new PurchaseListAdapter();

        purchaseView.setAdapter(purchaseListAdapter);

        purCode = (ClearEditText) findViewById(R.id.purCode);


    }

    public void getPurchaseList(){

        Api api = new Api(this, new OnNetRequest(this, true, "正在加载.....") {
            @Override
            public void onSuccess(String msg) {

                PurchaseBeanResponse response = JSONUtils.fromJson(msg, PurchaseBeanResponse.class);

                if (response != null && response.result != null) {

                    if (response.result.size() > 0) {

                        PurchaseBean = response.result;

                        purchaseListAdapter.refresh(PurchaseBean);


//                        order = OrderBeanList.get(0);


                    } else {

                        PurchaseBean = new ArrayList<>();

                        purchase = null;

                    }


                }
            }

            @Override
            public void onFail() {

                purchaseListAdapter.refresh(PurchaseBean);

            }
        });

        api.getPurchaseList("", purCode.getText().toString().trim());

    }
}
