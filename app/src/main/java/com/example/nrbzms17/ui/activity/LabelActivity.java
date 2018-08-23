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
import com.example.nrbzms17.data.model.ColorBean;
import com.example.nrbzms17.data.model.FactoryBeanResponse;
import com.example.nrbzms17.data.model.LabelBean;
import com.example.nrbzms17.data.model.LabelBeanResponse;
import com.example.nrbzms17.ui.adapter.LabelAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LabelActivity extends AppCompatActivity {

    LabelAdapter labelAdapter;

    @BindView(R.id.label_list)
    ListView label_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_label);
        ButterKnife.bind(this);
        initview();
        getLabelList();
    }
    public void initview(){
        labelAdapter = new LabelAdapter();
        label_list.setAdapter(labelAdapter);
        //栏目点击
        label_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LabelBean processOrder = (LabelBean) labelAdapter.getItem(position);
                String labeltname = processOrder.name;
                Intent intent = new Intent();
                intent.putExtra("name", labeltname);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }



    //获取标签信息
    public void getLabelList() {

        Api api = new Api(this, new OnNetRequest(this) {
            @Override
            public void onSuccess(String msg) {
                LabelBeanResponse labelBeanResponse = JSONUtils.fromJson(msg, LabelBeanResponse.class);
                if (labelBeanResponse != null && labelBeanResponse.result != null) {
                    labelAdapter.refresh(labelBeanResponse.result);
                }
            }

            @Override
            public void onFail() {
            }
        });
        api.getLabelList();
    }
}
