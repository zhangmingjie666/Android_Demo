package com.example.nrbzms17.data.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author MJ@ZHANG
 * @package: com.example.nrbzms17.data.model
 * @filename SaleDetailBean
 * @date on 2018/8/14 16:58
 * @descibe TODO
 * @email zhangmingjie@huansi.net
 */
public class SaleDetailBean implements Serializable {
    public List<Data> result;
    public class Data implements Serializable {
        public String id;
        public String billdate;
        public String sale_express_code;
        public String customcode;
        public String company;
        public String material;
        public String status;
        public String color;
        public String employee;
        public String quantity;
        public String address;
        public String sup_material_code;
        public String remark;
        public String width;
        public String weight;
        public String spec;
        public String style;
        public String density;
        public String craft;
        public String linkman;
        public String trade;



    }
}
