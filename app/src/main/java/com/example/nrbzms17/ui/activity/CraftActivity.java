package com.example.nrbzms17.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    CraftAdapter craftAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_craft);
        ButterKnife.bind(this);
        initview();
        getCraftList();
        txtvActionbarTitle.setText("工艺列表");
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
        api.getCraftList();
    }
}
