package com.example.nrbzms17.ui.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import com.example.nrbzms17.data.listener.OnNetRequest;
import com.example.nrbzms17.data.model.OrderBean;
import com.example.nrbzms17.data.model.OrderBeanResponse;
import com.example.nrbzms17.data.model.OrderListBean;
import com.example.nrbzms17.data.model.PurchaseBean;
import com.example.nrbzms17.data.model.PurchaseBeanResponse;
import com.example.nrbzms17.ui.adapter.PurchaseListAdapter;
import com.example.nrbzms17.ui.widget.ClearEditText;
import com.jingchen.pulltorefresh.PullToRefreshLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PurchaseActivity extends AppCompatActivity {

    Button purchase_menu;

    PurchaseBean purchaseBean;

    ListView purchaseView;

    PurchaseListAdapter purchaseListAdapter;

    private List<PurchaseBean.Data> PurchaseBean = new ArrayList<>();

    PurchaseBean purchase = new PurchaseBean();
    ClearEditText purCode;

    Spinner purchase_status;

    Button purSearch;

    //日期
    int mYear, mMonth, mDay;
    Button btn;
    TextView dateDisplay;
    final int DATE_DIALOG = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_list);

        initview();

        getPurchaseList();


        initData();
        final Calendar ca = Calendar.getInstance();
        mYear = ca.get(Calendar.YEAR);
        mMonth = ca.get(Calendar.MONTH);
        mDay = ca.get(Calendar.DAY_OF_MONTH);
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

        btn = (Button) findViewById(R.id.dateChoose);

//        dateDisplay = (TextView) findViewById(R.id.dateDisplay);

        purchase_status = (Spinner) findViewById(R.id.purchase_status);

        purSearch =(Button) findViewById(R.id.purSearch);

        final Calendar ca = Calendar.getInstance();
        mYear = ca.get(Calendar.YEAR);
        mMonth = ca.get(Calendar.MONTH);
        mDay = ca.get(Calendar.DAY_OF_MONTH);

        String date;

        date = new StringBuffer().append(mYear).append("-").append(mMonth + 1).append("-").append(mDay).append(" ").toString();

        btn.setText(date);

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


                Intent intent = new Intent(PurchaseActivity.this, PurchaseDetailActivity.class);
                Bundle bundle = new Bundle();

//                bundle.putSerializable(OrderListBean.Data.class.getSimpleName(), processOrder);
                intent.putExtras(bundle);

                startActivity(intent);
            }
        });


    }

    public void getPurchaseList( ) {

        Api api = new Api(this, new OnNetRequest(this, true, "正在加载.....") {
            @Override
            public void onSuccess(String msg) {

                PurchaseBeanResponse response = JSONUtils.fromJson(msg, PurchaseBeanResponse.class);

                if (response != null && response.result != null) {

                    if (response.result.size() > 0) {

                        PurchaseBean = response.result;

                        purchaseListAdapter.refresh(PurchaseBean);


//                        order = OrderBeanList.get(0);


                    } else {

                        PurchaseBean = new ArrayList<>();

                        purchase = null;

                    }


                }
            }

            @Override
            public void onFail() {

                purchaseListAdapter.refresh(PurchaseBean);

            }
        });

        api.getPurchaseList("", purCode.getText().toString().trim(), btn.getText().toString().trim());

    }

    //日期处理
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG:
                return new DatePickerDialog(PurchaseActivity.this, mdateListener, mYear, mMonth, mDay);
        }
        return null;
    }

    DatePickerDialog.OnDateSetListener mdateListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
//            display();
            String date;

            date = new StringBuffer().append(mYear).append("-").append(mMonth + 1).append("-").append(mDay).append(" ").toString();

//          dateDisplay.setText(new StringBuffer().append(mMonth + 1).append("-").append(mDay).append("-").append(mYear).append(" "));

            btn.setText(date);

            getPurchaseList();

        }

        /**
         * 设置日期 利用StringBuffer追加
         */
        private void display() {


        }
    };

    private void initData() {

        //监听事件
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_DIALOG);
            }
        });


    }


}
