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
import com.example.nrbzms17.data.model.PurchasingBeanResponse;
import com.example.nrbzms17.ui.adapter.DyingAdapter;
import com.example.nrbzms17.ui.widget.ClearEditText;
import com.jingchen.pulltorefresh.PullToRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DyeingActivity extends AppCompatActivity {

    PullToRefreshLayout pullToRefreshLayout;
    DyingAdapter dyingAdapter;

    @BindView(R.id.etCode)
    EditText etCode;

    @BindView(R.id.choose)
    TextView choose;

    @BindView(R.id.cancel)
    TextView cancel;

    @BindView(R.id.kongbai)
    TextView kongbai;

    @BindView(R.id.txtvActionbarTitle)
    TextView txtvActionbarTitle;

    @BindView(R.id.back_menu)
    TextView back_menu;

    private List<DyingBean.Data> DyingBeanList = new ArrayList<>();

    String search = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dyeing);
        ButterKnife.bind(this);
        initview();
        getDyeing();
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choose.setVisibility(View.GONE);
                kongbai.setVisibility(View.GONE);
                etCode.setVisibility(View.VISIBLE);
                cancel.setVisibility(View.VISIBLE);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choose.setVisibility(View.VISIBLE);
                kongbai.setVisibility(View.VISIBLE);
                etCode.setVisibility(View.GONE);
                cancel.setVisibility(View.GONE);
            }
        });
        back_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void initview(){
        pullToRefreshLayout = findViewById(R.id.pullToRefreshLayout);
        dyingAdapter = new DyingAdapter();
        txtvActionbarTitle.setText("收货列表");

        // 设置列表适配器
        ListView listView = (ListView) pullToRefreshLayout.getPullableView();
        listView.setAdapter(dyingAdapter);
        pullToRefreshLayout.setPullUpEnable(false);


        // 下拉刷新事件
        pullToRefreshLayout.setOnPullListener(new PullToRefreshLayout.OnPullListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                getDyeing();
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
                getDyeing();
            }
        });

        //栏目点击
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DyingBean.Data processOrder = (DyingBean.Data) dyingAdapter.getItem(position);

                Intent intent = new Intent(DyeingActivity.this, DyeingDetailActivity.class);
                Bundle bundle = new Bundle();

                bundle.putSerializable(PurchasingBean.Data.class.getSimpleName(), processOrder);
                intent.putExtras(bundle);

                startActivity(intent);
            }
        });
    }

    //获取订单列表
    public void getDyeing() {

//        String date ="";
//        if(dateBean !=null){
//            date= dateBean.id;
//        }
//        String date ="";
//        if(dateBean !=null){
//            date= dateBean.id;
//        }

        Api api = new Api(this, new OnNetRequest(this, true, "正在加载.....") {
            @Override
            public void onSuccess(String msg) {

                DyingBeanResponse response = JSONUtils.fromJson(msg, DyingBeanResponse.class);

                if (response != null && response.result != null) {

                    DyingBeanList = response.result;
                } else {
                    DyingBeanList = new ArrayList<>();
                }
                dyingAdapter.refresh(DyingBeanList);
                pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
            }

            @Override
            public void onFail() {

                dyingAdapter.refresh(DyingBeanList);

                pullToRefreshLayout.refreshFinish(PullToRefreshLayout.FAIL);
            }
        });

        api.getDyeing(search);
    }
}
