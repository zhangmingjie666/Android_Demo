package com.example.nrbzms17.data.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author MJ@ZHANG
 * @package: com.example.nrbzms17.data.model
 * @filename SaleBean
 * @date on 2018/8/7 17:21
 * @descibe TODO
 * @email zhangmingjie@huansi.net
 */
public class SaleBean implements Serializable {
    public List<Data> result;

    public class Data implements Serializable {
        public String id;
        public String billdate;
        public String customcode;
        public String style;
        public String volume;
        public String quantity;
        public String custom_code;
        public String customer_name;
        public String material;
        public String color;
        public String craft;
        public String quantity_string;
        public String status;

    }
}
