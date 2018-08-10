package com.example.nrbzms17.data.model;

import java.io.Serializable;

/**
 * @author MJ@ZHANG
 * @package: com.example.nrbzms17.data.model
 * @filename EmployeeBean
 * @date on 2018/8/7 9:50
 * @descibe TODO
 * @email zhangmingjie@huansi.net
 */
public class EmployeeBean implements Serializable{
    public String id;
    public String name;
    public EmployeeBean(String id,String name){
        this.id = id;
        this.name = name ;
    }
}
