package com.example.nrbzms17.data.model;

public class SpinnerItem {

    private String Id ;
    private String Value ;

    public SpinnerItem () {
        Id = "";
        Value = "";
    }

    public SpinnerItem (String _ID, String _Value) {
        Id = _ID;
        Value = _Value;
    }

    @Override
    public String toString() {           //为什么要重写toString()呢？因为适配器在显示数据的时候，如果传入适配器的对象不是字符串的情况下，直接就使用对象.toString()
        // TODO Auto-generated method stub
        return Value;
    }

    public String GetID() {
        return Id;
    }

    public String GetValue() {
        return Value;
    }
}

