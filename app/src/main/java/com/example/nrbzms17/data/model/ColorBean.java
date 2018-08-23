package com.example.nrbzms17.data.model;

import java.io.Serializable;

/**
 * @author MJ@ZHANG
 * @package: com.example.nrbzms17.data.model
 * @filename ColorBean
 * @date on 2018/8/23 14:14
 * @descibe TODO
 * @email zhangmingjie@huansi.net
 */
public class ColorBean implements Serializable {
    public String id;
    public String name;
    public ColorBean(String id,String name){
        this.id = id;
        this.name = name;
    }
}
