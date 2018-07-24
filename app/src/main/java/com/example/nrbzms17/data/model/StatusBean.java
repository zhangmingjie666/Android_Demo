package com.example.nrbzms17.data.model;

import java.io.Serializable;

public class StatusBean implements Serializable {

        public String id;
        public String status;
        public StatusBean(String id,String status){
                this.id = id;
                this.status =status;
        }

}
