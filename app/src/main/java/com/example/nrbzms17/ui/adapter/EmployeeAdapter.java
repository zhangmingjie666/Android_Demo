package com.example.nrbzms17.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.nrbzms17.R;
import com.example.nrbzms17.data.model.EmployeeBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author MJ@ZHANG
 * @package: com.example.nrbzms17.ui.adapter
 * @filename EmployeeAdapter
 * @date on 2018/8/7 9:56
 * @descibe TODO
 * @email zhangmingjie@huansi.net
 */
public class EmployeeAdapter extends BaseAdapter {
    List<EmployeeBean> listData = new ArrayList<>();
    public void refresh(List<EmployeeBean> ls){
        if(ls == null){
            ls = new ArrayList<>();
        }
        listData = ls;
        listData.add(0,new EmployeeBean("-1","请选择"));
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
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.spinner_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
        EmployeeBean managerBean = listData.get(position);
        viewHolder.txtvName.setText(managerBean.name);
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.txtv_Name)
        TextView txtvName;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
