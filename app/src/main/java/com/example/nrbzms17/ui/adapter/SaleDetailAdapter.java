package com.example.nrbzms17.ui.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.nrbzms17.R;
import com.example.nrbzms17.data.model.PurchaseDetailBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SaleDetailAdapter extends BaseAdapter {
    List<PurchaseDetailBean.Data> listData = new ArrayList<>();

    public void refresh(List<PurchaseDetailBean.Data> ls) {
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
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_purchase_detail, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        PurchaseDetailBean.Data bean = listData.get(position);
        viewHolder.pur_customcode.setText(bean.customcode);
        viewHolder.pur_billdate.setText(bean.billdate);
        viewHolder.pur_company.setText(bean.company);
        //小范秀操作
        if (bean.status.toString().equals("待审核")) {
            viewHolder.pur_status.setTextColor(Color.parseColor("#FF0000"));
        } else {
            viewHolder.pur_status.setTextColor(Color.parseColor("#0000FF"));
        }

        viewHolder.pur_status.setText(bean.status);
        viewHolder.pur_material.setText(bean.material);
        viewHolder.pur_employee.setText(bean.employee);
        viewHolder.pur_quantity.setText(bean.quantity);
        viewHolder.pur_color.setText(bean.color);
        viewHolder.pur_contcode.setText(bean.contcode);

        viewHolder.pur_spec.setText(bean.spec);
        viewHolder.pur_width.setText(bean.width);
        viewHolder.pur_weight.setText(bean.weight);
        viewHolder.pur_linkman.setText(bean.linkman);
        viewHolder.pur_trade.setText(bean.trade);
//        viewHolder.pur_sup_material_code.setText(bean.sup_material_code);
        viewHolder.pur_craft.setText(bean.craft);

        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.pur_customcode)
        TextView pur_customcode;

        @BindView(R.id.pur_billdate)
        TextView pur_billdate;

        @BindView(R.id.pur_company)
        TextView pur_company;

        @BindView(R.id.pur_status)
        TextView pur_status;

        @BindView(R.id.pur_material)
        TextView pur_material;

        @BindView(R.id.pur_employee)
        TextView pur_employee;

        @BindView(R.id.pur_quantity)
        TextView pur_quantity;

        @BindView(R.id.pur_color)
        TextView pur_color;

        @BindView(R.id.pur_contcode)
        TextView pur_contcode;

        @BindView(R.id.pur_spec)
        TextView pur_spec;

        @BindView(R.id.pur_width)
        TextView pur_width;

        @BindView(R.id.pur_weight)
        TextView pur_weight;

        @BindView(R.id.pur_linkman)
        TextView pur_linkman;

        @BindView(R.id.pur_trade)
        TextView pur_trade;

//        @BindView(R.id.pur_sup_material_code)
//        TextView pur_sup_material_code;

        @BindView(R.id.pur_craft)
        TextView pur_craft;



        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }


    }
}
