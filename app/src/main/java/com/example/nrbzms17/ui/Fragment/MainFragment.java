package com.example.nrbzms17.ui.Fragment;



import android.content.Context;
import android.content.Intent;

import android.graphics.drawable.Drawable;
import android.os.Bundle;


import android.view.Gravity;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;

import android.widget.Button;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.donkingliang.banner.CustomBanner;
import com.example.nrbzms17.R;

import com.example.nrbzms17.ui.activity.AfterFinishActivity;
import com.example.nrbzms17.ui.activity.AllocationActivity;
import com.example.nrbzms17.ui.activity.CheckActivity;
import com.example.nrbzms17.ui.activity.DyeingActivity;
import com.example.nrbzms17.ui.activity.EchartsActivity;
import com.example.nrbzms17.ui.activity.InspectListActivity;

import com.example.nrbzms17.ui.activity.OrderListActivity;
import com.example.nrbzms17.ui.activity.PurchaseListActivity;
import com.example.nrbzms17.ui.activity.SaleListActivity;
import com.example.nrbzms17.ui.activity.ShoppingActivity;


import java.util.ArrayList;


public class MainFragment extends Fragment{


    private int menuIcons[] = {
            R.drawable.order,

            R.drawable.purchase,

            R.drawable.inspect,

            R.drawable.sale,

            R.drawable.check,

            R.drawable.allocation,

            R.drawable.sale,

            R.drawable.analyse,

            R.drawable.shouhuo,

            R.drawable.ranchang,

            R.drawable.houzhengli,

            R.drawable.buzhidao,

            R.drawable.zhuzhuang,

            R.drawable.pie
    };

    private String menuNames[] = {"订单", "采购", "检验", "销售", "盘点", "调拨", "检验", "分析","采购收货","染厂收货","后整收货","定制","柱状图","饼状图"};

//    private List<MenuBean> list = new ArrayList<>();


    Button order;

    Button purchase;

    Button inspect;

    Button sale;

    Button check;

    Button allocation;

    Button sale1;

    Button no;

    Button echarts;

    TextView titleview;

    TextView secondview;

    TextView thirdview;

    TextView fourthview;

    Button purchasing;

    Button dye_works;

    Button houzhengli;

    Button buzhidao;

    TextView fifthview;

    Button zhuzhuang;

    Button pie;

    Button xxoo;

    private CustomBanner<String> mBanner;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, null);

        mBanner = (CustomBanner) view.findViewById(R.id.banner);

        ArrayList<String> images = new ArrayList<>();
        images.add("http://47.92.82.226/APP/appPhoto/1.jpg");
        images.add("http://47.92.82.226/APP/appPhoto/2.jpg");
        images.add("http://47.92.82.226/APP/appPhoto/3.jpg");
