package com.example.nrbzms17.data.model;

import java.io.Serializable;

/**
 * @author MJ@ZHANG
 * @package: com.example.nrbzms17.data.model
 * @filename DepotBean
 * @date on 2018/8/11 10:34
 * @descibe TODO
 * @email zhangmingjie@huansi.net
 */
public class DepotBean implements Serializable {
    public String id;
    public String name;

    public DepotBean(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
