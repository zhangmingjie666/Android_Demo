package com.example.nrbzms17.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.nrbzms17.R;
//import com.nrbzms17.Utils.JSONUtils;
import com.example.nrbzms17.Utils.JSONUtils;
import com.example.nrbzms17.data.Api;
import com.example.nrbzms17.data.listener.OnNetRequest;
import com.example.nrbzms17.data.model.PurchaseBean;
import com.example.nrbzms17.data.model.PurchaseDetailBean;
import com.example.nrbzms17.data.model.PurchaseDetailBeanResponse;
import com.example.nrbzms17.data.model.StatusBean;
import com.example.nrbzms17.ui.adapter.PurchaseDetailAdapter;

import java.util.ArrayList;
import java.util.List;

public class PurchaseDetailActivity extends AppCompatActivity {

    ListView purchasedetailView;
    PurchaseDetailAdapter purchaseDetailAdapter;
    private PurchaseBean.Data purchase;

    private List<PurchaseDetailBean.Data> PurchaseDetailBean = new ArrayList<>();

    PurchaseDetailBean purchasedetail = new PurchaseDetailBean();
    Button pur_back;
    Button purchase_noaudit;
    Button purchase_audit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchasedetail);



        initview();
        pur_back = findViewById(R.id.pur_back);

        pur_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void initview(){

        purchase = (PurchaseBean.Data) getIntent().getSerializableExtra(PurchaseBean.Data.class.getSimpleName());


        if (purchase.status.toString().equals("待审核")) {
             purchase_noaudit = findViewById(R.id.purchase_noaudit);
            purchase_noaudit.setEnabled(false);
           purchase_audit =  findViewById(R.id.purchase_audit);
            purchase_audit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PurchaseAudit();
                    Toast.makeText(PurchaseDetailActivity.this, "审核成功", Toast.LENGTH_SHORT).show();
                    finish();

                }
            });
        } else {
             purchase_audit =  findViewById(R.id.purchase_audit);
            purchase_audit.setEnabled(false);
             purchase_noaudit =  findViewById(R.id.purchase_noaudit);
            purchase_noaudit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PurchaseNoaudit();
                    Toast.makeText(PurchaseDetailActivity.this, "反审成功", Toast.LENGTH_SHORT).show();
                    finish();

                }
            });
        }
        getPurchaseDetail();
    }

    public void getPurchaseDetail(){

        purchasedetailView = (ListView) findViewById(R.id.purchasedetailView);

        purchaseDetailAdapter = new PurchaseDetailAdapter();

        purchasedetailView.setAdapter(purchaseDetailAdapter);

        Api api = new Api(this, new OnNetRequest(this, true, "正在加载.....") {
            @Override
            public void onSuccess(String msg) {

                PurchaseDetailBeanResponse response = JSONUtils.fromJson(msg, PurchaseDetailBeanResponse.class);

                if (response != null && response.result != null) {

                    if (response.result.size() > 0) {

                        PurchaseDetailBean = response.result;

                        purchaseDetailAdapter.refresh(PurchaseDetailBean);


//                        order = OrderBeanList.get(0);


                    } else {

                        PurchaseDetailBean = new ArrayList<>();

                        purchasedetail = null;

                    }


                }
            }

            @Override
            public void onFail() {

                purchaseDetailAdapter.refresh(PurchaseDetailBean);

            }
        });

        api.getPurchasedetail(purchase.id);

    }


    //审核
    public void PurchaseAudit() {

        Api api = new Api(this, new OnNetRequest(this, true, "正在加载...") {
            @Override
            public void onSuccess(String msg) {
                StatusBean responseBean = JSONUtils.fromJson(msg, StatusBean.class);
                if (responseBean != null) {


//                    if (responseBean.result.size() == 0) {

//                        OrderListBean = responseBean.result;
//
//                        adapter.refresh(OrderListBean);
//                    }
//                    adapter.refresh(responseBean.result);

                } else {
                }
            }

            @Override
            public void onFail() {
            }
        });

        api.PurchaseAudit(purchase.id);

    }

    //反审

    public void PurchaseNoaudit() {

        Api api = new Api(this, new OnNetRequest(this, true, "正在加载...") {
            @Override
            public void onSuccess(String msg) {
                StatusBean responseBean = JSONUtils.fromJson(msg, StatusBean.class);
                if (responseBean != null) {
//                    if (responseBean.result.size() == 0) {

//                        OrderListBean = responseBean.result;
//
//                        adapter.refresh(OrderListBean);
//                    }
//                    adapter.refresh(responseBean.result);

                } else {
                }
            }

            @Override
            public void onFail() {
//                adapter.refresh(OrderListBean);
            }
        });

        api.PurchaseNoaudit(purchase.id);

    }
}
