package com.example.nrbzms17.ui.activity;

import android.content.Intent;

import android.os.Bundle;
import android.renderscript.Allocation;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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
import com.example.nrbzms17.ui.adapter.SpinnerDepotAdapter;
import com.example.nrbzms17.ui.widget.ClearEditText;

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
                getAlloList();
                btnSubmit.setEnabled(true);
                btnSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addAllocation();
                    }
                });
            }
        });
    }

    //状态下拉监听
    private void setClickListeners() {

        spinner_depot.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                depotBean = (DepotBean) depotAdapter.getItem(position);
                SharedPreference.setDepotId(depotBean.id);
//                getOrderList();
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

                    List<AllocationBean> allcationBeanList = response.result;

                    if (allcationBeanList.size() == 1) {

                        allocationBean = allcationBeanList.get(0);

                        setAllcationinfo(allocationBean);

                    }

                } else {

                }
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

    @BindView(R.id.all_customcode)
    TextView all_customcode;

    @BindView(R.id.all_barcode)
    TextView all_barcode;

    @BindView(R.id.all_delot)
    TextView all_delot;

    @BindView(R.id.all_material)
    TextView all_material;

    @BindView(R.id.all_grade)
    TextView all_grade;

    @BindView(R.id.all_craft)
    TextView all_craft;

    @BindView(R.id.all_color)
    TextView all_color;

    @BindView(R.id.all_volum)
    TextView all_volum;

    @BindView(R.id.all_quantity)
    TextView all_quantity;

    @BindView(R.id.all_lot)
    TextView all_lot;

    @BindView(R.id.all_reel)
    TextView all_reel;

    //填充数据
    public void setAllcationinfo(AllocationBean allocationBean) {
        if (allocationBean == null) {
            all_barcode.setText("");
            all_delot.setText("");
            all_grade.setText("");
            all_material.setText("");
            all_craft.setText("");
            all_color.setText("");
            all_volum.setText("");
            all_quantity.setText("");
            all_lot.setText("");
            all_reel.setText("");
            all_customcode.setText("");
        } else {
            all_barcode.setText(allocationBean.barcode);
            all_delot.setText(allocationBean.depot);
            all_grade.setText(allocationBean.grade);
            all_material.setText(allocationBean.material);
            all_craft.setText(allocationBean.craft);
            all_color.setText(allocationBean.color);
            all_volum.setText(allocationBean.volume);
            all_quantity.setText(allocationBean.quantity);
            all_lot.setText(allocationBean.lot);
            all_reel.setText(allocationBean.reel);
            all_customcode.setText(allocationBean.customcode);
            codeSearch.requestFocus();
        }
    }

    //调拨完成
    public void addAllocation(){

        String barcode = codeSearch.getText().toString().trim();

        if( barcode.equals("")){
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
                setAllcationinfo(null);
                btnSubmit.setEnabled(false);
            }

            @Override
            public void onFail() {
            }
        });
        api.addAllocation(allocationBean.id,depot_);
    }

}
