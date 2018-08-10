package com.example.nrbzms17.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;


import com.example.codescan.zxing.activity.CaptureActivity;
import com.example.codescan.zxing.decode.DecodeThread;
import com.example.nrbzms17.R;
import com.example.nrbzms17.Utils.JSONUtils;
import com.example.nrbzms17.data.Api;
import com.example.nrbzms17.data.SharedPreference;
import com.example.nrbzms17.data.listener.OnNetRequest;
import com.example.nrbzms17.data.model.EmployeeBean;
import com.example.nrbzms17.data.model.EmployeeResponseBean;
import com.example.nrbzms17.ui.adapter.EmployeeAdapter;
import com.example.nrbzms17.ui.widget.ClearEditText;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class CheckActivity extends BaseActivity {


    Spinner spinner_employee;

    EmployeeAdapter employeeAdapter;

    ClearEditText codeSearch;

    Button codescan;

    EmployeeBean employee;

    Button back_menu;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_check);

        initview();

        getEmployeeInfo();

        initdata();

        setClickListeners();

        codescan = findViewById(R.id.codeScan);

        codescan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckActivity.this, CaptureActivity.class);
                startActivity(intent);
            }
        });
    }

    public void initview() {

        employeeAdapter = new EmployeeAdapter();

        spinner_employee = findViewById(R.id.spinner_employee);

        spinner_employee.setAdapter(employeeAdapter);

        back_menu =  findViewById(R.id.back_menu);
        back_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ActivityCollector.finishAll();
            }
        });
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

    public void initdata() {
        Bundle extras = getIntent().getExtras();

        codeSearch = (ClearEditText) findViewById(R.id.codeSearch);

        if (null != extras) {
            int width = extras.getInt("width");
            int height = extras.getInt("height");

            LinearLayout.LayoutParams lps = new LinearLayout.LayoutParams(width, height);
            lps.topMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics());
            lps.leftMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics());
            lps.rightMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics());


            String result = extras.getString("result");
            codeSearch.setText(result);

            Bitmap barcode = null;
            byte[] compressedBitmap = extras.getByteArray(DecodeThread.BARCODE_BITMAP);
            if (compressedBitmap != null) {
                barcode = BitmapFactory.decodeByteArray(compressedBitmap, 0, compressedBitmap.length, null);
                // Mutable copy:
                barcode = barcode.copy(Bitmap.Config.RGB_565, true);
            }

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


    //利用活动管理器退出
    @Override
    public void onBackPressed() {

        ActivityCollector.finishAll();

    }
}