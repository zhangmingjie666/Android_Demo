package com.example.nrbzms17.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.nrbzms17.R;


/**
 * 透明Activity，用于检查版本更新
 */
public class UpgradeActivity extends BaseActivity {

    boolean isShowDialog = false;

    public static void startInstance(Context context) {
        Intent intent = new Intent(context, UpgradeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_upgrade);
        overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
    }
}
