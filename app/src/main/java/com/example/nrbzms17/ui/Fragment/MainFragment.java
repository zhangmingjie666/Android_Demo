package com.example.nrbzms17.ui.Fragment;


import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.example.nrbzms17.R;
import com.example.nrbzms17.ui.activity.InspectActivity;
import com.example.nrbzms17.ui.activity.OrderListActivity;
import com.example.nrbzms17.ui.activity.PurchaseListActivity;
import com.example.nrbzms17.ui.activity.SettingActivity;

public class MainFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, null);
        TextView order = view.findViewById(R.id.order);
        TextView purchase = view.findViewById(R.id.purchase);
        TextView inspect = view.findViewById(R.id.inspect);

        //开启订单
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), OrderListActivity.class);
                startActivity(intent);
            }
        });

        //开启采购
        purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PurchaseListActivity.class);
                startActivity(intent);
            }
        });

        //开启检验
        inspect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), InspectActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}