package com.example.nrbzms17.ui.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.nrbzms17.R;
import com.example.nrbzms17.data.model.DyingBean;
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
public class DyingAdapter extends BaseAdapter {

    List<DyingBean.Data> listData = new ArrayList<>();

    public void refresh(List<DyingBean.Data> ls) {
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
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_dyeing_list, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final DyingBean.Data dyingBean = listData.get(position);
        viewHolder.dye_customcode.setText(dyingBean.customcode);
        viewHolder.dye_billdate.setText(dyingBean.billdate);
        viewHolder.dye_code.setText(dyingBean.code);
        viewHolder.dye_material.setText(dyingBean.material);
        viewHolder.dye_task_code.setText(dyingBean.taskcode);
        viewHolder.dye_employee.setText(dyingBean.tellas);
        viewHolder.dye_factory.setText(dyingBean.company);
        viewHolder.dye_color.setText(dyingBean.color);
        viewHolder.dye_quantity.setText(dyingBean.quantity_str);
        viewHolder.dye_rquantity.setText(dyingBean.receive_str);
        viewHolder.dye_iquantity.setText(dyingBean.instore_str);
        viewHolder.dye_status.setText(dyingBean.status);
        if (position % 2 == 1) {
            convertView.setBackgroundColor(Color.parseColor("#F5F5F5"));
        } else {
            convertView.setBackgroundColor(Color.parseColor("#FFFAFA"));
        }
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.dye_customcode)
        TextView dye_customcode;

        @BindView(R.id.dye_code)
        TextView dye_code;

        @BindView(R.id.dye_billdate)
        TextView dye_billdate;


        @BindView(R.id.dye_material)
        TextView dye_material;

        @BindView(R.id.dye_task_code)
        TextView dye_task_code;


        @BindView(R.id.dye_employee)
        TextView dye_employee;

        @BindView(R.id.dye_factory)
        TextView dye_factory;

        @BindView(R.id.dye_color)
        TextView dye_color;

        @BindView(R.id.dye_quantity)
        TextView dye_quantity;

        @BindView(R.id.dye_rquantity)
        TextView dye_rquantity;

        @BindView(R.id.dye_iquantity)
        TextView dye_iquantity;

        @BindView(R.id.dye_status)
        TextView dye_status;



        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
