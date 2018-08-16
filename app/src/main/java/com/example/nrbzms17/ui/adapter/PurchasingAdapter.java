package com.example.nrbzms17.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.nrbzms17.R;
import com.example.nrbzms17.data.model.PurchasingBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author MJ@ZHANG
 * @package: com.example.nrbzms17.ui.adapter
 * @filename PurchasingAdapter
 * @date on 2018/8/15 17:44
 * @descibe TODO
 * @email zhangmingjie@huansi.net
 */
public class PurchasingAdapter extends BaseAdapter {

    List<PurchasingBean.Data> listData = new ArrayList<>();

    public void refresh(List<PurchasingBean.Data> ls) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_shopping_list, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final PurchasingBean.Data purchasingBean = listData.get(position);
        viewHolder.pur_customcode.setText(purchasingBean.customcode);
        viewHolder.pur_billdate.setText(purchasingBean.billdate);
        viewHolder.pur_company.setText(purchasingBean.company);
        viewHolder. pur_material.setText(purchasingBean.material);
        viewHolder.pur_employee.setText(purchasingBean.employee);
        viewHolder.pur_quantity.setText(purchasingBean.quantity);
        viewHolder.pur_sup_material_code.setText(purchasingBean.sup_material_code);
        viewHolder.pur_rquantity.setText(purchasingBean.rquantity);
        viewHolder.pur_iquantity.setText(purchasingBean.iquantity);

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.pur_customcode)
        TextView pur_customcode;

        @BindView(R.id.pur_billdate)
        TextView pur_billdate;

        @BindView(R.id.pur_company)
        TextView pur_company;


        @BindView(R.id.pur_material)
        TextView pur_material;

        @BindView(R.id.pur_employee)
        TextView pur_employee;

        @BindView(R.id.pur_quantity)
        TextView pur_quantity;

        @BindView(R.id.pur_sup_material_code)
        TextView pur_sup_material_code;

        @BindView(R.id.pur_rquantity)
        TextView pur_rquantity;

        @BindView(R.id.pur_iquantity)
        TextView pur_iquantity;


        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
