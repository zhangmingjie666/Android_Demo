package com.example.nrbzms17.data.model;

import java.io.Serializable;

/**
 * @author MJ@ZHANG
 * @package: com.example.nrbzms17.data.model
 * @filename LabelBean
 * @date on 2018/8/23 11:52
 * @descibe TODO
 * @email zhangmingjie@huansi.net
 */
public class LabelBean implements Serializable {
    public String id;
    public String name;
    public LabelBean(String id,String name){
        this.id = id;
        this.name =name;
    }
}
