package com.example.nrbzms17.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.nrbzms17.R;
import com.example.nrbzms17.Utils.JSONUtils;
import com.example.nrbzms17.data.Api;
import com.example.nrbzms17.data.listener.OnNetRequest;
import com.example.nrbzms17.data.model.DateBean;
import com.example.nrbzms17.data.model.DateBeanResponse;
import com.example.nrbzms17.data.model.StraightBean;
import com.example.nrbzms17.data.model.StraightBeanResponse;
import com.example.nrbzms17.ui.adapter.SpinnerStraightAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DyeingDetailActivity extends AppCompatActivity {

    @BindView(R.id.dye_straight)
    Spinner dye_straight;

    SpinnerStraightAdapter StraightAdapter;

    private StraightBean  straightBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dyeing_detail);
        ButterKnife.bind(this);
        initview();
        getStraightInfo();
    }
    public void initview(){
        //下拉去向
        StraightAdapter = new SpinnerStraightAdapter();
        dye_straight.setAdapter(StraightAdapter);
    }

    //获取去向
    public void getStraightInfo() {
        Api api = new Api(this, new OnNetRequest(this) {
            @Override
            public void onSuccess(String msg) {
                StraightBeanResponse straightBeanResponse = JSONUtils.fromJson(msg, StraightBeanResponse.class);
                if (straightBeanResponse != null && straightBeanResponse.result != null) {
                    StraightAdapter.refresh(straightBeanResponse.result);
                }
            }
            @Override
            public void onFail() {
            }
        });

        api.getStraightInfo();
    }
}
