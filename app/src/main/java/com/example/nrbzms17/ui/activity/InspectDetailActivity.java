package com.example.nrbzms17.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.nrbzms17.R;
import com.example.nrbzms17.Utils.JSONUtils;

import com.example.nrbzms17.Utils.UIHelper;
import com.example.nrbzms17.data.Api;
import com.example.nrbzms17.data.listener.OnNetRequest;
import com.example.nrbzms17.data.model.InspectBean;
import com.example.nrbzms17.data.model.InspectDetailBean;
import com.example.nrbzms17.data.model.InspectDetailBeanResponse;
import com.example.nrbzms17.data.model.StatusBean;
import com.example.nrbzms17.ui.adapter.InspectDetailAdapter;


import java.util.ArrayList;
import java.util.List;

public class InspectDetailActivity extends AppCompatActivity {

    ListView inspectdetailView;

    InspectDetailAdapter inspectDetailAdapter;

    private InspectBean.Data inspect;

    private List<InspectDetailBean.Data> InspectDetailBean = new ArrayList<>();

    InspectDetailBean inspectdetail = new InspectDetailBean();

    Button ins_back;

    Button inspect_noaudit;

    Button inspect_audit;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_inspectdetail);

        initview();

        ins_back = findViewById(R.id.ins_back);

        ins_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    public void initview() {

        inspect = (InspectBean.Data) getIntent().getSerializableExtra(InspectBean.Data.class.getSimpleName());

        if (inspect.status.toString().equals("待审核")) {

            inspect_noaudit = findViewById(R.id.inspect_noaudit);

            inspect_noaudit.setEnabled(false);

            inspect_audit = findViewById(R.id.inspect_audit);

            inspect_audit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    InspectAudit();



                    finish();

                }
            });
        } else if (inspect.status.toString().equals("已审核")) {

            inspect_audit = findViewById(R.id.inspect_audit);

            inspect_audit.setEnabled(false);

            inspect_noaudit = findViewById(R.id.inspect_noaudit);

            inspect_noaudit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    InspectNoaudit();

                    finish();


                }
            });

        } else {

            inspect_audit = findViewById(R.id.inspect_audit);

            inspect_audit.setEnabled(false);

            inspect_noaudit = findViewById(R.id.inspect_noaudit);

            inspect_noaudit.setEnabled(false);

        }
        getInspectDetail();
    }

    //获取详细列表
    public void getInspectDetail() {

        inspectdetailView = (ListView) findViewById(R.id.inspectdetailView);

        inspectDetailAdapter = new InspectDetailAdapter();

        inspectdetailView.setAdapter(inspectDetailAdapter);

        Api api = new Api(this, new OnNetRequest(this, true, "正在加载.....") {
            @Override
            public void onSuccess(String msg) {

                InspectDetailBeanResponse response = JSONUtils.fromJson(msg, InspectDetailBeanResponse.class);

                if (response != null && response.result != null) {

                    if (response.result.size() > 0) {

                        InspectDetailBean = response.result;

                        inspectDetailAdapter.refresh(InspectDetailBean);

                    } else {

                        InspectDetailBean = new ArrayList<>();

                        inspectdetail = null;

                    }

                }
            }

            @Override
            public void onFail() {

                inspectDetailAdapter.refresh(InspectDetailBean);

            }
        });

        api.getInspectDetail(inspect.id);

    }


    //审核
    public void InspectAudit() {

        Api api = new Api(this, new OnNetRequest(this, true, "正在加载...") {
            @Override
            public void onSuccess(String msg) {
                StatusBean responseBean = JSONUtils.fromJson(msg, StatusBean.class);
                if (responseBean != null ) {
                    Toast.makeText(InspectDetailActivity.this, "审核成功", Toast.LENGTH_SHORT).show();
                } else {
                }
            }

            @Override
            public void onFail() {
            }
        });

        api.InspectAudit(inspect.id);

    }

    //反审

    public void InspectNoaudit() {

        Api api = new Api(this, new OnNetRequest(this, true, "正在加载...") {
            @Override
            public void onSuccess(String msg) {
                StatusBean responseBean = JSONUtils.fromJson(msg, StatusBean.class);
//                if (responseBean != null && responseBean.status) {
                    if (responseBean != null ) {
                    UIHelper.showShortToast(InspectDetailActivity.this,"反审成功");
                } else {
//                    UIHelper.showShortToast(InspectDetailActivity.this,responseBean.result );
                }
            }

            @Override
            public void onFail() {

            }
        });

        api.InspectNoaudit(inspect.id);

    }
}
