package com.example.nrbzms17.ui.Fragment;


import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.codescan.zxing.activity.CaptureActivity;
import com.example.nrbzms17.R;
import com.example.nrbzms17.data.model.MenuBean;
import com.example.nrbzms17.ui.activity.AllocationActivity;
import com.example.nrbzms17.ui.activity.CheckActivity;
import com.example.nrbzms17.ui.activity.EchartsActivity;
import com.example.nrbzms17.ui.activity.InspectListActivity;
import com.example.nrbzms17.ui.activity.OrderListActivity;
import com.example.nrbzms17.ui.activity.PurchaseListActivity;
import com.example.nrbzms17.ui.activity.SaleListActivity;
import com.example.nrbzms17.ui.activity.ShoppingActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainFragment extends Fragment {


    private int menuIcons[] = {
            R.drawable.order,

            R.drawable.purchase,

            R.drawable.inspect,

            R.drawable.sale,

            R.drawable.check,

            R.drawable.allocation,

            R.drawable.sale,

            R.drawable.analyse,

            R.drawable.shouhuo
    };

    private String menuNames[] = {"订单", "采购", "检验", "销售", "盘点", "调拨", "检验", "分析","采购收货"};

//    private List<MenuBean> list = new ArrayList<>();


    Button order;

    Button purchase;

    Button inspect;

    Button sale;

    Button check;

    Button allocation;

    Button sale1;

    Button echarts;

    TextView titleview;

    TextView secondview;

    TextView thirdview;

    TextView fourthview;

    Button purchasing;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, null);


