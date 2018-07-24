package com.example.nrbzms17.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.nrbzms17.R;
import com.example.nrbzms17.data.model.PurchaseBean;

public class PurchaseActivity extends AppCompatActivity {

    Button purSearch;

    PurchaseBean purchaseBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_list);

        initview();
    }

    public void initview() {
        //返回
        purSearch = findViewById(R.id.purSearch);
        purSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });




    }
}
