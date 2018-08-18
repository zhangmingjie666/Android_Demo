package com.example.nrbzms17.data.model;

import java.io.Serializable;

/**
 * @author MJ@ZHANG
 * @package: com.example.nrbzms17.data.model
 * @filename FactoryBean
 * @date on 2018/8/18 16:39
 * @descibe TODO
 * @email zhangmingjie@huansi.net
 */
public class FactoryBean implements Serializable {
    public String id;
    public String name;
    public FactoryBean(String id,String name){
        this.id = id;
        this.name =name;
    }
}
