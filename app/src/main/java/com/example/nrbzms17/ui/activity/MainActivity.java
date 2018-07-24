package com.example.nrbzms17.ui.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nrbzms17.R;
import com.example.nrbzms17.ui.Fragment.FirstFragment;

        public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView topBar;
    private TextView tabDeal;
    private TextView tabPoi;
    private TextView tabMore;
    private TextView tabUser;



    private FrameLayout ly_content;

    private FirstFragment f1, f2, f3, f4;
    private FragmentManager fragmentManager;

    private long lastBack = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);

        android.support.v7.app.ActionBar actionbar = getSupportActionBar();

        if (actionbar != null) {

            actionbar.hide();
        }

        bindView();

        initmenu();
    }

    private void initmenu() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        hideAllFragment(transaction);

        if (f1 == null) {
//                    f1 = new FirstFragment("功能");
//                    transaction.add(R.id.fragment_container,f1);
            selected();
            tabDeal.setSelected(true);
//代码优化失败
//            textView.setOnClickListener(new View.OnClickListener() {
//                Intent intent = null;
//
//                @Override
//                public void onClick(View view) {
//                    int id = view.getId();
//                    switch (id) {
//                        case R.id.order:
//                            intent = new Intent(MainActivity.this, OrderListActivity.class);
//                            startActivity(intent);
//                            break;
//                        case R.id.purchase:
//                            intent = new Intent(MainActivity.this, PurchaseActivity.class);
//                            startActivity(intent);
//                            break;
//                    }
//
//                }
//            });
                //启动订单
                TextView textView = (TextView) findViewById(R.id.order);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MainActivity.this, OrderListActivity.class);
                        startActivity(intent);
                    }
                });

                //启动采购
                TextView purchase = (TextView)findViewById(R.id.purchase);
                purchase.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MainActivity.this, PurchaseActivity.class);
                        startActivity(intent);
                    }
                });


//            TextView purchase = (TextView)findViewById(R.id.purchase);
//            purchase.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent();
//                    intent.setClass(MainActivity.this, PurchaseActivity.class);
//                    startActivity(intent);
//                    //设置切换动画，从右边进入，左边退出
//                    overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
//                }
//            });
            //仓库盘点
                TextView check =(TextView) findViewById(R.id.check);
                check.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MainActivity.this,CheckActivity.class);
                        startActivity(intent);
                    }
                });

//                    break;
        }
//                } else {
//                    transaction.show(f1);
//            }

        transaction.commit();
    }


    //UI组件初始化与事件绑定
    private void bindView() {
//        topBar = (TextView)this.findViewById(R.id.txt_top);
        tabDeal = (TextView) this.findViewById(R.id.txt_deal);
        tabPoi = (TextView) this.findViewById(R.id.txt_poi);
        tabUser = (TextView) this.findViewById(R.id.txt_user);
        tabMore = (TextView) this.findViewById(R.id.txt_more);
        ly_content = (FrameLayout) findViewById(R.id.fragment_container);

        tabDeal.setOnClickListener(this);
        tabMore.setOnClickListener(this);
        tabUser.setOnClickListener(this);
        tabPoi.setOnClickListener(this);

    }

    //重置所有文本的选中状态
    public void selected() {
        tabDeal.setSelected(false);
        tabMore.setSelected(false);
        tabPoi.setSelected(false);
        tabUser.setSelected(false);
    }

    //隐藏所有Fragment
    public void hideAllFragment(FragmentTransaction transaction) {
        if (f1 != null) {
            transaction.hide(f1);
        }
        if (f2 != null) {
            transaction.hide(f2);
        }
        if (f3 != null) {
            transaction.hide(f3);
        }
        if (f4 != null) {
            transaction.hide(f4);
        }
    }

    //返回键提示
    @Override
    public void onBackPressed() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//        builder.setMessage("确认退出吗?");
//        builder.setTitle("提示");
//        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//                    MainActivity.this.finish();
//            }
//        });
//        builder.setNegativeButton("取消", null);
//        builder.create().show();

        if (lastBack == 0 || System.currentTimeMillis() - lastBack > 2000) {
            Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            lastBack = System.currentTimeMillis();
            return;
        }
        super.onBackPressed();

    }


    //    @Override
    public void onClick(View v) {

        Bundle bundle = new Bundle();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        hideAllFragment(transaction);
        switch (v.getId()) {
            case R.id.txt_deal:
                selected();
                tabDeal.setSelected(true);
                if (f1 == null) {
//                    f1 = new FirstFragment("功能");
//                    transaction.add(R.id.fragment_container,f1);

//                    initMenu();
//                    Button button = findViewById(R.id.order);
//                    button.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            Intent intent = new Intent(MainActivity.this,testactivity.class);
//                            startActivity(intent);
//                        }
//                    });

//                    break;
                } else {
                    transaction.show(f1);
                }
                break;

            case R.id.txt_more:
                selected();
                tabMore.setSelected(true);
                if (f2 == null) {

                    f2 = new FirstFragment("更多");
                    transaction.add(R.id.fragment_container, f2);
                } else {
//                    transaction.show(f2);
                }
                break;

            case R.id.txt_poi:
                selected();
                tabPoi.setSelected(true);
                if (f3 == null) {
                    f3 = new FirstFragment("发现");
                    transaction.add(R.id.fragment_container, f3);
                } else {
                    transaction.show(f3);
                }
                break;

            case R.id.txt_user:
                selected();
                tabUser.setSelected(true);
                if (f4 == null) {
                    f4 = new FirstFragment("我的");
                    transaction.add(R.id.fragment_container, f4);
                } else {
                    transaction.show(f4);
                }
                break;
        }

        transaction.commit();
    }


}


