package com.example.nrbzms17.ui.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.nrbzms17.R;
import com.example.nrbzms17.Utils.JSONUtils;
import com.example.nrbzms17.data.Api;
import com.example.nrbzms17.data.SharedPreference;
import com.example.nrbzms17.data.listener.OnNetRequest;
import com.example.nrbzms17.data.model.PurchaseBean;
import com.example.nrbzms17.data.model.PurchaseBeanResponse;
import com.example.nrbzms17.data.model.StatusBean;
import com.example.nrbzms17.data.model.StatusBeanResponse;
import com.example.nrbzms17.ui.adapter.PurchaseListAdapter;
import com.example.nrbzms17.ui.adapter.SpinnerStatusAdapter;
import com.example.nrbzms17.ui.widget.ClearEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import java.util.Date;
import java.util.List;

public class PurchaseListActivity extends AppCompatActivity {

    Button purchase_menu;

    PurchaseBean purchaseBean;

    ListView purchaseView;

    PurchaseListAdapter purchaseListAdapter;

    private List<PurchaseBean.Data> PurchaseBean = new ArrayList<>();

    PurchaseBean purchase = new PurchaseBean();
    ClearEditText purCode;

    Spinner purchase_status;

    Button purSearch;

    SpinnerStatusAdapter statusAdapter;

    private StatusBean statusBean;
    //日期
    int mYear, mMonth, mDay;
    TextView start;
    TextView end;

    TextView txtv_Name;

    String str;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_list);

        initview();

        setClickListeners();
        getPurchaseList();


