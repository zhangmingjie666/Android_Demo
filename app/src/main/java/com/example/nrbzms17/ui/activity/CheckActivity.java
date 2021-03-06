package com.example.nrbzms17.ui.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.nrbzms17.data.model.CheckBean;
import com.example.nrbzms17.data.model.CheckBeanResponse;
import com.example.nrbzms17.data.model.EmployeeBean;
import com.example.nrbzms17.data.model.EmployeeResponseBean;

import com.example.nrbzms17.data.model.OrderBean;
import com.example.nrbzms17.data.model.ResponseBean;
import com.example.nrbzms17.ui.adapter.CheckAdapter;
import com.example.nrbzms17.ui.adapter.EmployeeAdapter;
import com.example.nrbzms17.ui.widget.ClearEditText;
import com.squareup.okhttp.Response;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CheckActivity extends BaseActivity {


    Spinner spinner_employee;

    EmployeeAdapter employeeAdapter;

    ClearEditText codeSearch;

    Button codescan;

    EmployeeBean employee;

    Button back_menu;

    Button check_search;

    private CheckBean checkbean;

    EditText check_volume;

    EditText check_quantity;

    EditText check_reason;

    Button check_Submit;

    ListView checkdetailView;

    CheckAdapter checkAdapter;

    private List<CheckBean> CheckBeanList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_check);
        ButterKnife.bind(this);
        initview();

        getEmployeeInfo();

        setClickListeners();

        codescan = findViewById(R.id.codeScan);

        codescan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckActivity.this, CaptureActivity.class);
                startActivityForResult(intent, 1);
            }
        });


    }


    //初始化
    public void initview() {

        employeeAdapter = new EmployeeAdapter();

        spinner_employee = findViewById(R.id.spinner_employee);

        spinner_employee.setAdapter(employeeAdapter);

        back_menu = findViewById(R.id.back_menu);
        back_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ActivityCollector.finishAll();
            }
        });

        check_search = findViewById(R.id.check_search);
        check_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCheckList();
            }
        });

        codeSearch = (ClearEditText) findViewById(R.id.codeSearch);

        check_volume = findViewById(R.id.check_volume);

        check_quantity = findViewById(R.id.check_quantity);

        check_reason = findViewById(R.id.check_reason);

        check_Submit = findViewById(R.id.check_Submit);

        check_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCheck();
            }
        });


        checkdetailView = findViewById(R.id.checkdetailView);

        checkAdapter = new CheckAdapter();

        checkdetailView.setAdapter(checkAdapter);

    }

    private void setClickListeners() {
        spinner_employee.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                employee = (EmployeeBean) employeeAdapter.getItem(position);
                SharedPreference.setEmployeeId(employee.id);
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
                    codeSearch.setText(returnedData);
                    getCheckList();

                }
                break;
            default:
        }
    }

    // 获取员工信息
    public void getEmployeeInfo() {
        Api api = new Api(this, new OnNetRequest(this) {
            @Override
            public void onSuccess(String msg) {
                EmployeeResponseBean employeeResponseBean = JSONUtils.fromJson(msg, EmployeeResponseBean.class);
                if (employeeResponseBean != null && employeeResponseBean.result != null) {

                    employeeAdapter.refresh(employeeResponseBean.result);

                    String employeeId = SharedPreference.getEmployeeId();
                    if (TextUtils.isEmpty(employeeId) || employeeId.equals("-1")) {
                        return;
                    }
                    for (int i = 0; i < employeeAdapter.getCount(); i++) {
                        EmployeeBean bean = (EmployeeBean) employeeAdapter.getItem(i);
                        if (bean.id.equals(employeeId)) {
                            spinner_employee.setSelection(i);
                            break;
                        }
                    }
                }
            }

            @Override
            public void onFail() {

            }
        });

        api.getEmployeeInfo();
    }

    //获取条码信息
    public void getCheckList() {

        String barcode = codeSearch.getText().toString().trim();
        if (barcode.equals("")) {
            UIHelper.showShortToast(CheckActivity.this, "请扫描条形码");
            return;
        }

        Api api = new Api(this, new OnNetRequest(this, true, "正在加载.....") {
            @Override
            public void onSuccess(String msg) {

                CheckBeanResponse response = JSONUtils.fromJson(msg, CheckBeanResponse.class);

                if (response != null && response.result.size() > 0) {
                    CheckBeanList = response.result;
                } else {
                    CheckBeanList = new ArrayList<>();
                }
                checkAdapter.refresh(CheckBeanList);
            }

            @Override
            public void onFail() {
            }
        });

        api.getCheckList(barcode);

    }

    //盘点
    public void addCheck() {

        String emp = "";


        String volume = check_volume.getText().toString().trim();

        String quantity = check_quantity.getText().toString().trim();

        String barcode = codeSearch.getText().toString().trim();

        if(barcode.equals("")){
            UIHelper.showShortToast(CheckActivity.this,"请输入条码");
            return ;
        }

//        if (checkbean == null) {
//            UIHelper.showShortToast(CheckActivity.this, "请扫描条形码");
//            return;
//        }

        Api api = new Api(this, new OnNetRequest(this, true, "请稍等...") {
            @Override
            public void onSuccess(String msg) {

                ResponseBean responseBean = JSONUtils.fromJson(msg, ResponseBean.class);

                if (responseBean != null && responseBean.status) {

                    UIHelper.showShortToast(CheckActivity.this, responseBean.result);

                } else {

                }
                codeSearch.setText("");
                check_volume.setText("");
                check_quantity.setText("");
                check_reason.setText("");
                checkAdapter.refresh(null);
            }

            @Override
            public void onFail() {
            }
        });
        api.addCheck(employee.id, barcode, volume, quantity, "");
    }

    //利用活动管理器退出
    @Override
    public void onBackPressed() {

        ActivityCollector.finishAll();

    }



}