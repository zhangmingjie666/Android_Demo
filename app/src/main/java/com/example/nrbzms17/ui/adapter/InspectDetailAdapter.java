package com.example.nrbzms17.ui.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.nrbzms17.R;
import com.example.nrbzms17.data.model.InspectBean;
import com.example.nrbzms17.data.model.InspectDetailBean;
import com.example.nrbzms17.data.model.PurchaseDetailBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InspectDetailAdapter extends BaseAdapter {
    List<InspectDetailBean.Data> listData = new ArrayList<>();

    public void refresh(List<InspectDetailBean.Data> ls) {
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


    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_inspect_detail, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        InspectDetailBean.Data bean = listData.get(position);
        viewHolder.ins_customcode.setText(bean.customcode);
        viewHolder.ins_billdate.setText(bean.billdate);
        viewHolder.ins_company.setText(bean.companyname);
        //小范秀操作
        if (bean.status.toString().equals("待审核")) {
            viewHolder.ins_status.setTextColor(Color.parseColor("#FF0000"));
        } else {
            viewHolder.ins_status.setTextColor(Color.parseColor("#0000FF"));
        }

        viewHolder.ins_status.setText(bean.status);
        viewHolder.ins_material.setText(bean.materialname);
        viewHolder.ins_employee.setText(bean.employee);
        viewHolder.ins_quantity.setText(bean.quantity);
        viewHolder.ins_color.setText(bean.colorname);
        viewHolder.ins_code.setText(bean.code);
        viewHolder.ins_craft.setText(bean.craftname);
        viewHolder.pur_source.setText(bean.source);

        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.ins_customcode)
        TextView ins_customcode;

        @BindView(R.id.ins_billdate)
        TextView ins_billdate;

        @BindView(R.id.ins_company)
        TextView ins_company;

        @BindView(R.id.ins_status)
        TextView ins_status;

        @BindView(R.id.ins_material)
        TextView ins_material;

        @BindView(R.id.ins_employee)
        TextView ins_employee;

        @BindView(R.id.ins_quantity)
        TextView ins_quantity;

        @BindView(R.id.ins_color)
        TextView ins_color;

        @BindView(R.id.ins_code)
        TextView ins_code;

        @BindView(R.id.ins_craft)
        TextView ins_craft;

        @BindView(R.id.pur_source)
        TextView pur_source;



        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }


    }
}
