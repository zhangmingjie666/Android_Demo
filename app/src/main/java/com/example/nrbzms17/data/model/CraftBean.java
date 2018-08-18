package com.example.nrbzms17.data.model;

import java.io.Serializable;

/**
 * @author MJ@ZHANG
 * @package: com.example.nrbzms17.data.model
 * @filename CraftBean
 * @date on 2018/8/18 10:45
 * @descibe TODO
 * @email zhangmingjie@huansi.net
 */
public class CraftBean implements Serializable {
    public String id;
    public String name;
    public CraftBean(String id,String name){
        this.id = id;
        this.name =name;
    }
}
