package com.example.nrbzms17.ui.activity;

import android.app.DatePickerDialog;
import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.nrbzms17.R;
//import com.nrbzms17.Utils.JSONUtils;
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
import com.jingchen.pulltorefresh.PullToRefreshLayout;
//import com.nrbzms17.ui.widget.ClearEditText;

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

    Spinner choose_date;

    Button purSearch;

    SpinnerStatusAdapter statusAdapter;

    private StatusBean statusBean;
    //日期
    int mYear, mMonth, mDay;
    TextView start;
    TextView end;

    TextView txtv_Name;

    private ArrayAdapter adapter;

    PullToRefreshLayout pullToRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_list);

        initview();

        setClickListeners();


        String starttime;

        String endtime;

        starttime = new StringBuffer().append(mYear).append("-").append(mMonth + 1).append("-").append(mDay).append(" ").toString();
        endtime = new StringBuffer().append(mYear).append("-").append(mMonth + 1).append("-").append(mDay).append(" ").toString();
        getPurchaseList(starttime, endtime);

        getStatusInfo();

        choose_date = (Spinner) findViewById(R.id.choose_date);

        //将可选内容与ArrayAdapter连接起来
        adapter = ArrayAdapter.createFromResource(this, R.array.timeChoose, android.R.layout.simple_spinner_item);

        //设置下拉列表的风格
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //将adapter2 添加到spinner中
        choose_date.setAdapter(adapter);

        //添加事件Spinner事件监听
        choose_date.setOnItemSelectedListener(new SpinnerXMLSelectedListener());


    }

    public void initview() {

        pullToRefreshLayout = findViewById(R.id.pullToRefreshLayout);
        purchaseListAdapter = new PurchaseListAdapter();
        // 设置列表适配器
        ListView listView = (ListView) pullToRefreshLayout.getPullableView();
        listView.setAdapter(purchaseListAdapter);
        pullToRefreshLayout.setPullUpEnable(false);

        //返回
        purchase_menu = findViewById(R.id.purchase_menu);
        purchase_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        purCode = (ClearEditText) findViewById(R.id.purCode);

        //时间查询

        start = (TextView) findViewById(R.id.start);

        end = (TextView) findViewById(R.id.end);



        purchase_status = (Spinner) findViewById(R.id.purchase_status);

        purSearch = (Button) findViewById(R.id.purSearch);

        txtv_Name = (TextView) findViewById(R.id.txtv_Name);

        pullToRefreshLayout.setOnPullListener(new PullToRefreshLayout.OnPullListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                getPurchaseList("","");
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {

            }

        });

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




        //查询
        purSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPurchaseList("", "");
            }
        });

        //栏目点击
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
                getPurchaseList("", "");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    //获取采购列表
    public void getPurchaseList(String starttime, String endtime) {

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
                pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
            }

            @Override
            public void onFail() {

                purchaseListAdapter.refresh(PurchaseBean);
                pullToRefreshLayout.refreshFinish(PullToRefreshLayout.FAIL);

            }
        });

        api.getPurchaseList(status, purCode.getText().toString().trim(), endtime, starttime);
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
//                        getPurchaseList();
                        break;
                    case 2:
                        end_time = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                        end.setText(end_time);
//                        getPurchaseList();
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

                    }
                }
            }

            @Override
            public void onFail() {

            }
        });

        api.getStatusInfo();
    }

    //使用XML形式操作
    class SpinnerXMLSelectedListener implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> arg0, View arg1, int position,
                                   long arg3) {
            if (position == 0) {

                getPurchaseList("", "");
            } else if (position == 1) {
                String starttime;

                String endtime;

                starttime = new StringBuffer().append(mYear).append("-").append(mMonth + 1).append("-").append(mDay).append(" ").toString();
                endtime = new StringBuffer().append(mYear).append("-").append(mMonth + 1).append("-").append(mDay).append(" ").toString();
                getPurchaseList(starttime, endtime);
            } else if (position == 2) {
                String starttime;

                String endtime;

                starttime = new StringBuffer().append(mYear).append("-").append(mMonth + 1).append("-").append(mDay).append(" ").toString();
                endtime = new StringBuffer().append(mYear).append("-").append(mMonth + 1).append("-").append(mDay -3).append(" ").toString();
                getPurchaseList(starttime, endtime);
            }else if(position == 3){
                String starttime;

                String endtime;

                starttime = new StringBuffer().append(mYear).append("-").append(mMonth + 1).append("-").append(mDay).append(" ").toString();
                endtime = new StringBuffer().append(mYear).append("-").append(mMonth + 1).append("-").append(mDay -4).append(" ").toString();
                getPurchaseList(starttime, endtime);
            }

        }

        public void onNothingSelected(AdapterView<?> arg0) {

        }

    }


    @Override
    protected void onResume() {

        super.onResume();

        getPurchaseList("", "");

        purCode.setText("");
    }
}