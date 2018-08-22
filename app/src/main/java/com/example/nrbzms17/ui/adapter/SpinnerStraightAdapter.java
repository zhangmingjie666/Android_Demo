package com.example.nrbzms17.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.nrbzms17.R;
import com.example.nrbzms17.data.model.StatusBean;
import com.example.nrbzms17.data.model.StraightBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author MJ@ZHANG
 * @package: com.example.nrbzms17.ui.adapter
 * @filename SpinnerStraightAdapter
 * @date on 2018/8/22 18:12
 * @descibe TODO
 * @email zhangmingjie@huansi.net
 */
public class SpinnerStraightAdapter extends BaseAdapter {
    List<StraightBean> listData = new ArrayList<>();

    public void refresh(List<StraightBean> ls) {
        if (ls == null) {
            ls = new ArrayList<>();
        }
        listData = ls;
        listData.add(0, new StraightBean("-1", "请选择"));
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

            convertView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.spinner_item, null);

            viewHolder = new ViewHolder(convertView);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();

        }
        StraightBean managerBean = listData.get(position);



        viewHolder.txtvName.setText(managerBean.name);

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
