package com.example.nrbzms17.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.nrbzms17.R;
import com.example.nrbzms17.data.model.OrderBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderListAdapter extends BaseAdapter {
    List<OrderBean.Data> listData = new ArrayList<>();


    public void refresh(List<OrderBean.Data> ls) {
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
    public Object getItem(int position) {
        return listData.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_order, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        OrderBean.Data bean = listData.get(position);
        viewHolder.txtv_customcode.setText(bean.customcode);
        viewHolder.txtv_billdate.setText(bean.billdate);
        viewHolder.txtv_company.setText(bean.company);
        //小范秀操作
        if (bean.status.toString().equals("待审核")) {
            viewHolder.txtv_status.setTextColor(Color.parseColor("#FF0000"));
        } else {
            viewHolder.txtv_status.setTextColor(Color.parseColor("#0000FF"));
        }

        viewHolder.txtv_status.setText(bean.status);
        viewHolder.txtv_material.setText(bean.material);
        viewHolder.txtv_employee.setText(bean.employee);
        viewHolder.txtv_quantity.setText(bean.yquantity);
        viewHolder.txtv_ratio.setText(bean.ratio);
        viewHolder.txtv_contcode.setText(bean.contcode);

        return convertView;
    }


static class ViewHolder {
    @BindView(R.id.txtv_customcode)
    TextView txtv_customcode;

    @BindView(R.id.txtv_billdate)
    TextView txtv_billdate;

    @BindView(R.id.txtv_company)
    TextView txtv_company;

    @BindView(R.id.txtv_status)
    TextView txtv_status;

    @BindView(R.id.txtv_material)
    TextView txtv_material;

    @BindView(R.id.txtv_employee)
    TextView txtv_employee;

    @BindView(R.id.txtv_quantity)
    TextView txtv_quantity;

    @BindView(R.id.txtv_ratio)
    TextView txtv_ratio;

    @BindView(R.id.txtv_contcode)
    TextView txtv_contcode;


    ViewHolder(View view) {
        ButterKnife.bind(this, view);
    }


}

}
