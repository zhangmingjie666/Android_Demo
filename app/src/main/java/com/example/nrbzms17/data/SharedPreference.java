package com.example.nrbzms17.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.example.nrbzms17.AppConstant;
import com.example.nrbzms17.MyApplication;
/**
 * Created by Joe on 2016/6/9.
 * Email-joe_zong@163.com
 */
public class SharedPreference {
    public static final SharedPreferences mSharedPreference = MyApplication.getInstance().getSharedPreferences("OutboundPreferences", Context.MODE_PRIVATE);
    public static final String DOWNLOAD_TASK_ID = ".download_task_id";
    public static final String Host = ".host";
    public static final String Project = ".project";

    public static String getUrl() {
        String host = mSharedPreference.getString(Host, AppConstant.Host);
        String project = mSharedPreference.getString(Project, AppConstant.Project);
        String http = TextUtils.isEmpty(AppConstant.Http) ? "http" : AppConstant.Http;
        String api = TextUtils.isEmpty(AppConstant.Api) ? "restful.php" : AppConstant.Api;
        String url = http + "://" + host + "/" + project + "/" + api + "/";
        return url;
    }

    public static String getProject() {
        return mSharedPreference.getString(Project, AppConstant.Project);
    }

    public static void setProject(String project) {
        mSharedPreference.edit().putString(Project, project).apply();
    }

    public static final String EmployeeId = ".employee_id";

    public static String getEmployeeId(){
        return mSharedPreference.getString(EmployeeId,"");
    }

    public static void setEmployeeId(String employeeId){
        mSharedPreference.edit().putString(EmployeeId,employeeId).apply();
    }

    public static final String DepartmentId = ".department_id";
    public static String getDepartmentId(){
        return mSharedPreference.getString(DepartmentId,"");
    }

    public static void setDepartmentId(String departmentId){
        mSharedPreference.edit().putString(DepartmentId, departmentId).apply();
    }


    public static final String ClassesId = ".classes_id";
    public static String getClassesId(){
        return mSharedPreference.getString(ClassesId,"");
    }

    public static void setClassesId(String classesId){
        mSharedPreference.edit().putString(ClassesId,classesId).apply();
    }


    public static String getHost() {
        String host = mSharedPreference.getString(Host, AppConstant.Host);
        return TextUtils.isEmpty(host)?AppConstant.Host:host;
    }

    public static void setHost(String host){
        mSharedPreference.edit().putString(Host, host).apply();
    }
    /**
     * 保存的当前下载任务id
     */
    public static void setDownloadTaskId(long downloadTaskId) {
        mSharedPreference.edit().putLong(DOWNLOAD_TASK_ID, downloadTaskId).apply();
    }

    /**
     * 获取保存的当前下载任务id
     */
    public static long getDownloadTaskId() {
        return mSharedPreference.getLong(DOWNLOAD_TASK_ID, -12306L);
    }

    /**
     * 移除保存的下载任务id
     */
    public static void deleteDownloadTaskId() {
        mSharedPreference.edit().remove(DOWNLOAD_TASK_ID);
    }

}
