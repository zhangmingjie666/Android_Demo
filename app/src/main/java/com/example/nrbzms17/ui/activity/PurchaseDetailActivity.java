package com.example.nrbzms17.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.nrbzms17.R;
import com.example.nrbzms17.Utils.JSONUtils;
import com.example.nrbzms17.data.Api;
import com.example.nrbzms17.data.listener.OnNetRequest;
import com.example.nrbzms17.data.model.OrderBean;
import com.example.nrbzms17.data.model.PurchaseBean;
import com.example.nrbzms17.data.model.PurchaseBeanResponse;
import com.example.nrbzms17.data.model.PurchaseDetailBean;
import com.example.nrbzms17.data.model.PurchaseDetailBeanResponse;
import com.example.nrbzms17.ui.adapter.PurchaseDetailAdapter;
import com.example.nrbzms17.ui.adapter.PurchaseListAdapter;

import java.util.ArrayList;
import java.util.List;

public class PurchaseDetailActivity extends AppCompatActivity {

    ListView purchasedetailView;
    PurchaseDetailAdapter purchaseDetailAdapter;

    private PurchaseBean.Data purchase;

    private List<PurchaseDetailBean.Data> PurchaseDetailBean = new ArrayList<>();

    PurchaseDetailBean purchasedetail = new PurchaseDetailBean();
    Button pur_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchasedetail);



        initview();

    }

    public void initview(){

        purchase = (PurchaseBean.Data) getIntent().getSerializableExtra(PurchaseBean.Data.class.getSimpleName());
        pur_back = findViewById(R.id.pur_back);

        pur_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
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
}
