package com.example.nrbzms17.data.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author MJ@ZHANG
 * @package: com.example.nrbzms17.data.model
 * @filename DyingBean
 * @date on 2018/8/22 16:16
 * @descibe TODO
 * @email zhangmingjie@huansi.net
 */
public class DyingBean implements Serializable {
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
        public String factory;
        public String code;
        public String remark;
        public String quantity_str;//点坯数
        public String receive_str; //收货数
        public String instore_str; //实收数
        public String taskcode;
        public String tellas;
        public String next_craft;
        public String label;
        public String craft;
        public String lot;
        public String depot;
        public Integer straight_;
        public String packaging;
        public String material_name;
        public String color_name;

    }
}