//        images.add("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2863927798,667335035&fm=23&gp=0.jpg");
//        images.add("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3885596348,1190704919&fm=23&gp=0.jpg");
//        images.add("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1597254274,1405139366&fm=23&gp=0.jpg");
//        images.add("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3513269361,2662598514&fm=23&gp=0.jpg");

        setBean(images);
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

        LinearLayout echartsLayout = new LinearLayout(getActivity());

        LinearLayout EarLayout = new LinearLayout(getActivity());



        titleLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

        order = new Button(getActivity());
        purchase = new Button(getActivity());
        inspect = new Button(getActivity());
        sale = new Button(getActivity());
        check = new Button(getActivity());
        allocation = new Button(getActivity());
        sale1 = new Button(getActivity());
        no= new Button(getActivity());
        purchasing = new Button(getActivity());
        dye_works = new Button(getActivity());
        houzhengli = new Button(getActivity());
        buzhidao = new Button(getActivity());

        echarts = new Button(getActivity());
        zhuzhuang = new Button(getActivity());
        pie = new Button(getActivity());
        xxoo= new Button(getActivity());

        titleview = new TextView(getActivity());
        titleview.setText("工作区");
        titleview.setTextColor(getResources().getColor(R.color.white));
        titleview.setGravity(Gravity.CENTER);
        titleview.setTextSize(16);

        secondview = new TextView(getActivity());
        secondview.setText("审核");
        secondview.setTextSize(13);

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
        thirdview.setTextSize(13);

        no.setText(menuNames[10]);
        top = getResources().getDrawable(menuIcons[10]);
        no.setCompoundDrawablesWithIntrinsicBounds(null, top, null, null);
        no.setBackgroundColor(getResources().getColor(R.color.white));

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
        fourthview.setTextSize(13);

        purchasing.setText(menuNames[8]);
        top = getResources().getDrawable(menuIcons[8]);
        purchasing.setCompoundDrawablesWithIntrinsicBounds(null, top, null, null);
        purchasing.setBackgroundColor(getResources().getColor(R.color.white));

        dye_works.setText(menuNames[9]);
        top = getResources().getDrawable(menuIcons[9]);
        dye_works.setCompoundDrawablesWithIntrinsicBounds(null, top, null, null);
        dye_works.setBackgroundColor(getResources().getColor(R.color.white));

        houzhengli.setText(menuNames[10]);
        top = getResources().getDrawable(menuIcons[10]);
        houzhengli.setCompoundDrawablesWithIntrinsicBounds(null, top, null, null);
        houzhengli.setBackgroundColor(getResources().getColor(R.color.white));

        buzhidao.setText(menuNames[11]);
        top = getResources().getDrawable(menuIcons[11]);
        buzhidao.setCompoundDrawablesWithIntrinsicBounds(null, top, null, null);
        buzhidao.setBackgroundColor(getResources().getColor(R.color.white));

        fifthview = new TextView(getActivity());
        fifthview.setText("图表");
        fifthview.setTextSize(13);

        echarts.setText(menuNames[7]);
        top = getResources().getDrawable(menuIcons[7]);
        echarts.setCompoundDrawablesWithIntrinsicBounds(null, top, null, null);
        echarts.setBackgroundColor(getResources().getColor(R.color.white));

        zhuzhuang.setText(menuNames[12]);
        top = getResources().getDrawable(menuIcons[12]);
        zhuzhuang.setCompoundDrawablesWithIntrinsicBounds(null, top, null, null);
        zhuzhuang.setBackgroundColor(getResources().getColor(R.color.white));

        pie.setText(menuNames[13]);
        top = getResources().getDrawable(menuIcons[13]);
        pie.setCompoundDrawablesWithIntrinsicBounds(null, top, null, null);
        pie.setBackgroundColor(getResources().getColor(R.color.white));

        xxoo.setText(menuNames[13]);
        top = getResources().getDrawable(menuIcons[13]);
        xxoo.setCompoundDrawablesWithIntrinsicBounds(null, top, null, null);
        xxoo.setBackgroundColor(getResources().getColor(R.color.white));


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

        //第一行四个按钮
        LinearLayout.LayoutParams button_parent_params
                = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        button_parent_params.weight = 1.0f;


        //仓库标题
        LinearLayout.LayoutParams thirdtitle
                = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        thirdtitle.setMargins(5, 0, 0, 0);

        //第二行四个按钮
        LinearLayout.LayoutParams button_second
                = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        button_second.weight = 1.0f;

        //收货标题
        LinearLayout.LayoutParams fourtitle
                = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        fourtitle.setMargins(5, 0, 0, 0);

        //第三行四个按钮
        LinearLayout.LayoutParams button_puchasing
                = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        button_puchasing.weight =1.0f;

        //图表标题
        LinearLayout.LayoutParams fivetitle
                = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        fourtitle.setMargins(5, 0, 0, 0);



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
        SecondbutrLayout.addView(no, button_second);
        purchaseLayout.addView(fourthview, fourtitle);
        shouhuoLayout.addView(purchasing, button_puchasing);
        shouhuoLayout.addView(dye_works, button_puchasing);
        shouhuoLayout.addView(houzhengli, button_puchasing);
        shouhuoLayout.addView(buzhidao, button_puchasing);
        echartsLayout.addView(fifthview,fivetitle);
        EarLayout.addView(echarts, button_second);
        EarLayout.addView(zhuzhuang, button_second);
        EarLayout.addView(pie, button_second);
        EarLayout.addView(xxoo, button_second);


        rootLayout.addView(titleLayout);
        rootLayout.addView(secondLayout);
        rootLayout.addView(linearrLayout);
        rootLayout.addView(thirdrLayout);
        rootLayout.addView(SecondbutrLayout);
        rootLayout.addView(purchaseLayout);
        rootLayout.addView(shouhuoLayout);
        rootLayout.addView(echartsLayout);
        rootLayout.addView(EarLayout);


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

        //开启染厂收货
        dye_works.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DyeingActivity.class);
                startActivity(intent);
            }
        });

        //开启后整理收货
        houzhengli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AfterFinishActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
    //设置普通指示器
    private void setBean(final ArrayList<String> beans) {
        mBanner.setPages(new CustomBanner.ViewCreator<String>() {
            @Override
            public View createView(Context context, int position) {
                ImageView imageView = new ImageView(context);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                return imageView;
            }

            @Override
            public void updateUI(Context context, View view, int position, String entity) {
                Glide.with(context).load(entity).into((ImageView) view);
            }
        }, beans)
//                //设置指示器为普通指示器
//                .setIndicatorStyle(CustomBanner.IndicatorStyle.ORDINARY)
//                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
//                .setIndicatorRes(R.drawable.shape_point_select, R.drawable.shape_point_unselect)
//                //设置指示器的方向
//                .setIndicatorGravity(CustomBanner.IndicatorGravity.CENTER)
//                //设置指示器的指示点间隔
//                .setIndicatorInterval(20)
                //设置自动翻页
                .startTurning(5000);
    }

}