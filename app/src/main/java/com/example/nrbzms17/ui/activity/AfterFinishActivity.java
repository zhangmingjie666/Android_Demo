package com.example.nrbzms17.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.nrbzms17.R;
import com.example.nrbzms17.Utils.JSONUtils;
import com.example.nrbzms17.data.Api;
import com.example.nrbzms17.data.listener.OnNetRequest;
import com.example.nrbzms17.data.model.DyingBean;
import com.example.nrbzms17.data.model.DyingBeanResponse;
import com.example.nrbzms17.data.model.PurchasingBean;
import com.example.nrbzms17.ui.adapter.AfterFinishAdapter;
import com.example.nrbzms17.ui.adapter.DyingAdapter;
import com.jingchen.pulltorefresh.PullToRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AfterFinishActivity extends AppCompatActivity {


    PullToRefreshLayout pullToRefreshLayout;
    AfterFinishAdapter afterfinishAdapter;
    private List<DyingBean.Data> DyingBeanList = new ArrayList<>();

    @BindView(R.id.txtvActionbarTitle)
    TextView txtvActionbarTitle;

    @BindView(R.id.back_menu)
    TextView back_menu;

    @BindView(R.id.etCode)
    EditText etCode;


    String search = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_finish);
        ButterKnife.bind(this);
        initview();
        back_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getAfterFinish();
    }
    public void initview(){
        pullToRefreshLayout = findViewById(R.id.pullToRefreshLayout);
        afterfinishAdapter = new AfterFinishAdapter();
        txtvActionbarTitle.setText("收货列表");

        // 设置列表适配器
        ListView listView = (ListView) pullToRefreshLayout.getPullableView();
        listView.setAdapter(afterfinishAdapter);
        pullToRefreshLayout.setPullUpEnable(false);


        // 下拉刷新事件
        pullToRefreshLayout.setOnPullListener(new PullToRefreshLayout.OnPullListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                getAfterFinish();
            }
            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
            }
        });

        //检索
        etCode.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                search = etCode.getText().toString().trim();

            }
            @Override
            public void afterTextChanged(Editable s) {
                getAfterFinish();
            }
        });

        //栏目点击
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DyingBean.Data processOrder = (DyingBean.Data) afterfinishAdapter.getItem(position);

                Intent intent = new Intent(AfterFinishActivity.this, AfterFinishDetailActivity.class);
                Bundle bundle = new Bundle();

                bundle.putSerializable(DyingBean.Data.class.getSimpleName(), processOrder);
                intent.putExtras(bundle);

                startActivity(intent);
            }
        });
    }

    //获取后整理收货列表
    public void getAfterFinish(){
        Api api = new Api(this, new OnNetRequest(this, true, "正在加载.....") {
            @Override
            public void onSuccess(String msg) {

                DyingBeanResponse response = JSONUtils.fromJson(msg, DyingBeanResponse.class);

                if (response != null && response.result != null) {

                    DyingBeanList = response.result;
                } else {
                    DyingBeanList = new ArrayList<>();
                }
                afterfinishAdapter.refresh(DyingBeanList);
                pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
            }

            @Override
            public void onFail() {

                afterfinishAdapter.refresh(DyingBeanList);

                pullToRefreshLayout.refreshFinish(PullToRefreshLayout.FAIL);
            }
        });

        api.getAfterFinish(search);
    }
}
