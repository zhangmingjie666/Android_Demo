package com.example.nrbzms17.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.nrbzms17.R;
import com.example.nrbzms17.Utils.JSONUtils;
import com.example.nrbzms17.data.Api;
import com.example.nrbzms17.data.listener.OnNetRequest;
import com.example.nrbzms17.data.model.PurchaseBean;

import com.example.nrbzms17.data.model.PurchaseDetailBeanResponse;
import com.example.nrbzms17.data.model.SaleBean;
import com.example.nrbzms17.data.model.SaleDetailBean;

import com.example.nrbzms17.data.model.SaleDetailBeanResponse;
import com.example.nrbzms17.data.model.StatusBean;
import com.example.nrbzms17.ui.adapter.PurchaseDetailAdapter;
import com.example.nrbzms17.ui.adapter.SaleDetailAdapter;

import java.util.ArrayList;
import java.util.List;

public class SaleDetailActivity extends AppCompatActivity {

    ListView saledetailView;

    SaleDetailAdapter saleDetailAdapter;

    private SaleBean.Data sale;

    private List<SaleDetailBean.Data> SaleDetailBean = new ArrayList<>();

    SaleDetailBean saledetail = new SaleDetailBean();

    Button sale_noaudit;

    Button sale_audit;

    Button sale_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saledetail);
        initview();
        sale_back =findViewById(R.id.sale_back);
        sale_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void initview() {


        sale = (SaleBean.Data) getIntent().getSerializableExtra(SaleBean.Data.class.getSimpleName());

        if (sale.status.toString().equals("待审核")) {

            sale_noaudit = findViewById(R.id.sale_noaudit);

            sale_noaudit.setEnabled(false);

            sale_audit = findViewById(R.id.sale_audit);

            sale_audit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    SaleAudit();

                    Toast.makeText(SaleDetailActivity.this, "审核成功", Toast.LENGTH_SHORT).show();

                    finish();

                }
            });
        } else if (sale.status.toString().equals("已审核")) {

            sale_audit = findViewById(R.id.sale_audit);

            sale_audit.setEnabled(false);

            sale_noaudit = findViewById(R.id.sale_noaudit);

            sale_noaudit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    SaleNoaudit();

                    Toast.makeText(SaleDetailActivity.this, "反审成功", Toast.LENGTH_SHORT).show();

                    finish();

                }
            });

        } else {

            sale_audit = findViewById(R.id.sale_audit);

            sale_audit.setEnabled(false);

            sale_noaudit = findViewById(R.id.sale_noaudit);

            sale_noaudit.setEnabled(false);

        }
        getSaleDetail();
    }


    //获取详细列表
    public void getSaleDetail() {

        //加载适配器
        saledetailView = findViewById(R.id.saledetailView);

        saleDetailAdapter = new SaleDetailAdapter();

        saledetailView.setAdapter(saleDetailAdapter);

        Api api = new Api(this, new OnNetRequest(this, true, "正在加载.....") {
            @Override
            public void onSuccess(String msg) {

                SaleDetailBeanResponse response = JSONUtils.fromJson(msg, SaleDetailBeanResponse.class);

                if (response != null && response.result != null) {

                    if (response.result.size() > 0) {

                        SaleDetailBean = response.result;

                        saleDetailAdapter.refresh(SaleDetailBean);

                    } else {

                        SaleDetailBean = new ArrayList<>();

                        saledetail = null;

                    }

                }
            }

            @Override
            public void onFail() {

                saleDetailAdapter.refresh(SaleDetailBean);

            }
        });

        api.getSaleDetail(sale.id);

    }

    //审核
    public void SaleAudit() {

        Api api = new Api(this, new OnNetRequest(this, true, "正在加载...") {
            @Override
            public void onSuccess(String msg) {
                StatusBean responseBean = JSONUtils.fromJson(msg, StatusBean.class);
                if (responseBean != null) {

                } else {
                }
            }

            @Override
            public void onFail() {
            }
        });

        api.SaleAudit(sale.id);

    }

    //反审

    public void SaleNoaudit() {

        Api api = new Api(this, new OnNetRequest(this, true, "正在加载...") {
            @Override
            public void onSuccess(String msg) {
                StatusBean responseBean = JSONUtils.fromJson(msg, StatusBean.class);
                if (responseBean != null) {

                } else {
                }
            }

            @Override
            public void onFail() {

            }
        });

        api.SaleNoaudit(sale.id);

    }
}
