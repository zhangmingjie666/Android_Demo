package com.example.nrbzms17.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.nrbzms17.R;
import com.example.nrbzms17.Utils.JSONUtils;
import com.example.nrbzms17.data.Api;
import com.example.nrbzms17.data.SharedPreference;
import com.example.nrbzms17.data.listener.OnNetRequest;
import com.example.nrbzms17.data.model.CraftBean;
import com.example.nrbzms17.data.model.EmployeeBean;
import com.example.nrbzms17.data.model.EmployeeResponseBean;
import com.example.nrbzms17.ui.adapter.CraftAdapter;
import com.example.nrbzms17.ui.adapter.EmployeeAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EmployeeActivity extends AppCompatActivity {
    @BindView(R.id.employee_list)
    ListView employee_list;

    @BindView(R.id.txtvActionbarTitle)
    TextView txtvActionbarTitle;

    @BindView(R.id.back_menu)
    TextView back_menu;

    @BindView(R.id.etCode)
    TextView etCode;

    @BindView(R.id.choose)
    TextView choose;

    @BindView(R.id.kongbai)
    TextView kongbai;

    @BindView(R.id.cancel)
    TextView cancel;

    EmployeeAdapter employeeAdapter;

    String search ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);
        ButterKnife.bind(this);
        initview();
        getEmployeeInfo();
        txtvActionbarTitle.setText("员工列表");
        back_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choose.setVisibility(View.GONE);
                kongbai.setVisibility(View.GONE);
                cancel.setVisibility(View.VISIBLE);
                etCode.setVisibility(View.VISIBLE);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choose.setVisibility(View.VISIBLE);
                kongbai.setVisibility(View.VISIBLE);
                cancel.setVisibility(View.GONE);
                etCode.setVisibility(View.GONE);
            }
        });
    }
    public void initview() {
        employeeAdapter = new EmployeeAdapter();
        employee_list.setAdapter(employeeAdapter);

        //栏目点击
        employee_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EmployeeBean processOrder = (EmployeeBean) employeeAdapter.getItem(position);
                String employeename = processOrder.name;
                Intent intent = new Intent();
                intent.putExtra("name", employeename);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        etCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                search = etCode.getText().toString().trim();
                getEmployeeInfo();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    // 获取员工信息
    public void getEmployeeInfo() {
        Api api = new Api(this, new OnNetRequest(this) {
            @Override
            public void onSuccess(String msg) {
                EmployeeResponseBean employeeResponseBean = JSONUtils.fromJson(msg, EmployeeResponseBean.class);
                if (employeeResponseBean != null && employeeResponseBean.result != null) {
                    employeeAdapter.refresh(employeeResponseBean.result);

                }
            }

            @Override
            public void onFail() {

            }
        });

        api.getEmployeeInfo();
    }
}
