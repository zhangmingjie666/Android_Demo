package com.example.nrbzms17.data.model;

import java.io.Serializable;
import java.util.List;

public class UserBean implements Serializable{

    public Data result;

    public class Data implements Serializable {
        public String id;
        public String username;
        public String password;
    }
}
