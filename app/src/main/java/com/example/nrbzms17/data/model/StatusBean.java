package com.example.nrbzms17.data.model;

import java.io.Serializable;

public class StatusBean implements Serializable {

//        public Data result;
//        public class Data implements Serializable {
//                public String id;
//                public String status;
//        }

        public String id;
        public String name;
        public StatusBean(String id,String name){
                this.id = id;
                this.name =name;
        }

}
