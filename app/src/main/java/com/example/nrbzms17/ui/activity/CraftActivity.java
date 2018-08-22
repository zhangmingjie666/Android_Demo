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
import com.example.nrbzms17.data.model.SaleBean;
import com.example.nrbzms17.ui.adapter.CraftAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CraftActivity extends AppCompatActivity {
    @BindView(R.id.craft_list)
    ListView craft_list;

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


    CraftAdapter craftAdapter;
    String search = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_craft);
        ButterKnife.bind(this);
        initview();
        getCraftList();
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
        craftAdapter = new CraftAdapter();
        craft_list.setAdapter(craftAdapter);

        //栏目点击
        craft_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CraftBean processOrder = (CraftBean) craftAdapter.getItem(position);
                String craftname = processOrder.name;
                Intent intent = new Intent();
                intent.putExtra("name", craftname);
                intent.putExtra("id",id);
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
                getCraftList();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    //获取工艺信息
    public void getCraftList() {
        Api api = new Api(this, new OnNetRequest(this) {
            @Override
            public void onSuccess(String msg) {
                CraftBeanResponse craftBeanResponse = JSONUtils.fromJson(msg, CraftBeanResponse.class);
                if (craftBeanResponse != null && craftBeanResponse.result != null) {
                    craftAdapter.refresh(craftBeanResponse.result);
                }
            }
            @Override
            public void onFail() {
            }
        });
        api.getCraftList(search);
    }
}
