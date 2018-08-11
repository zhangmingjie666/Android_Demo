package com.example.nrbzms17.ui.activity;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import com.example.codescan.zxing.activity.CaptureActivity;
import com.example.nrbzms17.R;
import com.example.nrbzms17.Utils.JSONUtils;
import com.example.nrbzms17.data.Api;
import com.example.nrbzms17.data.listener.OnNetRequest;

import com.example.nrbzms17.data.model.DepotBean;
import com.example.nrbzms17.data.model.DepotBeanResponse;

import com.example.nrbzms17.ui.adapter.SpinnerDepotAdapter;
import com.example.nrbzms17.ui.widget.ClearEditText;

public class AllocationActivity extends BaseActivity {

    Spinner spinner_depot;

    SpinnerDepotAdapter depotAdapter;

    DepotBean depotBean;

    ClearEditText codeSearch;

    Button codeScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allocation);
        initview();

        setClickListeners();

        getDepotinfo();
    }

    public void initview() {

        //扫一扫
        codeScan = findViewById(R.id.codeScan);
        codeScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllocationActivity.this, CaptureActivity.class);
                startActivityForResult(intent, 1);
            }
        });


        //获取仓库
        spinner_depot = findViewById(R.id.spinner_depot);
        depotAdapter = new SpinnerDepotAdapter();
        spinner_depot.setAdapter(depotAdapter);

        //状态下拉

    }

    //状态下拉监听
    private void setClickListeners() {

        spinner_depot.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                depotBean = (DepotBean) depotAdapter.getItem(position);
//                getOrderList();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    //获取扫描返回的数据
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    String returnedData = data.getStringExtra("code_return");
                    codeSearch = (ClearEditText) findViewById(R.id.codeSearch);
                    codeSearch.setText(returnedData);
                }
                break;
            default:
        }
    }

    //获取仓库信息
    public void getDepotinfo() {
        Api api = new Api(this, new OnNetRequest(this) {
            @Override
            public void onSuccess(String msg) {
                DepotBeanResponse depotBeanResponse = JSONUtils.fromJson(msg, DepotBeanResponse.class);
                if (depotBeanResponse != null && depotBeanResponse.result != null) {
                    depotAdapter.refresh(depotBeanResponse.result);
                    for (int i = 0; i < depotAdapter.getCount(); i++) {
                        DepotBean bean = (DepotBean) depotAdapter.getItem(i);
                    }
                }
            }

            @Override
            public void onFail() {

            }
        });

        api.getDepotinfo();
    }

}
