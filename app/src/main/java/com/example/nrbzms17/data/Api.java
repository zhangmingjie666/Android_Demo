package com.example.nrbzms17.data;

import android.content.Context;
import android.util.Log;

//import com.nrbzms17.Utils.Client;
//import com.nrbzms17.Utils.OnRequestCallback;
//import com.nrbzms17.Utils.UIHelper;
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
        this.mClient = Client.getInstance(SharedPreference.getUrl());
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
     * APP升级信息
     */
    public void upgrade() {
        Map<String, String> params = new HashMap<>();
        mClient.get("upgrade/info", params, callback);
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

    public void getPurchaseList(String name, String customcode,String starttime,String endtime) {
        Map<String, String> params = new HashMap<>();
        params.put("status", name);
        params.put("code", customcode);
        params.put("start_time", starttime);
        params.put("end_time", endtime);
        mClient.get(GET_PURCHASE, params, callback);
    }
//    public void getPurchaseList(String name, String customcode) {
//        Map<String, String> params = new HashMap<>();
//        params.put("status", name);
//        params.put("code", customcode);
//        mClient.get(GET_PURCHASE, params, callback);
//    }



    /**
     * 采购明细
     */
    public final static String GET_PURCHASEDETAIL= "purchase/detail";

    public void getPurchasedetail( String id) {
        Map<String, String> params = new HashMap<>();
        params.put("id", id);
        mClient.get(GET_PURCHASEDETAIL, params, callback);
    }

    /**
     * 获取状态信息
     */
    public final static String GET_STATUS = "status/list";
    public void getStatusInfo(){
        mClient.get(GET_STATUS,null,callback);
    }

    /**
     * 审核采购
     */
    public final static String POST_PURCHASEAUDIT = "Purchase/purchaseAudit";

    public void PurchaseAudit(String id) {
        Map<String, String> params = new HashMap<>();
        params.put("id", id);
        mClient.post(POST_PURCHASEAUDIT, params, callback);
    }


    /**
     * 反审采购
     */
    public final static String POST_PURCHASENOAUDIT = "Purchase/purchaseNoaudit";

    public void PurchaseNoaudit(String id) {
        Map<String, String> params = new HashMap<>();
        params.put("id", id);
        mClient.post(POST_PURCHASENOAUDIT, params, callback);
    }

    /**
     * 检验列表
     */
    public final static String GET_INSPECTLIST = "inspect/list";
    public void getInspectList(String status,String customcode) {
        Map<String, String> params = new HashMap<>();
        params.put("status", status);
        params.put("customcode", customcode);
        mClient.get(GET_INSPECTLIST, params, callback);
    }

    /**
     * 获取状态信息
     */
    public final static String GET_COMMENSTATUS = "status/commonList";
    public void getCommenStatus(){
        mClient.get(GET_COMMENSTATUS,null,callback);
    }

    /**
     * 检验明细
     */
    public final static String GET_INSPECTDETAIL= "inspect/detail";

    public void getInspectDetail( String id) {
        Map<String, String> params = new HashMap<>();
        params.put("id", id);
        mClient.get(GET_INSPECTDETAIL, params, callback);
    }

    /**
     * 审核检验
     */
    public final static String POST_INSPECTAUDIT = "inspect/inspectAudit";

    public void InspectAudit(String id) {
        Map<String, String> params = new HashMap<>();
        params.put("id", id);
        mClient.post(POST_INSPECTAUDIT, params, callback);
    }


    /**
     * 反审检验
     */
    public final static String POST_INSPECTNOAUDIT = "inspect/inspectNoaudit";

    public void InspectNoaudit(String id) {
        Map<String, String> params = new HashMap<>();
        params.put("id", id);
        mClient.post(POST_INSPECTNOAUDIT, params, callback);
    }

}
