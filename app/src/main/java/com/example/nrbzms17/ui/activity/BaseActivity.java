package com.example.nrbzms17.ui.activity;

import android.app.Activity;
import android.app.usage.UsageEvents;
import android.os.Bundle;

//import com.joe.app.baseutil.Event;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by Joe on 2016/6/7.
 * Email-joe_zong@163.com
 */
public class BaseActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);

        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

        ActivityCollector.removeActivity(this);
    }

    @Subscribe
    public void OnEvent(UsageEvents.Event event){

    }

}
