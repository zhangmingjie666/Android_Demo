package com.example.nrbzms17.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.nrbzms17.R;
import com.example.nrbzms17.Utils.JSONUtils;
import com.example.nrbzms17.data.Api;
import com.example.nrbzms17.data.listener.OnNetRequest;

import com.example.nrbzms17.data.model.FactoryBean;
import com.example.nrbzms17.data.model.FactoryBeanResponse;
import com.example.nrbzms17.ui.widget.ClearEditText;

//import com.example.nrbzms17.ui.adapter.FactoryAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FactoryActivity extends AppCompatActivity {

    @BindView(R.id.factory_list)
    ListView factory_list;

    @BindView(R.id.txtvActionbarTitle)
    TextView txtvActionbarTitle;

    @BindView(R.id.SearchCode)
    EditText SearchCode;

    @BindView(R.id.back_menu)
    TextView back_menu;

    @BindView(R.id.choose)
    TextView choose;

    @BindView(R.id.cancel)
    TextView cancel;

    @BindView(R.id.kongbai)
    TextView kongbai;




    FactoryAdapter factoryAdapter;

    String type = "4";
    String search = "";
    private List<FactoryBean> factoryBeanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_factory);
        ButterKnife.bind(this);
        initview();
        txtvActionbarTitle.setText("加工厂列表");
        getFactoryList();
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
                SearchCode.setVisibility(View.VISIBLE);
                cancel.setVisibility(View.VISIBLE);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choose.setVisibility(View.VISIBLE);
                kongbai.setVisibility(View.VISIBLE);
                SearchCode.setVisibility(View.GONE);
                cancel.setVisibility(View.GONE);
            }
        });

    }

    public void initview() {
        factoryAdapter = new FactoryAdapter();
        factory_list.setAdapter(factoryAdapter);

        //切换染厂和后整理厂
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.rg_title);
        radioGroup.check(R.id.dying_select);//默认选中的RadioButton
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (R.id.dying_select == checkedId) {
                    type = "4";
                    getFactoryList();
                }
                if (R.id.after_select == checkedId) {
                    type = "5";
                    getFactoryList();
                }
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

        SearchCode.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                search = SearchCode.getText().toString().trim();
            }

            @Override
            public void afterTextChanged(Editable s) {
                getFactoryList();
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
        api.getFactoryList(type,search);
    }

    //过滤
    class FactoryFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults filterResults = new FilterResults();
            if (prefix == null || prefix.length() == 0 || factoryBeanList == null || factoryBeanList.size() == 0) {
                ArrayList<FactoryBean> l = new ArrayList<>(factoryBeanList);
                filterResults.values = l;
                filterResults.count = l.size();
            } else {
                String prefixString = prefix.toString().toLowerCase();
                final List<FactoryBean> list = new ArrayList<>(factoryBeanList);
                final List<FactoryBean> newList = new ArrayList<>();
                for (int i = 0; i < list.size(); i++) {
                        String id = list.get(i).name.toString().trim().toLowerCase();
                    if (id.indexOf(prefixString) != -1) {
                        newList.add(list.get(i));
                    }
                }
                filterResults.values = newList;
                filterResults.count = newList.size();
            }
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            List<FactoryBean> list = (List<FactoryBean>) results.values;
            String string = SearchCode.toString().trim();
            if (string.equals("")) {
                list = new ArrayList<>();
                factoryAdapter.refresh(list);
            } else {
                factoryAdapter.refresh(list);
            }
        }
    }

    //加工厂适配器
    public class FactoryAdapter extends BaseAdapter implements Filterable {
        List<FactoryBean> listData = new ArrayList<>();
        private Filter filter;

        public void refresh(List<FactoryBean> ls) {
            if (ls == null) {
                listData = new ArrayList<>();
            } else {
                listData = ls;
            }
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return listData.size();
        }

        @Override
        public Object getItem(int i) {
            return listData.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {

            ViewHolder viewHolder;

            if (convertView == null) {

                convertView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_factory_detail, null);

                viewHolder = new ViewHolder(convertView);

                convertView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();

            }
            FactoryBean managerBean = listData.get(position);

            viewHolder.factory.setText(managerBean.name);
            if (position % 2 == 0) {
                convertView.setBackgroundColor(Color.parseColor("#DCDCDC"));
            } else {
                convertView.setBackgroundColor(Color.parseColor("#FFFAFA"));
            }

            return convertView;
        }

        public Filter getFilter() {
            if (filter == null) {
                filter = new FactoryFilter();
            }
            return filter;
        }

        class ViewHolder {
            @BindView(R.id.factory)
            TextView factory;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

    //异步
//    protected void onPostExecute(List<FactoryBean> FactoryBean) {
////        super.onPostExecute(FactoryBean);
//        //打开过滤功能
//        factory_list.setTextFilterEnabled(true);
//        SearchCode.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                if (TextUtils.isEmpty(newText)) {
//                    //搜索文本为空时，过滤设置
//                    factory_list.clearTextFilter();
//                } else {
//                    //设置过滤关键字
//                    factory_list.setFilterText(newText);
//                }
//                return true;
//            }
//        });
//    }


}

