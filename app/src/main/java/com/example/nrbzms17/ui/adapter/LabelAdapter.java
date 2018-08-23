package com.example.nrbzms17.ui.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.nrbzms17.R;
import com.example.nrbzms17.data.model.LabelBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author MJ@ZHANG
 * @package: com.example.nrbzms17.ui.adapter
 * @filename LabelAdapter
 * @date on 2018/8/23 11:54
 * @descibe TODO
 * @email zhangmingjie@huansi.net
 */
public class LabelAdapter extends BaseAdapter {
    List<LabelBean> listData = new ArrayList<>();

    public void refresh(List<LabelBean> ls) {
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
    public Object getItem(int i) {
        return listData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        ViewHolder viewHolder;

        if (convertView == null) {

            convertView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_craft_detail, null);

            viewHolder = new ViewHolder(convertView);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();

        }
        LabelBean managerBean = listData.get(position);

        viewHolder.craft.setText(managerBean.name);

        if (position % 2 == 1) {
            convertView.setBackgroundColor(Color.parseColor("#DCDCDC"));
        } else {
            convertView.setBackgroundColor(Color.parseColor("#FFFAFA"));
        }
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.craft)
        TextView craft;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

