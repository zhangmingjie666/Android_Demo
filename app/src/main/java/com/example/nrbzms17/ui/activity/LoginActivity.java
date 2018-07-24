package com.example.nrbzms17.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.nrbzms17.R;
import com.example.nrbzms17.Utils.JSONUtils;
import com.example.nrbzms17.Utils.UIHelper;
import com.example.nrbzms17.data.Api;
import com.example.nrbzms17.data.listener.OnNetRequest;
import com.example.nrbzms17.data.model.UserBean;
import com.example.nrbzms17.data.model.UserBeanResponse;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class LoginActivity extends AppCompatActivity {

    private EditText et_name;
    private EditText et_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button login = (Button) findViewById(R.id.btn_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }
        });
//        init();
    }
//    public void init(){
//        if (firstLogin()) {
//            checkBox_password.setChecked(false);//取消记住密码的复选框
//            checkBox_login.setChecked(false);//取消自动登录的复选框
//        }
//        //判断是否记住密码
//        if (remenberPassword()) {
//            checkBox_password.setChecked(true);//勾选记住密码
//            setTextNameAndPassword();//把密码和账号输入到输入框中
//        } else {
//            setTextName();//把用户账号放到输入账号的输入框中
//        }
//
//        //判断是否自动登录
//        if (autoLogin()) {
//            checkBox_login.setChecked(true);
//            login();//去登录就可以
//
//        }
//    }

    public void userLogin() {
        TextView et_account = (TextView) findViewById(R.id.et_account);

        TextView et_password = (TextView) findViewById(R.id.et_password);

        String username = et_account.getText().toString().trim();

        String password = et_password.getText().toString().trim();

        Api api = new Api(this, new OnNetRequest(this, true, "请稍等...") {
            @Override
            public void onSuccess(String msg) {

                UserBean userBean = JSONUtils.fromJson(msg, UserBean.class);
                Log.d("LoginActivity", "hello");

//                UIHelper.showShortToast(LoginActivity.this, "登录成功！");
                if (userBean != null) {
                    UIHelper.showShortToast(LoginActivity.this, "登录成功！");
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {

                }
//
            }

            @Override
            public void onFail() {

            }
        });
        api.userLogin(username, password);
    }


}
