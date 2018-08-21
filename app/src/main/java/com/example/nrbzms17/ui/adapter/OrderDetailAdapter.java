package com.example.nrbzms17.ui.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.nrbzms17.R;
import com.example.nrbzms17.data.model.OrderListBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.BindView;



public class OrderDetailAdapter extends BaseAdapter {

    List<OrderListBean.Data> listData = new ArrayList<>();

    public void refresh(List<OrderListBean.Data> ls) {
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
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_order_detail_view, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final OrderListBean.Data data = listData.get(position);
        viewHolder.txtv_color.setText(data.color_name);
        viewHolder.txtv_craft.setText(data.craft_name);
        viewHolder.txtv_price.setText(data.price);
        viewHolder.txtv_amount.setText(data.amount);
        viewHolder.txtv_quantity.setText(data.quantity);

        if (position % 2 == 1) {
            convertView.setBackgroundColor(Color.parseColor("#F5F5F5"));
        } else {
            convertView.setBackgroundColor(Color.parseColor("#FFFAFA"));
        }
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.txtv_color)
        TextView txtv_color;

        @BindView(R.id.txtv_craft)
        TextView txtv_craft;

        @BindView(R.id.txtv_price)
        TextView txtv_price;

        @BindView(R.id.txtv_amount)
        TextView txtv_amount;

        @BindView(R.id.txtv_quantity)
        TextView txtv_quantity;


        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
