package com.example.nrbzms17.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.nrbzms17.R;
//import com.nrbzms17.Utils.JSONUtils;
import com.example.nrbzms17.Utils.JSONUtils;
import com.example.nrbzms17.data.Api;
import com.example.nrbzms17.data.listener.OnNetRequest;
import com.example.nrbzms17.data.model.OrderBean;
import com.example.nrbzms17.data.model.OrderListBean;
import com.example.nrbzms17.data.model.StatusBean;
import com.example.nrbzms17.ui.adapter.OrderDetailAdapter;
import com.example.nrbzms17.ui.adapter.OrderListAdapter;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailActivity extends AppCompatActivity {

    private OrderBean.Data order;
    private StatusBean statusbean;
    OrderDetailAdapter adapter;
    OrderListAdapter orderListAdapter;

    ListView listView;

    private List<OrderListBean.Data> OrderListBean = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        initview();

        Button back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
//
//    @Override
//    public void next(View view) {
//
//    }
//
//    @Override
//    public void pre(View view) {
//    Intent intent  =new Intent(OrderDetailActivity.this,OrderListActivity.class);
//        startActivity(intent);
//        overridePendingTransition(R.anim.in_from_right,R.anim.out_to_left);
//        finish();
//    }
//
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        return super.onTouchEvent(event);
//    }

    private void initview() {
//        if (getIntent() != null) {
        order = (OrderBean.Data) getIntent().getSerializableExtra(OrderBean.Data.class.getSimpleName());
//        }

//        if (order != null) {
        TextView txtv_billdate = findViewById(R.id.txtv_billdate);
        TextView txtv_customcode = findViewById(R.id.txtv_customcode);
        TextView txtv_company = findViewById(R.id.txtv_company);
        TextView txtv_material = findViewById(R.id.txtv_material);
        TextView txtv_employee = findViewById(R.id.txtv_employee);
        TextView txtv_contcode = findViewById(R.id.txtv_contcode);
        TextView txtv_ratio = findViewById(R.id.txtv_ratio);
        TextView txtv_quantity = findViewById(R.id.txtv_quantity);

        txtv_billdate.setText(order.billdate);
        txtv_customcode.setText(order.customcode);
        txtv_company.setText(order.company);
        txtv_material.setText(order.material);
        txtv_employee.setText(order.employee);
        txtv_contcode.setText(order.contcode);
        txtv_ratio.setText(order.ratio);
        txtv_quantity.setText(order.yquantity);

        if (order.status.toString().equals("0")) {
            Button noaudit = (Button) findViewById(R.id.noaudit);
            noaudit.setEnabled(false);
            Button audit = (Button) findViewById(R.id.audit);
            audit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    IndentAudit();
                    finish();

                }
            });
        } else if(order.status.toString().equals("1")){
            Button audit = (Button) findViewById(R.id.audit);
            audit.setEnabled(false);
            Button noaudit = (Button) findViewById(R.id.noaudit);
            noaudit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    IndentNoaudit();
                    Toast.makeText(OrderDetailActivity.this, "反审成功", Toast.LENGTH_SHORT).show();
                    finish();

                }
            });
        }else{
            Button noaudit = (Button) findViewById(R.id.noaudit);
            noaudit.setEnabled(false);
            Button audit = (Button) findViewById(R.id.audit);
            audit.setEnabled(false);
        }

        getOrderListDetail(order.id);

    }

    public void getOrderListDetail(String id) {

        adapter = new OrderDetailAdapter();
        listView = findViewById(R.id.order_detail);
        listView.setAdapter(adapter);
        Api api = new Api(this, new OnNetRequest(this, true, "正在加载...") {


            @Override
            public void onSuccess(String msg) {
                OrderListBean responseBean = JSONUtils.fromJson(msg, OrderListBean.class);
                if (responseBean != null && responseBean.result != null) {
                    if (responseBean.result.size() == 0) {

//                        OrderListBean = responseBean.result;
//
//                        adapter.refresh(OrderListBean);
                    }
                    adapter.refresh(responseBean.result);

                } else {
                }
            }

            @Override
            public void onFail() {
            }
        });

        api.getOrderList(id);
    }

    //    @OnClick(R.id.audit)
//    public void onlick() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setMessage("确认审核");
//        builder.setTitle("提示");
//        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//            }
//        });
//        builder.setNegativeButton("取消", null);
//        builder.create().show();
//    }
    //审核
    public void IndentAudit() {

        Api api = new Api(this, new OnNetRequest(this, true, "正在加载...") {
            @Override
            public void onSuccess(String msg) {
                StatusBean responseBean = JSONUtils.fromJson(msg, StatusBean.class);
                if (responseBean != null) {

                    Toast.makeText(OrderDetailActivity.this, "审核成功", Toast.LENGTH_SHORT).show();


//                    if (responseBean.result.size() == 0) {

//                        OrderListBean = responseBean.result;
//
//                        adapter.refresh(OrderListBean);
//                    }
//                    adapter.refresh(responseBean.result);

                } else {
                }
            }

            @Override
            public void onFail() {
            }
        });

        api.IndentAudit(order.id);

    }
    //反审

    public void IndentNoaudit() {

        Api api = new Api(this, new OnNetRequest(this, true, "正在加载...") {
            @Override
            public void onSuccess(String msg) {
                StatusBean responseBean = JSONUtils.fromJson(msg, StatusBean.class);
                if (responseBean != null) {
//                    if (responseBean.result.size() == 0) {

//                        OrderListBean = responseBean.result;
//
//                        adapter.refresh(OrderListBean);
//                    }
//                    adapter.refresh(responseBean.result);

                } else {
                }
            }

            @Override
            public void onFail() {
                adapter.refresh(OrderListBean);
            }
        });

        api.IndentNoaudit(order.id);

    }


}
