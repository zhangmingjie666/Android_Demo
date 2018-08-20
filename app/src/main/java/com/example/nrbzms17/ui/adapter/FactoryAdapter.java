//package com.example.nrbzms17.ui.adapter;
//
//import android.graphics.Color;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.Filter;
//import android.widget.Filterable;
//import android.widget.TextView;
//
//import com.example.nrbzms17.R;
//import com.example.nrbzms17.data.model.FactoryBean;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//
///**
// * @author MJ@ZHANG
// * @package: com.example.nrbzms17.ui.adapter
// * @filename FactoryAdapter
// * @date on 2018/8/18 16:43
// * @descibe TODO
// * @email zhangmingjie@huansi.net
// */
//public class FactoryAdapter extends BaseAdapter implements Filterable {
//    List<FactoryBean> listData = new ArrayList<>();
//    private Filter filter;
//    public void refresh(List<FactoryBean> ls) {
//        if (ls == null) {
//            listData = new ArrayList<>();
//        } else {
//            listData = ls;
//        }
//        notifyDataSetChanged();
//    }
//
//    @Override
//    public int getCount() {
//        return listData.size();
//    }
//
//    @Override
//    public Object getItem(int i) {
//        return listData.get(i);
//    }
//
//    @Override
//    public long getItemId(int i) {
//        return 0;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup viewGroup) {
//
//        ViewHolder viewHolder;
//
//        if (convertView == null) {
//
//            convertView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_factory_detail, null);
//
//            viewHolder = new ViewHolder(convertView);
//
//            convertView.setTag(viewHolder);
//
//        } else {
//            viewHolder = (ViewHolder) convertView.getTag();
//
//        }
//        FactoryBean managerBean = listData.get(position);
//
//        viewHolder.factory.setText(managerBean.name);
//        if(position % 2==0){
//            convertView.setBackgroundColor(Color.parseColor("#F5F5DC"));
//        }else{
//            convertView.setBackgroundColor(Color.parseColor("#96CDCD"));
//        }
//
//
//        return convertView;
//    }
//
//
//
//    static class ViewHolder {
//        @BindView(R.id.factory)
//        TextView factory;
//
//        ViewHolder(View view) {
//            ButterKnife.bind(this, view);
//        }
//    }
//}