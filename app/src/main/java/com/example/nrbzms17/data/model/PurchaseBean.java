package com.example.nrbzms17.data.model;

import java.io.Serializable;
import java.util.List;

public class PurchaseBean implements Serializable {
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
        public String yquantity;
        public String contcode;
    }
}
