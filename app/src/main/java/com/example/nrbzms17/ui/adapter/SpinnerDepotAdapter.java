package com.example.nrbzms17.ui.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.nrbzms17.R;

import com.example.nrbzms17.data.model.DepotBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author MJ@ZHANG
 * @package: com.example.nrbzms17.ui.adapter
 * @filename SpinnerDepotAdapter
 * @date on 2018/8/11 10:37
 * @descibe TODO
 * @email zhangmingjie@huansi.net
 */
public class SpinnerDepotAdapter extends BaseAdapter {
    List<DepotBean> listData = new ArrayList<>();

    public void refresh(List<DepotBean> ls) {
        if (ls == null) {
            ls = new ArrayList<>();
        }
        listData = ls;
        listData.add(0, new DepotBean("-1", "转入仓库"));
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
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        ViewHolder viewHolder;

        if (convertView == null) {

            convertView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.spinner_item, null);

            viewHolder = new ViewHolder(convertView);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();

        }
        DepotBean managerBean = listData.get(position);


        viewHolder.txtvName.setText(managerBean.name);
        if (position % 2 == 1) {
            convertView.setBackgroundColor(Color.parseColor("#DCDCDC"));
        } else {
            convertView.setBackgroundColor(Color.parseColor("#FFFAFA"));
        }

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.txtv_Name)
        TextView txtvName;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