//        TextView order = view.findViewById(R.id.order);
//        TextView purchase = view.findViewById(R.id.purchase);
//        TextView inspect = view.findViewById(R.id.inspect);
//        TextView sale = view.findViewById(R.id.sale);
//        TextView check = view.findViewById(R.id.check);
//        TextView echarts = view.findViewById(R.id.echarts);
//        TextView allocation = view.findViewById(R.id.allocation);
//        //开启订单
//        order.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), OrderListActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        //开启采购
//        purchase.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), PurchaseListActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        //开启检验
//        inspect.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), InspectListActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        //开启销售
//        sale.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), SaleListActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        //开启盘点
//        check.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), CheckActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        //开启调拨
//        allocation.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(),AllocationActivity.class);
//                startActivity(intent);
//            }
//        });
//
//
//        //开启分析
//        echarts.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), EchartsActivity.class);
//                startActivity(intent);
//            }
//        });
        LinearLayout rootLayout = (LinearLayout) view.findViewById(R.id.root_layout);
        LinearLayout titleLayout = new LinearLayout(getActivity());

        LinearLayout secondLayout = new LinearLayout(getActivity());

        LinearLayout linearrLayout = new LinearLayout(getActivity());

        LinearLayout thirdrLayout = new LinearLayout(getActivity());

        LinearLayout SecondbutrLayout = new LinearLayout(getActivity());

        LinearLayout purchaseLayout = new LinearLayout(getActivity());

        LinearLayout shouhuoLayout = new LinearLayout(getActivity());

        order = new Button(getActivity());
        purchase = new Button(getActivity());
        inspect = new Button(getActivity());
        sale = new Button(getActivity());
        check = new Button(getActivity());
        allocation = new Button(getActivity());
        sale1 = new Button(getActivity());
        echarts = new Button(getActivity());
        purchasing = new Button(getActivity());

        titleview = new TextView(getActivity());
        titleview.setText("工作区");
        titleview.setGravity(Gravity.CENTER);
        titleview.setTextSize(25);

        secondview = new TextView(getActivity());
        secondview.setText("审核");
        secondview.setTextSize(20);

        sale.setText(menuNames[3]);
        Drawable top = getResources().getDrawable(menuIcons[3]);
        sale.setCompoundDrawablesWithIntrinsicBounds(null, top, null, null);
        sale.setBackgroundColor(getResources().getColor(R.color.white));

        inspect.setText(menuNames[2]);
        top = getResources().getDrawable(menuIcons[2]);
        inspect.setCompoundDrawablesWithIntrinsicBounds(null, top, null, null);
        inspect.setBackgroundColor(getResources().getColor(R.color.white));

        purchase.setText(menuNames[1]);
        top = getResources().getDrawable(menuIcons[1]);
        purchase.setCompoundDrawablesWithIntrinsicBounds(null, top, null, null);
        purchase.setBackgroundColor(getResources().getColor(R.color.white));

        order.setText(menuNames[0]);
        top = getResources().getDrawable(menuIcons[0]);
        order.setCompoundDrawablesWithIntrinsicBounds(null, top, null, null);
        order.setBackgroundColor(getResources().getColor(R.color.white));

        thirdview = new TextView(getActivity());
        thirdview = new TextView(getActivity());
        thirdview.setText("仓库");
        thirdview.setTextSize(20);


        echarts.setText(menuNames[7]);
        top = getResources().getDrawable(menuIcons[7]);
        echarts.setCompoundDrawablesWithIntrinsicBounds(null, top, null, null);
        echarts.setBackgroundColor(getResources().getColor(R.color.white));

        sale1.setText(menuNames[6]);
        top = getResources().getDrawable(menuIcons[6]);
        sale1.setCompoundDrawablesWithIntrinsicBounds(null, top, null, null);
        sale1.setBackgroundColor(getResources().getColor(R.color.white));

        allocation.setText(menuNames[5]);
        top = getResources().getDrawable(menuIcons[5]);
        allocation.setCompoundDrawablesWithIntrinsicBounds(null, top, null, null);
        allocation.setBackgroundColor(getResources().getColor(R.color.white));

        check.setText(menuNames[4]);
        top = getResources().getDrawable(menuIcons[4]);
        check.setCompoundDrawablesWithIntrinsicBounds(null, top, null, null);
        check.setBackgroundColor(getResources().getColor(R.color.white));

        fourthview = new TextView(getActivity());
        fourthview.setText("收货");
        fourthview.setTextSize(20);

        purchasing.setText(menuNames[8]);
        top = getResources().getDrawable(menuIcons[8]);
        purchasing.setCompoundDrawablesWithIntrinsicBounds(null, top, null, null);
        purchasing.setBackgroundColor(getResources().getColor(R.color.white));


        //root控件
        LinearLayout.LayoutParams relativeLayout_parent_params
                = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        //标题
        LinearLayout.LayoutParams title
                = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        //审核标题
        LinearLayout.LayoutParams sectitle
                = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        sectitle.setMargins(5, 0, 0, 0);

        //四个按钮
        LinearLayout.LayoutParams button_parent_params
                = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        button_parent_params.setMargins(0,1,0,0);


        //仓库标题
        LinearLayout.LayoutParams thirdtitle
                = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        thirdtitle.setMargins(5, 0, 0, 0);

        //四个按钮
        LinearLayout.LayoutParams button_second
                = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        button_second.setMargins(0,1,0,0);

        //收货标题
        LinearLayout.LayoutParams fourtitle
                = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        fourtitle.setMargins(5, 0, 0, 0);

        //目前一个按钮
        LinearLayout.LayoutParams button_puchasing
                = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        button_puchasing.setMargins(0,1,0,0);


        titleLayout.addView(titleview, title);
        secondLayout.addView(secondview, sectitle);
        linearrLayout.addView(order, button_parent_params);
        linearrLayout.addView(purchase, button_parent_params);
        linearrLayout.addView(inspect, button_parent_params);
        linearrLayout.addView(sale, button_parent_params);
        thirdrLayout.addView(thirdview, thirdtitle);
        SecondbutrLayout.addView(check, button_second);
        SecondbutrLayout.addView(allocation, button_second);
        SecondbutrLayout.addView(sale1, button_second);
        SecondbutrLayout.addView(echarts, button_second);
        purchaseLayout.addView(fourthview, fourtitle);
        shouhuoLayout.addView(purchasing, button_puchasing);


        rootLayout.addView(titleLayout, title);
        rootLayout.addView(secondLayout, sectitle);
        rootLayout.addView(linearrLayout, button_parent_params);
        rootLayout.addView(thirdrLayout, thirdtitle);
        rootLayout.addView(SecondbutrLayout, button_second);
        rootLayout.addView(purchaseLayout, fourtitle);
        rootLayout.addView(shouhuoLayout, button_puchasing);


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
                Intent intent = new Intent(getActivity(), InspectListActivity.class);
                startActivity(intent);
            }
        });

        //开启销售
        sale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SaleListActivity.class);
                startActivity(intent);
            }
        });

        //开启盘点
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CheckActivity.class);
                startActivity(intent);
            }
        });

        //开启调拨
        allocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AllocationActivity.class);
                startActivity(intent);
            }
        });


        //开启分析
        echarts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EchartsActivity.class);
                startActivity(intent);
            }
        });

        //开启采购收货
        purchasing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ShoppingActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }


}