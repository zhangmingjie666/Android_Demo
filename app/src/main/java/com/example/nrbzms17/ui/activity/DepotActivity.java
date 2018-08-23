package com.example.nrbzms17.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.nrbzms17.R;
import com.example.nrbzms17.Utils.JSONUtils;
import com.example.nrbzms17.data.Api;
import com.example.nrbzms17.data.listener.OnNetRequest;
import com.example.nrbzms17.data.model.CraftBean;
import com.example.nrbzms17.data.model.CraftBeanResponse;
import com.example.nrbzms17.data.model.DepotBean;
import com.example.nrbzms17.data.model.DepotBeanResponse;
import com.example.nrbzms17.ui.adapter.CraftAdapter;
import com.example.nrbzms17.ui.adapter.DepotAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DepotActivity extends AppCompatActivity {
    @BindView(R.id.depot_list)
    ListView depot_list;

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


    DepotAdapter depotAdapter;
    String search = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depot);
        ButterKnife.bind(this);
        initview();
        getDepotinfo();
        txtvActionbarTitle.setText("工艺列表");
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
        depotAdapter = new DepotAdapter();
        depot_list.setAdapter(depotAdapter);

        //栏目点击
        depot_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DepotBean processOrder = (DepotBean) depotAdapter.getItem(position);
                String depotname = processOrder.name;
                Intent intent = new Intent();
                intent.putExtra("name", depotname);
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
                getDepotinfo();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    //获取工艺信息
    public void getDepotinfo() {
        Api api = new Api(this, new OnNetRequest(this) {
            @Override
            public void onSuccess(String msg) {
                DepotBeanResponse depotBeanResponse = JSONUtils.fromJson(msg, DepotBeanResponse.class);
                if (depotBeanResponse != null && depotBeanResponse.result != null) {
                    depotAdapter.refresh(depotBeanResponse.result);
                }
            }
            @Override
            public void onFail() {
            }
        });
        api.getDepotinfo();
    }
}
