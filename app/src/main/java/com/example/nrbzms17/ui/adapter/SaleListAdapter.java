package com.example.nrbzms17.ui.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.nrbzms17.R;
import com.example.nrbzms17.data.model.SaleBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author MJ@ZHANG
 * @package: com.example.nrbzms17.ui.adapter
 * @filename SaleListAdapter
 * @date on 2018/8/7 17:36
 * @descibe TODO
 * @email zhangmingjie@huansi.net
 */
public class SaleListAdapter extends BaseAdapter {
    List<SaleBean.Data> listData = new ArrayList<>();


    public void refresh(List<SaleBean.Data> ls) {
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
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_sale, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        SaleBean.Data bean = listData.get(position);
        viewHolder.sale_customcode.setText(bean.customcode);
        viewHolder.sale_billdate.setText(bean.billdate);

        //小范秀操作
        if (bean.status.toString().equals("待审核")) {

            viewHolder.sale_status.setTextColor(Color.parseColor("#FF0000"));

        } else {

            viewHolder.sale_status.setTextColor(Color.parseColor("#0000FF"));
        }

        viewHolder.sale_status.setText(bean.status);
        viewHolder.sale_material.setText(bean.material);
        viewHolder.sale_craft.setText(bean.craft);
        viewHolder.sale_quantity.setText(bean.quantity);
        viewHolder.sale_volume.setText(bean.volume);
        viewHolder.sale_style.setText(bean.style);
        viewHolder.sale_company.setText(bean.customer_name);
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

        @BindView(R.id.sale_style)
        TextView sale_style;

        @BindView(R.id.sale_status)
        TextView sale_status;

        @BindView(R.id.sale_volume)
        TextView sale_volume;

        @BindView(R.id.sale_quantity)
        TextView sale_quantity;

        @BindView(R.id.sale_craft)
        TextView sale_craft;


        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }


    }

}
