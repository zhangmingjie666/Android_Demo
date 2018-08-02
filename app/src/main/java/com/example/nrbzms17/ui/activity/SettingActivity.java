package com.example.nrbzms17.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;



import com.example.nrbzms17.R;
import com.example.nrbzms17.Utils.MUtils;
import com.example.nrbzms17.Utils.UIHelper;
import com.example.nrbzms17.data.SharedPreference;
import com.example.nrbzms17.data.event.HostChangeEvent;
import com.example.nrbzms17.ui.widget.ClearEditText;

import org.greenrobot.eventbus.EventBus;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity {

    @BindView(R.id.etHost)
    ClearEditText etHost;

    @BindView(R.id.etProject)
    ClearEditText etProject;

    @BindView(R.id.btnConfirm)
    Button btnConfirm;

    @BindView(R.id.set_back)
    Button set_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        set_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        etHost.setText(SharedPreference.getHost());
        etProject.setText(SharedPreference.getProject());

    }

    @OnClick(R.id.btnConfirm)
    public void onClick() {
        MUtils.hideSoftInput(SettingActivity.this);
        String host = etHost.getText().toString();
        String project = etProject.getText().toString();

        if(TextUtils.isEmpty(host)){
            UIHelper.showShortToast(SettingActivity.this,"请输入服务地址");
            return;
        }

        if(TextUtils.isEmpty(project)){
            UIHelper.showShortToast(SettingActivity.this,"请输入服务地址");
            return;
        }

        SharedPreference.setHost(host);
        SharedPreference.setProject(project);

        UIHelper.showShortToast(SettingActivity.this, "更改成功");
        EventBus.getDefault().post(new HostChangeEvent());
        finish();
    }
}
