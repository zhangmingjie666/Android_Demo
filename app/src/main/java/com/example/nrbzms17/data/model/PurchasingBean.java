package com.example.nrbzms17.data.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author MJ@ZHANG
 * @package: com.example.nrbzms17.data.model
 * @filename PurchasingBean
 * @date on 2018/8/15 17:30
 * @descibe TODO
 * @email zhangmingjie@huansi.net
 */
public class PurchasingBean implements Serializable {
    public List<Data> result;
    public class Data implements Serializable {
        public String id;
        public String billdate;
        public String customcode;
        public String company;
        public String material;
        public String status;
        public String color;
        public String employee;
        public String quantity;
        public String sup_material_code;
        public String deliverydate;
        public String factory;

        public String spec;
        public String rquantity;
        public String iquantity;
        public String comp;
        public String remark;
        public String next_craft;
    }
}
