package com.example.nrbzms17.data.model;

import java.io.Serializable;

/**
 * @author MJ@ZHANG
 * @package: com.example.nrbzms17.data.model
 * @filename DateBean
 * @date on 2018/8/10 17:17
 * @descibe TODO
 * @email zhangmingjie@huansi.net
 */
public class DateBean implements Serializable{
    public String id;
    public String name;
    public DateBean(String id,String name){
        this.id = id;
        this.name =name;
    }
}
