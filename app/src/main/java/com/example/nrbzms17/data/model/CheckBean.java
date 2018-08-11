package com.example.nrbzms17.data.model;

import java.io.Serializable;

/**
 * @author MJ@ZHANG
 * @package: com.example.nrbzms17.data.model
 * @filename CheckBean
 * @date on 2018/8/11 14:09
 * @descibe TODO
 * @email zhangmingjie@huansi.net
 */
public class CheckBean implements Serializable {

    public CheckBean(String id, String barcode) {

        this.id = id;
        this.barcode = barcode;
    }

    public String id;
    public String material_name;
    public String color_name;
    public String craft_name;
    public String depot_name;
    public String volume;
    public String quantity;
    public String lot;
    public String reel;
    public String grade;
    public String barcode;
    public String quantity_string;


}
