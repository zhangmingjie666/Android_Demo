package com.example.nrbzms17.data.model;

import java.io.Serializable;

/**
 * @author MJ@ZHANG
 * @package: com.example.nrbzms17.data.model
 * @filename MenuBean
 * @date on 2018/8/14 16:25
 * @descibe TODO
 * @email zhangmingjie@huansi.net
 */
public class MenuBean implements Serializable{

    public String id;
    public String icon;
    public String name;
    public MenuBean(String id,String icon,String name){
        this.id = id;
        this.icon =icon;
        this.name =name;
    }
}
