package com.example.nrbzms17.data.model;

import java.io.Serializable;

/**
 * @author MJ@ZHANG
 * @package: com.example.nrbzms17.data.model
 * @filename AllocationBean
 * @date on 2018/8/13 10:48
 * @descibe TODO
 * @email zhangmingjie@huansi.net
 */
public class AllocationBean implements Serializable {

    public AllocationBean(String id, String barcode) {

        this.id = id;
        this.barcode = barcode;
    }

    public String id;
    public String material;
    public String color;
    public String craft;
    public String depot;
    public String volume;
    public String quantity;
    public String lot;
    public String reel;
    public String grade;
    public String barcode;
    public String customcode;
}
