package com.example.nrbzms17.data.model;

import java.io.Serializable;
import java.util.List;

public class OrderListBean implements Serializable {
    public List<Data> result;

    public class Data implements Serializable {
        public String id;
        public String code;
        public String color_name;
        public String craft_name;
        public String price;
        public String amount;
        public String quantity;
    }
}
