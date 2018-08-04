package com.example.nrbzms17.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.nrbzms17.R;
import com.example.nrbzms17.Utils.APKVersionCodeUtils;

public class UpdateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        TextView edit_text = findViewById(R.id.edit_text);
        String versionCode = APKVersionCodeUtils.getVersionCode(this) + "";
        String versionName = APKVersionCodeUtils.getVerName(this);
        edit_text.setText(versionName);
    }
}
