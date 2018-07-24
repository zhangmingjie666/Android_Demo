package com.example.nrbzms17.data.model;

import java.io.Serializable;
import java.util.List;

public class OrderBean implements Serializable{
    public List<Data> result;
    public class Data implements Serializable{

    public String id;
    public String billdate;
    public String customcode;
    public String company;
    public String material;
    public String status;
    public String color;
    public String employee;
    public String ratio;
    public String yquantity;
    public String contcode;
    public String quantity;

    public String material_name;
    public String color_name;
    public String craft_name;
    public String depot_name;
    public String volume;
    public String lot;
    public String reel;
    public String grade;
    }
}