//        initData();
//        final Calendar ca = Calendar.getInstance();
//        mYear = ca.get(Calendar.YEAR);
//        mMonth = ca.get(Calendar.MONTH);
//        mDay = ca.get(Calendar.DAY_OF_MONTH);

        getStatusInfo();
    }

    public void initview() {
        //返回
        purchase_menu = findViewById(R.id.purchase_menu);
        purchase_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        purchaseView = findViewById(R.id.purchaseView);

        purchaseListAdapter = new PurchaseListAdapter();

        purchaseView.setAdapter(purchaseListAdapter);

        purCode = (ClearEditText) findViewById(R.id.purCode);

        start = (TextView) findViewById(R.id.start);

        end = (TextView) findViewById(R.id.end);

//        dateDisplay = (TextView) findViewById(R.id.dateDisplay);

        purchase_status = (Spinner) findViewById(R.id.purchase_status);

        purSearch = (Button) findViewById(R.id.purSearch);

        txtv_Name = (TextView) findViewById(R.id.txtv_Name);

        final Calendar ca = Calendar.getInstance();
        mYear = ca.get(Calendar.YEAR);
        mMonth = ca.get(Calendar.MONTH);
        mDay = ca.get(Calendar.DAY_OF_MONTH);
//
        String date;

        String deliverdate;

        date = new StringBuffer().append(mYear).append("-").append(mMonth + 1).append("-").append(mDay).append(" ").toString();
        Date d;
        deliverdate = new StringBuffer().append(mYear).append("-").append(mMonth + 1).append("-").append(mDay).append(" ").toString();
        d = StringToDate(deliverdate);
        deliverdate = DateToString(d);
        start.setText(date);
        end.setText(deliverdate);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog(1);
            }
        });

        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog(2);
            }
        });


        //查询
        purSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPurchaseList();
            }
        });

        //栏目点击
        purchaseView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PurchaseBean.Data processOrder = (PurchaseBean.Data) purchaseListAdapter.getItem(position);


                Intent intent = new Intent(PurchaseListActivity.this, PurchaseDetailActivity.class);
                Bundle bundle = new Bundle();

                bundle.putSerializable(PurchaseBean.Data.class.getSimpleName(), processOrder);
                intent.putExtras(bundle);

                startActivity(intent);
            }
        });


        //状态
        statusAdapter = new SpinnerStatusAdapter();

        purchase_status.setAdapter(statusAdapter);

    }

    //状态下拉
    private void setClickListeners() {

        purchase_status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                statusBean = (StatusBean) statusAdapter.getItem(position);
                getPurchaseList();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    public void getPurchaseList() {

        String status = "";

        if (statusBean != null) {
            status = statusBean.id;

            if (status == "-1") {
                status = "";
            }
        }

        Api api = new Api(this, new OnNetRequest(this, true, "正在加载.....") {
            @Override
            public void onSuccess(String msg) {

                PurchaseBeanResponse response = JSONUtils.fromJson(msg, PurchaseBeanResponse.class);

                if (response != null && response.result != null) {
                    PurchaseBean = response.result;
                } else {
                    PurchaseBean = new ArrayList<>();
                }

                purchaseListAdapter.refresh(PurchaseBean);
            }

            @Override
            public void onFail() {

                purchaseListAdapter.refresh(PurchaseBean);

            }
        });

        api.getPurchaseList(status, purCode.getText().toString().trim(), start.getText().toString().trim(), end.getText().toString().trim());

    }

    /**
     *
     */
    String start_time = "";
    String end_time = "";

    //number参数表示设置 开始:1 或 结束:2 时间
    public void showDateDialog(final int number) {

        //获得当前时间 DatePicker默认显示
        Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        //SimpleDateFromat转变表示时间的格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        //实例化DatePickerDialog对象 并设置时间选择监听
        DatePickerDialog dp = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                log(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                switch (number) {
                    case 1:
                        //为什么这么设置时间格式? 本人项目服务器要求这么传 哈哈
                        start_time = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;

                        start.setText(start_time);
                        getPurchaseList();
                        break;
                    case 2:
                        end_time = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                        end.setText(end_time);
                        getPurchaseList();
                        break;
                }
            }
        }, year, month, day);

        //当开始时间已经选则而且是点击结束时间弹出picker
        if (!TextUtils.isEmpty(start_time) && number == 2) {
            try {
                Date date = sdf.parse(start_time);

                //设置最小可选择时间

                dp.getDatePicker().setMinDate(date.getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        //当结束时间已经选择而且是点击开始时间弹出的picker
        if (!TextUtils.isEmpty(end_time) && number == 1) {
            try {
                Date date = sdf.parse(end_time);
                //设置最大可选择时间
                dp.getDatePicker().setMaxDate(date.getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        dp.show();

    }

    //日期处理
//    @Override
//    protected Dialog onCreateDialog(int id) {
//        switch (id) {
//            case DATE_DIALOG:
//                return new DatePickerDialog(PurchaseListActivity.this, mdateListener, mYear, mMonth, mDay);
//        }
//        return null;
//    }
//
//    DatePickerDialog.OnDateSetListener mdateListener = new DatePickerDialog.OnDateSetListener() {
//
//        @Override
//        public void onDateSet(DatePicker view, int year, int monthOfYear,
//                              int dayOfMonth) {
//            mYear = year;
//            mMonth = monthOfYear;
//            mDay = dayOfMonth;
////            display();
//            String date;
//            String deliverdate;
//
//            String time;
//
//            Date d;
//            deliverdate = new StringBuffer().append(mYear).append("-").append(mMonth + 1).append("-").append(mDay+7).append(" ").toString();
//            date = new StringBuffer().append(mYear).append("-").append(mMonth + 1).append("-").append(mDay).append(" ").toString();
//
////          dateDisplay.setText(new StringBuffer().append(mMonth + 1).append("-").append(mDay).append("-").append(mYear).append(" "));
////            getDateAfter(7);
//            d=StringToDate(deliverdate);
//            deliverdate=DateToString(d);
//            btn.setText(date);
//            button.setText(deliverdate);
//
//
//
//
//
//            getPurchaseList();
//
//        }
//
//        /**
//         * 设置日期 利用StringBuffer追加
//         */
//        private void display() {
//
//
//        }
//    };

//    private void initData() {
//        //监听事件
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showDialog(DATE_DIALOG);
//            }
//        });
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showDialog(DATE_DIALOG);
//            }
//        });


    public static Date getDateAfter(int day) {
        Calendar now = Calendar.getInstance();
        now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
        return now.getTime();
    }

    public static Date getDateBefore(int day) {
        Calendar now = Calendar.getInstance();
//        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
        return now.getTime();
    }

    public static String DateToString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String str = sdf.format(date);
        return str;
    }

    public static Date StringToDate(String str) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟
        Date date = null;
        try {
            date = sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    // 获取状态信息
    public void getStatusInfo() {
        Api api = new Api(this, new OnNetRequest(this) {
            @Override
            public void onSuccess(String msg) {
                StatusBeanResponse statusBeanResponse = JSONUtils.fromJson(msg, StatusBeanResponse.class);
                if (statusBeanResponse != null && statusBeanResponse.result != null) {

                    statusAdapter.refresh(statusBeanResponse.result);

                    String departmentId = SharedPreference.getDepartmentId();
                    if (TextUtils.isEmpty(departmentId) || departmentId.equals("-1")) {
                        return;
                    }
                    for (int i = 0; i < statusAdapter.getCount(); i++) {
                        StatusBean bean = (StatusBean) statusAdapter.getItem(i);
//                        if(bean.id.equals(departmentId))
//                        {
//                            statusBean.setSelection(i);
//                            break;
//                        }
                    }
                }
            }

            @Override
            public void onFail() {

            }
        });

        api.getStatusInfo();
    }

    @Override
    protected void onResume() {

        super.onResume();

        getPurchaseList();

        purCode.setText("");
    }
}