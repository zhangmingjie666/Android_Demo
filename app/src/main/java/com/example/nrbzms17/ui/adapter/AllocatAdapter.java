package com.example.nrbzms17.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.nrbzms17.R;
import com.example.nrbzms17.data.model.AllocationBean;
import com.example.nrbzms17.data.model.CheckBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author MJ@ZHANG
 * @package: com.example.nrbzms17.ui.adapter
 * @filename AllocatAdapter
 * @date on 2018/8/15 15:58
 * @descibe TODO
 * @email zhangmingjie@huansi.net
 */
public class AllocatAdapter extends BaseAdapter {

    List<AllocationBean> listData = new ArrayList<>();

    public void refresh(List<AllocationBean> ls) {
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
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_allocation_detail, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final AllocationBean allocationBean = listData.get(position);
        viewHolder.all_barcode.setText(allocationBean.barcode);
        viewHolder.all_delot.setText(allocationBean.depot);
        viewHolder.all_grade.setText(allocationBean.grade);
        viewHolder.all_material.setText(allocationBean.material);
        viewHolder.all_craft.setText(allocationBean.craft);
        viewHolder.all_color.setText(allocationBean.color);
        viewHolder.all_volum.setText(allocationBean.volume);
        viewHolder.all_quantity.setText(allocationBean.quantity);
        viewHolder.all_lot.setText(allocationBean.lot);
        viewHolder.all_reel.setText(allocationBean.reel);
        viewHolder.all_customcode.setText(allocationBean.customcode);

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.all_customcode)
        TextView all_customcode;

        @BindView(R.id.all_barcode)
        TextView all_barcode;

        @BindView(R.id.all_delot)
        TextView all_delot;

        @BindView(R.id.all_material)
        TextView all_material;

        @BindView(R.id.all_grade)
        TextView all_grade;

        @BindView(R.id.all_craft)
        TextView all_craft;

        @BindView(R.id.all_color)
        TextView all_color;

        @BindView(R.id.all_volum)
        TextView all_volum;

        @BindView(R.id.all_quantity)
        TextView all_quantity;

        @BindView(R.id.all_lot)
        TextView all_lot;

        @BindView(R.id.all_reel)
        TextView all_reel;


        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
