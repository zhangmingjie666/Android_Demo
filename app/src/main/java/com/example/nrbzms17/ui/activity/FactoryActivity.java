package com.example.nrbzms17.ui.activity;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.nrbzms17.R;
import com.example.nrbzms17.Utils.JSONUtils;
import com.example.nrbzms17.data.Api;
import com.example.nrbzms17.data.listener.OnNetRequest;
import com.example.nrbzms17.data.model.CraftBean;
import com.example.nrbzms17.data.model.CraftBeanResponse;
import com.example.nrbzms17.data.model.FactoryBean;
import com.example.nrbzms17.data.model.FactoryBeanResponse;
import com.example.nrbzms17.ui.adapter.CraftAdapter;
import com.example.nrbzms17.ui.adapter.FactoryAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FactoryActivity extends AppCompatActivity {

    @BindView(R.id.factory_list)
    ListView factory_list;

    @BindView(R.id.txtvActionbarTitle)
    TextView txtvActionbarTitle;

    FactoryAdapter factoryAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_factory);
        ButterKnife.bind(this);
        initview();
        txtvActionbarTitle.setText("加工厂列表");
        getFactoryList();
    }
    public void initview(){
        factoryAdapter = new FactoryAdapter();
        factory_list.setAdapter(factoryAdapter);

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.rg_title);
        radioGroup.check(R.id.rb_title_select);//默认选中的RadioButton
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

            }
        });


        //栏目点击
        factory_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FactoryBean processOrder = (FactoryBean) factoryAdapter.getItem(position);
                String craftname = processOrder.name;
                Intent intent = new Intent();
                intent.putExtra("name", craftname);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    //获取工加工厂信息
    public void getFactoryList() {


        Api api = new Api(this, new OnNetRequest(this) {
            @Override
            public void onSuccess(String msg) {
                FactoryBeanResponse factoryBeanResponse = JSONUtils.fromJson(msg, FactoryBeanResponse.class);
                if (factoryBeanResponse != null && factoryBeanResponse.result != null) {
                    factoryAdapter.refresh(factoryBeanResponse.result);
                }
            }

            @Override
            public void onFail() {
            }
        });
        api.getFactoryList();
    }
}
