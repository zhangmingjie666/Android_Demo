package com.example.nrbzms17.ui.activity;

import android.content.Intent;

import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.AllocationAdapter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.codescan.zxing.activity.CaptureActivity;
import com.example.nrbzms17.R;
import com.example.nrbzms17.Utils.JSONUtils;
import com.example.nrbzms17.Utils.UIHelper;
import com.example.nrbzms17.data.Api;
import com.example.nrbzms17.data.SharedPreference;
import com.example.nrbzms17.data.listener.OnNetRequest;

import com.example.nrbzms17.data.model.AllocationBean;
import com.example.nrbzms17.data.model.AllocationBeanResponse;
import com.example.nrbzms17.data.model.DepotBean;
import com.example.nrbzms17.data.model.DepotBeanResponse;

import com.example.nrbzms17.data.model.ResponseBean;
import com.example.nrbzms17.ui.adapter.AllocatAdapter;
import com.example.nrbzms17.ui.adapter.SpinnerDepotAdapter;
import com.example.nrbzms17.ui.widget.ClearEditText;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AllocationActivity extends BaseActivity {

    Spinner spinner_depot;

    SpinnerDepotAdapter depotAdapter;

    DepotBean depotBean;

    ClearEditText codeSearch;

    Button codeScan;

    Button btnSubmit;

    Button txtvConfirm;

    Button back_menu;

    private AllocationBean allocationBean;

    ListView allodetailView;

    AllocatAdapter allocationAdapter;

    private List<AllocationBean> allocationBeanList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allocation);
        ButterKnife.bind(this);
        initview();

        setClickListeners();
        getDepotinfo();
        back_menu = findViewById(R.id.back_menu);
        back_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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

        //调拨完成
        btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setEnabled(false);

        codeSearch = (ClearEditText) findViewById(R.id.codeSearch);
        //查询条码
        txtvConfirm = findViewById(R.id.txtvConfirm);
        txtvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(codeSearch.getText().toString().trim().equals("")){
                    getAlloList();
                }else{
                    btnSubmit.setEnabled(true);
                    btnSubmit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            addAllocation();
                        }
                    });
                }

            }
        });

        //适配器
        allodetailView = findViewById(R.id.allodetailView);
        allocationAdapter = new AllocatAdapter();
        allodetailView.setAdapter(allocationAdapter);

    }

    //状态下拉监听
    private void setClickListeners() {

        spinner_depot.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                depotBean = (DepotBean) depotAdapter.getItem(position);
                SharedPreference.setDepotId(depotBean.id);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    //获取条码信息
    public void getAlloList() {

        String barcode = codeSearch.getText().toString().trim();
        if (barcode.equals("")) {
            UIHelper.showShortToast(AllocationActivity.this, "请扫描条形码");
            return;
        }

        Api api = new Api(this, new OnNetRequest(this, true, "正在加载.....") {
            @Override
            public void onSuccess(String msg) {

                AllocationBeanResponse response = JSONUtils.fromJson(msg, AllocationBeanResponse.class);

                if (response != null && response.result.size() > 0) {

                    allocationBeanList = response.result;

                    List<AllocationBean> allcationBeanList = response.result;


                    allocationBean = allcationBeanList.get(0);


                } else {
                    UIHelper.showShortToast(AllocationActivity.this,"请正确输入条码");
                }
                allocationAdapter.refresh(allocationBeanList);
            }

            @Override
            public void onFail() {
            }
        });

        api.getAlloList(barcode);

    }

    //获取扫描返回的数据
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    String returnedData = data.getStringExtra("code_return");
                    codeSearch.setText(returnedData);
                    getAlloList();

                    btnSubmit.setEnabled(true);
                    btnSubmit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            addAllocation();
                        }
                    });
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


    //调拨完成
    public void addAllocation() {

        String barcode = codeSearch.getText().toString().trim();

        if (barcode.equals("")) {
            UIHelper.showShortToast(AllocationActivity.this, "请扫描条形码");
            return;
        }

        String depot_ = "";

        if (depotBean != null) {
            depot_ = depotBean.id;

            if (depot_ == "-1") {
//                depot_ = "";
                UIHelper.showShortToast(AllocationActivity.this, "请选择仓库");
                return;
            }
        }

        Api api = new Api(this, new OnNetRequest(this, true, "请稍等...") {
            @Override
            public void onSuccess(String msg) {

                ResponseBean responseBean = JSONUtils.fromJson(msg, ResponseBean.class);

                if (responseBean != null && responseBean.status) {

                    UIHelper.showShortToast(AllocationActivity.this, "调拨成功");

                } else {

                }
                codeSearch.setText("");
                allocationAdapter.refresh(null);
                depotAdapter.refresh(null);
                btnSubmit.setEnabled(false);
            }

            @Override
            public void onFail() {
            }
        });
        api.addAllocation(allocationBean.id, depot_);
    }

}
