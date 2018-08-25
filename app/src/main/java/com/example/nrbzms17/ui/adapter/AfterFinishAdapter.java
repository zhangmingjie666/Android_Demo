package com.example.nrbzms17.ui.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.nrbzms17.R;
import com.example.nrbzms17.data.model.DyingBean;

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
public class AfterFinishAdapter extends BaseAdapter {

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
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_after_finish_list, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final DyingBean.Data dyingBean = listData.get(position);
        viewHolder.aft_customcode.setText(dyingBean.customcode);
        viewHolder.aft_billdate.setText(dyingBean.billdate);
        viewHolder.aft_code.setText(dyingBean.code);
        viewHolder.aft_material.setText(dyingBean.material);
        viewHolder.aft_task_code.setText(dyingBean.taskcode);
        viewHolder.aft_employee.setText(dyingBean.tellas);
        viewHolder.aft_factory.setText(dyingBean.company);
        viewHolder.aft_color.setText(dyingBean.color);
        viewHolder.aft_quantity.setText(dyingBean.quantity_str);
        viewHolder.aft_rquantity.setText(dyingBean.receive_str);
        viewHolder.aft_iquantity.setText(dyingBean.instore_str);
        viewHolder.aft_status.setText(dyingBean.status);
        if (position % 2 == 1) {
            convertView.setBackgroundColor(Color.parseColor("#F5F5F5"));
        } else {
            convertView.setBackgroundColor(Color.parseColor("#FFFAFA"));
        }
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.aft_customcode)
        TextView aft_customcode;

        @BindView(R.id.aft_code)
        TextView aft_code;

        @BindView(R.id.aft_billdate)
        TextView aft_billdate;


        @BindView(R.id.aft_factory)
        TextView aft_factory;

        @BindView(R.id.aft_color)
        TextView aft_color;


        @BindView(R.id.aft_task_code)
        TextView aft_task_code;

        @BindView(R.id.aft_employee)
        TextView aft_employee;

        @BindView(R.id.aft_material)
        TextView aft_material;

        @BindView(R.id.aft_quantity)
        TextView aft_quantity;

        @BindView(R.id.aft_rquantity)
        TextView aft_rquantity;

        @BindView(R.id.aft_iquantity)
        TextView aft_iquantity;

        @BindView(R.id.aft_status)
        TextView aft_status;



        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
