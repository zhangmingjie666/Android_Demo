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

    public void getIndentInfoList(String status, String customcode, String date) {
        Map<String, String> params = new HashMap<>();
        params.put("status", status);
        params.put("code", customcode);
        params.put("select_date", date);
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

    public void getPurchaseList(String name, String customcode, String date) {
        Map<String, String> params = new HashMap<>();
        params.put("status", name);
        params.put("code", customcode);
        params.put("select_date", date);
        mClient.get(GET_PURCHASE, params, callback);
    }


    /**
     * 采购明细
     */
    public final static String GET_PURCHASEDETAIL = "purchase/detail";

    public void getPurchasedetail(String id) {
        Map<String, String> params = new HashMap<>();
        params.put("id", id);
        mClient.get(GET_PURCHASEDETAIL, params, callback);
    }

    /**
     * 获取状态信息
     */
    public final static String GET_STATUS = "status/list";

    public void getStatusInfo() {
        mClient.get(GET_STATUS, null, callback);
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

    public void getInspectList(String status, String customcode,String date) {
        Map<String, String> params = new HashMap<>();
        params.put("status", status);
        params.put("customcode", customcode);
        params.put("select_date", date);
        mClient.get(GET_INSPECTLIST, params, callback);
    }

    /**
     * 获取状态信息
     */
    public final static String GET_COMMENSTATUS = "status/commonList";

    public void getCommenStatus() {
        mClient.get(GET_COMMENSTATUS, null, callback);
    }

    /**
     * 获取日期信息
     */
    public final static String GET_DATE = "date/list";

    public void getDateInfo() {
        mClient.get(GET_DATE, null, callback);
    }

    /**
     * 获取仓库信息
     */
    public final static String GET_DEPOT = "depot/list";

    public void getDepotinfo() {
        mClient.get(GET_DEPOT, null, callback);
    }

    /**
     * 检验明细
     */
    public final static String GET_INSPECTDETAIL = "inspect/detail";

    public void getInspectDetail(String id) {
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

    /**
     * 获取员工信息
     */
    public final static String GET_EMPLOYEE = "employee/list";

    public void getEmployeeInfo() {
        mClient.get(GET_EMPLOYEE, null, callback);
    }


    /**
     * 获取销售信息
     */
    public final static String GET_SALELIST = "salesend/list";

    public void getSaleInfoList(String status,String billcode,String date) {
        Map<String, String> params = new HashMap<>();
        params.put("status", status);
        params.put("billcode", billcode);
        params.put("select_date", date);
        mClient.get(GET_SALELIST, params, callback);
    }

    /**
     * 获取盘点信息
     */
    public final static String GET_CHECKLIST= "check/list";

    public void getCheckList(String barcode) {
        Map<String, String> params = new HashMap<>();
        params.put("barcode", barcode);
        mClient.get(GET_CHECKLIST, params, callback);
    }

    /**
     * 仓库盘点
     */
    public final static String POST_ADD_CHECK= "check/add";

    public void addCheck(String employee_,String barcode,String volume,String quantity,String remark) {
        Map<String, String> params = new HashMap<>();
        params.put("barcode",barcode);
        params.put("volume",volume);
        params.put("quantity",quantity);
        params.put("remark",remark);
        params.put("employee_",employee_);
        mClient.post(POST_ADD_CHECK, params, callback);
    }


    /**
     * 获取调拨信息
     */
    public final static String GET_ALLOCATIONLIST= "allocate/list";

    public void getAlloList(String barcode) {
        Map<String, String> params = new HashMap<>();
        params.put("barcode", barcode);
        mClient.get(GET_ALLOCATIONLIST, params, callback);
    }


    /**
     * 仓库调拨
     */
    public final static String POST_ADD_ALLOCATION= "allocate/edit";

    public void addAllocation(String id,String depot_) {
        Map<String, String> params = new HashMap<>();
        params.put("id",id);
        params.put("depot_",depot_);
        mClient.post(POST_ADD_ALLOCATION, params, callback);
    }


    /**
     * 获取销售明细
     */
    public final static String GET_SALEDERAILLIST = "salesend/item";

    public void getSaleDetail(String id) {
        Map<String, String> params = new HashMap<>();
        params.put("id", id);
        mClient.get(GET_SALEDERAILLIST, params, callback);
    }

    /**
     * 审核发货
     */
    public final static String POST_SALEAUDIT = "salesend/saleAudit";

    public void SaleAudit(String id) {
        Map<String, String> params = new HashMap<>();
        params.put("id", id);
        mClient.post(POST_SALEAUDIT, params, callback);
    }


    /**
     * 反审发货
     */
    public final static String POST_SALENOAUDIT = "salesend/saleNoaudit";

    public void SaleNoaudit(String id) {
        Map<String, String> params = new HashMap<>();
        params.put("id", id);
        mClient.post(POST_SALENOAUDIT, params, callback);
    }

    /**
     * 采购收货列表
     */
    public final static String GET_PURCHASEING = "purchase/porder";

    public void getPurchasing() {
        Map<String, String> params = new HashMap<>();
        mClient.get(GET_PURCHASEING, params, callback);
    }

}
