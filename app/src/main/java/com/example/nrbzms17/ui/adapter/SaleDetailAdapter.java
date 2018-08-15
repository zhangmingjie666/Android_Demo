package com.example.nrbzms17.ui.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.nrbzms17.R;
import com.example.nrbzms17.data.model.PurchaseDetailBean;
import com.example.nrbzms17.data.model.SaleDetailBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SaleDetailAdapter extends BaseAdapter {
    List<SaleDetailBean.Data> listData = new ArrayList<>();

    public void refresh(List<SaleDetailBean.Data> ls) {
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
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_sale_detail, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        SaleDetailBean.Data bean = listData.get(position);
        viewHolder.sale_customcode.setText(bean.customcode);
        viewHolder.sale_billdate.setText(bean.billdate);
        viewHolder.sale_company.setText(bean.company);
        //小范秀操作
        if (bean.status.toString().equals("待审核")) {
            viewHolder.sale_status.setTextColor(Color.parseColor("#FF0000"));
        } else {
            viewHolder.sale_status.setTextColor(Color.parseColor("#0000FF"));
        }

        viewHolder.sale_status.setText(bean.status);
        viewHolder.sale_material.setText(bean.material);
        viewHolder.sale_employee.setText(bean.employee);
        viewHolder.sale_quantity.setText(bean.quantity);
        viewHolder.sale_color.setText(bean.color);
        viewHolder.sale_linkman.setText(bean.linkman);

        viewHolder.sale_billtrade.setText(bean.spec);
        viewHolder.sale_remark.setText(bean.remark);
        viewHolder.sale_style.setText(bean.style);
        viewHolder.sale_address.setText(bean.address);
        viewHolder.sale_express_code.setText(bean.sale_express_code);
        viewHolder.sale_craft.setText(bean.craft);
        viewHolder.sale_trade.setText(bean.trade);

        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.sale_customcode)
        TextView sale_customcode;

        @BindView(R.id.sale_billdate)
        TextView sale_billdate;

        @BindView(R.id.sale_company)
        TextView sale_company;

        @BindView(R.id.sale_material)
        TextView sale_material;

        @BindView(R.id.sale_status)
        TextView sale_status;

        @BindView(R.id.sale_employee)
        TextView sale_employee;

        @BindView(R.id.sale_quantity)
        TextView sale_quantity;

        @BindView(R.id.sale_color)
        TextView sale_color;

        @BindView(R.id.sale_craft)
        TextView sale_craft;

        @BindView(R.id.sale_express_code)
        TextView sale_express_code;

        @BindView(R.id.sale_billtrade)
        TextView sale_billtrade;

        @BindView(R.id.sale_address)
        TextView sale_address;

        @BindView(R.id.sale_style)
        TextView sale_style;

        @BindView(R.id.sale_linkman)
        TextView sale_linkman;

        @BindView(R.id.sale_remark)
        TextView sale_remark;

        @BindView(R.id.sale_trade)
        TextView sale_trade;



        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }


    }
}
