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
import com.example.nrbzms17.data.model.ColorBean;
import com.example.nrbzms17.data.model.ColorBeanResponse;
import com.example.nrbzms17.data.model.CraftBean;
import com.example.nrbzms17.data.model.CraftBeanResponse;
import com.example.nrbzms17.ui.adapter.ColorAdapter;
import com.example.nrbzms17.ui.adapter.CraftAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ColorActivity extends AppCompatActivity {
    @BindView(R.id.color_list)
    ListView color_list;

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


    ColorAdapter colorAdapter;
    String search = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color);
        ButterKnife.bind(this);
        initview();
        getColorList();
        txtvActionbarTitle.setText("颜色列表");
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
        colorAdapter = new ColorAdapter();
        color_list.setAdapter(colorAdapter);

        //栏目点击
        color_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ColorBean processOrder = (ColorBean) colorAdapter.getItem(position);
                String colortname = processOrder.name;
                Intent intent = new Intent();
                intent.putExtra("name", colortname);
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
                getColorList();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    //获取工艺信息
    public void getColorList() {
        Api api = new Api(this, new OnNetRequest(this) {
            @Override
            public void onSuccess(String msg) {
                ColorBeanResponse colorBeanResponse = JSONUtils.fromJson(msg, ColorBeanResponse.class);
                if (colorBeanResponse != null && colorBeanResponse.result != null) {
                    colorAdapter.refresh(colorBeanResponse.result);
                }
            }
            @Override
            public void onFail() {
            }
        });
        api.getColorList(search);
    }
}
