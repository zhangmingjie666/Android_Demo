package com.example.nrbzms17.data.model;

import java.io.Serializable;
import java.util.List;

public class PurchaseDetailBean implements Serializable {
    public List<Data> result;
    public class Data implements Serializable {
        public String id;
        public String billdate;
        public String deliverydate;
        public String customcode;
        public String company;
        public String material;
        public String status;
        public String color;
        public String employee;
        public String quantity;
        public String contcode;
        public String sup_material_code;
        public String remark;
        public String width;
        public String weight;
        public String spec;
        public String comp;
        public String density;
        public String craft;
        public String linkman;
        public String trade;



    }
}
