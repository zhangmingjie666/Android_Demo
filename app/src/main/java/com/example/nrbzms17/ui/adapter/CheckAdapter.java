package com.example.nrbzms17.ui.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.nrbzms17.R;
import com.example.nrbzms17.data.model.CheckBean;
import com.example.nrbzms17.data.model.OrderListBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author MJ@ZHANG
 * @package: com.example.nrbzms17.ui.adapter
 * @filename CheckAdapter
 * @date on 2018/8/15 15:39
 * @descibe TODO
 * @email zhangmingjie@huansi.net
 */
public class CheckAdapter extends BaseAdapter {

    List<CheckBean> listData = new ArrayList<>();

    public void refresh(List<CheckBean> ls) {
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
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_check_detail, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final CheckBean checkbean = listData.get(position);
        viewHolder.che_barcode.setText(checkbean.barcode);
        viewHolder.che_color.setText(checkbean.color_name);
        viewHolder.che_reel.setText(checkbean.reel);
        viewHolder. che_grade.setText(checkbean.grade);
        viewHolder.che_quantity_string.setText(checkbean.quantity_string);
        viewHolder.che_material.setText(checkbean.material_name);
        viewHolder.che_lot.setText(checkbean.lot);
        viewHolder.che_depot.setText(checkbean.depot_name);
        if (position % 2 == 1) {
            convertView.setBackgroundColor(Color.parseColor("#F5F5F5"));
        } else {
            convertView.setBackgroundColor(Color.parseColor("#FFFAFA"));
        }
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.che_barcode)
        TextView che_barcode;

        @BindView(R.id.che_color)
        TextView che_color;

        @BindView(R.id.che_reel)
        TextView che_reel;

        @BindView(R.id.che_grade)
        TextView che_grade;

        @BindView(R.id.che_quantity_string)
        TextView che_quantity_string;

        @BindView(R.id.che_material)
        TextView che_material;

        @BindView(R.id.che_lot)
        TextView che_lot;

        @BindView(R.id.che_depot)
        TextView che_depot;


        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
