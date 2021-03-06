package com.example.nrbzms17.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.nrbzms17.R;
import com.example.nrbzms17.Utils.JSONUtils;
import com.example.nrbzms17.data.Api;
import com.example.nrbzms17.data.SharedPreference;
import com.example.nrbzms17.data.listener.OnNetRequest;
import com.example.nrbzms17.data.model.DateBean;
import com.example.nrbzms17.data.model.DateBeanResponse;
import com.example.nrbzms17.data.model.InspectBean;
import com.example.nrbzms17.data.model.InspectBeanResponse;
import com.example.nrbzms17.data.model.StatusBean;
import com.example.nrbzms17.data.model.StatusBeanResponse;
import com.example.nrbzms17.ui.adapter.InspectListAdapter;
import com.example.nrbzms17.ui.adapter.SpinnerDateAdapter;
import com.example.nrbzms17.ui.adapter.SpinnerStatusAdapter;
import com.example.nrbzms17.ui.widget.ClearEditText;
import com.jingchen.pulltorefresh.PullToRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InspectListActivity extends AppCompatActivity {


    PullToRefreshLayout pullToRefreshLayout;
    InspectListAdapter inspectListAdapter;

    Spinner inspect_status;

    SpinnerStatusAdapter statusAdapter;

    private List<InspectBean.Data> InspectBeanList = new ArrayList<>();


    private StatusBean statusBean;

    Button insSearch;

    ClearEditText insCode;

    TextView back_menu;

    Spinner ins_date;

    private DateBean dateBean;

    SpinnerDateAdapter dateAdapter;

    @BindView(R.id.txtvActionbarTitle)
    TextView txtvActionbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspect);
        ButterKnife.bind(this);

        insCode = (ClearEditText) findViewById(R.id.insCode);



        initview();

        getInspectList();

        setClickListeners();

        getCommenStatus();

        back_menu = findViewById(R.id.back_menu);
        back_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getDateInfo();
        txtvActionbarTitle.setText("检验列表");
    }

    public void initview() {
        pullToRefreshLayout = findViewById(R.id.pullToRefreshLayout);
        inspectListAdapter = new InspectListAdapter();
        inspect_status = (Spinner) findViewById(R.id.inspect_status);

        //状态
        statusAdapter = new SpinnerStatusAdapter();
        inspect_status.setAdapter(statusAdapter);


        ListView listView = (ListView) pullToRefreshLayout.getPullableView();
        listView.setAdapter(inspectListAdapter);
        pullToRefreshLayout.setPullUpEnable(false);
        pullToRefreshLayout.setOnPullListener(new PullToRefreshLayout.OnPullListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                getInspectList();
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {

            }

        });
        insSearch = (Button) findViewById(R.id.insSearch);

        //查询
        insSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInspectList();
            }
        });


        //栏目点击
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                InspectBean.Data processOrder = (InspectBean.Data) inspectListAdapter.getItem(position);

                Intent intent = new Intent(InspectListActivity.this, InspectDetailActivity.class);
                Bundle bundle = new Bundle();

                bundle.putSerializable(InspectBean.Data.class.getSimpleName(), processOrder);
                intent.putExtras(bundle);

                startActivity(intent);
            }
        });

        //日期查询
        ins_date = findViewById(R.id.ins_date);
        dateAdapter = new SpinnerDateAdapter();
        ins_date.setAdapter(dateAdapter);
    }

    //状态下拉
    private void setClickListeners() {

        inspect_status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                statusBean = (StatusBean) statusAdapter.getItem(position);

                getInspectList();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ins_date.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                dateBean = (DateBean) dateAdapter.getItem(position);
                getInspectList();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    //获取检验
    public void getInspectList() {
        String status = "";

        if (statusBean != null) {
            status = statusBean.id;

            if (status == "-1") {
                status = "";
            }
        }


        String date ="";
        if(dateBean !=null){
            date= dateBean.id;
        }
        Api api = new Api(this, new OnNetRequest(this, true, "正在加载.....") {
            @Override
            public void onSuccess(String msg) {

                InspectBeanResponse response = JSONUtils.fromJson(msg, InspectBeanResponse.class);

                if (response != null && response.result != null) {

                    InspectBeanList = response.result;

                } else {

                    InspectBeanList = new ArrayList<>();

                }
                inspectListAdapter.refresh(InspectBeanList);

                pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
            }

            @Override
            public void onFail() {

                inspectListAdapter.refresh(InspectBeanList);

                pullToRefreshLayout.refreshFinish(PullToRefreshLayout.FAIL);
            }
        });

        api.getInspectList(status, insCode.getText().toString().trim(),date);
    }

    // 获取状态信息
    public void getCommenStatus() {
        Api api = new Api(this, new OnNetRequest(this) {
            @Override
            public void onSuccess(String msg) {
                StatusBeanResponse statusBeanResponse = JSONUtils.fromJson(msg, StatusBeanResponse.class);
                if (statusBeanResponse != null && statusBeanResponse.result != null) {

                    statusAdapter.refresh(statusBeanResponse.result);

                    String departmentId = SharedPreference.getDepartmentId();
                    if (TextUtils.isEmpty(departmentId) || departmentId.equals("-1")) {
                        return;
                    }
                    for (int i = 0; i < statusAdapter.getCount(); i++) {
                        StatusBean bean = (StatusBean) statusAdapter.getItem(i);

                    }
                }
            }

            @Override
            public void onFail() {

            }
        });

        api.getCommenStatus();
    }
    //获取查询日期
    public void getDateInfo(){
        Api api = new Api(this, new OnNetRequest(this) {
            @Override
            public void onSuccess(String msg) {
                DateBeanResponse dateBeanResponse = JSONUtils.fromJson(msg, DateBeanResponse.class);
                if (dateBeanResponse != null && dateBeanResponse.result != null) {
                    dateAdapter.refresh(dateBeanResponse.result);
                    for (int i = 0; i < statusAdapter.getCount(); i++) {
                        DateBean bean = (DateBean) dateAdapter.getItem(i);
                    }
                }
            }
            @Override
            public void onFail() {

            }
        });

        api.getDateInfo();
    }

    @Override
    protected void onResume() {

        super.onResume();

        getInspectList();

        insCode.setText("");

    }
}
