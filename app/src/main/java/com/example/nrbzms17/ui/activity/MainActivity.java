package com.example.nrbzms17.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import android.widget.TextView;
import android.widget.Toast;

import com.example.nrbzms17.R;
import com.example.nrbzms17.ui.Fragment.FindFragment;
import com.example.nrbzms17.ui.Fragment.MainFragment;
import com.example.nrbzms17.ui.Fragment.PersonFragment;
import com.example.nrbzms17.ui.Fragment.SettingFragment;

public class MainActivity extends FragmentActivity {

    private MainFragment mainFragment;
    private FindFragment findFragment;
    private SettingFragment settingFragment;
    private PersonFragment personFragment;

    private int currentId = R.id.nr_function;// 当前选中id,默认是主页

    private TextView tabFunction, tabFind, tabPerson, tabSetting;//底部四个TextView

    private long lastBack = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabFunction = (TextView) findViewById(R.id.nr_function);
        tabFunction.setSelected(true);//首页默认选中
        tabFind = (TextView) findViewById(R.id.nr_find);
        tabPerson = (TextView) findViewById(R.id.nr_person);
        tabSetting = (TextView) findViewById(R.id.nr_setting);

        /**
         * 默认加载首页
         */
        mainFragment = new MainFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, mainFragment).commit();

        tabFunction.setOnClickListener(tabClickListener);
        tabFind.setOnClickListener(tabClickListener);
        tabPerson.setOnClickListener(tabClickListener);
        tabSetting.setOnClickListener(tabClickListener);
    }


    private View.OnClickListener tabClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() != currentId) {//如果当前选中跟上次选中的一样,不需要处理
                changeSelect(v.getId());//改变图标跟文字颜色的选中
                changeFragment(v.getId());//fragment的切换
                currentId = v.getId();//设置选中id
            }
        }
    };

    /**
     * 改变fragment的显示
     *
     * @param resId
     */
    private void changeFragment(int resId) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();//开启一个Fragment事务

        hideFragments(transaction);//隐藏所有fragment
        if(resId==R.id.nr_function){//主页
            if(mainFragment==null){//如果为空先添加进来.不为空直接显示
                mainFragment = new MainFragment();
                transaction.add(R.id.fragment_container,mainFragment);
            }else {
                transaction.show(mainFragment);
            }
        }else if(resId==R.id.nr_setting){//动态
            if(settingFragment==null){
                settingFragment = new SettingFragment();
                transaction.add(R.id.fragment_container,settingFragment);
            }else {
                transaction.show(settingFragment);
            }
        }else if(resId==R.id.nr_find){//消息中心
            if(findFragment==null){
                findFragment = new FindFragment();
                transaction.add(R.id.fragment_container,findFragment);
            }else {
                transaction.show(findFragment);
            }
        }else if(resId==R.id.nr_person){//我
            if(personFragment==null){
                personFragment = new PersonFragment();
                transaction.add(R.id.fragment_container,personFragment);
            }else {
                transaction.show(personFragment);
            }
        }
        transaction.commit();//一定要记得提交事务
    }

    /**
     * 显示之前隐藏所有fragment
     * @param transaction
     */
    private void hideFragments(FragmentTransaction transaction){
        if (mainFragment != null)//不为空才隐藏,如果不判断第一次会有空指针异常
            transaction.hide(mainFragment);
        if (findFragment != null)
            transaction.hide(findFragment);
        if (settingFragment != null)
            transaction.hide(settingFragment);
        if (personFragment != null)
            transaction.hide(personFragment);
    }

    /**
     * 改变TextView选中颜色
     * @param resId
     */
    private void changeSelect(int resId) {
        tabFunction.setSelected(false);
        tabFind.setSelected(false);
        tabPerson.setSelected(false);
        tabSetting.setSelected(false);

        switch (resId) {
            case R.id.nr_function:
                tabFunction.setSelected(true);
                break;
            case R.id.nr_find:
                tabFind.setSelected(true);
                break;
            case R.id.nr_setting:
                tabSetting.setSelected(true);
                break;
            case R.id.nr_person:
                tabPerson.setSelected(true);
                break;
        }
    }
    //返回键提示
    @Override
    public void onBackPressed() {
        if (lastBack == 0 || System.currentTimeMillis() - lastBack > 2000) {
            Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            lastBack = System.currentTimeMillis();
            return;
        }
        super.onBackPressed();

    }
}


