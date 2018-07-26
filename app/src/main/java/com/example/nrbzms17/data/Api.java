package com.example.nrbzms17.data;

import android.content.Context;
import android.util.Log;

import com.example.nrbzms17.Utils.Client;
import com.example.nrbzms17.Utils.OnRequestCallback;
import com.example.nrbzms17.Utils.UIHelper;
import com.example.nrbzms17.data.listener.OnNetRequest;
import com.squareup.okhttp.Request;

import org.json.JSONObject;

import java.net.ConnectException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class Api {
    private Context mContext;
    private OnRequestCallback callback;
    private Client mClient;

    public Api(Context aContext, final OnNetRequest listener) {
        this.mContext = aContext;
        this.mClient = Client.getInstance(SharedPreference.getHost());
        callback = new OnRequestCallback(mContext, listener.isShowLoading(), listener.getLoadingText()) {
            @Override
            public void onResponse(String response) {
                super.onResponse(response);
                Log.i("Response", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optBoolean("status", false)) {
                        listener.onSuccess(response);
                    } else {
                        String errorMessage = jsonObject.optString("result");
                        Log.e("Response", errorMessage);
                        UIHelper.showLongToast(mContext, errorMessage);
                        listener.onFail();
                    }
                } catch (Exception e) {
                    UIHelper.showLongToast(mContext, "Json解析错误");
                    listener.onFail();
                }
            }

            @Override
            public void onError(Request request, Exception e) {
                super.onError(request, e);
                if (e instanceof TimeoutException) {
                    UIHelper.showLongToast(mContext, "请求超时");
                    listener.onFail();
                } else if (e instanceof ConnectException) {
                    UIHelper.showLongToast(mContext, "请求出错，检查网络是否正常");
                    listener.onFail();
                } else {
                    UIHelper.showLongToast(mContext, "您输入的不正确，请确认后重新输入");
                    listener.onFail();
                }
            }
        };
    }

    /**
     * 获取订单列表
     */
    public final static String GET_INDENTILIST = "Indent/indentList";

    public void getIndentInfoList(String status, String customcode) {
        Map<String, String> params = new HashMap<>();
        params.put("status", status);
        params.put("code", customcode);

        mClient.get(GET_INDENTILIST, params, callback);
    }

    /**
     * 获取明细列表
     */
    public final static String GET_INDENTITEMILIST = "Indent/indentItemList";

    public void getOrderList(String id) {
        Map<String, String> params = new HashMap<>();
        params.put("id", id);
        mClient.get(GET_INDENTITEMILIST, params, callback);
    }

    /**
     * 审核订单
     */
    public final static String POST_INDENTAUDIT = "Indent/indentAudit";

    public void IndentAudit(String id) {
        Map<String, String> params = new HashMap<>();
        params.put("id", id);
        mClient.post(POST_INDENTAUDIT, params, callback);
    }


    /**
     * 反审订单
     */
    public final static String POST_INDENTNOAUDIT = "Indent/indentNoaudit";

    public void IndentNoaudit(String id) {
        Map<String, String> params = new HashMap<>();
        params.put("id", id);
        mClient.post(POST_INDENTNOAUDIT, params, callback);
    }

    /**
     * 用户登录
     */
    public final static String POST_LOGINAUTHENTICATION = "Login/loginAuthentication";

    public void userLogin(String username, String password) {
        Map<String, String> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
        mClient.post(POST_LOGINAUTHENTICATION, params, callback);
    }

    /**
     * 采购列表
     */
    public final static String GET_PURCHASE = "purchase/list";

    public void getPurchaseList(String status, String customcode,String date,String deliverdate) {
        Map<String, String> params = new HashMap<>();
        params.put("status", status);
        params.put("code", customcode);
        params.put("start_time", date);
        params.put("end_time", deliverdate);
        mClient.get(GET_PURCHASE, params, callback);
    }


    /**
     * 采购明细
     */
    public final static String GET_PURCHASEDETAIL= "purchase/detail";

    public void getPurchasedetail( String id) {
        Map<String, String> params = new HashMap<>();
        params.put("id", id);
        mClient.get(GET_PURCHASEDETAIL, params, callback);
    }
}
